package com.jjb.acl.gmp.impl;

import java.io.IOException;

import org.apache.commons.cli.ParseException;

public class Test {

	
	
	public static void main(String[] args) {
		try {
			args = new String[11];
		
			args[0]="--mqaddress=10.109.3.204";
			args[1]="--mqvhost=ecms_sit01";
			args[2]="--mquser=admin";
			args[3]="--mqpass=admin";
			args[4]="--jdbcdriver=oracle.jdbc.driver.OracleDriver";
			args[5]="--jdbcurl=jdbc:oracle:thin:@10.109.2.186:1521:orcl";
			args[6]="--jdbcuser=shenpiconf";
			args[7]="--jdbcpass=admin123";
			args[8]="-pids";
			args[9]="49";
			args[10]="-start";
//			List list = new ArrayList<String>();
//			list.toArray();
//			args = (String[]) list.toArray();
//			args = new String[]{};
			Bootstrap.main(args);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
