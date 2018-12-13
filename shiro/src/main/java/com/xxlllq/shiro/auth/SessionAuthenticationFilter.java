package com.xxlllq.shiro.auth;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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