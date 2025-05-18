package com.mall.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.common.result.R;
import com.mall.sys.entity.MallGoodsType;
import com.mall.sys.entity.queryVo.MallGoodsTypeQueryVo;
import com.mall.sys.service.MallGoodsTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品类型表 前端控制器
 */
@RestController
@RequestMapping("/sys/mallGoodsType")
@Api(tags="商品类型表")
public class MallGoodsTypeController {

    @Autowired
    MallGoodsTypeService iMallGoodsTypeService;

        /**
        * 查询商品类型表列表
        */
        @PostMapping("queryMallGoodsTypeList")
        @ApiOperation("商品类型表列表")
        public R queryMallGoodsTypeList(@RequestBody MallGoodsTypeQueryVo queryVo) {
            QueryWrapper<MallGoodsType> queryWrapper=new QueryWrapper<>();
            Page<MallGoodsType> page = iMallGoodsTypeService.page(queryVo.getPage(), queryWrapper);
            return R.ok().data("dataList",page);
        }

        /**
         * 保存或者更新商品类型表信息
         */
        @PostMapping("saveOrUpdateMallGoodsType")
        @ApiOperation("保存或者更新商品类型表信息")
        public R saveOrUpdateMallGoodsType(@RequestBody MallGoodsType entity) {
            boolean b =true;
            if(StringUtils.isEmpty(entity.getId())){
                b= iMallGoodsTypeService.save(entity);
            }else{
                b=iMallGoodsTypeService.updateById(entity);
            }
            return R.th(b);
        }


        /**
         * 根据id查询商品类型表信息
         */
        @GetMapping("getByIdMallGoodsType/{id}")
        @ApiOperation("根据id查询商品类型表信息")
        public R getByIdMallGoodsType(@PathVariable("id") String id) {
             MallGoodsType dataMallGoodsType=  iMallGoodsTypeService.getById(id);
             return R.ok().data("entity",dataMallGoodsType);

        }
        /**
         * 根据id删除商品类型表信息
         */
        @DeleteMapping("removeByIdMallGoodsType/{id}")
        @ApiOperation("根据id删除商品类型表信息")
        public R removeByIdMallGoodsType(@PathVariable("id") String id) {
            boolean effect= iMallGoodsTypeService.removeById(id);
            return R.th(effect);

        }

        @GetMapping("mallGoodsTypeList")
        public R mallGoodsTypeList(){
            List<MallGoodsType> list = iMallGoodsTypeService.list();
            List<Map<String, String>> collect = list.stream().map(res -> {
                Map<String, String> maps = new HashMap();
                maps.put("key", res.getTypeName());
                maps.put("value", String.valueOf(res.getId()));
                return maps;
            }).collect(Collectors.toList());

            return R.ok().data("dataList",collect);
        }

}

