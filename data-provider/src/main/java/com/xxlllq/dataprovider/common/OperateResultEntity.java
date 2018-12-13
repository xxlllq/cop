package com.xxlllq.dataprovider.common;

import lombok.Data;

/**
 * @类名称： OperateResultEntity
 * @类描述：返回消息模型，多用于CRUD的消息提示
 * @创建人：xiangxl
 * @创建时间：2018/12/13 17:54
 * @version：
 */
@Data
public class OperateResultEntity {
    public boolean result;
    public String msg;
    public Object data;
    public String id;//新增成功返回的id

}
