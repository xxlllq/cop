package com.xxlllq.web.controller;


import com.xxlllq.dataprovider.entity.User;
import com.xxlllq.dataprovider.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xiangxl
 * @since 2018-12-12
 */
@RestController
@RequestMapping("/sys/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/index")
    public List<User> index() {
        logger.error("asd");
        List<User> list = userService.list();
        return list;
    }

    @RequestMapping("/add")
    public Object add() {
        logger.error("asd");
        User user = new User();
        user.setAge(12);
        return userService.saveOrUpdate(user);
    }

}
