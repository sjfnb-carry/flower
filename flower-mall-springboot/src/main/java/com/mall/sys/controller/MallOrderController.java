package com.mall.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.common.result.R;
import com.mall.common.utils.MailServiceImpl;
import com.mall.sys.entity.MallOrder;
import com.mall.sys.entity.queryVo.MallOrderQueryVo;
import com.mall.sys.entity.vo.MallGoodsOrderVo;
import com.mall.sys.mapper.MallGoodsOrderMapper;
import com.mall.sys.service.MallOrderService;
import com.mall.goods.entity.Address;
import com.mall.goods.entity.User;
import com.mall.goods.service.AddressService;
import com.mall.goods.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单详情表 前端控制器
 */
@RestController
@RequestMapping("/sys/mallOrder")
@Api(tags = "订单详情表")
public class MallOrderController {

    @Autowired
    MallOrderService iMallOrderService;

    @Autowired
    AddressService addressService;

    @Resource
    MallGoodsOrderMapper mallGoodsOrderMapper;


    @Autowired
    private MailServiceImpl mailService;

    @Autowired
    UserService iUserService;


    /**
     * 查询订单详情表列表
     */
    @PostMapping("queryMallOrderList")
    @ApiOperation("订单详情表列表")
    public R queryMallOrderList(@RequestBody MallOrderQueryVo queryVo) {
        QueryWrapper<MallOrder> queryWrapper = new QueryWrapper<>();
        if (queryVo.getUserId() != null) {
            queryWrapper.eq("user_id", queryVo.getUserId());
        }
        queryWrapper.orderByDesc("gmt_create");
        Page<MallOrder> page = iMallOrderService.page(queryVo.getPage(), queryWrapper);
        return R.ok().data("dataList", page);
    }

    /**
     * 保存或者更新订单详情表信息
     */
    @PostMapping("saveOrUpdateMallOrder")
    @ApiOperation("保存或者更新订单详情表信息")
    public R saveOrUpdateMallOrder(@RequestBody MallOrder entity) {
        boolean b = true;
        if (StringUtils.isEmpty(entity.getId())) {
            b = iMallOrderService.save(entity);
        } else {
            b = iMallOrderService.updateById(entity);
        }
        return R.th(b);
    }


    /**
     * 根据id查询订单详情表信息
     */
    @GetMapping("getByIdMallOrder/{id}")
    @ApiOperation("根据id查询订单详情表信息")
    public R getByIdMallOrder(@PathVariable("id") String id) {
        QueryWrapper<MallOrder> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_umber", id);
        MallOrder one = iMallOrderService.getOne(orderQueryWrapper);
        Address byId = addressService.getById(one.getAddressId());
        List<MallGoodsOrderVo> mallGoodsOrderVoList = mallGoodsOrderMapper.getMallGoodsOrderVoList(id);
        return R.ok().data("orderInfo", one).data("goodsAddressInfo", byId).data("MallGoodsList", mallGoodsOrderVoList);

    }

    /**
     * 根据id删除订单详情表信息
     */
    @DeleteMapping("removeByIdMallOrder/{id}")
    @ApiOperation("根据id删除订单详情表信息")
    public R removeByIdMallOrder(@PathVariable("id") String id) {
        boolean effect = iMallOrderService.removeById(id);
        return R.th(effect);

    }

    @GetMapping("sendCore/{orderNumber}")
    public R sendCore(@PathVariable String orderNumber) throws Exception {
        QueryWrapper<MallOrder> mallOrderQueryWrapper = new QueryWrapper<>();
        mallOrderQueryWrapper.eq("order_umber", orderNumber);
        MallOrder one = iMallOrderService.getOne(mallOrderQueryWrapper);
        User byId = iUserService.getById(one.getUserId());
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("username", byId.getUsername());
        hashMap.put("orderNumber", orderNumber);
        //mailService.sendHtmlMessageMail(byId.getEmail(), "发货通知", hashMap, "sys-notice.ftl");
        UpdateWrapper<MallOrder> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("order_umber", orderNumber);
        updateWrapper.set("status",1);
        iMallOrderService.update(updateWrapper);
        return R.ok();
    }

}

