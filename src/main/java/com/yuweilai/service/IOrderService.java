package com.yuweilai.service;

import com.yuweilai.entity.OrderBase;
import com.yuweilai.entity.OrderDetailResult;
import com.yuweilai.entity.OrderInfo;
import com.yuweilai.entity.UserBase;

import java.util.List;
import java.util.Map;

/**
 * Created by 87734 on 2017/11/22.
 */
public interface IOrderService {
    List<Map> getOrder(Integer pageNum,Integer pageSize,String userId);
    Integer insertOrder(OrderBase orderBase);
    OrderInfo getOrderInfoById(String id);
    Integer insertOrderInfo(OrderInfo orderInfo);
    boolean isTeacherById(String userId);
    List<Map> getOrderList(UserBase userBase);
    OrderDetailResult getOrderDetail(String orderId);
    public int selectTeacher(String orderId,String teacherId);
}
