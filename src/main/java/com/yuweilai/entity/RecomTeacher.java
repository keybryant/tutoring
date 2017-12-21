package com.yuweilai.entity;

public class RecomTeacher {
    private String recomTeacherId;

    private String orderId;

    public String getRecomTeacherId() {
        return recomTeacherId;
    }

    public void setRecomTeacherId(String recomTeacherId) {
        this.recomTeacherId = recomTeacherId == null ? null : recomTeacherId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }
}