package com.mall.sys.service;

import com.mall.sys.entity.MallMenu;
import com.mall.sys.entity.vo.MallMenuGet;
import com.mall.sys.entity.vo.MallMenuVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 菜单 服务类
 */
public interface MallMenuService extends IService<MallMenu> {

    /**
     *根据权限获取菜单
     */
    List<MallMenuVo> getListMode(String roleId);

    /**
     *获取按钮
     */
    String getListModeBtn(String roleId);

    /**
     *获取所有菜单信息
     */
    List<MallMenuVo> getMenuAll();

    /**
     * 根据id查询菜单详情
     */
    MallMenuGet getByModel(String id);

    /**
     *保存子菜单
     */
    Boolean saveModel(MallMenu hujieitMenu);


    boolean delModel(String id);

}
