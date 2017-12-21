package com.yuweilai.dao;

import com.yuweilai.entity.OrderInfo;
import com.yuweilai.entity.OrderInfoExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface OrderInfoMapper {
    List<Map>selectByUserId(OrderInfoExample example);
    long countByExample(OrderInfoExample example);

    int deleteByExample(OrderInfoExample example);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    List<OrderInfo> selectByExample(OrderInfoExample example);

    int updateByExampleSelective(@Param("record") OrderInfo record, @Param("example") OrderInfoExample example);

    int updateByExample(@Param("record") OrderInfo record, @Param("example") OrderInfoExample example);
}