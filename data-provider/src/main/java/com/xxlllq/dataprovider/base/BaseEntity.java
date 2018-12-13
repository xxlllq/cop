package com.xxlllq.dataprovider.base;

import lombok.Data;

import java.util.Date;

/**
 * @类名称： BaseEntity
 * @类描述：公共的Entity
 * @创建人：xiangxl
 * @创建时间：2018/12/13 12:49
 * @version：
 */
@Data
public class BaseEntity {
    public String id;
    /**
     * 添加数据的用户id
     */
    private String createUid;

    /**
     * 更新数据的用户id
     */
    private String updateUid;

    /**
     * 数据新建时间
     */
    private Date createTime;

    /**
     * 数据更新时间
     */
    private Date updateTime;
}
