package com.yuweilai.entity;

import java.util.List;

/**
 * Created by 87734 on 2017/12/8.
 */
public class Time {
    private String week;
    private List<String> clock;

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public List<String> getClock() {
        return clock;
    }

    public void setClock(List<String> clock) {
        this.clock = clock;
    }
}
