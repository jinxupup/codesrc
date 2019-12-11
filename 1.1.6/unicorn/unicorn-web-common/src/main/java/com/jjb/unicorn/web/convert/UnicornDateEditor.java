package com.jjb.unicorn.web.convert;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期时间类型转换
 * @author jjb
 *
 */
public class UnicornDateEditor extends PropertyEditorSupport{
	
	@Override  
    public void setAsText(String text) throws IllegalArgumentException {  
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        format.setLenient(true);
        Date date = null;  
        try {  
            date = format.parse(text);  
        } catch (ParseException e) {  
            format = new SimpleDateFormat("yyyy-MM-dd");  
            format.setLenient(true);
            try {  
                date = format.parse(text);  
            } catch (ParseException e1) {  
                e1.printStackTrace();  
            }  
        }  
        setValue(date);  
    }

}
