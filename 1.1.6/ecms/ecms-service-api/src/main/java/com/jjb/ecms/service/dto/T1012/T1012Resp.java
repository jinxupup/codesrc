package com.jjb.ecms.service.dto.T1012;

import com.jjb.ecms.service.dto.BasicResponse;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JYData-R&D-Big Star
 * @version V1.0
 * @Description: 申请进度查询返回数据类
 * @date 2016年11月23日 上午10:10:02
 */
@Data
public class T1012Resp extends BasicResponse implements Serializable {
    private static final long serialVersionUID = 1L;


    private String Name;// 客户姓名
    private String IdType;// 证件类型
    private String IdNo;// 证件号码
    private String Cellphone;// 手机号码
    private String AppProducts;// 申请产品
    private BigDecimal AppAmount;// 申请贷款金额
    private String CompanyName;// 单位名称
    private String MaritalStatus;// 婚姻状况
    private String PolicyResult;// 决策结果
    private String RuleList;// 人行规则命中结果清单
    private String RefuseCode;// 拒绝原因
    private String ImageNum;// 影像批次号
    private String WeCode;// 微信个人识别码
    private String PptyProvince;// 所属省
    private String PptyCity;// 所属市
    private String PptyArea;// 车牌归属地
    private String PptyAreaCode;// 车牌归属地字母代号

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("Name", this.Name);
        map.put("IdType", this.IdType);
        map.put("IdNo", this.IdNo);
        map.put("Cellphone", this.Cellphone);
        map.put("AppProducts", this.AppProducts);
        map.put("AppAmount", this.AppAmount);
        map.put("CompanyName", this.CompanyName);
        map.put("MaritalStatus", this.MaritalStatus);
        map.put("PolicyResult", this.PolicyResult);
        map.put("RuleList", this.RuleList);
        map.put("RefuseCode", this.RefuseCode);
        map.put("ImageNum", this.ImageNum);
        map.put("WeCode", this.WeCode);
        map.put("PptyProvince", this.PptyProvince);
        map.put("PptyCity", this.PptyCity);
        map.put("PptyArea", this.PptyArea);
        map.put("PptyAreaCode", this.PptyAreaCode);
        return map;
    }
}
