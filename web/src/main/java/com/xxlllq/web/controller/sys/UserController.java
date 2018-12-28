package com.xxlllq.web.controller.sys;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxlllq.dataprovider.sys.pojo.User;
import com.xxlllq.dataprovider.sys.service.IUserService;
import com.xxlllq.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户Controller
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

    @RequestMapping("/query")
    @ResponseBody
    public Object index() {
        IPage<User> lsd = userService.page(new Page<>(0, 1));
        return lsd;
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
