package com.yuweilai.service;

import com.yuweilai.entity.Student;

/**
 * Created by 87734 on 2017/11/23.
 */
public interface IStudentService {
    int insertStudent(Student student);
    Student getStudentByStudent(Student student);
}
