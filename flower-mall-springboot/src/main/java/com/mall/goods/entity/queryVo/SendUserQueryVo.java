package com.mall.goods.entity.queryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.goods.entity.SendUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*用户发布闲置表增强类*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SendUserQueryVo对象", description="用户发布闲置表增强对象")
public class SendUserQueryVo extends SendUser {

    @ApiModelProperty(value = "分页page对象")
    private Page page;

}