package com.yuweilai.util;

import java.net.URLEncoder;

/**
 * Created by 87734 on 2017/10/23.
 */
public class URLUtil {
    public static String getEncodeURL(String url){
        return URLEncoder.encode(url);
    }
}
