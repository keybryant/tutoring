//package com.yuweilai.util;
//
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.util.EntityUtils;
//
//import java.net.URI;
//import java.net.URL;
//
///**
// * Created by 87734 on 2017/10/9.
// */
//
//public class HttpUtil {
//    public static String contentType;
//public static String doGet(String strUrl){
//    String s=null;
//    HttpClient httpClient=null;
//    HttpGet httpGet=null;
//
//    try{
//        URL url = new URL(strUrl);
//        URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
//        httpGet=new HttpGet(uri);
//        httpClient=new DefaultHttpClient();
//        HttpResponse response=httpClient.execute(httpGet);
//        HttpEntity entity=response.getEntity();
//        if(entity!=null){
////            byte[] bytes=EntityUtils.toByteArray(entity);
////            s=new String(bytes,"UTF-8");
////            Header header=entity.getContentEncoding();
////            contentType=header.toString();
//            s=EntityUtils.toString(entity,"UTF-8");
//        }
//    }
//    catch (Exception e){
//        e.printStackTrace();
//    }
//    finally {
//        httpClient.getConnectionManager().shutdown();
//        httpClient=null;
//        httpGet=null;
//    }
//return s;
//}
//}
