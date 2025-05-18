package com.mall.goods.service.impl;

import com.mall.goods.entity.Address;
import com.mall.goods.mapper.AddressMapper;
import com.mall.goods.service.AddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户地址表 服务实现类
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

}
