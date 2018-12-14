package com.xxlllq.dataprovider.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxlllq.dataprovider.sys.mapper.UserMapper;
import com.xxlllq.dataprovider.sys.pojo.User;
import com.xxlllq.dataprovider.sys.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xiangxl
 * @since 2018-12-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    /**
     * 通过账号获取用户（系统中的账号唯一）
     *
     * @param code
     * @return
     */
    @Override
    public User getUserByCode(String code) {
        return getOne(new QueryWrapper<User>()
                .eq("status",1)//有效
                .gt("validate_date", new Date())//用户账号有效期
                .eq("code", code));
    }
}
