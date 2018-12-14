package com.xxlllq.dataprovider.sys.entity;

import com.xxlllq.dataprovider.sys.pojo.Permission;
import com.xxlllq.dataprovider.sys.pojo.Role;
import lombok.Data;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author xiangxl
 * @since 2018-12-13
 */
@Data
public class RoleEntity extends Role {

    /**
     * 角色关联的权限列表
     *
     * 1-->n（一对多）
     */
    private List<PermissionEntity> permissions;

}
