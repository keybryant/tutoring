package com.yuweilai.service.impl;

import com.yuweilai.dao.AuthorityMapper;
import com.yuweilai.dao.RoleAuthorityMapper;
import com.yuweilai.dao.RoleMapper;
import com.yuweilai.entity.*;
import com.yuweilai.service.IRoleAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 87734 on 2017/11/30.
 */
@Service
public class RoleAuthorityServiceImpl implements IRoleAuthorityService {
    @Autowired
    private RoleAuthorityMapper roleAuthorityMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AuthorityMapper authorityMapper;
    /*
    @Method:通过角色id获取角色权限对应表
     */
    @Override
    public List<RoleAuthority> getRoleAuthorityByRoleId(String roleId) {
        RoleAuthorityExample roleAuthorityExample=new RoleAuthorityExample();
        RoleAuthorityExample.Criteria criteria=roleAuthorityExample.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        List<RoleAuthority>roleAuthorityList= roleAuthorityMapper.selectByExample(roleAuthorityExample);
        return roleAuthorityList;
    }
    /*
    @Method:通过角色id获取角色表
     */

    @Override
    public Role getRoleById(String id) {
        return roleMapper.selectByPrimaryKey(id);
    }
    /*
      @Method:通过权限id获取权限表
       */
    @Override
    public Authority getAuthority(String id) {
        AuthorityExample authorityExample=new AuthorityExample();
        AuthorityExample.Criteria criteria=authorityExample.createCriteria();
        criteria.andAuthIdEqualTo(id);
        List<Authority> authorityList= authorityMapper.selectByExample(authorityExample);
        if(!authorityList.isEmpty()){
            return authorityList.get(0);
        }
        return null;
    }
    @Override
    public Role getRole(String roleName) {
        RoleExample roleExample=new RoleExample();
        RoleExample.Criteria criteria=roleExample.createCriteria();
        criteria.andRoleNameEqualTo(roleName);
        List<Role> list=roleMapper.selectByExample(roleExample);
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }
}
