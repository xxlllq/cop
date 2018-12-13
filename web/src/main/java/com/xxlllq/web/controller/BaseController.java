package com.xxlllq.web.controller;

import com.xxlllq.dataprovider.sys.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.subject.Subject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
    protected Logger logger = Logger.getLogger(this.getClass());

    /**
     * 进入对应index界面，满足RESTful风格
     *
     * @return
     */
    @RequestMapping(value = "/")
    public String index(HttpServletRequest request) {
        String url = "";
        try {
            if (request != null) {
                url = request.getServletPath();
                if (!StringUtils.isBlank(url)) {
                    url = url.substring(1);//将/sys/user变成sys/user
                    if (!"login".equals(url))
                        return url + "/index";
                }
            }
        } catch (Exception ex) {
            logger.error(ex);
        }
        return "main/index";
    }

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
