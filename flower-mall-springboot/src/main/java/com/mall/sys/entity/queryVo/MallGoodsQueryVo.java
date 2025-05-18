package com.mall.sys.entity.queryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.mall.sys.entity.MallGoods;
/**
* 商品表增强类
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MallGoodsQueryVo对象", description="商品表增强对象")
public class MallGoodsQueryVo extends MallGoods {

    @ApiModelProperty(value = "分页page对象")
    private Page page;

}