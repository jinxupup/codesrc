package com.jjb.ecms.biz.ext.helper;

public class CreditLifeResponse {
	
	private String message	;//	Y	返回信息
	private String age	;//	Y	客户年龄
	private String cellphone	;//	Y	申请手机号
	private String credit_limit	;//	Y	已发卡客户当前固定额度
	private String id_no	;//	Y	申请客户证件号码
	private String birthyear	;//	Y	出生年份
	private String gender	;//	Y	性别
	private String marriage_self	;//	Y	自填婚姻
	private String is_bank_emp	;//	Y	行员标识
	private String cust_cellphone_in_virtual	;//	Y	申请手机号是否处于虚拟小号号段（1700、1705、1709、1707、1708、1718、1719）
	private String relacellphone_in_virtual	;//	Y	        直系亲属手机号是否处于虚拟小号号段（1700、1705、1709、1707、1708、1718、1719）
	private String emergencyphone_in_virtual;//        紧急联系人手机号是否处于虚拟小号号段（1700、1705、1709、1707、1708、1718、1719）
	private String cust_approve_same_product_cnt	;//	Y	本次申请身份证号匹配存量已通过申请人身份证号，存在已通过申请人身份证号且有相同的卡产品类型的个数
	private String city_level	;//	Y	城市等级
	private String cust_s_idcard_d_cellphone_cnt	;//	Y	同一申请人身份证，但申请人手机号不同的数量
	private String cust_90dsid_empadd_similar_min	;//	Y	90天内申请人身份证=历史库申请人身份证，申请人申请单位地址与历史库申请人申请单位地址相似度最小值
	private String cust_s_cellphone_d_idcard_cnt	;//	Y	同一申请人申请手机号，但申请人身份证不同的数量
	private String cust_90dsph_empname_similar_min	;//	Y	90天内申请人申请手机号=历史库申请人申请手机号，申请人申请工作单位与历史库申请人申请工作单位相似度最小值
	private String cust_90dsph_empadd_similar_min	;//	Y	90天内申请人申请手机号=历史库申请人申请手机号，申请人申请单位地址与历史库申请人申请单位地址相似度最小值
	private String s_custphone2rela_name_similar_min	;//	Y	申请人申请手机号=历史库直系亲属手机号，申请人姓名与历史库直系亲属姓名相似度最小值
	private String cust_90dsempadd_idcard_cnt	;//	Y	90天内申请人申请单位地址=历史库申请人申请单位地址，申请人身份证与历史库申请人身份证不同的次数
	private String cust_90dsempadd_cellphone_cnt	;//	Y	90天内申请人申请单位地址=历史库申请人申请单位地址，申请人申请手机号与历史库申请人申请手机号不同的次数
	private String s_relaphone_relaname_similar_min	;//	Y	申请人直系亲属手机号=历史库申请人直系亲属手机号，但直系亲属姓名与历史库直系亲属姓名相似度最小值
	private String s_relaphone2cust_name_similar_min	;//	Y	申请人直系亲属手机号=历史库申请人申请手机号，申请人直系亲属姓名与历史库申请人姓名相似度最小值
	private String relaname2creditbureau_similar	;//	Y	同一申请件，直系亲属是配偶的姓名与人行配偶姓名相似度
	private String relaphone2creditbureau_diff	;//	Y	同一申请件，直系亲属是配偶的配偶手机号≠人行配偶手机号（90天内）
	private String cust_historyapply_cnt	;//	Y	申请人身份证=历史库申请人身份证，历史申请人申请次数
	private String s_custphone2rela_curr_odue	;//	Y	申请人手机号与存量申请的直系亲属手机号相同，且存量申请人的当前逾期期数
	private String s_custphone2rela_highest_odue	;//	Y	申请人手机号与存量申请的直系亲属手机号相同，且存量申请人的历史最高逾期期数
	private String s_relaphone2cust_curr_odue	;//	Y	本次申请的直系亲属手机号与存量申请的申请人手机号相同，且存量申请人的当前逾期期数
	private String s_relaphone2cust_highest_odue	;//	Y	本次申请的直系亲属手机号与存量申请的申请人手机号相同，且存量申请人的历史最高逾期期数
	private String s_relaphone_cust_curr_odue	;//	Y	本次申请的直系亲属手机号与存量申请的直系亲属手机号相同，且存量申请人的账户当前逾期期数
	private String s_relaphone_cust_highest_odue	;//	Y	本次申请的直系亲属手机号与存量申请的直系亲属手机号相同，且存量申请人的历史最高逾期期数
	private String cust_curr_odue	;//	Y	行内信用卡账户当前逾期期数
	private String s_relaphone_d_idcard_cnt	;//	Y	同一直系亲属手机号，但申请人身份证不同的数量
	private String s_relaphone_d_custphone_cnt	;//	Y	同一直系亲属手机号，但申请人手机号不同的数量
	private String cust_90dsid_ename_similar_min	;//	Y	90天内申请人身份证=历史库申请人身份证，申请人申请工作单位与历史库申请人申请工作单位相似度最小值
	private String m_0010_fraudrejectscore	;//	Y	准入（反欺诈）
	private String m_0011_fraudcheckscore	;//	Y	反欺诈（转人工）
	private String black_hit_emgencyphone	;//	Y	紧急联系人手机号码击中行内黑名单库
	private String black_hit_relaphone	;//	Y	亲属号码击中行内黑名单库
	private String realtime	;//	Y	是否为实时，A:实时计算 B:异步计算(保留字段，暂不用)
	private String count_status	;//	Y	计算状态，N:未开始 I:计算中 S:计算成功 F:计算失败
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getCredit_limit() {
		return credit_limit;
	}
	public void setCredit_limit(String credit_limit) {
		this.credit_limit = credit_limit;
	}
	public String getId_no() {
		return id_no;
	}
	public void setId_no(String id_no) {
		this.id_no = id_no;
	}
	public String getBirthyear() {
		return birthyear;
	}
	public void setBirthyear(String birthyear) {
		this.birthyear = birthyear;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMarriage_self() {
		return marriage_self;
	}
	public void setMarriage_self(String marriage_self) {
		this.marriage_self = marriage_self;
	}
	public String getIs_bank_emp() {
		return is_bank_emp;
	}
	public void setIs_bank_emp(String is_bank_emp) {
		this.is_bank_emp = is_bank_emp;
	}
	public String getCust_cellphone_in_virtual() {
		return cust_cellphone_in_virtual;
	}
	public void setCust_cellphone_in_virtual(String cust_cellphone_in_virtual) {
		this.cust_cellphone_in_virtual = cust_cellphone_in_virtual;
	}
	public String getRelacellphone_in_virtual() {
		return relacellphone_in_virtual;
	}
	public void setRelacellphone_in_virtual(String relacellphone_in_virtual) {
		this.relacellphone_in_virtual = relacellphone_in_virtual;
	}
	public String getCust_approve_same_product_cnt() {
		return cust_approve_same_product_cnt;
	}
	public void setCust_approve_same_product_cnt(String cust_approve_same_product_cnt) {
		this.cust_approve_same_product_cnt = cust_approve_same_product_cnt;
	}
	public String getCity_level() {
		return city_level;
	}
	public void setCity_level(String city_level) {
		this.city_level = city_level;
	}
	public String getCust_s_idcard_d_cellphone_cnt() {
		return cust_s_idcard_d_cellphone_cnt;
	}
	public void setCust_s_idcard_d_cellphone_cnt(String cust_s_idcard_d_cellphone_cnt) {
		this.cust_s_idcard_d_cellphone_cnt = cust_s_idcard_d_cellphone_cnt;
	}
	public String getCust_90dsid_empadd_similar_min() {
		return cust_90dsid_empadd_similar_min;
	}
	public void setCust_90dsid_empadd_similar_min(String cust_90dsid_empadd_similar_min) {
		this.cust_90dsid_empadd_similar_min = cust_90dsid_empadd_similar_min;
	}
	public String getCust_s_cellphone_d_idcard_cnt() {
		return cust_s_cellphone_d_idcard_cnt;
	}
	public void setCust_s_cellphone_d_idcard_cnt(String cust_s_cellphone_d_idcard_cnt) {
		this.cust_s_cellphone_d_idcard_cnt = cust_s_cellphone_d_idcard_cnt;
	}
	public String getCust_90dsph_empname_similar_min() {
		return cust_90dsph_empname_similar_min;
	}
	public void setCust_90dsph_empname_similar_min(String cust_90dsph_empname_similar_min) {
		this.cust_90dsph_empname_similar_min = cust_90dsph_empname_similar_min;
	}
	public String getCust_90dsph_empadd_similar_min() {
		return cust_90dsph_empadd_similar_min;
	}
	public void setCust_90dsph_empadd_similar_min(String cust_90dsph_empadd_similar_min) {
		this.cust_90dsph_empadd_similar_min = cust_90dsph_empadd_similar_min;
	}
	public String getS_custphone2rela_name_similar_min() {
		return s_custphone2rela_name_similar_min;
	}
	public void setS_custphone2rela_name_similar_min(String s_custphone2rela_name_similar_min) {
		this.s_custphone2rela_name_similar_min = s_custphone2rela_name_similar_min;
	}
	public String getCust_90dsempadd_idcard_cnt() {
		return cust_90dsempadd_idcard_cnt;
	}
	public void setCust_90dsempadd_idcard_cnt(String cust_90dsempadd_idcard_cnt) {
		this.cust_90dsempadd_idcard_cnt = cust_90dsempadd_idcard_cnt;
	}
	public String getCust_90dsempadd_cellphone_cnt() {
		return cust_90dsempadd_cellphone_cnt;
	}
	public void setCust_90dsempadd_cellphone_cnt(String cust_90dsempadd_cellphone_cnt) {
		this.cust_90dsempadd_cellphone_cnt = cust_90dsempadd_cellphone_cnt;
	}
	public String getS_relaphone_relaname_similar_min() {
		return s_relaphone_relaname_similar_min;
	}
	public void setS_relaphone_relaname_similar_min(String s_relaphone_relaname_similar_min) {
		this.s_relaphone_relaname_similar_min = s_relaphone_relaname_similar_min;
	}
	public String getS_relaphone2cust_name_similar_min() {
		return s_relaphone2cust_name_similar_min;
	}
	public void setS_relaphone2cust_name_similar_min(String s_relaphone2cust_name_similar_min) {
		this.s_relaphone2cust_name_similar_min = s_relaphone2cust_name_similar_min;
	}
	public String getRelaname2creditbureau_similar() {
		return relaname2creditbureau_similar;
	}
	public void setRelaname2creditbureau_similar(String relaname2creditbureau_similar) {
		this.relaname2creditbureau_similar = relaname2creditbureau_similar;
	}
	public String getRelaphone2creditbureau_diff() {
		return relaphone2creditbureau_diff;
	}
	public void setRelaphone2creditbureau_diff(String relaphone2creditbureau_diff) {
		this.relaphone2creditbureau_diff = relaphone2creditbureau_diff;
	}
	public String getCust_historyapply_cnt() {
		return cust_historyapply_cnt;
	}
	public void setCust_historyapply_cnt(String cust_historyapply_cnt) {
		this.cust_historyapply_cnt = cust_historyapply_cnt;
	}
	public String getS_custphone2rela_curr_odue() {
		return s_custphone2rela_curr_odue;
	}
	public void setS_custphone2rela_curr_odue(String s_custphone2rela_curr_odue) {
		this.s_custphone2rela_curr_odue = s_custphone2rela_curr_odue;
	}
	public String getS_custphone2rela_highest_odue() {
		return s_custphone2rela_highest_odue;
	}
	public void setS_custphone2rela_highest_odue(String s_custphone2rela_highest_odue) {
		this.s_custphone2rela_highest_odue = s_custphone2rela_highest_odue;
	}
	public String getS_relaphone2cust_curr_odue() {
		return s_relaphone2cust_curr_odue;
	}
	public void setS_relaphone2cust_curr_odue(String s_relaphone2cust_curr_odue) {
		this.s_relaphone2cust_curr_odue = s_relaphone2cust_curr_odue;
	}
	public String getS_relaphone2cust_highest_odue() {
		return s_relaphone2cust_highest_odue;
	}
	public void setS_relaphone2cust_highest_odue(String s_relaphone2cust_highest_odue) {
		this.s_relaphone2cust_highest_odue = s_relaphone2cust_highest_odue;
	}
	public String getS_relaphone_cust_curr_odue() {
		return s_relaphone_cust_curr_odue;
	}
	public void setS_relaphone_cust_curr_odue(String s_relaphone_cust_curr_odue) {
		this.s_relaphone_cust_curr_odue = s_relaphone_cust_curr_odue;
	}
	public String getS_relaphone_cust_highest_odue() {
		return s_relaphone_cust_highest_odue;
	}
	public void setS_relaphone_cust_highest_odue(String s_relaphone_cust_highest_odue) {
		this.s_relaphone_cust_highest_odue = s_relaphone_cust_highest_odue;
	}
	public String getCust_curr_odue() {
		return cust_curr_odue;
	}
	public void setCust_curr_odue(String cust_curr_odue) {
		this.cust_curr_odue = cust_curr_odue;
	}
	public String getS_relaphone_d_idcard_cnt() {
		return s_relaphone_d_idcard_cnt;
	}
	public void setS_relaphone_d_idcard_cnt(String s_relaphone_d_idcard_cnt) {
		this.s_relaphone_d_idcard_cnt = s_relaphone_d_idcard_cnt;
	}
	public String getS_relaphone_d_custphone_cnt() {
		return s_relaphone_d_custphone_cnt;
	}
	public void setS_relaphone_d_custphone_cnt(String s_relaphone_d_custphone_cnt) {
		this.s_relaphone_d_custphone_cnt = s_relaphone_d_custphone_cnt;
	}
	public String getCust_90dsid_ename_similar_min() {
		return cust_90dsid_ename_similar_min;
	}
	public void setCust_90dsid_ename_similar_min(String cust_90dsid_ename_similar_min) {
		this.cust_90dsid_ename_similar_min = cust_90dsid_ename_similar_min;
	}
	public String getM_0010_fraudrejectscore() {
		return m_0010_fraudrejectscore;
	}
	public void setM_0010_fraudrejectscore(String m_0010_fraudrejectscore) {
		this.m_0010_fraudrejectscore = m_0010_fraudrejectscore;
	}
	public String getM_0011_fraudcheckscore() {
		return m_0011_fraudcheckscore;
	}
	public void setM_0011_fraudcheckscore(String m_0011_fraudcheckscore) {
		this.m_0011_fraudcheckscore = m_0011_fraudcheckscore;
	}
	public String getBlack_hit_emgencyphone() {
		return black_hit_emgencyphone;
	}
	public void setBlack_hit_emgencyphone(String black_hit_emgencyphone) {
		this.black_hit_emgencyphone = black_hit_emgencyphone;
	}
	public String getBlack_hit_relaphone() {
		return black_hit_relaphone;
	}
	public void setBlack_hit_relaphone(String black_hit_relaphone) {
		this.black_hit_relaphone = black_hit_relaphone;
	}
	public String getRealtime() {
		return realtime;
	}
	public void setRealtime(String realtime) {
		this.realtime = realtime;
	}
	public String getCount_status() {
		return count_status;
	}
	public void setCount_status(String count_status) {
		this.count_status = count_status;
	}
	public String getEmergencyphone_in_virtual() {
		return emergencyphone_in_virtual;
	}
	public void setEmergencyphone_in_virtual(String emergencyphone_in_virtual) {
		this.emergencyphone_in_virtual = emergencyphone_in_virtual;
	}

}
