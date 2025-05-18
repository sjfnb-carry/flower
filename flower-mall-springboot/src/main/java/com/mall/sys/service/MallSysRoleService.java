package com.mall.sys.service;

import com.mall.sys.entity.MallSysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 角色信息表 服务类
 */
public interface MallSysRoleService extends IService<MallSysRole> {


    /**
     *查询角色列表
     */
    List<MallSysRole> queryRoleAll();

}
