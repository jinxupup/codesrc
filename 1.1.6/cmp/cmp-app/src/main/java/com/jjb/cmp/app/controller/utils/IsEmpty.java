package com.jjb.cmp.app.controller.utils;

import com.github.pagehelper.StringUtil;
import com.jjb.unicorn.facility.exception.ProcessException;

import java.util.Map;
import java.util.regex.PatternSyntaxException;

/**
 * Created by Administrator on 2019/4/12.
 */
public class IsEmpty {


    /**
     * 对String非空判断
     *
     */

    public static void StrIsEmpty(String STR) {

        boolean b = StringUtil.isEmpty(STR);
        if (b) {
            //是空
            throw new ProcessException("一个空的字符: " + STR.getClass().getName()+": "+STR);
        }

    }

    /**
     * 对String非空判断并返回STR
     *
     */

    public static String StrIsEmptyRe(String STR,String ee) {

        boolean b = StringUtil.isEmpty(STR);
        if (b) {
            //是空
            throw new ProcessException("一个空的字符: "+ee+ STR.getClass().getName()+": "+STR);
        }
        return STR;
    }


    /**
     * 对对象的非空判断
     */
    public static void ObjectIsEmpty(Object o) {
        if (o == null) {
            //对象为空
            throw new ProcessException("一个空的对象: " + o.getClass().getName()+": "+o);
        }

    }




}
