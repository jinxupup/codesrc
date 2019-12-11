package com.jjb.cas.biz.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jjb.ecms.util.HttpClientUtil;

public class TestPost {
	public static void main(String[] args) throws IOException {

//		String path = "C://usr//1.html";
//		File file = new File(path);
//		String encoded = null;
//		try {
//			byte[] bytes = Files.toByteArray(file);
//			BaseEncoding baseEncoding = BaseEncoding.base64();
//			// encoded = new String(bytes,"UTF-8");
//			encoded = baseEncoding.encode(bytes);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		/*JSONObject jso = new JSONObject();
		JSONObject reqData = new JSONObject();
		reqData.put("file_type", "2");// 身份证
		reqData.put("report_file", encoded);// 姓名
		reqData.put("apply_id", "729f8a2d-eac1-4ad4-bd62-481894b8dc87");// 电话号
		reqData.put("meal", "JjccbStandard");// 银行卡号
		jso.put("reqData", reqData);*/

//		String jsonData = jso.getString("reqData");
//		String tokenid = jso.getString("tokenid");
		String url = "http://10.109.3.220:8838/CreditReport/v1/getFile.action";
//		String url = "http://127.0.0.1:8838/CreditReport/v1/getFile.action";
		Map<String, Object> params = new HashMap<>();
		params.put("name", "曾毅豪");
		params.put("idCard", "362526199303250011");
		params.put("meal", "JjccbStandard");
//		params.put("checkCode", "435434");
		String res_str = HttpClientUtil.httpPostRequest(url, params);
        JSONObject jsonObject = JSONObject.parseObject(res_str);

        get(jsonObject);
	}

    public static void get(JSONObject jsonObject ){
        String str="当前逾期金额,jj_cur_over_amt,最近6个月，逾期次数,jj_over_num_6m,最近6个月，出现30天以上的逾期次数（单笔最高）,jj_over30d_num_6m,最近6个月内出现 30 天以上逾期的信用卡数,jj_over30d_card_num_6m,最近6个月内逾期次数≥2的信用卡数,jj_over_card_2num_6m,最近12个月，出现60天以上的逾期次数,jj_over60d_num_12m,最近12个月，出现30天以上的逾期次数,jj_over30d_num_12m,最近12个月内出现60天以上逾期的信用卡数,jj_over60d_card_num_12m,最近12个月内出现30天以上逾期的信用卡数,jj_over30d_card_num_12m,最近5年，出现180天（含）以上的逾期次数,jj_5y_over180d_num,呆账账户数,jj_card_dz_num,冻结账户数,jj_card_dj_num,止付账户数,jj_card_zf_num,近2个月查询次数,jj_query_num_m2,近6个月查询次数,jj_query_num_m6,近12个月查询次数,jj_query_num_m12,近12个月任意连续三个月最高查询次数,jj_query_num_m12_3m_max,近12个月信用卡查询总次数,jj_card1_query_num_m12,近12个月信用卡拒绝总次数,jj_card1_reject_num_m12,近12个月贷款查询总次数,jj_loan_query_num_m12,近12个月贷款拒绝总次数,jj_loan_reject_num_m12,近6个月信用卡查询总次数,jj_card1_query_num_m6,近6个月信用卡拒绝总次数,jj_card1_reject_num_m6,近6个月贷款查询总次数,jj_loan_query_num_m6,近6个月贷款拒绝总次数,jj_loan_reject_num_m6,近3个月信用卡查询总次数,jj_card1_query_num_m3,近3个月信用卡拒绝总次数,jj_card1_reject_num_m3,近3个月贷款查询总次数,jj_loan_query_num_m3,近3个月贷款拒绝总次数,jj_loan_reject_num_m3,贷款机构数,jj_loan_org_num,非银行机构放款金额≧5000元,jj_nbank_loan_amt5000_num,贷记卡最近6个月平均使用额度,jj_card1_use_limit_avg_m6,贷记卡平均额度,jj_all_card1_balance_m6,贷记卡最高额度（用卡满12个月）,jj_card1_max_limit,贷记卡共债比例,jj_card1_debt_rate,总额度使用率,jj_card1_use_rate,特殊交易类型为担保人代还总次数,jj_loan_st_guaran_num,特殊交易类型为强制提前结清总次数,jj_loan_st_force_num,特殊交易类型为以资抵债总次数,jj_loan_st_topay_num,贷款五级分类为次级的账户数,jj_loan_sec_num,贷款五级分类为可疑的账户数,jj_loan_sus_num,贷款五级分类为损失的账户数,jj_loan_lost_num,担保贷款五级分类次级账户数,jj_gua_loan_sec_num,担保贷款五级分类可疑账户数,jj_gua_loan_sus_num,担保贷款五级分类损失账户数,jj_gua_loan_lost_num";
        String[] split = str.split(",");
        Map<String,String > map=new HashMap<String,String >();
        for (int i=0;i<split.length;i=i+2){
            map.put(split[i],split[i+1]);
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            if(jsonObject!=null){
            }
        }
    }
}
