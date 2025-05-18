package com.mall.sys.service;

import com.mall.common.result.R;
import com.mall.sys.entity.MallOrder;
import com.mall.goods.entity.vo.OrderPayEntityVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 订单详情表 服务类
 */
public interface MallOrderService extends IService<MallOrder> {

    /**
     *保存订单
     */
    R saveOrder(OrderPayEntityVo orderPayEntityVo, Long userId);

    void orderPay(String orderId);


}
