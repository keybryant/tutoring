package com.yuweilai.util;

/**
 * Created by 87734 on 2017/12/14.
 */
public class FormatUtil {
    public static String format(String s){
        if(s!=null&&s!=""){
            return s.replaceAll("(\\[|\\]|\")","");
        }
        else return s;

    }
}
