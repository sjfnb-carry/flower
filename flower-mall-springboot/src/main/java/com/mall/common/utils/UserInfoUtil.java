package com.mall.common.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class UserInfoUtil {
    private static Map<String, Object> userInfoMap = Collections.synchronizedMap(new HashMap<String, Object>());


    public static String getUserId() {
        return (String) userInfoMap.get("userId");
    }

    public static String getEmail() {
        return (String) userInfoMap.get("email");
    }

    public static String getUsername() {
        return (String) userInfoMap.get("username");
    }

    public static Integer getType() {
        return (Integer) userInfoMap.get("type");
    }


    public static String getRoleId() {
        return (String) userInfoMap.get("RoleId");
    }


    public static Long getAcademy() {
        return (Long) userInfoMap.get("academy");
    }


    public static void setUserInfo(String key, Object value) {
        userInfoMap.put(key, value);
    }

    public static void setUserInfoPutAll(Map<String, Object> map) {
        userInfoMap.putAll(map);
    }

    public static Map<String, Object> getUserInfoMap() {
        return userInfoMap;
    }

    public static Object getUserInfo(String key) {
        return userInfoMap.get(key);
    }

    public static Long getLoginDateLong() {
        return (long) userInfoMap.get("loginDateLong");
    }

    public static String getLoginKey() {
        return (String) userInfoMap.get("loginKey");
    }
}
