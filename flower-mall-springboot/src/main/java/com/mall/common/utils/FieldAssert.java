package com.mall.common.utils;

import cn.hutool.core.util.StrUtil;
import com.mall.common.handler.GlobalException;
import com.mall.common.handler.GlobalExceptionEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;


public class FieldAssert {
    public static void isBlank(String str, String message){
        if (StrUtil.isBlank(str)) {
             throw new GlobalException(GlobalExceptionEnum.UNKNOWN_REASON.getCode(),message);
        }
    }

    public static void isNull(Object obj, String message){
        if (null == obj||"".equals(obj)) {
             throw new GlobalException(GlobalExceptionEnum.UNKNOWN_REASON.getCode(),message);
        }
    }
    public static void isLeisure(String str, String message){
        if (StringUtils.isEmpty(str)) {
             throw new GlobalException(GlobalExceptionEnum.UNKNOWN_REASON.getCode(),message);
        }
    }

    public static void isTrue(Boolean obj, String message){
        if (null == obj || !obj) {
             throw new GlobalException(GlobalExceptionEnum.UNKNOWN_REASON.getCode(),message);
        }
    }

    public static void isNotNull(Object obj, String message){
        if (null != obj) {
             throw new GlobalException(GlobalExceptionEnum.UNKNOWN_REASON.getCode(),message);
        }
    }

    public static void isNumeric(String str, String message) {
        Pattern pattern = compile("[0-9]*");
        if( StrUtil.isBlank(str)){
            throw new GlobalException(GlobalExceptionEnum.UNKNOWN_REASON.getCode(),message);
        }
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
             throw new GlobalException(GlobalExceptionEnum.UNKNOWN_REASON.getCode(),message);
        }
    }
}
