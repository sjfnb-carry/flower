package com.mall.sys.entity.queryVo;

import com.mall.sys.entity.MallGoods;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
* 商品表增强类
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MallGoodsQueryVo对象", description="商品表增强对象")
public class MallIdleGoodesSaveVo extends MallGoods {

    @ApiModelProperty(value = "商品图片")
    private List<String> imgUrlList;

    private String buyingId;

}