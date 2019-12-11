package com.jjb.ecms.facility.nodeobject;

import java.io.Serializable;

public class ScoreItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String scoreElement;//评分项
	private String scoreOutcomde;//评分项取值
	private String scoreValue;//评分项得分
	/**
	 * @return the scoreElement
	 */
	public String getScoreElement() {
		return scoreElement;
	}
	/**
	 * @param scoreElement the scoreElement to set
	 */
	public void setScoreElement(String scoreElement) {
		this.scoreElement = scoreElement;
	}
	/**
	 * @return the scoreOutcomde
	 */
	public String getScoreOutcomde() {
		return scoreOutcomde;
	}
	/**
	 * @param scoreOutcomde the scoreOutcomde to set
	 */
	public void setScoreOutcomde(String scoreOutcomde) {
		this.scoreOutcomde = scoreOutcomde;
	}
	/**
	 * @return the scoreValue
	 */
	public String getScoreValue() {
		return scoreValue;
	}
	/**
	 * @param scoreValue the scoreValue to set
	 */
	public void setScoreValue(String scoreValue) {
		this.scoreValue = scoreValue;
	}
	
	
}
