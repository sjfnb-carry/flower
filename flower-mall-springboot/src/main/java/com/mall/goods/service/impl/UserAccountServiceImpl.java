package com.mall.goods.service.impl;

import com.mall.goods.entity.UserAccount;
import com.mall.goods.mapper.UserAccountMapper;
import com.mall.goods.service.UserAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户账户表 服务实现类
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements UserAccountService {

}
