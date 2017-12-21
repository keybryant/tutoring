package com.yuweilai.entity;

/**
 * Created by 87734 on 2017/12/12.
 */
public class Row {
    String left;
    String right;
    public Row(String left,String right){
        this.left=left;
        this.right=right;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }
}
