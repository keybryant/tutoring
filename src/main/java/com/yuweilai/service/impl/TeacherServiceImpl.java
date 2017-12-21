package com.yuweilai.service.impl;

import com.yuweilai.dao.RecomTeacherMapper;
import com.yuweilai.dao.TeacherInfoMapper;
import com.yuweilai.dao.UserBaseMapper;
import com.yuweilai.entity.*;
import com.yuweilai.service.IRoleAuthorityService;
import com.yuweilai.service.ITeacherService;
import com.yuweilai.service.IUserService;
import com.yuweilai.util.FormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 87734 on 2017/11/23.
 */
@Service
public class TeacherServiceImpl implements ITeacherService {
    @Autowired
    private TeacherInfoMapper teacherInfoMapper;
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleAuthorityService roleAuthorityService;
    @Autowired
    private RecomTeacherMapper recomTeacherMapper;
    /*
    @method:保存老师信息
     */
    @Override
    public Integer insertTeacher(TeacherInfo teacherInfo) {
        UserBase userBase=new UserBase();
        userBase.setUserId(teacherInfo.getUserId());
        Role role=roleAuthorityService.getRole("TEACHER");
        userService.updateRoleId(userBase,role.getRoleId());
        return  teacherInfoMapper.insertSelective(teacherInfo);
    }
    /*
        @method:根据id获取老师
         */
    @Override
    public TeacherInfo getTeacherById(String id) {
        TeacherInfoExample teacherInfoExample=new TeacherInfoExample();
        TeacherInfoExample.Criteria criteria=teacherInfoExample.createCriteria();
        criteria.andUserIdEqualTo(id);
        criteria.andStateNotEqualTo(0);//老师没有被注销
        List<TeacherInfo> teacherInfoList=teacherInfoMapper.selectByExample(teacherInfoExample);
        if(!teacherInfoList.isEmpty()){
            if(teacherInfoList.get(0).getCourse()!=null) {
                teacherInfoList.get(0).setCourse(FormatUtil.format(teacherInfoList.get(0).getCourse()));
            }
            if(teacherInfoList.get(0).getGrade()!=null){
                teacherInfoList.get(0).setGrade(FormatUtil.format(teacherInfoList.get(0).getGrade()));
            }
            return teacherInfoList.get(0);
        }
        return null;
    }
    /*
          @method:更新老师信息
           */
    @Override
    public Integer updateTeacher(TeacherInfo teacherInfo, TeacherInfoExample teacherInfoExample) {
        return teacherInfoMapper.updateByExampleSelective(teacherInfo,teacherInfoExample);
    }
    @Override
    public List<TeacherInfo> getRecomTeacherInfoList(String orderId){
        List<TeacherInfo> teacherInfoList=new ArrayList<>();
        RecomTeacherExample recomTeacherExample=new RecomTeacherExample();
        RecomTeacherExample.Criteria criteria=recomTeacherExample.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        List<RecomTeacher> recomTeacherList=recomTeacherMapper.selectByExample(recomTeacherExample);
        if(!recomTeacherList.isEmpty()){
            for(RecomTeacher recomTeacher:recomTeacherList){
                TeacherInfo teacherInfo=getTeacherById(recomTeacher.getRecomTeacherId());
                teacherInfoList.add(teacherInfo);
            }
            for(TeacherInfo teacherInfo:teacherInfoList){
                if(teacherInfo.getGrade()!=null){
                    teacherInfo.setGrade(FormatUtil.format(teacherInfo.getGrade()));
                }
                if(teacherInfo.getCourse()!=null){
                    teacherInfo.setCourse(FormatUtil.format(teacherInfo.getCourse()));
                }

            }
            return teacherInfoList;
        }
        return null;
    }

}
