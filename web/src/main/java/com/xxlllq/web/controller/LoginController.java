package com.xxlllq.web.controller;

import com.xxlllq.dataprovider.sys.pojo.User;
import com.xxlllq.dataprovider.sys.service.IUserService;
import com.xxlllq.dataprovider.util.SnowFlakeUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = {"/login"})
public class LoginController extends BaseController {
    @Autowired
    private IUserService userService;

    /**
     * 跳转到登录页
     *
     * @return
     */
    /**
     * 进入对应index界面
     *
     * @return
     */
    @RequestMapping(value = "/")
    @Override
    public String index(HttpServletRequest request) {
        try {

            User user = getCurrentUser();
            //无当前用户信息，跳转到登录页。
            if (user == null)
                return "login/index";
            else
                return "redirect:/sys/user/";

        } catch (Exception ex) {
            logger.error(ex);
        }
        return "login/index";
    }

    /**
     * 登录按钮，点击提交
     *
     * @param user
     * @param attributes
     * @return
     */
    @RequestMapping(value = "/submit", method = {RequestMethod.POST})
    public String loginSubmit(User user, Boolean rememberMe, RedirectAttributes attributes) {
        String defaultUrl = "redirect:/login/";
        if (user == null) {
            attributes.addFlashAttribute("msg", "请求参数有误！");
            return defaultUrl;
        }
        if (StringUtils.isBlank(user.getCode()) || StringUtils.isBlank(user.getPassword())) {
            attributes.addFlashAttribute("msg", "用户名或密码不能为空");
            return defaultUrl;
        }

        try {
            //用户是否存在
            User DbUser = userService.getUserByCode(user.getCode());
            if (DbUser == null) {
                attributes.addFlashAttribute("msg", "该账号不存在或账号已过有效期！");
                return defaultUrl;
            }

            // 1、 封装用户名、密码、是否记住我到token令牌对象 [支持记住我]
            UsernamePasswordToken token = new UsernamePasswordToken(
                    user.getCode(), user.getPassword());
            token.setRememberMe(rememberMe == null ? false : true);
            // 2、 Subject调用login
            Subject subject = SecurityUtils.getSubject();
            if (subject != null) {
                // 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
                // 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
                // 所以这一步在调用login(token)方法时,它会走到AuthRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
                subject.login(token);
                defaultUrl = "redirect:/sys/user/";
            }
        } catch (AuthenticationException auth) {
            attributes.addFlashAttribute("msg", "登录认证失败！");
        } catch (Exception ex) {
            logger.error("token与credentials不匹配！" + user.getCode() + "," + user.getPassword(), ex);
            attributes.addFlashAttribute("msg", "系统认证错误！");
        }
        return defaultUrl;
    }

    /**
     * 功能描述: 登出
     *
     * @param:
     * @return:
     * @auther: xiangxl
     * @date: 2018/10/22 8:38
     */
    @RequestMapping(value = "/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:/login/";
    }

}
