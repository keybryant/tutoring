package com.yuweilai.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import com.yuweilai.entity.*;
import com.yuweilai.service.IRoleAuthorityService;
import com.yuweilai.service.IUserService;
import com.yuweilai.util.ResultUtil;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Created by 87734 on 2017/10/9.
 */
@RestController
public class WebAuthController {
    private Logger log = Logger.getLogger(WebAuthController.class);
    @Autowired
    private IUserService userService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private IRoleAuthorityService roleAuthorityService;
    private  static final String appId="wx8fb9b336b47731cd";
    private  static final String appSecret="75e94a7a8e89d5a1ad0c1defe0466dfd";
    private  static String webAccessTokenUrl="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    private  static String getUserInfoUrl="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    @RequestMapping(value="/webAuth",method = RequestMethod.GET)
    public @ResponseBody
    Object webAuth(HttpServletRequest request, HttpServletResponse response) {
        String code = (String) request.getParameter("code");
        String state = (String) request.getParameter("state");
        request.getSession().setAttribute("state", state);
        String url = webAccessTokenUrl.replace("APPID", appId).replace("SECRET", appSecret).replace("CODE", code);
        String tokenStr=this.restTemplate.getForObject(url,String.class);
        ObjectMapper objectMapper=new ObjectMapper();
        try{
            WXToken wxToken=objectMapper.readValue(tokenStr,WXToken.class);
            log.info(wxToken.getErrmsg()+wxToken.getErrcode());
            if(wxToken.getOpenid()!=null&&wxToken.getAccess_token()!=null){
                String userInfoUrl = getUserInfoUrl.replace("OPENID", wxToken.getOpenid()).replace("ACCESS_TOKEN", wxToken.getAccess_token());
                String userInfoStr=this.restTemplate.getForObject(userInfoUrl,String.class);
                WXUserInfo wxUserInfo=objectMapper.readValue(userInfoStr,WXUserInfo.class);
                if(userService.getUserBaseByAccount(wxToken.getOpenid())==null){
                    UserBase userBase=new UserBase();
                    userBase.setAccount(wxToken.getOpenid());
                    userBase.setPassword("wechat123456");
                    String userId= UUID.randomUUID().toString();
                    userBase.setUserId(userId);
                    Role role=roleAuthorityService.getRole("USER");
                    userBase.setRoleId(role.getRoleId());
                    userBase.setUserState(1);//状态初始化为已注册可使用
                    userBase.setRegTime(new Date(System.currentTimeMillis()));
                    if(userService.insertUserBase(userBase)==1){
                        UserInfo userInfo=new UserInfo();
                        userInfo.setUserId(userId);
                        userInfo.setLoginState(0);
                        userInfo.setProfile(wxUserInfo.getHeadimgurl());
                        userInfo.setNickName(wxUserInfo.getNickname());
                        if(userService.insertUserInfo(userInfo)==1){
                            return ResultUtil.ok();
                        }

                    }
                }else {
                    Subject subject = SecurityUtils.getSubject() ;
                    UsernamePasswordToken token = new UsernamePasswordToken(wxToken.getOpenid(),"wechat123456");
                    subject.login(token);
                    UserBase userBase=userService.getUserBaseByAccount(wxToken.getOpenid());
                    UserInfo userInfo=userService.getUserInfoByUserId(userBase.getUserId());
                    if(userInfo==null){
                        return ResultUtil.build(101,"userInfo is not exiting");
                    }
                    userInfo.setLoginState(1);//用户登录状态改为1：已经登录
                    userInfo.setProfile(wxUserInfo.getHeadimgurl());
                    userInfo.setNickName(wxUserInfo.getNickname());
                    userInfo.setLastLoginTime(new Date(System.currentTimeMillis()));
                    if(userService.updateUserInfoByUserId(userBase.getUserId(),userInfo)!=1){
                        return ResultUtil.build(101,"update userInfo fail");
                    }
                    HttpSession session=request.getSession();
                    session.setAttribute("user",userBase);
                    Role role=roleAuthorityService.getRoleById(userBase.getRoleId());
                    response.sendRedirect("https://ssl.wenzijvlebu.com/tutorPage/index.html#/home");
                    return ResultUtil.ok(role);
                }
            }
            else{
                return ResultUtil.build(101,wxToken.getErrmsg());
            }


        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ResultUtil.build(101,"login fail");

    }
}
