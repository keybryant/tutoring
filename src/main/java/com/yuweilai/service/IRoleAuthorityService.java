package com.yuweilai.service;

import com.yuweilai.entity.Authority;
import com.yuweilai.entity.Role;
import com.yuweilai.entity.RoleAuthority;

import java.util.List;

/**
 * Created by 87734 on 2017/11/30.
 */
public interface IRoleAuthorityService {
     List<RoleAuthority> getRoleAuthorityByRoleId(String roleId);
     Role getRoleById(String id);
     Authority getAuthority(String id);
     Role getRole(String roleName);
}
