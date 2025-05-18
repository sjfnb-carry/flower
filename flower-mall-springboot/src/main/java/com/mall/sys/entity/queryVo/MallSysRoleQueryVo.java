package com.mall.sys.entity.queryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.sys.entity.MallSysRole;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
* 角色信息表增强类
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MallSysRoleQueryVo对象", description="角色信息表增强对象")
public class MallSysRoleQueryVo extends MallSysRole {

    @ApiModelProperty(value = "分页page对象")
    private Page page;

}