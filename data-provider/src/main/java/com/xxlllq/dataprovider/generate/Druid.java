package com.xxlllq.dataprovider.generate;

import com.alibaba.druid.filter.config.ConfigTools;

/**
 * @类名称： Druid
 * @类描述：Druid相关值生成
 * @创建人：xiangxl
 * @创建时间：2018/12/14 8:31
 * @version：
 */
public class Druid {
    public static void main(String[] args) {
        System.out.print(decrypt("bNVOqb7WKLX5Bjnw+LMv92taj25KOxDimXxILPQjw42wgv+1lHzOH8kr97xDwWdhpY67QuYCS7sWN4W46YbkFA=="));
    }

    /**
     * 加密
     *
     * @param string 加密前字符串
     * @return
     */
    static String encrypt(String string) {
        try {
            return ConfigTools.encrypt(string);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param string 密文
     * @return
     */
    static String decrypt(String string) {
        try {
            return ConfigTools.decrypt(string);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
