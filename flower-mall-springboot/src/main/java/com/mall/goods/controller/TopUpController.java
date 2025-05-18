package com.mall.goods.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.common.result.R;
import com.mall.common.utils.UserInfoUtil;
import com.mall.goods.entity.UserAccount;
import com.mall.goods.service.UserAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.mall.goods.entity.TopUp;
import com.mall.goods.service.TopUpService;
import com.mall.goods.entity.queryVo.TopUpQueryVo;

import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping("/goods/topUp")
@Api(tags = "充值记录表")
public class TopUpController {

    @Autowired
    TopUpService iTopUpService;

    @Autowired
    UserAccountService userAccountService;

    /*查询充值记录表列表*/
    @PostMapping("queryTopUpList")
    @ApiOperation("充值记录表列表")
    public R queryTopUpList(@RequestBody TopUpQueryVo queryVo) {
        QueryWrapper<TopUp> queryWrapper = new QueryWrapper<>();
        Page<TopUp> page = iTopUpService.page(queryVo.getPage(), queryWrapper);
        return R.ok().data("dataList", page);
    }

    /*保存或者更新充值记录表信息*/
    @PostMapping("saveOrUpdateTopUp")
    @ApiOperation("保存或者更新充值记录表信息")
    public R saveOrUpdateTopUp(@RequestBody TopUp entity) {

        QueryWrapper<UserAccount> userAccountQueryWrapper = new QueryWrapper<>();

        boolean b;
        if (StringUtils.isEmpty(entity.getId())) {
            String userId = UserInfoUtil.getUserId();
            userAccountQueryWrapper.eq("goods_user_id", userId);
            UserAccount one = userAccountService.getOne(userAccountQueryWrapper);
            if (one == null) {
                one = new UserAccount();
                one.setMoney(BigDecimal.valueOf(0));
                one.setGoodsUserId(Long.valueOf(userId));
                one.setGmtCreate(new Date());
                userAccountService.save(one);
            }
            entity.setUserId(Long.valueOf(userId));
            entity.setAccountId(one.getId());
            entity.setGmtCreate(new Date());
            entity.setIsPay(2);
            b = iTopUpService.save(entity);
        } else {
            b = iTopUpService.updateById(entity);
        }
        return R.th(b);
    }


    /*根据id查询充值记录表信*/
    @GetMapping("getByIdTopUp/{id}")
    @ApiOperation("根据id查询充值记录表信息")
    public R getByIdTopUp(@PathVariable("id") String id) {
        TopUp dataTopUp = iTopUpService.getById(id);
        return R.ok().data("entity", dataTopUp);

    }

    /*根据id删除充值记录表信息*/
    @DeleteMapping("removeByIdTopUp/{id}")
    @ApiOperation("根据id删除充值记录表信息")
    public R removeByIdTopUp(@PathVariable("id") String id) {
        boolean effect = iTopUpService.removeById(id);
        return R.th(effect);

    }


}

