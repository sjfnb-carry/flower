package com.mall.goods.service;

import com.mall.goods.entity.MallBuying;
import com.mall.goods.entity.vo.MallBuyingVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 求购信息表 服务类
 */
public interface MallBuyingService extends IService<MallBuying> {

    void saveMallBuyingVo(MallBuyingVo mallBuyingVo);

}
