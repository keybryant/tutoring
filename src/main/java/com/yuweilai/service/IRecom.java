package com.yuweilai.service;

import com.yuweilai.entity.Evaluate;
import com.yuweilai.entity.TeacherInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by 87734 on 2017/11/22.
 */
public interface IRecom {
    List<TeacherInfo> getRecom(int pageNum,int pageSize);
}
