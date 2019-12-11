package com.jjb.ecms.service.dto.T1006;

import java.io.Serializable;

import lombok.Data;

/**
 * @Description: 随T1006提交上来的影像内容清单
 * @author JYData-R&D-HN
 * @date 2018年10月13日 下午3:42:25
 * @version V1.0
 */
@Data
public class T1006Image implements Serializable {
	
	private static final long serialVersionUID = -4085333700917194561L;
	private String imageNum;//影像批次号
	private String picTypeCode;//影像类型代码
	private String picTypeDesc;//影像类型描述
	private String picName;//影像名称
	private String picSort;//同类型影像序号
	private String picUrl;//影像URL
}
