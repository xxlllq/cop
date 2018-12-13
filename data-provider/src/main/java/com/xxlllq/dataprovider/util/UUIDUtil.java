package com.xxlllq.dataprovider.util;

import java.util.UUID;

/**
 * @类名称： UUIDUtil
 * @类描述：用于UUID的操作
 * @创建人：xiangxl
 * @创建时间：2018/12/13 13:18
 * @version：
 */
public class UUIDUtil {

    /**
     * 生成UUID
     *
     * @return
     */
    public static String generate() {
        return UUID.randomUUID().toString();
//        return UUID.randomUUID().toString().replace("_","");
    }

}
