package com.mall.goods.entity.vo;

import com.mall.goods.entity.MallBuying;
import lombok.Data;

import java.util.List;


@Data
public class MallBuyingVo extends MallBuying {

    private String imgUrl;

    private String tender;

    List<String> imgUrlList;
}
