package com.yuweilai.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 87734 on 2017/12/12.
 */
public class InfoSection {
    String title;
    List<Row> infoList;
    public InfoSection(String title){
        this.title=title;
        infoList=new ArrayList<>();
    }
    public void add(Row row){
        this.infoList.add(row);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Row> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<Row> infoList) {
        this.infoList = infoList;
    }
}
