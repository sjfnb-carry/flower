package com.mall.sys.mapper;

import com.mall.sys.entity.MallOrder;
import com.mall.sys.entity.vo.MallOrderVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单详情表 Mapper 接口
 */
public interface MallOrderMapper extends BaseMapper<MallOrder> {

    /**
     * 查询订单列表携带商品详情
     */
    List<MallOrderVo> orderList(@Param("uid") String userId);

}
