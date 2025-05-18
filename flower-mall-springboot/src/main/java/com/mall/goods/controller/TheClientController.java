package com.mall.goods.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.common.utils.FieldAssert;
import com.mall.common.utils.UserInfoUtil;
import com.mall.common.annotation.IgnoreSecurity;
import com.mall.common.result.R;
import com.mall.common.utils.FileUtil;
import com.mall.goods.entity.UserGoods;
import com.mall.goods.service.UserGoodsService;
import com.mall.sys.entity.queryVo.MallGoodsQueryVo;
import com.mall.sys.service.MallGoodsImgService;
import com.mall.sys.service.MallGoodsService;
import com.mall.sys.service.MallGoodsTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/goods/mallGoods")
@Api(tags = "商品表-商城前端")
public class TheClientController {

    @Autowired
    MallGoodsService iMallGoodsService;

    @Autowired
    UserGoodsService userGoodsService;

    @Autowired
    MallGoodsTypeService mallGoodsTypeService;

    @Autowired
    MallGoodsImgService iMallGoodsImgService;

    /*查询商品表列表*/
    @PostMapping("queryMallGoodsList")
    @ApiOperation("商品表列表")
    @IgnoreSecurity
    public R queryMallGoodsList(@RequestBody MallGoodsQueryVo queryVo) {
        return R.ok().data("list", iMallGoodsService.queryPage(queryVo.getPage(), queryVo));
    }


    @GetMapping("queryMallGoodsTypeList")
    @ApiOperation("商品类型表列表")
    @IgnoreSecurity
    public R queryMallGoodsTypeList() {
//        QueryWrapper<MallGoods> mallGoodsQueryWrapper = new QueryWrapper<>();
//        if (type!=null){
//            mallGoodsQueryWrapper.eq("goode_user_type",1);
//        }

        return R.ok().data("list", mallGoodsTypeService.list());
    }


    @GetMapping("queryMallGoodsDetail/{id}")
    @ApiOperation("商品详情")
    @IgnoreSecurity
    public R queryMallGoodsDetail(@PathVariable Long id) {
        return R.ok().data(userGoodsService.queryMallGoodsDetail(id));
    }


    @PostMapping("collectionGoods")
    @ApiOperation("(收藏/添加购物车)商品")
    public R collectionGoods(@RequestBody UserGoods userGoods) {
        String userId = UserInfoUtil.getUserId();
        userGoods.setUserId(Long.valueOf(userId));

        return userGoodsService.
                collectionGoods(userGoods.getType(), userGoods.getUserId(), userGoods.getGoodsId());
    }


    @GetMapping("selectCollectionList/{type}")
    @ApiOperation("查看(收藏/购物车)商品列表")
    public R selectCollectionList(@PathVariable Integer type) {
        String userId = UserInfoUtil.getUserId();
        return R.ok().data("list", userGoodsService.selectCollectionList(Long.valueOf(userId), type));
    }

    @PostMapping("addGoods")
    @ApiOperation("修改")
    public R addGoods(@RequestBody UserGoods userGoods) {

        QueryWrapper<UserGoods> userGoodsQueryWrapper = new QueryWrapper<>();
        userGoodsQueryWrapper
                .eq("user_id", UserInfoUtil.getUserId())
                .eq("type", 2)
                .eq("goods_id", userGoods.getGoodsId());
        userGoodsService.remove(userGoodsQueryWrapper);
        List<UserGoods> list = new ArrayList<>();
        for (int i = 0; i < userGoods.getNum(); i++) {
            list.add(new UserGoods().setGoodsId(userGoods.getGoodsId())
                    .setUserId(userGoods.getUserId())
                    .setType(2).setGmtCreate(new Date()));
        }
        userGoodsService.saveBatch(list);
        return R.ok();
    }

    @PostMapping("removeGoods")
    @ApiOperation("删除")
    public R removeGoods(@RequestBody UserGoods userGoods) {
        QueryWrapper<UserGoods> userGoodsQueryWrapper = new QueryWrapper<>();
        userGoodsQueryWrapper
                .eq("user_id", UserInfoUtil.getUserId())
                .eq("type", userGoods.getType())
                .eq("goods_id", userGoods.getGoodsId());
        userGoodsService.remove(userGoodsQueryWrapper);
        return R.ok();
    }

    @PostMapping("uploadIdleMallGoodsImg")
    @ApiOperation("上传图片")
    public R uploadImg(MultipartFile httpFile, Long goodsId) throws Exception {
        FieldAssert.isNull(goodsId, "传入用户id");
        FieldAssert.isNull(httpFile, "请传入图片");
        return R.ok().data("path", FileUtil.uploadAffix(httpFile,"idleGoods", String.valueOf(goodsId)));

    }
}

