package com.yuweilai.entity;

import com.yuweilai.util.ResultUtil;

import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 87734 on 2017/12/12.
 */
public class OrderDetailResult {
    FormHead formHead;
    List<InfoSection> resultList;
    public OrderDetailResult(){
        this.resultList=new ArrayList<>();
    }
    public void add(InfoSection infoSection){
        this.resultList.add(infoSection);
    }

    public FormHead getFormHead() {
        return formHead;
    }

    public void setFormHead(FormHead formHead) {
        this.formHead = formHead;
    }
    public List getResultList(){
        return this.resultList;
    }

    public void setResultList(List<InfoSection> resultList) {
        this.resultList = resultList;
    }
}
