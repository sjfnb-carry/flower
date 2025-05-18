package com.mall.sys.entity.vo;

import com.mall.sys.entity.MallGoods;
import com.mall.sys.entity.MallGoodsType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 商品类型表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MallGoodsTypeVo extends MallGoodsType {

  List<MallGoods> goodsList;


}