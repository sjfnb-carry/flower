package com.mall.sys.service;

import com.mall.common.query.CommonSave;
import com.mall.sys.entity.MallMenuRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 菜单角色中间表 服务类
 */
public interface MallMenuRoleService extends IService<MallMenuRole> {

    /**
     * 查询最终的菜单
     */
    List<String> getFinallyModule(String id);

    /**
     * 更新插入权限
     */
    Boolean saveMenuRoleService(CommonSave commonSave);

}
