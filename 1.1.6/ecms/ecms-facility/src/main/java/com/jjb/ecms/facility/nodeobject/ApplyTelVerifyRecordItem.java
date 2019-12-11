package com.jjb.ecms.facility.nodeobject;

import java.io.Serializable;

/**
 * @Description: 初审调查-电话基本信息确认信息
 * @author JYData-R&D-Big Star
 * @date 2016年11月23日 上午10:41:01
 * @version V1.0
 */
public class ApplyTelVerifyRecordItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6705982460693177925L;
	private String askType;
	private String askContent;
	private String answer;
	private String result;

	public String getAskType() {
		return askType;
	}

	public void setAskType(String askType) {
		this.askType = askType;
	}

	public String getAskContent() {
		return askContent;
	}

	public void setAskContent(String askContent) {
		this.askContent = askContent;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
