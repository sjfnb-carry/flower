package com.mall.goods.mapper;

import com.mall.goods.entity.UserAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 用户账户表 Mapper 接口
 */
public interface UserAccountMapper extends BaseMapper<UserAccount> {

    /**
     * 更新用户账户
     */
    boolean lessAccount(@Param("goodsId") Long goodsId, @Param("money")BigDecimal money);
}
