package com.yuweilai.controller;

import com.yuweilai.dao.UserBaseMapper;
import com.yuweilai.entity.UserBase;
import com.yuweilai.util.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 87734 on 2017/11/16.
 */
@RestController
public class HelloController {
    @Autowired
    private UserBaseMapper userBaseMapper;
    @RequestMapping(value="/index")
    public @ResponseBody Object index(){
        Map<String,Object> resultMap=new HashMap<String,Object>();

        try{
            byte[] bytes=Base64Util.decodeBase64("hhhhhhhhhhh");
            resultMap.put("byte",bytes);
            System.out.println(bytes);
        }catch (Exception e){
            e.printStackTrace();


        }

        return resultMap;
    }
}
