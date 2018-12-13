package com.xxlllq.shiro.auth;

import com.xxlllq.dataprovider.sys.entity.PermissionEntity;
import com.xxlllq.dataprovider.sys.entity.RoleEntity;
import com.xxlllq.dataprovider.sys.pojo.User;
import com.xxlllq.dataprovider.sys.service.IRoleService;
import com.xxlllq.dataprovider.sys.service.IUserService;
import com.xxlllq.dataprovider.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @类名称： AuthRealm
 * @类描述：权限验证
 * @创建人：xiangxl
 * @创建时间：2018/12/13 11:44
 * @version：
 */
@Service
public class AuthRealm extends AuthorizingRealm {
    protected Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    @Lazy
    private IUserService userService;
    @Autowired
    @Lazy
    private IRoleService roleService;

    /**
     * 功能描述: 认证.登录
     *
     * @param:
     * @return:
     * @auther: xiangxl
     * @date: 2018/10/21 15:47
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken utoken = (UsernamePasswordToken) token;//获取用户输入的token
        String username = utoken.getUsername();
        User user = userService.getUserByCode(username);
        if (user == null) {
            // 用户不存在
            return null;
        } else {
            // 密码盐值
            ByteSource salt = ByteSource.Util.bytes(user.getCode() + user.getSalt());

            // 密码存在
            // 第一个参数 ，登陆后，需要在session保存数据
            // 第二个参数，查询到密码(加密规则要和自定义的HashedCredentialsMatcher中的HashAlgorithmName散列算法一致)
            // 第三个参数 ，salt 盐
            // 第四个参数 ，realm名字
            //放入shiro.调用CredentialsMatcher检验密码
            return new SimpleAuthenticationInfo(user, user.getPassword(), salt,
                    getName());
        }
    }

    /**
     * 功能描述: 授予角色和权限
     *
     * @param:
     * @return:
     * @auther: xiangxl
     * @date: 2018/10/21 15:47
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principalCollection) {
        // 添加权限 和 角色信息
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 获取当前登陆用户
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            User user = (User) subject.getPrincipal();
            if (user != null) {
                if (user.getCode().equals("admin")) {
                    // 超级管理员，添加所有角色、添加所有权限
                    List<RoleEntity> roleEntities = roleService.list();
                    if (roleEntities != null && !roleEntities.isEmpty()) {
                        roleEntities.forEach(m -> {
                            authorizationInfo.addRole(m.getCode());
                        });
                    } else
                        authorizationInfo.addRole("administrator");

                    authorizationInfo.addStringPermission("*");
                } else {
                    // 普通用户，查询用户的角色，根据角色查询权限
                    try {
                        List<RoleEntity> rolePermissions = roleService.getRoleAndPermissionsByUserId(user.getId());
                        if (rolePermissions != null && !rolePermissions.isEmpty()) {
                            List<String> roleList = new ArrayList<>();
                            List<String> permissionList = new ArrayList<>();

                            for (int i = 0; i < rolePermissions.size(); i++) {
                                RoleEntity rp = rolePermissions.get(i);
                                if (rp != null && !StringUtils.isBlank(rp.getCode())) {
                                    if ("administrator".equals(rp.getCode())) {
                                        roleList.clear();
                                        roleList.add(rp.getCode());//角色
                                        permissionList.clear();
                                        permissionList.add("*");//权限
                                        break;
                                    } else {
                                        //角色
                                        roleList.add(rp.getCode());
                                        // 授权角色下所有权限
                                        List<PermissionEntity> permissions = rp.getPermissions();
                                        if (permissions != null && !permissions.isEmpty())
                                            permissions.forEach(p -> {
                                                if (!StringUtils.isBlank(p.getCode())) {
                                                    String permission = StringUtil.getBracketStr(p.getCode());
                                                    if (!StringUtils.isBlank(permission))
                                                        permissionList.add(permission);
                                                }
                                            });
                                    }
                                }
                            }

                            authorizationInfo.addRoles(roleList);//角色
                            authorizationInfo.addStringPermissions(permissionList);//权限

                        }
                    } catch (Exception ex) {
                        logger.error("获取用户权限错误！", ex);
                    }
                }
            }
        }
        return authorizationInfo;
    }
}
