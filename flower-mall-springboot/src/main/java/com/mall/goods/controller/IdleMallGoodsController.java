package com.mall.goods.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.common.result.R;
import com.mall.common.utils.UserInfoUtil;
import com.mall.goods.mapper.SendUserMapper;
import com.mall.sys.entity.MallGoods;
import com.mall.sys.entity.MallGoodsImg;
import com.mall.sys.entity.queryVo.MallIdleGoodesSaveVo;
import com.mall.sys.entity.vo.MallGoodsVo;
import com.mall.sys.service.MallGoodsImgService;
import com.mall.sys.service.MallGoodsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/goods/idle")
@Api(tags = "商品-商城前端")
public class IdleMallGoodsController {

    @Autowired
    MallGoodsService iMallGoodsService;

    @Autowired
    MallGoodsImgService mallGoodsimgService;

    @Resource
    SendUserMapper sendUserMapper;
    /*保存商品*/
    @PostMapping("saveIdleGoods")
    public R saveIdleGoods(@RequestBody MallIdleGoodesSaveVo mallIdleGoodesSaveVo) {
        iMallGoodsService.saveIdleGoods(mallIdleGoodesSaveVo);
        return R.ok();
    }

    /*查询用户发布的商品*/
    @PostMapping("getPublishedGoods")
    public R getPublishedGoods() {
        List<MallGoodsVo> byUserGoods = sendUserMapper.getByUserGoods(Long.valueOf(UserInfoUtil.getUserId()));
        return R.ok().data("info", byUserGoods);
    }

    @GetMapping("gitByGoodsId/{goodsId}")
    public R gitByGoodsId(@PathVariable("goodsId") Long id) {
        QueryWrapper<MallGoodsImg> imgQueryWrapper = new QueryWrapper<>();
        imgQueryWrapper.eq("goods_id", id);
        List<String> path = mallGoodsimgService.
                list(imgQueryWrapper).stream().
                map(res -> {

                    return res.getImgUrl();
                }).collect(Collectors.toList());
        MallGoods byId = iMallGoodsService.getById(id);
        MallGoodsVo mallGoodsVo = BeanUtil.toBean(byId, MallGoodsVo.class);
        mallGoodsVo.setImgUrlList(path);

        return R.ok().data("info", mallGoodsVo);
    }
}
