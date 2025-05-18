package com.mall.goods.entity.queryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.goods.entity.MallBuying;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*求购信息表增强类*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MallBuyingQueryVo对象", description="求购信息表增强对象")
public class MallBuyingQueryVo extends MallBuying {

    @ApiModelProperty(value = "分页page对象")
    private Page page;

}