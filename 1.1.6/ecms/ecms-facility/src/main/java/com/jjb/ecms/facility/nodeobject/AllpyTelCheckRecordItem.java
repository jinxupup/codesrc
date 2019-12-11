package com.jjb.ecms.facility.nodeobject;

import java.io.Serializable;

/**
 * 电话调查核身问题及必问问题
 * 
 * @author BIG.LT.M
 * @date 创建时间：2017年5月16日 下午5:11:12
 * @version 1.0
 */
public class AllpyTelCheckRecordItem implements Serializable {

	private static final long serialVersionUID = -3604321259146447904L;
	private String askContent;
	private String answer;
	private String result;
	private String memo;

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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
