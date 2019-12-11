package com.jjb.ecms.util;


/**
 * @Description: 证件类型转化
 * @author JYData-R&D-BigZ.Z
 * @date 
 * @version V1.0
 */
public class CertificatesCodeTransformationUtil {
    public static String transformation(String  idType){
    	String IDTYOE="";
    	if(idType.equals("A")){
    		//军官证  
    		IDTYOE="30120007,30120009,30120010,30120011,30120012";
    	}else if (idType.equals("C")) {
    		//警官证   
    		IDTYOE="30120013,30120015,30120016";
		}else if (idType.equals("G")) {
			//澳门身份证
			IDTYOE="";
		}else if (idType.equals("H")) {
			//港澳居民来往内地通行证
			IDTYOE="30120029";
		}else if (idType.equals("I")) {
			//身份证 
			IDTYOE="30120001,30120002";
		}else if (idType.equals("J")) {
			//台湾身份证
			IDTYOE="";
		}else if (idType.equals("L")) {
			//香港身份证
			IDTYOE="";
		}else if (idType.equals("O")) {
			//其他 
			IDTYOE="30120021,30120025,30120027,30120031,30120032,30120038,30120041,30120042";
		}else if (idType.equals("P")) {
			//护照 
			IDTYOE="30120022,30120023,30120024";
		}else if (idType.equals("R")) {
			//户口簿 
			IDTYOE="30120019";
		}else if (idType.equals("S")) {
			//士兵证
			IDTYOE="30120006";
		}else if (idType.equals("W")) {
			//台湾同胞来往内地通行证
			IDTYOE="30120030";
		}
 
    	return IDTYOE;
    }
}
