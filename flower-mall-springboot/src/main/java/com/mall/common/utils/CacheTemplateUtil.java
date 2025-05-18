package com.mall.common.utils;

import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * jvm缓存
 **/
@Configuration
public class CacheTemplateUtil {

    private static final HashMap<String, String> cacheTemplateHash = new HashMap<>();

    /**
     * 商品标签缓存
     */
    private static final HashMap<String, String> goodsTagHash = new HashMap<>();


    /**
     * 主页轮播图
     */
    private static final HashMap<String, String> goodsImgHash = new HashMap<>();

    /**
     * 广告图
     */
    private static List<HashMap<String, String>> homeGoodsImg;

    /**
     * 验证码工具类
     */
    private static final Map<String, String> verificationCode = new HashMap<>();

    public static void setVerificationCode(String k, String v) {
        verificationCode.put(k, v);
    }

    public String getVerificationCode(String k) {
       return verificationCode.get(k);
    }
    public static HashMap<String, String> getGoodsTagHash() {
        return goodsTagHash;
    }

    public static String getGoodsTagHash(String key) {
        return goodsTagHash.get(key);
    }

    public static void setGoodsTagHash(String key, String value) {
        goodsTagHash.put(key, value);
    }

    public static void setHomeGoodsImg(List<HashMap<String, String>> map) {
        homeGoodsImg = map;
    }

    public static List<HashMap<String, String>> getHomeGoodsImg() {
        return homeGoodsImg;
    }


    public static void setGoodsImgHash(String key, String value) {
        goodsImgHash.put(key, value);
    }

    public static HashMap<String, String> getGoodsImgHash() {
        return goodsImgHash;
    }

    public static String get(String key) {
        return cacheTemplateHash.get(key);
    }

    public static void set(String key, String value) {
        cacheTemplateHash.put(key, value);
    }


    public static void del(String... key) {
        for (String s : key) {
            cacheTemplateHash.remove(s);
        }

    }

}
