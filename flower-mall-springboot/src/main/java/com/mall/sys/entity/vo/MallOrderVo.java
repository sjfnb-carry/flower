package com.mall.sys.entity.vo;

import com.mall.sys.entity.MallOrder;
import lombok.Data;

import java.util.List;


@Data
public class MallOrderVo extends MallOrder {

    List<MallGoodsOrderVo> mallGoodsOrderVoList;

}
