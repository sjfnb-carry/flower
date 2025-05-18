package com.mall.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.result.R;
import com.mall.common.utils.CacheTemplateUtil;
import com.mall.common.utils.SnowflakeUtil;
import com.mall.goods.entity.UserGoods;
import com.mall.goods.mapper.UserGoodsMapper;
import com.mall.sys.entity.MallGoods;
import com.mall.sys.entity.MallGoodsImg;
import com.mall.sys.service.MallGoodsImgService;
import com.mall.sys.service.MallGoodsService;
import com.mall.sys.service.MallGoodsTypeService;
import com.mall.goods.service.UserGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品收藏表 服务实现类
 */
@Service
public class UserGoodsServiceImpl extends ServiceImpl<UserGoodsMapper, UserGoods> implements UserGoodsService {


    @Autowired
    MallGoodsService mallGoodsService;

    @Autowired
    MallGoodsTypeService mallGoodsTypeService;

    @Autowired
    MallGoodsImgService mallGoodsImgService;

    @Override
    public List<? extends Object> selectCollectionList(Long userId, Integer type) {

        List<MallGoods> mallGoods = baseMapper.selectCollectionList(userId, type);
        if (type == 2) {
            List<Map<String, Object>> maps = baseMapper.countMallGoods(userId);
            List<Map<String, Object>> collect = mallGoods.stream().map(res -> {
                HashMap<String, Object> objectObjectHashMap = new HashMap<>();
                objectObjectHashMap.put("id", SnowflakeUtil.randomLong()+"");
                objectObjectHashMap.put("productID", res.getId() + "");
                objectObjectHashMap.put("productName", res.getGoodsName());
                objectObjectHashMap.put("productImg", res.getImgUrl());
                objectObjectHashMap.put("price", res.getGoodsPrice());
                objectObjectHashMap.put("maxNum", 10);
                objectObjectHashMap.put("check", false);

                for (Map<String, Object> map : maps) {
                    if (res.getId() == (long) map.get("goodId")) {
                        objectObjectHashMap.put("num", map.get("sl"));
                    }
                }
                return objectObjectHashMap;
            }).collect(Collectors.toList());
            return collect;
        }



        return mallGoods;
    }


    @Override
    public Map<String, Object> queryMallGoodsDetail(Long goodsId) {
        //商品详情
        MallGoods byId = mallGoodsService.getById(goodsId);

        //商品图片
        QueryWrapper<MallGoodsImg> imgQueryWrapper = new QueryWrapper<>();
        imgQueryWrapper.eq("goods_id", goodsId);
        imgQueryWrapper.eq("img_type", 2);
        List<String> collect = mallGoodsImgService.list(imgQueryWrapper).stream().map(MallGoodsImg::getImgUrl).collect(Collectors.toList());

        //商品标签
        HashMap<String, String> goodsTagHash = CacheTemplateUtil.getGoodsTagHash();
        List<String> collectList = new ArrayList<>();
        if(!StringUtils.isEmpty(byId.getGoodsMarkCode())){
            String[] split = byId.getGoodsMarkCode().split(",");
            for (String s : split) {
                collectList.add(goodsTagHash.get(s));
            }
        }


        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("information", byId);
        objectObjectHashMap.put("img_url", collect);
        objectObjectHashMap.put("tag", collectList);


        return objectObjectHashMap;
    }


    @Override
    public R collectionGoods(Integer type, Long userId, Long goodsId) {
        if (type == 2) {
            QueryWrapper<UserGoods> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", userId)
                    .eq("goods_id", goodsId)
                    .eq("type", type);
            int count = this.count(wrapper);
            if (count < 10) {
                save(new UserGoods().setUserId(userId).setGoodsId(goodsId).setGmtCreate(new Date()).setType(type));
                return R.ok().data("code", count == 0 ? "001" : "002").data("shoppingCartData", mallGoodsService.getById(goodsId));
            }
            return R.ok().data("code", "003");

        } else {
            QueryWrapper<UserGoods> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", userId)
                    .eq("goods_id", goodsId)
                    .eq("type", type);
            int count = this.count(wrapper);
            if (count > 0) {
                return R.error(50000, "已经藏了;无需重复收藏!");
            }
            save(new UserGoods().setUserId(userId).setGoodsId(goodsId).setGmtCreate(new Date()).setType(type));
            return R.ok();
        }


    }
}
