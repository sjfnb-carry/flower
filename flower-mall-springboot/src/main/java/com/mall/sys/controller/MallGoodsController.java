package com.mall.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.common.annotation.IgnoreSecurity;
import com.mall.common.result.R;
import com.mall.common.utils.CacheTemplateUtil;
import com.mall.sys.entity.MallGoods;
import com.mall.sys.entity.MallGoodsS;
import com.mall.sys.entity.queryVo.MallGoodsQueryVo;
import com.mall.sys.entity.queryVo.MallIdleGoodesSaveVo;
import com.mall.sys.mapper.QiTa;
import com.mall.sys.service.MallGoodsService;
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
 * 商品表 前端控制器
 */
@RestController
@RequestMapping("/sys/mallGoods")
@Api(tags = "商品表")
public class MallGoodsController {

    @Autowired
    MallGoodsService iMallGoodsService;

    @Autowired
    private QiTa qiTa;

    /**
     * 查询商品表列表
     */
    @PostMapping("queryMallGoodsList")
    @ApiOperation("商品表列表")
    public R queryMallGoodsList(@RequestBody MallGoodsQueryVo queryVo) {
        QueryWrapper<MallGoods> queryWrapper = new QueryWrapper<>();
        IPage<MallGoods> page = iMallGoodsService.page(queryVo.getPage(), queryWrapper);
        return R.ok().data("dataList", page);
    }

    /**
     * 保存或者更新商品表信息
     */
    @PostMapping("saveOrUpdateMallGoods")
    @ApiOperation("保存或者更新商品表信息")
    public R saveOrUpdateMallGoods(@RequestBody MallGoods entity) {
        boolean b = true;
        if (StringUtils.isEmpty(entity.getId())) {
            b = iMallGoodsService.save(entity);
        } else {
            b = iMallGoodsService.updateById(entity);
        }
        return R.th(b);
    }


    /**
     * 根据id查询商品表信息
     */
    @GetMapping("getByIdMallGoods/{id}")
    @ApiOperation("根据id查询商品表信息")
    public R getByIdMallGoods(@PathVariable("id") String id) {
        MallGoods dataMallGoods = iMallGoodsService.getById(id);
        return R.ok().data("entity", dataMallGoods);

    }

    /**
     * 根据id删除商品表信息
     */
    @DeleteMapping("removeByIdMallGoods/{id}")
    @ApiOperation("根据id删除商品表信息")
    public R removeByIdMallGoods(@PathVariable("id") String id) {
        boolean effect = iMallGoodsService.removeById(id);
        return R.th(effect);

    }


    /**
     * 根据id删除商品表信息
     */
    @GetMapping("removeByIdMallGoodsTag")
    @ApiOperation("根据id删除商品表信息")
    public R removeByIdMallGoodsTag() {
        HashMap<String, String> goodsTagHash = CacheTemplateUtil.getGoodsTagHash();
        List<Map<String, String>> collect = goodsTagHash.keySet().stream().map(res -> {
            Map<String, String> maps = new HashMap();
            maps.put("key", goodsTagHash.get(res));
            maps.put("value", res);
            return maps;
        }).collect(Collectors.toList());

        return R.ok().data("dataList", collect);

    }


    @PostMapping("saveIdleGoods")
    public R saveIdleGoods(@RequestBody MallIdleGoodesSaveVo mallIdleGoodesSaveVo) {
        iMallGoodsService.saveIdleGoods(mallIdleGoodesSaveVo);
        return R.ok();
    }

    @GetMapping("shuliang")
    @IgnoreSecurity
    public List<MallGoodsS> shuliang() throws Exception {
        List<MallGoodsS> list = qiTa.selectShop();
        return list;
    }
}

