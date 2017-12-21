package com.yuweilai.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 87734 on 2017/10/10.
 */
public class TimeUtil {
    public static String dateToStamp(String s)  {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            Date date = simpleDateFormat.parse(s);
            long ts = date.getTime();
            res = String.valueOf(ts);
            return res;
        }
        catch (ParseException  e){
            e.printStackTrace();
        }

        return null;

    }
    /*
    * 将时间戳转换为时间
    */
    public static String stampToDate(Date d){
        long lt=d.getTime();
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
}
