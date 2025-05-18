package com.mall.goods.service.impl;

import com.mall.goods.entity.SendUser;
import com.mall.goods.mapper.SendUserMapper;
import com.mall.goods.service.SendUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户发布闲置表 服务实现类
 */
@Service
public class SendUserServiceImpl extends ServiceImpl<SendUserMapper, SendUser> implements SendUserService {

}
