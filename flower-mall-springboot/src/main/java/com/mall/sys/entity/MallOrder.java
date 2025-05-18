package com.mall.sys.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 订单详情表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MallOrder对象", description="订单详情表")
public class MallOrder implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主建")
  @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "OD+年(2)+月(2)+日(2)+20位雪花算法")
    private String orderUmber;

    @ApiModelProperty(value = "地址编号")
    private Long addressId;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "数量")
    private Integer quantity;

    @ApiModelProperty(value = "1.以支付 2.未支付")
    private Integer isPay;

    @ApiModelProperty(value = "支付时间")
    private Date paymentTime;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;


    @ApiModelProperty(value = "状态 1以发货;2未发货")
    private Integer status;


}