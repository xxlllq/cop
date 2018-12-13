package com.xxlllq.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @类名称： ControllerExceptionHandler
 * @类描述：Controller全局异常处理
 * @创建人：xiangxl
 * @创建时间：2018/12/13 10:06
 * @version：
 */
@RestControllerAdvice
public class ControllerExceptionHandler extends BaseController {

    /**
     * 公共异常处理
     * <p>
     * AJAX返回JSON，不是AJAX返回视图
     *
     * @param request
     * @param ex
     * @return
     * @throws Exception
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object exceptionHandler(HttpServletRequest request, Exception ex) throws Exception {
        ModelAndView result = new ModelAndView();
        String exMsg = ex.getMessage(), msg = "其他异常！", code = "204";

        if (!StringUtils.isBlank(exMsg)) {
            //未登录
            if (exMsg.contains("it does not have any identifying principals and authorization")) {
                msg = "请登录，然后重新请求！";
                result.addObject("urlExt", "/login/");
                code = "401";
            }
            //没有权限
            else if (exMsg.contains("Subject does not have")) {
                msg = "您暂无权限访问该请求，请联系管理员！";
                code = "403";
            }
            //请求方法不支持
            else if (exMsg.contains("Request method 'GET'")) {
                msg = "该接口不支持GET请求方法！";
                code = "405";
            }
        }
        result.addObject("msg", msg);//信息
        result.addObject("code", code);//状态码

        String clazz = ex.getClass() != null ? ex.getClass().toString() : "";
        result.addObject("clazz", clazz.substring(clazz.lastIndexOf(".") + 1));//异常类
        result.addObject("url", request != null ? request.getRequestURI() : "");//url

        if (!isAjax(request)) {
            result.setViewName("error/info");
            return result;
        }

        return result.getModel();
    }

    /**
     * 异常消息返回结果
     */
    public class ExceptionMsg {
        private String msg;
        private Integer code;
        private String url;
        private String urlExt;
        private String clazz;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrlExt() {
            return urlExt;
        }

        public void setUrlExt(String urlExt) {
            this.urlExt = urlExt;
        }

        public String getClazz() {
            return clazz;
        }

        public void setClazz(String clazz) {
            this.clazz = clazz;
        }
    }
}
