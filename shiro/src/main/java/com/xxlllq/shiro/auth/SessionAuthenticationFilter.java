package com.xxlllq.shiro.auth;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @类名称： SessionAuthenticationFilter
 * @类描述：控制系统用户在session生命周期的跳转 无session时：浏览器链接，跳转到登录页
 * AJAX请求，返回相应的Json提示信息
 * @创建人：xiangxl
 * @创建时间：2018/12/13 11:45
 * @version：
 */
public class SessionAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            } else {
                // 放行
                return true;
            }
        } else {
            Subject subject = getSubject(request, response);
            // 如果是记住我登录的，则需要处理一下
            // isRemembered为true、isAuthenticated为false
            if (!subject.isAuthenticated() && subject.isRemembered()) {
                // 通过记住我第一次进程序，并且保存的principal中有内容，添加用户到session
                Object principal = subject.getPrincipal();
                if (principal != null) {
                    Session subSession = subject.getSession();
                    if (subSession.getAttribute("user") == null)//用户没有session，但是记住我中有数据，复制给当前用户session
                        subSession.setAttribute("user", principal);
                    return true;
                }

                return false;
            }

            HttpServletRequest httpRequest = WebUtils.toHttp(request);
            //当AJAX请求时，session过期，返回相关参数，供JS控制
            if (isAjax(httpRequest)) {
                HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
                httpServletResponse.setHeader("sessionStatus", "timeout");
                httpServletResponse.sendError(520, "session timeout");
                return true;
            }
            //如果为普通的浏览器请求，则直接返回登录页面
            else {
                saveRequestAndRedirectToLogin(request, response);
            }
            return false;
        }
    }

    /**
     * 判断是否为AJAX请求
     *
     * @param request
     * @return
     */
    boolean isAjax(HttpServletRequest request) {
        String requestedWith = request.getHeader("X-Requested-With");
        return !StringUtils.isBlank(requestedWith) && "ISAJAX".equals(requestedWith);
    }

}