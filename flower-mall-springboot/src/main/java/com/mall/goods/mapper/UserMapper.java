package com.mall.goods.mapper;

import com.mall.goods.entity.User;
import com.mall.goods.entity.vo.UserVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 商城用户表 Mapper 接口
 */
public interface UserMapper extends BaseMapper<User> {

     /**
      * 用户详情
      */
     UserVo getUserDetail(@Param("id") Long id);


     /**
      * 查询用户的余额
      */
     UserVo getByCarryMoneyDetail(@Param("id")Long id);


}
