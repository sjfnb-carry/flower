package com.mall.goods.entity;

import java.math.BigDecimal;
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

/*充值记录表*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("goods_top_up")
@ApiModel(value="TopUp对象", description="充值记录表")
public class TopUp implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主建")
  @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "账户id")
    private Long accountId;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "充值金额")
    private BigDecimal goodsTopUpMoney;

    @ApiModelProperty(value = "账户余额")
    private String accountBalance;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;


    @ApiModelProperty(value = "支付结果")
    private Integer isPay;


}