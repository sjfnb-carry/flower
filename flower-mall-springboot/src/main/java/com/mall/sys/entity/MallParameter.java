package com.mall.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 参数表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MallParameter对象", description="参数表")
public class MallParameter implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "id")
  @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "key")
    private String pKey;

    @ApiModelProperty(value = "值多个用逗号隔开")
    private String value;

    @ApiModelProperty(value = "类型0系统类型,1普通类型,系统类型无法删除")
    private String type;

    @ApiModelProperty(value = "备注")
    private String remark;


}