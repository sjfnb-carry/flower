package com.mall.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
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
 * 角色信息表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mall_sys_role")
@ApiModel(value="MallSysRole对象", description="角色信息表")
public class MallSysRole implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "序号,PK")
  @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "角色名")
    private String roleName;

    @ApiModelProperty(value = "创建者ID")
    private String createId;

    @ApiModelProperty(value = "创建时间 自动记录")
    private Date createTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    private Integer newColumn;


}