package com.jjb.unicorn.facility.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerializeUtils {

	private static Logger logger = LoggerFactory.getLogger(SerializeUtils.class);
	
	/**
	 * 序列化实体类数据
	 * @param obj
	 * @return
	 */
	public static byte[] serialize(Object obj) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream bos = null;
		try {
			if(obj!=null) {
				bos = new ByteArrayOutputStream();
				oos = new  ObjectOutputStream(bos);
				oos.writeObject(obj);
				byte[] bytes = bos.toByteArray();
				return bytes;
			}
		} catch (Exception e) {
			logger.error("序列化实体数据["+obj+"]失败", e);
		}
		return null;
	}
	
	/**
	 * 序列化实体类数据
	 * @param obj
	 * @return
	 */
	public static Object unSerialize(byte[] bytes) {
		ByteArrayInputStream bis = null;
		try {
			if(bytes!=null) {
				bis = new ByteArrayInputStream(bytes);
				ObjectInputStream ois = new ObjectInputStream(bis);
				return ois.readObject();
			}
		} catch (Exception e) {
			logger.error("反序列化bytes数据["+bytes+"]失败", e);
		}
		return null;
	}
	
	
}
