package com.yuweilai.dao;

import com.yuweilai.entity.OrderBase;
import com.yuweilai.entity.OrderBaseExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrderBaseMapper {
    long countByExample(OrderBaseExample example);

    int deleteByExample(OrderBaseExample example);

    int deleteByPrimaryKey(String orderId);

    int insert(OrderBase record);

    int insertSelective(OrderBase record);

    List<OrderBase> selectByExample(OrderBaseExample example);

    OrderBase selectByPrimaryKey(String orderId);

    int updateByExampleSelective(@Param("record") OrderBase record, @Param("example") OrderBaseExample example);

    int updateByExample(@Param("record") OrderBase record, @Param("example") OrderBaseExample example);

    int updateByPrimaryKeySelective(OrderBase record);

    int updateByPrimaryKey(OrderBase record);
}