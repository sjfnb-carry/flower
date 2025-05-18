package com.mall.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.common.result.R;
import com.mall.sys.entity.MallGoodsOrder;
import com.mall.sys.entity.queryVo.MallGoodsOrderQueryVo;
import com.mall.sys.service.MallGoodsOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品订单中间表 前端控制器
 */
@RestController
@RequestMapping("/sys/mallGoodsOrder")
@Api(tags="商品订单中间表")
public class MallGoodsOrderController {

    @Autowired
    MallGoodsOrderService iMallGoodsOrderService;

        /**
        * 查询商品订单中间表列表
        */
        @PostMapping("queryMallGoodsOrderList")
        @ApiOperation("商品订单中间表列表")
        public R queryMallGoodsOrderList(@RequestBody MallGoodsOrderQueryVo queryVo) {
            QueryWrapper<MallGoodsOrder> queryWrapper=new QueryWrapper<>();
            Page<MallGoodsOrder> page = iMallGoodsOrderService.page(queryVo.getPage(), queryWrapper);
            return R.ok().data("dataList",page);
        }

        /**
         * 保存或者更新商品订单中间表信息
         */
        @PostMapping("saveOrUpdateMallGoodsOrder")
        @ApiOperation("保存或者更新商品订单中间表信息")
        public R saveOrUpdateMallGoodsOrder(@RequestBody MallGoodsOrder entity) {
            boolean b =true;
            if(StringUtils.isEmpty(entity.getId())){
                b= iMallGoodsOrderService.save(entity);
            }else{
                b=iMallGoodsOrderService.updateById(entity);
            }
            return R.th(b);
        }


        /**
         * 根据id查询商品订单中间表信息
         */
        @GetMapping("getByIdMallGoodsOrder/{id}")
        @ApiOperation("根据id查询商品订单中间表信息")
        public R getByIdMallGoodsOrder(@PathVariable("id") String id) {
             MallGoodsOrder dataMallGoodsOrder=  iMallGoodsOrderService.getById(id);
             return R.ok().data("entity",dataMallGoodsOrder);

        }
        /**
         * 根据id删除商品订单中间表信息
         */
        @DeleteMapping("removeByIdMallGoodsOrder/{id}")
        @ApiOperation("根据id删除商品订单中间表信息")
        public R removeByIdMallGoodsOrder(@PathVariable("id") String id) {
            boolean effect= iMallGoodsOrderService.removeById(id);
            return R.th(effect);

        }

}

