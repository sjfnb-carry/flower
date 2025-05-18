package com.mall.goods.entity.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/*用户下单实体*/
@Data
public class OrderPayEntityVo {
    //商品集合
   private List<Map<String,Object>> goodsList;

    //总计金额
   private BigDecimal amount;


   private Long confirmAddress;
}
