package com.mall.goods.entity;

import lombok.Data;

@Data
public class  ShopCat {

    //用户id
    private String userId;
    //活动地点
    private String activityPlace;
    //活动时间
    private String activityTime;
    //活动编号
    private String acitivityNumber;
    //活动名称
    private String itemName;
    //头图
    private String firstPicture;
    //库存
    private Integer stock;
    //商品价格
    private double price;
    //购买数量
    private Integer number;
    //商品id
    private String id;
    //分类id
    private Integer itemId;


}
