package com.mall.sys.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.sys.entity.MallSysUser;
import com.mall.sys.entity.vo.MallSysUserVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户表 Mapper 接口
 */
public interface MallSysUserMapper extends BaseMapper<MallSysUser> {

    IPage<MallSysUserVo> queryPage(Page page, @Param("user") MallSysUser user);


    MallSysUserVo getUserInfo(@Param("id") String id);

}
