package com.mall.goods.entity.vo;

import com.mall.goods.entity.MallBuying;
import com.mall.sys.entity.MallGoodsImg;
import com.mall.sys.entity.vo.MallGoodsVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MallBuyingInfoVo extends MallBuying {

    List<MallGoodsImg> imgList = new ArrayList<>();

    List<MallGoodsVo> goodsList = new ArrayList<>();

    List<CommentGoodsVo> commentGoods = new ArrayList<>();

}
