package com.yuweilai.dao;

import com.yuweilai.entity.IndexPic;
import com.yuweilai.entity.IndexPicExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IndexPicMapper {
    long countByExample(IndexPicExample example);

    int deleteByExample(IndexPicExample example);

    int insert(IndexPic record);

    int insertSelective(IndexPic record);

    List<IndexPic> selectByExample(IndexPicExample example);

    int updateByExampleSelective(@Param("record") IndexPic record, @Param("example") IndexPicExample example);

    int updateByExample(@Param("record") IndexPic record, @Param("example") IndexPicExample example);
}