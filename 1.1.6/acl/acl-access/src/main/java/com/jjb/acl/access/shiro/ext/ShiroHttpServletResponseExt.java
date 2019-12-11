package com.jjb.acl.access.shiro.ext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.ShiroHttpServletResponse;

/**
 * 扩展代理，用于去掉url后面的sessionId
 * @author LSW
 *
 */
public class ShiroHttpServletResponseExt extends ShiroHttpServletResponse {

	public ShiroHttpServletResponseExt(HttpServletResponse wrapped,
			ServletContext context, ShiroHttpServletRequest request) {
		
		super(wrapped, context, request);
	}
	
	@Override
	protected String toEncoded(String url, String sessionId) {

        if ((url == null) || (sessionId == null))
            return (url);

        String path = url;
        String query = "";
        String anchor = "";
        int question = url.indexOf('?');
        if (question >= 0) {
            path = url.substring(0, question);
            query = url.substring(question);
        }
        int pound = path.indexOf('#');
        if (pound >= 0) {
            anchor = path.substring(pound);
            path = path.substring(0, pound);
        }
        StringBuilder sb = new StringBuilder(path);
       
        sb.append(anchor);
        sb.append(query);
        return (sb.toString());

    }

}
