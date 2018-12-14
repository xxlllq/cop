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
public class Permission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 编码，对应权限代号
     */
    private String code;

    /**
     * 菜单url
     */
    private String url;

    /**
     * 唯一标识页面中的元素，用于Jquery查找
     */
    private String menuId;

    /**
     * 父菜单id
     */
    private String parentId;

    /**
     * 菜单排序
     */
    private Integer zindex;

    /**
     * 权限分类（0 菜单；1（子菜单），2（按钮） 功能，3（其他））
     */
    private Integer typ;

    /**
     * 描述
     */
    private String descpt;

    /**
     * 菜单图标名称
     */
    private String icon;

    /**
     * 是否有效
     */
    private Boolean enable;
}
