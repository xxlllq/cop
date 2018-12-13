package com.xxlllq.web.controller;

import com.xxlllq.dataprovider.sys.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
    protected Logger logger = Logger.getLogger(this.getClass());

    /**
     * 获取当前系统用户
     *
     * @return
     */
    public User getCurrentUser() {
        try {
            Subject subject = SecurityUtils.getSubject();
            if (subject != null) {
                User user = (User) subject.getPrincipal();
                return user;
            }
        } catch (UnavailableSecurityManagerException usme) {
            logger.error("获取用户失败！", usme);
        }
        return null;
    }

    /**
     * 判断请求与是否为AJAX
     *
     * @param request
     * @return
     */
    public boolean isAjax(HttpServletRequest request) {
        String requestedWith = request.getHeader("X-Requested-With");
        return !StringUtils.isBlank(requestedWith) && "ISAJAX".equals(requestedWith);
    }
}
