package com.xxlllq.web.controller;

import com.xxlllq.dataprovider.common.OperateResultEntity;
import com.xxlllq.dataprovider.sys.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * @类名称： ErrorController
 * @类描述：错误页面配置
 * @创建人：xiangxl
 * @创建时间：2018/12/13 10:07
 * @version：
 */

@Controller
@RequestMapping(value = "/error")
public class ErrorController extends BaseController {
    /**
     * 401页面
     *
     * @return
     */
    @RequestMapping(value = "/unauthorized")
    @ResponseBody
    public Object unauthorized(HttpServletRequest request) {
        ModelAndView result = new ModelAndView();

        result.addObject("msg", "您暂无权限访问该请求，请联系管理员！");//信息
        result.addObject("code", "403");//状态码
        result.addObject("clazz", "AuthenticationException");//异常类
        result.addObject("url", request != null ? request.getRequestURI() : "");//url

        if (!isAjax(request)) {
            result.setViewName("error/info");
            return result;
        }

        return result.getModel();
    }

    /**
     * 404页面
     *
     * @return
     */
    @RequestMapping(value = "/notFound")
    public String notFound(HttpServletRequest request) {
        if (isAjax(request)) {
            return "redirect:/error/isAjax";
        }
        return "redirect:/error/whetherLogout";
    }

    /**
     * 500页面
     *
     * @return
     */
    @RequestMapping(value = "/serverError")
    public String serverError() {
        return "error/500";
    }


    /**
     * 是否登出
     *
     * @return
     */
    @RequestMapping(value = "/whetherLogout")
    public String whetherLogout() {
        User user = getCurrentUser();
        if (user != null) {
            return "redirect:/sys/user/";//登录状态就跳转到organization页面
        }

        return "redirect:/login/";//没有就跳转到login界面
    }

    /**
     * AJAX请求404错误
     *
     * @return
     */
    @RequestMapping(value = "/isAjax")
    @ResponseBody
    public Object isAjax() {
        OperateResultEntity resultEntity = new OperateResultEntity();
        resultEntity.setResult(false);
        resultEntity.setMsg("请求路径不存在!");
        return resultEntity;
    }
}
