package com.mall.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.common.result.R;
import com.mall.common.utils.FieldAssert;
import com.mall.common.utils.FileUtil;
import com.mall.sys.entity.MallGoods;
import com.mall.sys.entity.MallGoodsImg;
import com.mall.sys.service.MallGoodsImgService;
import com.mall.sys.service.MallGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 商品图片表 前端控制器
 */
@RestController
@RequestMapping("/sys/mallGoodsImg")
@Api(tags = "商品图片表")
public class MallGoodsImgController {

    @Autowired
    MallGoodsImgService iMallGoodsImgService;


    @Autowired
    MallGoodsService mallGoodsService;

    /**
     * 查询商品图片表列表
     */
    @GetMapping("queryMallGoodsImgList/{id}")
    @ApiOperation("商品图片表列表")
    public R queryMallGoodsImgList(@PathVariable Long id) {
        QueryWrapper<MallGoodsImg> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_id",id);
        List<MallGoodsImg> list = iMallGoodsImgService.list(queryWrapper);
        return R.ok().data("dataList", list);
    }

    /**
     * 保存或者更新商品图片表信息
     */
    @PostMapping("saveOrUpdateMallGoodsImg")
    @ApiOperation("保存或者更新商品图片表信息")
    public R saveOrUpdateMallGoodsImg(@RequestBody MallGoodsImg entity) {
        boolean b = true;
        if (StringUtils.isEmpty(entity.getId())) {
            String imgUrl = entity.getImgUrl();
            String i=imgUrl.contains("http://")?"http://":"https://";
            String substring =imgUrl.substring(imgUrl.indexOf(i) + i.length());
            String substringAll =substring.substring(substring.indexOf("/"));
            entity.setImgUrl(substringAll);
            b = iMallGoodsImgService.save(entity);
        } else {
            b = iMallGoodsImgService.updateById(entity);
        }
        return R.th(b);
    }


    /**
     * 根据id查询商品图片表信息
     */
    @GetMapping("getByIdMallGoodsImg/{id}")
    @ApiOperation("根据id查询商品图片表信息")
    public R getByIdMallGoodsImg(@PathVariable("id") String id) {
        MallGoodsImg dataMallGoodsImg = iMallGoodsImgService.getById(id);
        return R.ok().data("entity", dataMallGoodsImg);

    }

    /**
     * 根据id删除商品图片表信息
     */
    @DeleteMapping("removeByIdMallGoodsImg/{id}")
    @ApiOperation("根据id删除商品图片表信息")
    public R removeByIdMallGoodsImg(@PathVariable("id") String id) {
        boolean effect = iMallGoodsImgService.removeById(id);
        return R.th(effect);

    }

    /**
     * 根据id删除商品图片表信息
     */
    @PostMapping("uploadMallGoodsImg")
    @ApiOperation("上传图片")
    public R uploadImg(MultipartFile httpFile, Long goodsId) throws Exception {
        FieldAssert.isNull(goodsId, "请传入商品编号");
        MallGoods byId = mallGoodsService.getById(goodsId);
        FieldAssert.isNull(byId, "商品编号有误");
        FieldAssert.isNull(httpFile, "请传入图片");
        return R.ok().data("path", FileUtil.uploadAffix(httpFile, byId.getGoodsTypeCode(), byId.getId().toString()));

    }


}

