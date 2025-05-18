package com.mall.common.utils;

import lombok.Data;


@Data
public class UserLogInfoDto {
    /**
     * 用户id
     */
     private String userId;

    /**
     * 用户姓名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 角色id
     */
    private String roleId;


    private String loginDateLong;

    private String loginKey;

}
