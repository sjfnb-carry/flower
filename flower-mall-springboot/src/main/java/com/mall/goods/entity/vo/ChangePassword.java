package com.mall.goods.entity.vo;

import lombok.Data;


@Data
public class ChangePassword {

    /**
     * 当前密码
     */
    private String dqPassword;

    /**
     * 新密码
     */
    private String xqPassword;

    /**
     * 确定密码
     */
    private String qdPassword;
}
