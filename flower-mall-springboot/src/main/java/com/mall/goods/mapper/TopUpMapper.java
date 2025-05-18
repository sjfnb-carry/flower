package com.mall.goods.mapper;

import com.mall.goods.entity.TopUp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*充值记录表 Mapper 接口*/
public interface TopUpMapper extends BaseMapper<TopUp> {


    /**
     * 查询用户充值记录
     */
    List<TopUp> getTopUpList(@Param("id") String userId);


    /**
     更新用户余额
     */
    void uptGoodsUserAccount(@Param("topUp") TopUp topUp);

}
