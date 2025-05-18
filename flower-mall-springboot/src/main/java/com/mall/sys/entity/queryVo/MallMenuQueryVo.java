package com.mall.sys.entity.queryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.sys.entity.MallMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
* 菜单增强类
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MallMenuQueryVo对象", description="菜单增强对象")
public class MallMenuQueryVo extends MallMenu {

    @ApiModelProperty(value = "分页page对象")
    private Page page;

}