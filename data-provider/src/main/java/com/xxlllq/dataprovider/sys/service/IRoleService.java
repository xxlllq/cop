package com.xxlllq.dataprovider.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxlllq.dataprovider.sys.entity.RoleEntity;
import com.xxlllq.dataprovider.sys.pojo.Role;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiangxl
 * @since 2018-12-13
 */
public interface IRoleService extends IService<Role> {

    /**
     * 获取指定用户具有的角色和权限
     * @param id
     * @return
     */
    List<RoleEntity> getRoleAndPermissionsByUserId(String id);
}
