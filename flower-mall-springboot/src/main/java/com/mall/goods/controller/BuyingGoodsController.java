package com.mall.goods.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.common.annotation.IgnoreSecurity;
import com.mall.common.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.mall.goods.entity.BuyingGoods;
import com.mall.goods.service.BuyingGoodsService;
import com.mall.goods.entity.queryVo.BuyingGoodsQueryVo;


@RestController
@RequestMapping("/goods/buyingGoods")
@Api(tags="商品求购信息表")
public class BuyingGoodsController {

    @Autowired
    BuyingGoodsService iBuyingGoodsService;

        /*查询商品求购信息表列表*/
        @PostMapping("queryBuyingGoodsList")
        @ApiOperation("商品求购信息表列表")
        @IgnoreSecurity
        public R queryBuyingGoodsList(@RequestBody BuyingGoodsQueryVo queryVo) {
            QueryWrapper<BuyingGoods> queryWrapper=new QueryWrapper<>();
            Page<BuyingGoods> page = iBuyingGoodsService.page(queryVo.getPage(), queryWrapper);
            return R.ok().data("dataList",page);
        }

        /*保存或者更新商品求购信息表信息*/
        @PostMapping("saveOrUpdateBuyingGoods")
        @ApiOperation("保存或者更新商品求购信息表信息")
        public R saveOrUpdateBuyingGoods(@RequestBody BuyingGoods entity) {
            boolean b =true;
            if(StringUtils.isEmpty(entity.getId())){
                b= iBuyingGoodsService.save(entity);
            }else{
                b=iBuyingGoodsService.updateById(entity);
            }
            return R.th(b);
        }


        /*根据id查询商品求购信息表信息*/
        @GetMapping("getByIdBuyingGoods/{id}")
        @ApiOperation("根据id查询商品求购信息表信息")
        @IgnoreSecurity
        public R getByIdBuyingGoods(@PathVariable("id") String id) {
             BuyingGoods dataBuyingGoods=  iBuyingGoodsService.getById(id);
             return R.ok().data("entity",dataBuyingGoods);

        }
        /*根据id删除商品求购信息表信息*/
        @DeleteMapping("removeByIdBuyingGoods/{id}")
        @ApiOperation("根据id删除商品求购信息表信息")
        public R removeByIdBuyingGoods(@PathVariable("id") String id) {
            boolean effect= iBuyingGoodsService.removeById(id);
            return R.th(effect);

        }

}

