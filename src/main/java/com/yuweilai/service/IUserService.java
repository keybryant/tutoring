package com.yuweilai.service;

import com.yuweilai.entity.UserBase;
import com.yuweilai.entity.UserInfo;
import com.yuweilai.entity.UserBaseExample;
import com.yuweilai.entity.UserInfoExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 87734 on 2017/11/21.
 */
public interface IUserService {

    List<UserInfo>getUserInfoListByIdList(List<String> list);
    int insertUserBase(UserBase record);
    List<UserBase> selectUserBaseByExample(UserBaseExample example);
    int insertUserInfo(UserInfo record);
    Integer updateUserInfo(UserInfo userInfo,UserInfoExample userInfoExample);
    UserBase getUserBaseByAccount(String account);
    UserInfo getUserInfoByUserId(String userId);
    int updateUserInfoByUserId(String userId,UserInfo userInfo);
    int updateRoleId(UserBase userBase,String roleId);
}
