package com.xxlllq.dataprovider.sys.pojo;

import java.time.LocalDateTime;
import com.xxlllq.dataprovider.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiangxl
 * @since 2018-12-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String descpt;

    /**
     * 角色编号
     */
    private String code;

}
