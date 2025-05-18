package com.mall.goods.entity.queryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.goods.entity.BuyingGoods;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*商品求购信息表增强类*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="BuyingGoodsQueryVo对象", description="商品求购信息表增强对象")
public class BuyingGoodsQueryVo extends BuyingGoods {

    @ApiModelProperty(value = "分页page对象")
    private Page page;

}