package com.mall.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import org.springframework.util.StringUtils;

import java.util.Date;

/*编码工具类*/
public class CodeUtils {

    /*生成编码*/
    public static String getMaxIncrease(String s, String parentId) {

        if (StringUtils.isEmpty(s)){
            return parentId+"01";
        }
        char c = s.charAt(s.length() - 1);
        char[] chars = s.toCharArray();
        char[] format = String.valueOf(String.format("%d", Integer.valueOf(c + "") + 1)).toCharArray();
        chars[chars.length - 1] = format[format.length - 1];
        return new String(chars);
    }


    public static String produceNumber(String numbering,String coding,String type){
        String format = DateUtil.format(new Date(), "yyyyMMddHHmmss");
        String a = RandomUtil.randomString("1234567890", 6);
        return type+numbering+coding+format+a;
    }

}
