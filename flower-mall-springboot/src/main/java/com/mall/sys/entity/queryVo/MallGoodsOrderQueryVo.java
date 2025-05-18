package com.mall.sys.entity.queryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.sys.entity.MallGoodsOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
* 商品订单中间表增强类
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MallGoodsOrderQueryVo对象", description="商品订单中间表增强对象")
public class MallGoodsOrderQueryVo extends MallGoodsOrder {

    @ApiModelProperty(value = "分页page对象")
    private Page page;

}