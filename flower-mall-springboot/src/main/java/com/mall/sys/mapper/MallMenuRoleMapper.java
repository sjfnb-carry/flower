package com.mall.sys.mapper;

import com.mall.sys.entity.MallMenuRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单角色中间表 Mapper 接口
 */
public interface MallMenuRoleMapper extends BaseMapper<MallMenuRole> {

    /**
     *查询角色下底层菜单
     */
    List<String> getFinallyModule(@Param("roleId") String s);

}
