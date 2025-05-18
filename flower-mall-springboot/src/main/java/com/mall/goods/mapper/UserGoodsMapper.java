package com.mall.goods.mapper;

import com.mall.goods.entity.UserGoods;
import com.mall.sys.entity.MallGoods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 商品收藏表 Mapper 接口
 */
public interface UserGoodsMapper extends BaseMapper<UserGoods> {

    /**
     * 根据用户id查询收藏商品
     */
    List<MallGoods> selectCollectionList(@Param("userId") Long userId, @Param("type") Integer type);


    /**
     * 统计购物车的数量
     * @param userId
     * @return
     */
    List<Map<String, Object>> countMallGoods(@Param("userId") Long userId);

}
