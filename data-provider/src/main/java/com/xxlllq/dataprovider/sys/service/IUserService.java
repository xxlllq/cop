package com.xxlllq.dataprovider.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxlllq.dataprovider.sys.pojo.User;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xiangxl
 * @since 2018-12-13
 */
public interface IUserService extends IService<User> {

    /**
     * 通过账号获取用户（系统中的账号唯一）
     *
     * @param code
     * @return
     */
    User getUserByCode(String code);
}
