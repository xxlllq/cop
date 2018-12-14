package com.xxlllq.dataprovider.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxlllq.dataprovider.sys.entity.RoleEntity;
import com.xxlllq.dataprovider.sys.mapper.RoleMapper;
import com.xxlllq.dataprovider.sys.service.IRoleService;
import org.springframework.stereotype.Service;

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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements IRoleService {
    /**
     * 获取指定用户具有的角色和权限
     *
     * @param id
     * @return
     */
    @Override
    public List<RoleEntity> getRoleAndPermissionsByUserId(String id) {
        return null;
    }
}
