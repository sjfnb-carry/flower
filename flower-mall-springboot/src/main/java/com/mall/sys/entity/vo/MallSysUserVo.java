package com.mall.sys.entity.vo;

import com.mall.sys.entity.MallSysUser;
import lombok.Data;


@Data
public class MallSysUserVo extends MallSysUser {

    /**
     * 角色名称
     */
    private String roleName;
}
