package com.yuweilai.controller;

import com.yuweilai.entity.*;
import com.yuweilai.service.ITeacherService;
import com.yuweilai.util.FileUtil;
import com.yuweilai.util.ResultUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by 87734 on 2017/11/23.
 */
@RestController
public class TeacherController {
    private static final Logger logger = Logger.getLogger(TeacherController.class);
    @Autowired
    private ITeacherService teacherService;

    @RequestMapping(value="/insertTeacher",method = RequestMethod.POST)
    public @ResponseBody
    Object insertTeacher(HttpServletRequest request, @RequestBody TeacherInfoForm teacherInfoForm){
        TeacherInfo teacherInfo=teacherInfoForm.getTeacherInfo();
        teacherInfo.setAuthState(1);//认证状态初始化为开始认证
        UserBase userBase=(UserBase)request.getSession().getAttribute("user");
        teacherInfo.setUserId(userBase.getUserId());
        teacherInfo.setScore(0);//评价总分初始化为0
        teacherInfo.setState(1);
        TeacherInfoExample teacherInfoExample=new TeacherInfoExample();
        TeacherInfoExample.Criteria criteria=teacherInfoExample.createCriteria();
        criteria.andUserIdEqualTo(userBase.getUserId());
        if(teacherService.updateTeacher(teacherInfo,teacherInfoExample)==1){
           return ResultUtil.ok();
        }else {
            return ResultUtil.build(101,"insert teacher fail");
        }

    }
    @RequestMapping(value="/auth",method = RequestMethod.POST)
    public @ResponseBody Object auth(@RequestParam("file") MultipartFile file,
                                              HttpServletRequest request) {

        TeacherInfo teacherInfo=new TeacherInfo();
        if(file!=null){
            String originName=file.getOriginalFilename();
            System.out.println(originName);
            String[] strings=originName.split("\\.");
            String fileName = UUID.randomUUID().toString()+"."+strings[1];
            String filePath = "/home/keyong/tomcat/apache-tomcat-8.5.23/webapps/tutorAuthPic/";
            try {
                FileUtil.uploadFile(file.getBytes(), filePath, fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            teacherInfo.setAuthPic(filePath+fileName);
            teacherInfo.setAuthState(1);//开始认证
        }
        UserBase userBase=(UserBase)request.getSession().getAttribute("user");
        String userId=userBase.getUserId();
        TeacherInfoExample example=new TeacherInfoExample();
        TeacherInfoExample.Criteria criteria=example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        if(teacherService.updateTeacher(teacherInfo,example)==1){
            return ResultUtil.ok();
        }else {
            return ResultUtil.build(101,"auth fail");
        }
    }
    @RequestMapping(value="/uploadPic",method = RequestMethod.POST)
    public @ResponseBody
    Object uploadPic(HttpServletRequest request,@RequestParam("file") MultipartFile file ) {
        if (file != null) {
            String originName = file.getOriginalFilename();
            System.out.println(originName);
            String[] strings = originName.split("\\.");
            String fileName = UUID.randomUUID().toString() + "." + strings[1];
            String filePath = "/home/keyong/tomcat/apache-tomcat-8.5.23/webapps/tutorAuthPic/";
            try {
                FileUtil.uploadFile(file.getBytes(), filePath, fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "https://ssl.wenzijvlebu.com/tutorAuthPic/" + fileName;
        }
        return null;
    }
    @RequestMapping(value="/getTeacherDetail",method = RequestMethod.POST)
    public @ResponseBody Object getTeacherDetail(HttpServletRequest request,@RequestBody UserBase userBase){
        TeacherInfo teacherInfo=teacherService.getTeacherById(userBase.getUserId());
        return ResultUtil.ok(teacherInfo);
    }
    @RequestMapping(value="/getRecomTeacherList",method = RequestMethod.POST)
    public @ResponseBody
    Object getRecomTeacherList(HttpServletRequest request,@RequestBody OrderBase orderBase){
        List<TeacherInfo> teacherInfoList=teacherService.getRecomTeacherInfoList(orderBase.getOrderId());
        if(teacherInfoList!=null&&!teacherInfoList.isEmpty()){
            int total=teacherInfoList.size();
            request.getSession().setAttribute("changeNum",0);
            if(total<=3){
                return ResultUtil.ok(teacherInfoList);
            }
            else {
                List<TeacherInfo> resultList=new ArrayList<>();
                for(int i=0;i<3;i++){
                    resultList.add(teacherInfoList.get(i));
                }
                return ResultUtil.ok(resultList);
            }

        }
        return ResultUtil.build(101,"no recomTeacher");

    }
    @RequestMapping(value="/changeRecomTeacher",method = RequestMethod.POST)
    public @ResponseBody
    Object changeRecomTeacher(HttpServletRequest request,@RequestBody OrderBase orderBase){
        List<TeacherInfo> teacherInfoList=teacherService.getRecomTeacherInfoList(orderBase.getOrderId());
        if(teacherInfoList!=null&&!teacherInfoList.isEmpty()){
            int total=teacherInfoList.size();
            int changeNum=(Integer)request.getSession().getAttribute("changeNum");
            if(changeNum+3<total){
                request.getSession().setAttribute("changeNum",changeNum+1);
                return ResultUtil.ok(teacherInfoList.get(changeNum+3));
            }else {
                request.getSession().setAttribute("change",0);
                return ResultUtil.ok(teacherInfoList.get(0));
            }
        }
        return ResultUtil.build(101,"changeTeacher fail");
    }



}
