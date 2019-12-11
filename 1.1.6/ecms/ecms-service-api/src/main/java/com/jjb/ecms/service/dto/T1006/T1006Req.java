package com.jjb.ecms.service.dto.T1006;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jjb.ecms.service.dto.BasicRequest;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import lombok.Data;

/**
 * @Description: 预审提交
 * @author JYData-R&D-Big Star
 * @date 2016年1月13日 下午2:44:45
 * @version V1.0
 */
@Data
public class T1006Req extends BasicRequest implements Serializable {
	
	private static final long serialVersionUID = -4085333700917194561L;
	@XStreamOmitField
	public static final String servId="T1006";
	private String appNo ;//申请件编号 
	private String opUserNo ;//操作员工号 
	private String signFileInd ;//客户签名状况
	private String idFileInd ;//身份证明状况
	private String jobFileInd ;//工作证明状况
	private String confirmType ;//确认类型;P：通过 ； R：拒绝删除
	private String cfOwningBranch;//客户经理确认的发卡网点
	private String spreaderType;//推广渠道
	private String preRemark;//预审备注
	private String imageNum;//影像批次号
	private List<T1006Image> pics = new ArrayList<T1006Image>();

	
}
