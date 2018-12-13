package com.xxlllq.shiro.auth;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * @类名称： CredentialsMatcher
 * @类描述：密码验证器
 * @创建人：xiangxl
 * @创建时间：2018/12/13 11:45
 * @version：
 */
public class CredentialsMatcher extends SimpleCredentialsMatcher {
    protected Logger logger = Logger.getLogger(this.getClass());

    /**
     * shiro管理密码与数据库密码比较
     *
     * @param token
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        if (token == null || info == null)
            return false;

        try {
            //用户登录提交的账号和密码
            String code = (String) token.getPrincipal(), password = new String((char[]) token.getCredentials());
            if (StringUtils.isBlank(code) || StringUtils.isBlank(password))
                return false;

            SimpleAuthenticationInfo inf = (SimpleAuthenticationInfo) info;
            if (inf == null)
                return false;

            //用户提交密码经过转换后的MD5
            String commitPwd = DigestUtils.md5Hex(DigestUtils.md5Hex(password) + inf.getCredentialsSalt());
            if (!StringUtils.isBlank(commitPwd) && commitPwd.equals(inf.getCredentials().toString()))
                return true;

        } catch (ClassCastException cast) {
            logger.error("数据类型转换错误！", cast);
            return false;
        } catch (Exception ex) {
            logger.error("其他异常！", ex);
            return false;
        }
        return false;

    }
}