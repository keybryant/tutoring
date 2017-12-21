package com.yuweilai.shrio;//package com.yuweilai.shrio;


import com.yuweilai.entity.Authority;
import com.yuweilai.entity.Role;
import com.yuweilai.entity.RoleAuthority;
import com.yuweilai.entity.UserBase;
import com.yuweilai.service.IRoleAuthorityService;
import com.yuweilai.service.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by 87734 on 2017/11/30.
 */
public class MyShiroRealm extends AuthorizingRealm {
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleAuthorityService roleAuthorityService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        System.out.println(principalCollection.getPrimaryPrincipal().toString());
//        UserBase userBase  = (UserBase)principalCollection.getPrimaryPrincipal();
        String account=principalCollection.getPrimaryPrincipal().toString();
        UserBase userBase=userService.getUserBaseByAccount(account);
        Role role=roleAuthorityService.getRoleById(userBase.getRoleId());
//        System.out.println(role.getRoleId()+role.getRoleName());
//        authorizationInfo.addRole(role.getRoleName());
//        List<RoleAuthority> roleAuthorityList=roleAuthorityService.getRoleAuthorityByRoleId(role.getRoleId());
//        List authorityList=new ArrayList<Authority>();
//        for(RoleAuthority roleAuthority:roleAuthorityList){
//            Authority authority=roleAuthorityService.getAuthority(roleAuthority.getAuthId());
//            authorityList.add(authority);
//            authorizationInfo.addStringPermission(authority.getAuthName());
//            System.out.println("auth"+authority.getAuthName());
//        }
        Set<String> roleSet = new HashSet<String>();
        roleSet.add(role.getRoleName());
        authorizationInfo.setRoles(roleSet);
        Set<String> permissionSet = new HashSet<String>();
        List<RoleAuthority> roleAuthorityList=roleAuthorityService.getRoleAuthorityByRoleId(role.getRoleId());
        for(RoleAuthority roleAuthority:roleAuthorityList){
            Authority authority=roleAuthorityService.getAuthority(roleAuthority.getAuthId());
            permissionSet.add(authority.getAuthName());
        }
        authorizationInfo.setStringPermissions(permissionSet);

        return authorizationInfo;
    }
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        System.out.println("MyShiroRealm.doGetAuthenticationInfo()");
        UsernamePasswordToken token=(UsernamePasswordToken) authenticationToken;
        System.out.println("userName"+token.getUsername());
        System.out.println("password"+token.getPassword());
        //获取用户的输入的账号.
        String account = (String)token.getPrincipal();
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        UserBase userBase = userService.getUserBaseByAccount(account);
        System.out.println("----->>userInfo="+userBase.getAccount());
        if(userBase == null||userBase.getUserState()!=1){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userBase.getAccount(), //用户名
                userBase.getPassword(), //密码
                getName()  //realm name
        );
        return authenticationInfo;
    }

}
