package com.mall.goods.service;

import com.mall.common.result.R;
import com.mall.goods.entity.UserGoods;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 商品收藏表 服务类
 */
public interface UserGoodsService extends IService<UserGoods> {


    /**
     * 根据用户id查询(收藏/购物车)商品
     */
    List<? extends Object> selectCollectionList(Long userId, Integer type);


    /**
     * 商品详情
     */
    Map<String, Object> queryMallGoodsDetail(Long goodsId);



    /**
     * 添加收藏|购物车
     */
    R collectionGoods(Integer type, Long userId, Long goodsId);

}
