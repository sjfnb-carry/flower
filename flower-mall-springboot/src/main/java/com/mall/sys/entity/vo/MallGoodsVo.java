package com.mall.sys.entity.vo;

import com.mall.sys.entity.MallGoods;
import lombok.Data;

import java.util.List;


@Data
public class MallGoodsVo extends MallGoods {

//    private String imgUrl;

    private List<String> imgUrlList;

}
