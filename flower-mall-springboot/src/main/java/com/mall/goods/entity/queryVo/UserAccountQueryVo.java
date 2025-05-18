package com.mall.goods.entity.queryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.goods.entity.UserAccount;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*用户账户表增强类*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UserAccountQueryVo对象", description="用户账户表增强对象")
public class UserAccountQueryVo extends UserAccount {

    @ApiModelProperty(value = "分页page对象")
    private Page page;

}