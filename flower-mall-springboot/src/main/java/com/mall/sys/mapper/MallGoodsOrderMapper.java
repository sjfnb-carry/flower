package com.mall.sys.mapper;

import com.mall.sys.entity.MallGoodsOrder;
import com.mall.sys.entity.vo.MallGoodsOrderVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品订单中间表 Mapper 接口
 */
public interface MallGoodsOrderMapper extends BaseMapper<MallGoodsOrder> {

    /**
     * 查询携带商品信息的订单记录
     */
    List<MallGoodsOrderVo> getMallGoodsOrderVoList(@Param("orderUmber") String orderUmber);

}
