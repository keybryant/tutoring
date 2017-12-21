package com.yuweilai.service.impl;

import com.yuweilai.dao.StudentMapper;
import com.yuweilai.entity.Student;
import com.yuweilai.entity.StudentExample;
import com.yuweilai.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 87734 on 2017/11/23.
 */
@Service
public class StudentServiceImpl implements IStudentService {
    @Autowired
    private StudentMapper studentMapper;
    /*
    @methode:保存学生信息
     */
    @Override
    public int insertStudent(Student student) {
        return studentMapper.insertSelective(student);

    }
/*
@method:
 */
    @Override
    public Student getStudentByStudent(Student student) {
        StudentExample studentExample=new StudentExample();
        StudentExample.Criteria criteria=studentExample.createCriteria();
        return null;
    }
}
