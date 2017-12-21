package com.yuweilai.entity;

public class Evaluate {
    private String evaluId;

    private String userId;

    private String evaluatedUserId;

    private String orderId;

    private Integer score;

    private String comment;

    public String getEvaluId() {
        return evaluId;
    }

    public void setEvaluId(String evaluId) {
        this.evaluId = evaluId == null ? null : evaluId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getEvaluatedUserId() {
        return evaluatedUserId;
    }

    public void setEvaluatedUserId(String evaluatedUserId) {
        this.evaluatedUserId = evaluatedUserId == null ? null : evaluatedUserId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}