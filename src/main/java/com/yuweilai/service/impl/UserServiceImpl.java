package com.yuweilai.service.impl;

import com.yuweilai.dao.UserBaseMapper;
import com.yuweilai.dao.UserInfoMapper;
import com.yuweilai.entity.UserBase;
import com.yuweilai.entity.UserInfo;
import com.yuweilai.entity.UserBaseExample;
import com.yuweilai.entity.UserInfoExample;
import com.yuweilai.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 87734 on 2017/11/21.
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserBaseMapper userBaseMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    /*
      @method:保存用户基本信息
       */
    @Override
    public int insertUserBase(UserBase record) {
        return userBaseMapper.insert(record);
    }
    /*
     @method:根据条件返回基本用户
      */
    @Override
    public List<UserBase> selectUserBaseByExample(UserBaseExample example) {
        List<UserBase> list=userBaseMapper.selectByExample(example);
        if(!list.isEmpty()){
            return list;
        }
        return null;
    }

    @Override
    public List<UserInfo> getUserInfoListByIdList(List<String> list) {
        UserInfoExample userInfoExample=new UserInfoExample();
        UserInfoExample.Criteria criteria=userInfoExample.createCriteria();
        for(String id:list){
            UserInfoExample.Criteria criteria1=userInfoExample.createCriteria();
            criteria1.andUserIdEqualTo(id);
            userInfoExample.or(criteria1);
        }
        List<UserInfo> userInfoList=userInfoMapper.selectByExample(userInfoExample);
        if(!list.isEmpty()){
            return userInfoList;
        }
        return null;
    }
    /*
        @method:保存用户信息
         */
    @Override
    public int insertUserInfo(UserInfo record) {
        return userInfoMapper.insertSelective(record);
    }
    /*
          @method:修改用户信息
           */
    @Override
    public Integer updateUserInfo(UserInfo userInfo,UserInfoExample userInfoExample) {
        return userInfoMapper.updateByExampleSelective(userInfo,userInfoExample);
    }
    /*
             @method:根据用户名获取用户基础信息
              */
    @Override
    public UserBase getUserBaseByAccount(String account) {
        UserBaseExample userBaseExample=new UserBaseExample();
        UserBaseExample.Criteria criteria=userBaseExample.createCriteria();
        criteria.andAccountEqualTo(account);
        List<UserBase> userBaseList=userBaseMapper.selectByExample(userBaseExample);
        if(!userBaseList.isEmpty()){
            return userBaseList.get(0);
        }
        return null;
    }
    /*
                @method:根据用户id获取用户信息
                 */
    @Override
    public UserInfo getUserInfoByUserId(String userId) {
        UserInfoExample userInfoExample=new UserInfoExample();
        UserInfoExample.Criteria criteria=userInfoExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<UserInfo>list=userInfoMapper.selectByExample(userInfoExample);
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }
    /*
                    @method:根据用户id更新用户信息
                     */
    @Override
    public int updateUserInfoByUserId(String userId,UserInfo userInfo) {
        UserInfoExample userInfoExample=new UserInfoExample();
        UserInfoExample.Criteria criteria=userInfoExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        return userInfoMapper.updateByExampleSelective(userInfo,userInfoExample);

    }

    @Override
    public int updateRoleId(UserBase record,String roleId) {
        UserBase userBase=userBaseMapper.selectByPrimaryKey(record.getUserId());
        userBase.setRoleId(roleId);
        return userBaseMapper.updateByPrimaryKeySelective(userBase);

    }
}
