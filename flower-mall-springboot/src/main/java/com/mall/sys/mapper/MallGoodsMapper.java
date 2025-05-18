package com.mall.sys.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.sys.entity.MallGoods;
import com.mall.sys.entity.vo.MallGoodsVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品表 Mapper 接口
 */
public interface MallGoodsMapper extends BaseMapper<MallGoods> {

    IPage<MallGoodsVo> queryPage(Page page, @Param("mallGoods") MallGoods mallGoods);

    List<MallGoodsVo> queryGoods(@Param("buyingId") String buyingId);


    Boolean lessStock(@Param("listMap") List<String> listMap);


    /**
     * 根据商品类型查询商品
     */
    List<MallGoods> getMallGoodsList(@Param("goodsId") Long goodsId);
}
