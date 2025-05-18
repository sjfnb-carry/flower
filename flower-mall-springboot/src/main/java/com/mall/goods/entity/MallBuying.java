package com.mall.goods.entity;

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

/*购信息表*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MallBuying对象", description="求购信息表")
public class MallBuying implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主建")
  @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "标题")
    private String buyingName;

    @ApiModelProperty(value = "收购价钱")
    private String buyingPrice;

    @ApiModelProperty(value = "简介")
    private String buyingAddress;

    @ApiModelProperty(value = "新旧程度")
    private Integer oldAndNew;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}