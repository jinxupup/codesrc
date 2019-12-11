package com.jjb.cas.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jjb.ecms.biz.ext.push.CreditSysPushSupport;

public class ServiceRunner {

	static ApplicationContext context;
	public static void main(String[] args) throws Exception {
		context = new ClassPathXmlApplicationContext("service-context.xml");
        
		CreditSysPushSupport sss = context.getBean(CreditSysPushSupport.class);
		int ii = 0;
        while (true) {
//        	try {
//        		sss.asynPushApplyProgress("20180508220000000130");
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
        	
//        		ApplyCreditReportSupport crs = context.getBean(ApplyCreditReportSupport.class);
//        		String [] apps = new String []{
//        				//九份有报告的
////        				"20180508210000000121","20180508210000000122","20180508210000000123","20180508210000000124","20180508210000000125","20180508210000000126","20180508210000000127","20180508210000000128","20180508210000000129"
////        				"20180508210000000125","20180508210000000129","20180508210000000122","20180508210000000121","20180508210000000126","20180508210000000128","20180508210000000123","20180508210000000127","20180508210000000124"
////        				"20180508220000000130","20180508220000000131"
////        				"20180424190000000088"
//        				"20180531000000000171"
//        		};
//        		ApplyQueryService aqs = context.getBean(ApplyQueryService.class);
//        		for (int i = 0; i < apps.length; i++) {
//					String appNo = apps[i];
//					try {
//						TmAppMain main = aqs.getTmAppMainByAppNo(appNo);
////						sss.asynPushApplyProgress(appNo,main);
////						PbocCreditStatResp resp = crs.queryPbocCreditStat(appNo,null);
////		        		sss.pushApplyPbocDate(appNo, resp.getCreditData());
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
			
        	ii++;
        	if(ii>=2){
        		break;
        	}
		}

		System.err.println("cas service running ......");
		System.in.read();
		System.in.close();
	}

}
