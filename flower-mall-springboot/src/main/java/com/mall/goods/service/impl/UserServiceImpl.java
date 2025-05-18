package com.mall.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.common.handler.GlobalException;
import com.mall.common.handler.GlobalExceptionEnum;
import com.mall.common.utils.JwtUtils;
import com.mall.common.utils.MD5Util;
import com.mall.common.utils.SnowflakeUtil;
import com.mall.goods.entity.User;
import com.mall.goods.entity.UserAccount;
import com.mall.goods.entity.UserGoods;
import com.mall.goods.entity.vo.UserVo;
import com.mall.goods.mapper.UserMapper;
import com.mall.goods.service.UserAccountService;
import com.mall.goods.service.UserGoodsService;
import com.mall.goods.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 商城用户表 服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    UserGoodsService userGoodsService;

    @Override
    public Map<String, Object> login(User user) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        User userBs = baseMapper.selectOne(queryWrapper);


        if (userBs == null) {
            throw new GlobalException(GlobalExceptionEnum.LOGIN_FAILED.getCode(), "无此用户");
        }

        MD5Util.verification(userBs.getPassword(), userBs.getSalt(), user.getPassword(), userBs.getId());

        //token
        String token = JwtUtils.createJWT(SnowflakeUtil.randomLong() + "", "hujie", "{'userId':" + userBs.getId() + ",'username':" + userBs.getUsername() + ",'email':"+userBs.getEmail()+"}", 60 * 1000 * 30 *100);

        QueryWrapper<UserGoods> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userBs.getId());


        int count = userGoodsService.count(wrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("user", userBs);
        map.put("trolleyAmount", count);


        return map;
    }

    @Override
    public UserVo getByUsId(Long id) {
        return baseMapper.getUserDetail(id);
    }

    @Override
        public UserVo getByCarryMoneyDetail(Long id) {
        return baseMapper.getByCarryMoneyDetail(id);
    }

    @Override
    @Transactional
    public void registered(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        User userBs = baseMapper.selectOne(queryWrapper);
        if (userBs != null) {
            throw new GlobalException(GlobalExceptionEnum.LOGIN_FAILED.getCode(), "用户名已存在");
        }
        Map<String, String> stringStringMap = MD5Util.encryptionPass(user.getPassword());
        user.setPassword(stringStringMap.get("password"));
        user.setSalt(stringStringMap.get("salt"));
        user.setGmtCreate(new Date());
        this.save(user);

        UserAccount userAccount = new UserAccount();
        userAccount.setGoodsUserId(user.getId());
        userAccount.setMoney(BigDecimal.valueOf(0));
        userAccount.setGmtCreate(new Date());
        userAccountService.save(userAccount);
    }


}
