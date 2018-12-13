package com.xxlllq.shiro.auth;

import com.xxlllq.dataprovider.entity.User;
import com.xxlllq.dataprovider.service.IUserService;
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
//    @Autowired
//    @Lazy
//    private RoleService roleService;

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
        User user = userService.getById(username);
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
            }
        }
        return authorizationInfo;
    }
}
