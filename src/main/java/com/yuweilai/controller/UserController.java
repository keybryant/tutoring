package com.yuweilai.controller;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import com.yuweilai.entity.*;
import com.yuweilai.service.IRoleAuthorityService;
import com.yuweilai.service.IUserService;
import com.yuweilai.util.FileUtil;
import com.yuweilai.util.ResultUtil;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Result;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by 87734 on 2017/11/21.
 */
@RestController
public class UserController {
    private static final Logger logger = Logger.getLogger(UserController.class);
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleAuthorityService roleAuthorityService;
    @Transactional
    @RequestMapping(value="/register",method = RequestMethod.POST)
    public @ResponseBody
    Object register(HttpServletRequest request, String account,String password){
        UserBase userBase=new UserBase();
        userBase.setAccount(account);
        userBase.setPassword(password);
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
            if(userService.insertUserInfo(userInfo)==1){
                return ResultUtil.ok();
            }

        }
        return ResultUtil.build(101,"register fail");

    }
    @RequestMapping(value="/login",method = RequestMethod.POST)
    public @ResponseBody
    Object login(HttpServletRequest request, @RequestBody UserBase user){
//        String account=request.getParameter("account");
//        String password=request.getParameter("password");
        logger.info("-------------------------");
//        logger.info(sb.toString());
        logger.info(user.getAccount());

        Subject subject = SecurityUtils.getSubject() ;
        UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount(),user.getPassword());
        try {
            subject.login(token);
            UserBase userBase=userService.getUserBaseByAccount(user.getAccount());
            UserInfo userInfo=userService.getUserInfoByUserId(userBase.getUserId());
            if(userInfo==null){
                return ResultUtil.build(101,"userInfo is not exiting");
            }
            userInfo.setLoginState(1);//用户登录状态改为1：已经登录
            userInfo.setLastLoginTime(new Date(System.currentTimeMillis()));
            if(userService.updateUserInfoByUserId(userBase.getUserId(),userInfo)!=1){
                return ResultUtil.build(101,"update userInfo fail");
            }
            HttpSession session=request.getSession();
            session.setAttribute("user",userBase);
            Role role=roleAuthorityService.getRoleById(userBase.getRoleId());
            return ResultUtil.ok(role);
        }catch (Exception exception){
            System.out.println("exception=" + exception);
            String msg = "";
            if (exception != null) {
                if (UnknownAccountException.class.getName().equals(exception)) {
                    System.out.println("UnknownAccountException -- > 账号不存在：");
                    msg = "UnknownAccountException -- > 账号不存在：";
                } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                    System.out.println("IncorrectCredentialsException -- > 密码不正确：");
                    msg = "IncorrectCredentialsException -- > 密码不正确：";
                } else if ("kaptchaValidateFailed".equals(exception)) {
                    System.out.println("kaptchaValidateFailed -- > 验证码错误");
                    msg = "kaptchaValidateFailed -- > 验证码错误";
                } else {
                    msg = "else >> "+exception;
                    System.out.println("else -- >" + exception);
                }
            }
        }

        return ResultUtil.build(101,"login fail") ;
    }
    @RequestMapping(value="/updatetUserInfo",method = RequestMethod.POST)
    public @ResponseBody
    Object updateUserInfo(HttpServletRequest request,UserInfo userInfo){
        UserBase userBase=(UserBase)request.getSession().getAttribute("user");
        String userId=userBase.getUserId();
        if(userInfo.getMobileNum()==null||userInfo.getNickName()==null){
            return ResultUtil.build(101,"param is null");
        }
        if(userService.updateUserInfoByUserId(userId,userInfo)!=1){
            return ResultUtil.build(101,"update userInfo fail");
        }
        return ResultUtil.ok();

    }
    @RequestMapping(value="/uploadProfile",method = RequestMethod.POST)
    public @ResponseBody Object uploadProfile(@RequestParam("file") MultipartFile file,
                HttpServletRequest request) {
        UserInfo userInfo=new UserInfo();
        if(file!=null){
            String originName=file.getOriginalFilename();
            System.out.println(originName);
            String[] strings=originName.split("\\.");
            String fileName = UUID.randomUUID().toString()+"."+strings[1];
            String filePath = "/home/keyong/tomcat/apache-tomcat-8.5.23/webapps/tutorProfilePic/";
            try {
                FileUtil.uploadFile(file.getBytes(), filePath, fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            userInfo.setProfile(filePath+fileName);
        }
        UserBase userBase=(UserBase)request.getSession().getAttribute("user");
        String userId=userBase.getUserId();

        if(userService.updateUserInfoByUserId(userId,userInfo)==1){
            return ResultUtil.ok();
        }else {
            return ResultUtil.build(101,"update profile fail");
        }

    }
    @RequestMapping(value="/isLogin",method = RequestMethod.GET)
    public @ResponseBody
    Object isLogin(HttpServletRequest request, HttpServletResponse response){
        UserBase userBase=(UserBase)request.getSession().getAttribute("user");
        if(userBase!=null){
            return ResultUtil.ok();
        }else{
            return ResultUtil.build(101,"not login");
        }
    }
    @RequestMapping(value="/getCurrentUser",method = RequestMethod.POST)
    public @ResponseBody
    Object getCurrentUser(HttpServletRequest request){
        UserBase userBase=(UserBase)request.getSession().getAttribute("user");
        Role role=roleAuthorityService.getRoleById(userBase.getRoleId());
        UserInfo userInfo=userService.getUserInfoByUserId(userBase.getUserId());
        userBase.setPassword("");
        Map map=new HashMap<String,Object>();
        map.put("userBase",userBase);
        map.put("role",role);
        map.put("userInfo",userInfo);
        return ResultUtil.ok(map);
    }
    @RequestMapping(value="/getCode",method = RequestMethod.POST)
    public @ResponseBody
    Object getCode(HttpServletRequest request){
        Code code=new Code();
        code.setCode("123456");
        code.setTime(System.currentTimeMillis());
        request.getSession().setAttribute("code",code);
        return ResultUtil.ok();
    }
    @RequestMapping(value="/verifyCode",method = RequestMethod.POST)
    public @ResponseBody
    Object getCode(HttpServletRequest request,@RequestBody Code code){
        Code realCode=(Code)request.getSession().getAttribute("code");
        if(code.getCode().equals(realCode.getCode())&&(System.currentTimeMillis()-realCode.getTime())<600000){
            return ResultUtil.ok();
        }
        return ResultUtil.build(101,"Verification code wrong");

    }

}
