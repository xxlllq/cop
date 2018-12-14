package com.xxlllq.shiro.util;

import org.apache.shiro.util.ByteSource;

/**
 * @类名称： ShiroUtil
 * @类描述：Shiro工具类
 * @创建人：xiangxl
 * @创建时间：2018/12/13 13:08
 * @version：
 */
public class ShiroUtil {

    /**
     * 生成ByteSource
     *
     * @param string
     * @return
     */
    public static ByteSource convertByteSource(String string) {
        return ByteSource.Util.bytes(string);
    }
}
