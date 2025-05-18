package com.mall.common.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;


public class SnowflakeUtil {

    private SnowflakeUtil(){}
    /*雪花算法生唯一数据*/
    public static Long randomLong(Long workerId,Long datacenterId){
        Snowflake snowflake = IdUtil.createSnowflake(workerId, datacenterId);
        return snowflake.nextId();
    }
    /*雪花算法生唯一数据*/
    public static Long randomLong(){
        return randomLong(Long.valueOf(RandomUtil.randomInt(31)),Long.valueOf(RandomUtil.randomInt(31)));
    }
}
