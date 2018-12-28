package com.xxlllq.dataprovider.sys.pojo;

import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    private String code;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 关联person表的Id
     */
    private String personId;

    /**
     * 状态（0：禁用，1：启用）
     */
    private Boolean status;

    /**
     * 用户账号有效期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date validateDate;

    /**
     * 备注
     */
    private String remark;

}
