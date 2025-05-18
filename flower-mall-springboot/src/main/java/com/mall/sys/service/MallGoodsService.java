package com.mall.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.sys.entity.MallGoods;
import com.mall.sys.entity.MallGoodsOrder;
import com.mall.sys.entity.queryVo.MallIdleGoodesSaveVo;
import com.mall.sys.entity.vo.MallGoodsVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 商品表 服务类
 */
public interface MallGoodsService extends IService<MallGoods> {

    IPage<MallGoodsVo> queryPage(Page page, MallGoods mallGoods);

    Boolean lessStock(List<MallGoodsOrder> list);


    void  saveIdleGoods(MallIdleGoodesSaveVo mallIdleGoodesSaveVo);


}
