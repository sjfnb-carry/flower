package com.mall.sys.service.impl;

import com.mall.sys.entity.MallGoodsOrder;
import com.mall.sys.mapper.MallGoodsOrderMapper;
import com.mall.sys.service.MallGoodsOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 商品订单中间表 服务实现类
 */
@Service
public class MallGoodsOrderServiceImpl extends ServiceImpl<MallGoodsOrderMapper, MallGoodsOrder> implements MallGoodsOrderService {

}
