package com.mall.goods.service;

import com.mall.goods.entity.User;
import com.mall.goods.entity.vo.UserVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 商城用户表 服务类
 */
public interface UserService extends IService<User> {

    /**
     * 登录
     */
    Map<String, Object> login(User user);


    /**
     * 查询详情
     */
   UserVo getByUsId(Long id);


    UserVo getByCarryMoneyDetail(Long id);


   /*注册*/
   void registered(User user);
}
