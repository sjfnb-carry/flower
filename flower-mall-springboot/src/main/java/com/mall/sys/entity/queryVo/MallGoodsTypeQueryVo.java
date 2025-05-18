package com.mall.sys.entity.queryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.mall.sys.entity.MallGoodsType;
/**
* 商品类型表增强类
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MallGoodsTypeQueryVo对象", description="商品类型表增强对象")
public class MallGoodsTypeQueryVo extends MallGoodsType {

    @ApiModelProperty(value = "分页page对象")
    private Page page;

}