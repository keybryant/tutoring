package com.yuweilai.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuweilai.controller.OrderController;
import com.yuweilai.dao.*;
import com.yuweilai.entity.*;
import com.yuweilai.service.IOrderService;
import com.yuweilai.service.IRoleAuthorityService;
import com.yuweilai.util.FormatUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 87734 on 2017/11/22.
 */
@Service
public class OrderServiceImpl implements IOrderService {
    private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderBaseMapper orderBaseMapper;
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherInfoMapper teacherInfoMapper;
    @Autowired
    private RecomTeacherMapper recomTeacherMapper;
    @Autowired
    private IRoleAuthorityService roleAuthorityService;
    /*
             @method:保存订单
              */
    @Override
    public Integer insertOrder(OrderBase orderBase) {
        return orderBaseMapper.insertSelective(orderBase);
    }
    /*
                 @method:根据userid获取订单以及订单信息
                  */
    @Override
    public List<Map> getOrder(Integer pageNum,Integer pageSize,String userId) {
        OrderInfoExample orderInfoExample=new OrderInfoExample();
        OrderInfoExample.Criteria criteria=orderInfoExample.createCriteria();
        if(isTeacherById(userId)){
            criteria.andTeacherIdEqualTo(userId);

        }else{
            criteria.andParentIdEqualTo(userId);
        }
        long maxPage=orderInfoMapper.countByExample(orderInfoExample);
        PageHelper.startPage(pageNum, pageSize);
        System.out.println(maxPage);
        if((pageNum-1)*pageSize>=maxPage){
            return null;
        }
        List<Map> list=orderInfoMapper.selectByUserId(orderInfoExample);
        for(Map map:list){
            String orderId=(String)map.get("orderId");
            String stuId=(String)map.get("stuId");
            String teacherId=(String)map.get("teacherId");
            Integer orderState=(Integer)map.get("orderState");
            Student student=studentMapper.selectByPrimaryKey(stuId);
            map.put("student",student);
            if(orderState==2){            //已推荐老师
                RecomTeacherExample recomTeacherExample=new RecomTeacherExample();
                RecomTeacherExample.Criteria criteria1=recomTeacherExample.createCriteria();
                criteria1.andOrderIdEqualTo(orderId);
                List<RecomTeacher> recomTeacherList=recomTeacherMapper.selectByExample(recomTeacherExample);
                List teacherList=new ArrayList<TeacherInfo>();
                for(RecomTeacher recomTeacher:recomTeacherList){
                    TeacherInfoExample teacherInfoExample=new TeacherInfoExample();
                    TeacherInfoExample.Criteria criteria2=teacherInfoExample.createCriteria();
                    criteria2.andUserIdEqualTo(recomTeacher.getRecomTeacherId());
                    List<TeacherInfo> teacherInfoList=teacherInfoMapper.selectByExample(teacherInfoExample);
                    if(!teacherInfoList.isEmpty()){
                        teacherList.add(teacherInfoList.get(0));
                    }
                }
                if(!teacherList.isEmpty()){
                    map.put("recomTeacher",list);
                }
            }
            if(orderState==3){    //已选择老师
                TeacherInfoExample teacherInfoExample=new TeacherInfoExample();
                TeacherInfoExample.Criteria criteria1=teacherInfoExample.createCriteria();
                criteria1.andUserIdEqualTo(teacherId);
                TeacherInfo teacherInfo=teacherInfoMapper.selectByExample(teacherInfoExample).get(0);
                if(teacherInfo!=null){
                    map.put("selectTeacher",teacherInfo);
                }

            }

        }
        return list;
    }
    /*
                    @method:根据id获取订单详情
                     */
    @Override
    public OrderInfo getOrderInfoById(String id) {
        OrderInfoExample orderInfoExample=new OrderInfoExample();
        OrderInfoExample.Criteria criteria=orderInfoExample.createCriteria();
        criteria.andOrderIdEqualTo(id);
        List<OrderInfo> list=orderInfoMapper.selectByExample(orderInfoExample);
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }
    /*
       @method:保存订单详情
                        */
    @Override
    public Integer insertOrderInfo(OrderInfo orderInfo) {
        return orderInfoMapper.insert(orderInfo);
    }

    @Override
    public boolean isTeacherById(String userId) {
        TeacherInfoExample teacherInfoExample=new TeacherInfoExample();
        TeacherInfoExample.Criteria criteria=teacherInfoExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<TeacherInfo> teacherInfoList=teacherInfoMapper.selectByExample(teacherInfoExample);
        if(!teacherInfoList.isEmpty()){
            return true;
        }
        return false;
    }
    @Override
    public List<Map> getOrderList(UserBase userBase){
        List<Map> resultList=new ArrayList<>();
        OrderInfoExample orderInfoExample=new OrderInfoExample();
        OrderInfoExample.Criteria criteria=orderInfoExample.createCriteria();
        if(isTeacherById(userBase.getUserId())){
            criteria.andTeacherIdEqualTo(userBase.getUserId());
            OrderInfoExample orderInfoExample1=new OrderInfoExample();
            OrderInfoExample.Criteria criteria1=orderInfoExample1.createCriteria();
            criteria1.andParentIdEqualTo(userBase.getUserId());
            orderInfoExample.or(criteria1);

        }else{
            criteria.andParentIdEqualTo(userBase.getUserId());
        }
        List<OrderInfo> orderInfos=orderInfoMapper.selectByExample(orderInfoExample);
        List<OrderInfo> orderInfoList=sort(orderInfos);
        logger.info("orderInfoNum:"+orderInfoList.size());
        for(OrderInfo orderInfo:orderInfoList){
            logger.info(orderInfo.getOrderId());
            Map dataMap=new HashMap<String,Object>();
            dataMap.put("orderName","订单");
            dataMap.put("orderId",orderInfo.getOrderId());
            OrderBase orderBase=orderBaseMapper.selectByPrimaryKey(orderInfo.getOrderId());
            dataMap.put("orderState",orderBase.getOrderStateName());
            Student student=studentMapper.selectByPrimaryKey(orderInfo.getStuId());
            dataMap.put("orderText1",student.getName());
            dataMap.put("orderText2", student.getCourse());
            dataMap.put("orderText3",orderBase.getOrderTime());
            resultList.add(dataMap);

        }
        return resultList;
    }

    @Override
    public OrderDetailResult getOrderDetail(String orderId) {
       OrderDetailResult orderDetailResult=new OrderDetailResult();
       OrderBase orderBase=orderBaseMapper.selectByPrimaryKey(orderId);
       OrderInfo orderInfo=getOrderInfoById(orderId);
        FormHead formHead=new FormHead();
        formHead.setFormName("订单详情");
        formHead.setOrderId(orderBase.getOrderId());
        formHead.setOrderState(orderBase.getOrderStateName());
        formHead.setTime(orderBase.getOrderTime().getTime());
        logger.info("orderId:"+orderInfo.getStuId());
       if(orderInfo.getStuId()!=null){
           Student student=studentMapper.selectByPrimaryKey(orderInfo.getStuId());
           formHead.setT2(student.getCallName());
           formHead.setT3(student.getPhone());
           InfoSection infoSection=new InfoSection("学生详情");
           Row row1=new Row("学生姓名",student.getName());
           Row row2=new Row("科目",student.getCourse());
           Row row3 =new Row("年级",student.getGrade());
           infoSection.add(row1);
           infoSection.add(row2);
           infoSection.add(row3);
           orderDetailResult.add(infoSection);
           logger.info("run to here__________________________________");
           logger.info(orderDetailResult.getResultList());
       }
       if(orderInfo.getTeacherId()!=null){
            TeacherInfo teacherInfo=teacherInfoMapper.selectByPrimaryKey(orderInfo.getTeacherId());
            InfoSection infoSection=new InfoSection("老师详情");
            Row row1=new Row("老师姓名",teacherInfo.getUserName());
            Row row2=new Row("学校",teacherInfo.getSchool());
            Row row3 =new Row("电话号码",teacherInfo.getMobileNum());
            infoSection.add(row1);
            infoSection.add(row2);
            infoSection.add(row3);
            orderDetailResult.add(infoSection);
           logger.info("run to here__________________________________");
           logger.info(orderDetailResult.getResultList());
        }else{
           InfoSection infoSection=new InfoSection("老师详情");
           Row row1=new Row("分配状态","未分配");
           infoSection.add(row1);
           orderDetailResult.add(infoSection);
       }
        orderDetailResult.setFormHead(formHead);
        logger.info(orderDetailResult);
        return orderDetailResult;
    }
    List<OrderInfo> sort(List<OrderInfo> orderInfoList){
        List<OrderInfo> list=new ArrayList<>();
        OrderBaseExample orderBaseExample=new OrderBaseExample();
        orderBaseExample.setOrderByClause("ORDER_TIME DESC");
        List<OrderBase> orderBaseList=orderBaseMapper.selectByExample(orderBaseExample);
        for(OrderBase orderBase:orderBaseList){
            logger.info(orderBase.getOrderId());
            for(OrderInfo orderInfo:orderInfoList){
                logger.info(orderBase.getOrderId());
                if(orderBase.getOrderId().equals(orderInfo.getOrderId())){
                    logger.info("equal");
                    list.add(orderInfo);
                }
            }
        }
        return list;
    }

    @Override
    public int selectTeacher(String orderId, String teacherId) {
        OrderInfo orderInfo=getOrderInfoById(orderId);
        orderInfo.setTeacherId(teacherId);
        OrderInfoExample orderInfoExample=new OrderInfoExample();
        OrderInfoExample.Criteria criteria=orderInfoExample.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        return orderInfoMapper.updateByExampleSelective(orderInfo,orderInfoExample);
    }
}
