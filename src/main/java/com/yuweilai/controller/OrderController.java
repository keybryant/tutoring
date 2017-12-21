package com.yuweilai.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuweilai.entity.*;
import com.yuweilai.service.IOrderService;
import com.yuweilai.service.IStudentService;
import com.yuweilai.service.ITeacherService;
import com.yuweilai.util.ResultUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by 87734 on 2017/11/23.
 */
@RestController
public class OrderController {
    private static final Logger logger = Logger.getLogger(OrderController.class);
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IStudentService studentService;
    @Autowired
    private ITeacherService teacherService;
    @Transactional
    @RequestMapping(value="/insertOrder",method = RequestMethod.POST)
    public @ResponseBody
    Object insertOrder(HttpServletRequest request, @RequestBody StudentForm studentForm){
        Student student=new Student();
        if(studentForm.getName()!=null){
            student.setName(studentForm.getName());
        }
        String s=null;
        if(!studentForm.getTime().isEmpty()){
            try{
                ObjectMapper objectMapper=new ObjectMapper();
                s=objectMapper.writeValueAsString(studentForm.getTime());
                logger.info(s);
                student.setTime(s);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(studentForm.getCallName()!=null){
            student.setCallName(studentForm.getCallName());
        }
        if(studentForm.getGrade()!=null){
            student.setGrade(studentForm.getGrade());
        }
        if(studentForm.getCourse()!=null){
            student.setCourse(studentForm.getCourse());
        }
        if(studentForm.getFeature()!=null){
            student.setFeature(studentForm.getFeature());
        }
        if(studentForm.getPhone()!=null){
            student.setPhone(studentForm.getPhone());
        }
        if(studentForm.getStuId()!=null){
            student.setSchool(studentForm.getSchool());
        }
        String studentId=UUID.randomUUID().toString();
        student.setStuId(studentId);
        OrderBase record=new OrderBase();
        record.setOrderState(1);//初始化订单状态为1：预约下单
        record.setOrderTime(new Date(System.currentTimeMillis()));
        String orderId=UUID.randomUUID().toString();
        record.setOrderId(orderId);
        if(orderService.insertOrder(record)!=1){
            return ResultUtil.build(101,"insert order fail");
        }
        OrderInfo orderInfo=new OrderInfo();
        UserBase userBase=(UserBase) request.getSession().getAttribute("user");
        orderInfo.setParentId(userBase.getUserId());
        orderInfo.setOrderId(orderId);
        if( studentService.insertStudent(student)!=1){
            return ResultUtil.build(101,"insert student fail");
        }
        orderInfo.setStuId(studentId);
        if(orderService.insertOrderInfo(orderInfo)!=1){
            return ResultUtil.build(101,"insert order fail");
        }
       return ResultUtil.ok(s);
    }
//    @RequestMapping(value="/getOrderDetail",method = RequestMethod.GET)
//    public @ResponseBody
//        Object getOrderDetail(HttpServletRequest request, HttpServletResponse response){
////        UserBase userBase=(UserBase) request.getSession().getAttribute("user");
//        UserBase userBase=new UserBase();
//        userBase.setRoleId("4");
//        userBase.setUserId("2");
//        List<Map> orderList=orderService.getOrder(1,50,userBase.getUserId());
//        return ResultUtil.ok(orderList);
//    }
    @RequestMapping(value="/getOrderList",method = RequestMethod.GET)
    public @ResponseBody
    Object getOrderList(HttpServletRequest request, HttpServletResponse response){
        UserBase userBase=(UserBase) request.getSession().getAttribute("user");
        List orderList=orderService.getOrderList(userBase);
        return ResultUtil.ok(orderList);
    }
    @RequestMapping(value="/getOrderDetail",method = RequestMethod.POST)
    public @ResponseBody
    Object getOrderDetail(HttpServletRequest request, @RequestBody OrderBase orderBase ){
        UserBase userBase=(UserBase) request.getSession().getAttribute("user");
        OrderDetailResult orderDetailResult=orderService.getOrderDetail(orderBase.getOrderId());
        return ResultUtil.ok(orderDetailResult);
    }
    @RequestMapping(value="/selectTeacher",method = RequestMethod.POST)
    public @ResponseBody
    Object selectTeacher(HttpServletRequest request, @RequestBody OrderInfo orderInfo){
        if(orderInfo.getOrderId()!=null&&orderInfo.getTeacherId()!=null){
           if(orderService.selectTeacher(orderInfo.getOrderId(),orderInfo.getTeacherId())==1){
               return ResultUtil.ok();
           }
        }
        return ResultUtil.build(101,"select teacher fail");
    }



}
