package com.mall.sys.mapper;

import com.mall.sys.entity.MallMenu;
import com.mall.sys.entity.vo.MallMenuGet;
import com.mall.sys.entity.vo.MallMenuVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 菜单 Mapper 接口
 */
public interface MallMenuMapper extends BaseMapper<MallMenu> {


    /**
     * 返回所有菜单信息
     */
    List<MallMenuVo> getListMode(@Param("roleId") String roleId);

    /**
     * 查询登录用户下所有按钮权限
     */
    List<String> getAllBtn(@Param("roleId") String roleId);

    /**
     * 判断菜单下是否有菜单
     */
    @Select("select count(*) from (select * from mall_menu where id=${id}) as m1 join mall_menu m2 where m1.menu_code=m2.parent_id and m2.is_deleted=0")
    Integer isnull(@Param("id") String id);

    /**
     * 返回所有菜单信息
     */
    List<MallMenuVo> findMenuAll();

    /**
     * 查询菜单信息
     */
    MallMenuGet getByModel(@Param("id") String id);

    /**
     *根据父菜单code查询最大的子菜单code
     */
    String maxModel(@Param("pId") String pId);


}
