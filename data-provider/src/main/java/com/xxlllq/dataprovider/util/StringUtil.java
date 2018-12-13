package com.xxlllq.dataprovider.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @类名称： StringUtil
 * @类描述：字符串操作工具
 * @创建人：xiangxl
 * @创建时间：2018/12/13 13:17
 * @version：
 */
public class StringUtil {

    /**
     * 获取中括号中的文本值[string]
     *
     * @param string
     * @return
     */
    public static String getBracketStr(String string) {
        Pattern p = Pattern.compile("(\\[[^\\]]*\\])");
        Matcher m = p.matcher(string);
        while (m.find()) {
            String str = m.group();
            if (!StringUtils.isBlank(str) && str.length() > 2) {
                str = str.substring(1, str.length() - 1);
                return str;
            }
        }
        return null;
    }

}
