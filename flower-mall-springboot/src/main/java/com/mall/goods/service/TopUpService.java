package com.mall.goods.service;

import com.mall.goods.entity.TopUp;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 充值记录表 服务类
 */
public interface TopUpService extends IService<TopUp> {


    void uptGoodsUserAccount(TopUp topUp);
}
