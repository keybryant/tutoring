package com.yuweilai.entity;

import java.util.Date;

public class OrderBase {
    private String orderId;

    private Integer orderState;

    private Date orderTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }
    public String getOrderStateName(){
        if(orderState==1){
            return "未分配老师";
        }else if(orderState==2){
            return "已分配";
        }else {
            return "待服务";
        }
    }

}