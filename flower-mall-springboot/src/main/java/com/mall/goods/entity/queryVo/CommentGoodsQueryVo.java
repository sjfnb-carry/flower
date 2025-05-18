package com.mall.goods.entity.queryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.goods.entity.CommentGoods;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*评分增强类*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="CommentGoodsQueryVo对象", description="评分增强对象")
public class CommentGoodsQueryVo extends CommentGoods {

    @ApiModelProperty(value = "分页page对象")
    private Page page;

}