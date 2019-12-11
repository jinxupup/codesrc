package com.jjb.unicorn.facility.util;

public final class PropertyConversionUtil {
	
	public static String colNameToPropName (String colName) {
		StringBuilder propName = new StringBuilder ("");
		colName = colName.toUpperCase();
		
		int len = colName.length();
		
		char c;
		for (int i=0;i<len;i++) {
			c = colName.charAt(i);
			if (c =='_') {
				try {
					c = colName.charAt (++i);
				} catch (Exception e) {//考虑以-结尾
					;
				}
				propName.append((char)c);
				
			} else {
				propName.append((char)(c+32));
			}
			
		}
		
		return propName.toString();
	}
	
	public static String propNameToColName (String propName) {
		StringBuilder colName = new StringBuilder ("");
		int len = propName.length();
		
		char c;
		for (int i=0;i<len;i++) {
			c = propName.charAt(i);
			if (c>=97 && c <=122) {
				colName.append((char)(c-32));
			} else {
				colName.append("_");
				colName.append((char)c);
			}
		}
		
		return colName.toString();
	}
	
	public static String classNameToTableNm (String classNm) {
		
		StringBuilder tableNm = new StringBuilder ("");
		int len = classNm.length();
		
		char c;
		for (int i=0;i<len;i++) {
			c = classNm.charAt(i);
			if (i ==0) {
				tableNm.append((char)c);
				continue;
			}
			if (c>=97 && c <=122) {
				tableNm.append((char)(c-32));
			} else {
				tableNm.append("_");
				tableNm.append((char)c);
			}
		}
		
		return tableNm.toString();
	}
	
	public static String tableNameToClassNm (String tableName) {
		StringBuilder className = new StringBuilder ("");
		tableName = tableName.toUpperCase();
		
		int len = tableName.length();
		
		char c;
		for (int i=0;i<len;i++) {
			c = tableName.charAt(i);
			if (c =='_') {
				try {
					c = tableName.charAt (++i);
				} catch (Exception e) {//考虑以-结尾
					;
				}
				className.append((char)c);
				
			} else {
				className.append((char)(c+32));
			}
			
		}
		
		return className.toString();
	}
	
	private PropertyConversionUtil () {
		//unused
	}

}
