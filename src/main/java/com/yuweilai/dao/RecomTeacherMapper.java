package com.yuweilai.dao;

import com.yuweilai.entity.RecomTeacher;
import com.yuweilai.entity.RecomTeacherExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RecomTeacherMapper {
    long countByExample(RecomTeacherExample example);

    int deleteByExample(RecomTeacherExample example);

    int insert(RecomTeacher record);

    int insertSelective(RecomTeacher record);

    List<RecomTeacher> selectByExample(RecomTeacherExample example);

    int updateByExampleSelective(@Param("record") RecomTeacher record, @Param("example") RecomTeacherExample example);

    int updateByExample(@Param("record") RecomTeacher record, @Param("example") RecomTeacherExample example);
}