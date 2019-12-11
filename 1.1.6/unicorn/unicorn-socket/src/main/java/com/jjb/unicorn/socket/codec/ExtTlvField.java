package com.jjb.unicorn.socket.codec;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: TLV格式子域枚举定义</p>
 * <p>Company: 上海JYDATA服务有限公司</p>
 * @ClassName: ExtTlvField
 * @author LI.H
 * @date 2015年12月10日 下午3:09:23
 * @version 1.0
 */
public enum ExtTlvField {

	V55 {
		
		private Map<String, String> datasetIdMap;
		
		private Map<String, String> tagsMap;
		
		private Map<String, String> partTag;
		
		@Override
		protected void setExtTlvField() {
			datasetIdMap = new HashMap<String, String>();
			datasetIdMap.put("01", "01");
			tagsMap = new HashMap<String, String>();
			for (String string : Arrays.asList("71", "72", "82", "91", "95", "9A", "9C", "C0", "5F2A", "9F02", "9F03", "9F10", "9F1A", "9F26", "9F33", "9F36", "9F37", "9F73", "9F5B", "9F6E", "9F7C")) {
				tagsMap.put(string, string);
			}
			partTag = new HashMap<String, String>();
			for (String name : Arrays.asList("1F", "3F", "5F", "7F", "9F", "BF", "DF", "FF")) {
				partTag.put(name, name);
			}
		}
		
		@Override
		public boolean whetherContainTag(String string) {
			return tagsMap.containsKey(string.toUpperCase());
		}

		@Override
		public boolean nextBytePartTag(String string) {
			return partTag.containsKey(string.toUpperCase());
		}

		@Override
		public boolean whetherDatasetId(String string) {
			return datasetIdMap.containsKey(string);
		}

		
	},
	
	V125 {

		private Map<String, String> datasetIdMap;

		private Map<String, String> tagsMap;

		private Map<String, String> partTag;

		@Override
		protected void setExtTlvField() {
			datasetIdMap = new HashMap<String, String>();
			datasetIdMap.put("03", "03");
			tagsMap = new HashMap<String, String>();
			for (String string : Arrays.asList("03")) {
				tagsMap.put(string, string);
			}
			partTag = new HashMap<String, String>();
			for (String name : Arrays.asList("1F", "3F", "5F", "7F", "9F", "BF", "DF", "FF")) {
				partTag.put(name, name);
			}
		}

		@Override
		public boolean whetherContainTag(String string) {
			return tagsMap.containsKey(string.toUpperCase());
		}

		@Override
		public boolean nextBytePartTag(String string) {
			return partTag.containsKey(string.toUpperCase());
		}

		@Override
		public boolean whetherDatasetId(String string) {
			return datasetIdMap.containsKey(string);
		}


	},
	
	V56 {

		private Map<String, String> datasetIdMap;

		private Map<String, String> tagsMap;

		private Map<String, String> partTag;

		@Override
		protected void setExtTlvField() {
			datasetIdMap = new HashMap<String, String>();
			datasetIdMap.put("01", "01");
			tagsMap = new HashMap<String, String>();
			for (String string : Arrays.asList("01")) {
				tagsMap.put(string, string);
			}
			partTag = new HashMap<String, String>();
			
		}

		@Override
		public boolean whetherContainTag(String string) {
			return tagsMap.containsKey(string.toUpperCase());
		}

		@Override
		public boolean nextBytePartTag(String string) {
			return partTag.containsKey(string.toUpperCase());
		}

		@Override
		public boolean whetherDatasetId(String string) {
			return datasetIdMap.containsKey(string);
		}


	};
	
	private ExtTlvField() {
		setExtTlvField();
	}
	
	/**
	 * <p>目的：初始化枚举中所有域</p>
	 * <p>承诺：</p>
	 */
	protected abstract void setExtTlvField();
	
	/**
	 * <p>目的：判断该子域中是否含有此格式datasetId</p>
	 * <p>承诺：存在则响应true</p>
	 * @param string
	 * @return
	 */
	public abstract boolean whetherDatasetId(String datasetId);
	
	/**
	 * <p>目的：判断该子域中TLV格式是否有指定Tag</p>
	 * <p>承诺：存在响应true</p>
	 * @param string
	 * @return
	 */
	public abstract boolean whetherContainTag(String tag);
	
	/**
	 * <p>目的：判断下一个byte是否依然属于此TAG</p>
	 * <p>承诺：存在则响应true</p>
	 * @param string
	 * @return
	 */
	public abstract boolean nextBytePartTag(String string);
	
}
