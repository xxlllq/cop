package com.xxlllq.web.controller.sys;


import com.xxlllq.dataprovider.sys.pojo.User;
import com.xxlllq.dataprovider.sys.service.IUserService;
import com.xxlllq.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xiangxl
 * @since 2018-12-12
 */
@Controller
@RequestMapping("/sys/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/index")
    public String index() {
        logger.error("as56d67");
        List<User> list = userService.list();
        return "user/index";
    }

    @RequestMapping("/add")
    public Object add() {
        logger.error("asd");
        try {
            User user = new User();
            user.setCode("asd");
            return userService.saveOrUpdate(user);
        } catch (Exception ex) {
            logger.error(ex);
        }
        return null;
    }

}
