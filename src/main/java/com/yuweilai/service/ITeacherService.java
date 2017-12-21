package com.yuweilai.service;

import com.yuweilai.entity.TeacherInfo;
import com.yuweilai.entity.TeacherInfoExample;

import java.util.List;

/**
 * Created by 87734 on 2017/11/23.
 */
public interface ITeacherService {
    Integer insertTeacher(TeacherInfo teacherInfo);
    TeacherInfo getTeacherById(String id);
    Integer updateTeacher(TeacherInfo teacherInfo, TeacherInfoExample teacherInfoExample);
    List<TeacherInfo> getRecomTeacherInfoList(String orderId);
}
