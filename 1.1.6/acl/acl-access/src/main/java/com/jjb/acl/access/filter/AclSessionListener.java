package com.jjb.acl.access.filter;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

//@Service
public class AclSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

////	private Map<String, String> userNameMap = AppHolder.getUserNameMap();
//	private Logger logger = LoggerFactory.getLogger(getClass());
//	
//	@Override
//	public void sessionCreated(HttpSessionEvent arg0) {
//		// TODO Auto-generated method stub
//		logger.info(arg0.getSession().getId() + "会话创建成功");
//	}
//
//	@Override
//	public void sessionDestroyed(HttpSessionEvent arg0) {
//		// TODO Auto-generated method stub
//		removeBySessionId(arg0.getSession());
//		logger.info(arg0.getSession().getId() + "会话超时结束");
//	}
//	
//	 /**
//	  * isAlreadyEnter-用于判断用户是否已经登录以及相应的处理方法 
//	  * @param sUserName String-登录的用户名称
//	  * @return boolean-该用户是否已经登录过的标志
//	  */
//	public  boolean isAlreadyEnter(HttpSession session,String sUserName){
//		 boolean flag = false;
//		 // 如果该用户已经登录过，则不让该用户再次登录
//		 if(userNameMap.containsValue(sUserName)) {
//			 flag = true;
//		 } else {
//			 flag = false;
//			 userNameMap.put(session.getId(),sUserName);
//		 }
//		 
//	  return flag;
//	}
//	 
//	 /**
//	  * removeLoginName-用于logout时清除userName
//	  * @param session HttpSession-登录的用户名称 
//	  * @return void
//	  */
//	 @SuppressWarnings({ "rawtypes", "unused" })
//	public void removeByLoginName(HttpSession session){              // 通过sessionID判断会话唯一性是不对的！sessionID每次请求可能会变!
//		                                                           // 但是session绑定的loginName不会变
//		 String userName = (String)session.getAttribute("loginName");
//		 Iterator iter = userNameMap.entrySet().iterator();
//	     Map.Entry entry;
//	     while (iter.hasNext()){
//	    	 entry = (Map.Entry)iter.next();
//	    	 Object key = entry.getKey();
//	    	 Object val = entry.getValue();
//	    	 if(((String)val ).equals(userName)){
//	    		 iter.remove();
//	    	 }
//	     }
//	 }
//	 
//	 public void removeBySessionId(HttpSession session) {
//		 if(userNameMap != null) {
//			 userNameMap.remove(session.getId());
//		 }
//	 }
//	
}
