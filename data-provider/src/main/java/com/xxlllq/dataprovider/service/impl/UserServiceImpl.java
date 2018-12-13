package com.xxlllq.dataprovider.service.impl;

import com.xxlllq.dataprovider.entity.User;
import com.xxlllq.dataprovider.mapper.UserMapper;
import com.xxlllq.dataprovider.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiangxl
 * @since 2018-12-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
