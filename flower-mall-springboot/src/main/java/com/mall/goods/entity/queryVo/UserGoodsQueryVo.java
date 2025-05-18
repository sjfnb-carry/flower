package com.mall.goods.entity.queryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.goods.entity.UserGoods;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*商品收藏表增强类*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UserGoodsQueryVo对象", description="商品收藏表增强对象")
public class UserGoodsQueryVo extends UserGoods {

    @ApiModelProperty(value = "分页page对象")
    private Page page;

}