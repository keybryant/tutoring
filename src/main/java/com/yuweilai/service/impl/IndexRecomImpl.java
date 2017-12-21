package com.yuweilai.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuweilai.dao.EvaluateMapper;
import com.yuweilai.dao.TeacherInfoMapper;
import com.yuweilai.dao.UserBaseMapper;
import com.yuweilai.entity.Evaluate;
import com.yuweilai.entity.TeacherInfo;
import com.yuweilai.entity.EvaluateExample;
import com.yuweilai.entity.TeacherInfoExample;
import com.yuweilai.service.IRecom;
import com.yuweilai.util.FormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 87734 on 2017/11/22.
 */
@Service
public class IndexRecomImpl implements IRecom {
    @Autowired
    private UserBaseMapper userBaseMapper;
    @Autowired
    private TeacherInfoMapper teacherInfoMapper;
    /*
    method:首页获取推荐评价信息
     */
    @Override
    public List<TeacherInfo> getRecom(int pageNum,int pageSize) {
        TeacherInfoExample teacherInfoExample=new TeacherInfoExample();
        teacherInfoExample.setOrderByClause("SCORE DESC");
        PageHelper.startPage(pageNum, pageSize);
        List<TeacherInfo> teacherInfoList=teacherInfoMapper.selectByExample(teacherInfoExample);
        if(!teacherInfoList.isEmpty()){
            for(TeacherInfo teacherInfo:teacherInfoList){
                teacherInfo.setGrade(FormatUtil.format(teacherInfo.getGrade()));
                teacherInfo.setCourse(teacherInfo.getCourse().replaceAll("(\\[|\\]|\")",""));
            }
            return teacherInfoList;
        }
        return null;
    }
}
