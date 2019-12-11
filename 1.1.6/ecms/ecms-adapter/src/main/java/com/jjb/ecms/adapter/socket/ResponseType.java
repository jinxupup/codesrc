package com.jjb.ecms.adapter.socket;

/**
 * @Description: 返回报文拼接xml使用
 * @author JYData-R&D-Big H.N
 * @date 2016年3月31日 下午2:37:32
 * @version V1.0
 */
public class ResponseType {

	String STATUS;

	String CODE;

	String DESC;

	/**
	 * @return the sTATUS
	 */
	public String getSTATUS() {
		return STATUS;
	}

	/**
	 * @param sTATUS
	 *            the sTATUS to set
	 */
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	/**
	 * @return the cODE
	 */
	public String getCODE() {
		return CODE;
	}

	/**
	 * @param cODE
	 *            the cODE to set
	 */
	public void setCODE(String cODE) {
		CODE = cODE;
	}

	/**
	 * @return the dESC
	 */
	public String getDESC() {
		return DESC;
	}

	/**
	 * @param dESC
	 *            the dESC to set
	 */
	public void setDESC(String dESC) {
		DESC = dESC;
	}

}
