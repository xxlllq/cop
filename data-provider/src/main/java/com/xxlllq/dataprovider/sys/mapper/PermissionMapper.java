package com.xxlllq.dataprovider.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxlllq.dataprovider.sys.pojo.Permission;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author xiangxl
 * @since 2018-12-13
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> getPermissionUrlNotNull();
}
