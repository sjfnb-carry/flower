package com.mall.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mall.goods.entity.TopUp;
import com.mall.goods.mapper.TopUpMapper;
import com.mall.goods.service.TopUpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.goods.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 充值记录表 服务实现类
 */
@Service
public class TopUpServiceImpl extends ServiceImpl<TopUpMapper, TopUp> implements TopUpService {

    @Autowired
    UserAccountService userAccountService;


    @Override
    public void uptGoodsUserAccount(TopUp topUp) {

        TopUp byId = getById(topUp);
        UpdateWrapper<TopUp> updateWrapper = new UpdateWrapper<>();
        if (topUp.getIsPay() == 1) {
            baseMapper.uptGoodsUserAccount(byId);
            updateWrapper.notIn("is_pay", Collections.singletonList("3"))
                    .set("is_pay", 1)
                    .eq("id", byId.getId());
            this.update(updateWrapper);
        } else {
            this.updateById(topUp);
        }


    }
}
