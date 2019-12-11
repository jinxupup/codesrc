package com.jjb.unicorn.facility.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jjb.unicorn.facility.cstruct.CChar;


/**
 * @Description: 根据注释配置处理C风格的结构体，在处理时的迭代需要优化以提高性能。
 * @author JYData-R&D-Big Star
 * @date 2016年11月23日 上午10:59:03
 * @version V1.0
 */
public class CStruct2<T> {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private List<Field> fields = new ArrayList<Field>();

	private String charset;

	private Class<T> clazz;

	HashMap fmap = new HashMap();

	/**
	 * 默认使用gbk编码
	 * 
	 * @param clazz
	 */
	public CStruct2(Class<T> clazz) {
		this(clazz, "gbk");
	}

	public CStruct2(Class<T> clazz, String charset) {
		this.clazz = clazz;
		Collections.addAll(this.fields, clazz.getFields()); // 缓存一下
		this.charset = charset;
		// 排序字段
		Collections.sort(fields, new Comparator<Field>() {
			@Override
			public int compare(Field o1, Field o2) {

				int order1 = getOrder(o1);
				int order2 = getOrder(o2);

				// 这段复制自 Integer.compareTo
				return (order1 < order2 ? -1 : (order1 == order2 ? 0 : 1));
			}

			private int getOrder(Field field) {
				CChar cc = field.getAnnotation(CChar.class);
				if (cc != null) {
					HashMap anmap = new HashMap();
					anmap.put("value", cc.value());
					anmap.put("autoTrim", cc.autoTrim());
					anmap.put("required", cc.required());
					anmap.put("order", cc.order());
					anmap.put("datePattern", cc.datePattern());
					anmap.put("formatPattern", cc.formatPattern());
					anmap.put("classtype", field.getType());
					fmap.put(field.getName(), anmap);
					return cc.order();
				}
				return 0;
			}
		});

	}

	public Object getFieldKey(String name) {
		HashMap anmap = (HashMap) fmap.get(name);
		return anmap.get("classtype");
	}

	/**
	 * 非空、格式、长度验证
	 * @param cellname
	 * @param cellvalue
	 * @return
	 */
	public String validatefield(String cellname, Object cellvalue) {
		Object anmapObj = fmap.get(cellname);
		if (anmapObj == null) {
			logger.warn(cellname + "[字段没有找到]");
			return "";
		}

		HashMap anmap = (HashMap) anmapObj;

		if (anmap.get("classtype") == String.class) {
			String cellvalueStr = cellvalue == null?null:(String)cellvalue;
			if(StringUtils.isBlank(cellvalueStr)){
				if((Boolean) anmap.get("required")){
					return cellname + "[必填]";
				}
			}else {
				if (cellvalueStr.length() > ((Integer) anmap.get("value"))) {
					return cellname + "[长度超过限制]";
				}
				if (StringUtils.isNotBlank(anmap.get("formatPattern")==null?null:anmap.get("formatPattern").toString())
						&& !cellvalueStr.matches((String)anmap.get("formatPattern"))) {
					return cellname + "["+cellvalueStr+"][格式不正确]";
				}
			}	
		} else {
			if ((Boolean) anmap.get("required") && StringUtils.isBlank(cellvalue==null?null:cellvalue.toString())) {
				return cellname + "[必填]";
			}
		}

		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "finally" })
	public HashMap<String, Serializable> putValue(String cellname,
			String value, HashMap<String, Serializable> cellvalue)
			throws IllegalArgumentException {
		try {
			for (Field field : fields) {
				if (field.getName().equals(cellname)) {
					Class<?> type = field.getType();

					CChar annoChar = field.getAnnotation(CChar.class);

					if (annoChar != null) {
						if (StringUtils.isBlank(value) && !annoChar.required()
								&& !type.equals(String.class)) {
							// 可以忽略的字段，填null
							cellvalue.put(cellname, null);
						} else if (type.equals(Date.class)) {
							SimpleDateFormat sdf = new SimpleDateFormat(
									annoChar.datePattern());
							sdf.setLenient(true);
							String datePattern = annoChar.datePattern();
							if (datePattern.length() < value.length()) {
								if (annoChar.leftPadding())
									value = StringUtils.right(value,
											datePattern.length());
								else
									value = StringUtils.left(value,
											datePattern.length());
							}
							cellvalue.put(cellname, sdf.parse(value));
						} else if (type.equals(String.class)) {
							if (annoChar.autoTrim()){
								if (StringUtils.isNotBlank(value)) {
									value = value.trim().split("\\|")[0];
								}	
							}
							// 字符串直接赋值
							cellvalue.put(cellname, value);
						} else if (type.equals(Integer.class)
								|| type.equals(int.class)) {
							cellvalue.put(cellname, Integer.parseInt(value));
						} else if (type.equals(Long.class)
								|| type.equals(long.class)) {
							cellvalue.put(cellname, Long.valueOf(value));
						} else if (type.equals(BigDecimal.class)) {
							cellvalue.put(cellname, new BigDecimal(value));
						} else if (type.isEnum()) {
							if (StringUtils.isNotBlank(value)) {
								value = value.split("\\|")[0];
								cellvalue.put(cellname, Enum.valueOf(
										(Class<? extends Enum>) type,
										value.trim()));
							}
						} else {
							throw new IllegalArgumentException("不支持的字段类型:"
									+ type);
						}
					} else
						assert false : field.getName() + " 必须指定字段类型注释";
				}
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		} finally {
			return cellvalue;
		}
	}

	public Map<String, Serializable> convertMapString2Enum(
			Map<String, Serializable> cellMap) {

		for (Field field : fields) {
			Class<?> type = field.getType();

			CChar annoChar = field.getAnnotation(CChar.class);

			if (annoChar != null) {
				if (type.isEnum()) {
					if (cellMap.get(field.getName()) != null) {
						cellMap.put(field.getName(), Enum.valueOf(
								(Class<? extends Enum>) type,
								cellMap.get(field.getName()).toString().trim()));
					}
				}
			}
		}
		return cellMap;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

}
