package com.mall.goods.entity.vo;

import com.mall.goods.entity.TopUp;
import com.mall.goods.entity.User;
import lombok.Data;

import java.util.List;


@Data
public class UserVo extends User {

    /**
     * 用户充值记录
     */
    List<TopUp> topUps;


    Double money;



}
