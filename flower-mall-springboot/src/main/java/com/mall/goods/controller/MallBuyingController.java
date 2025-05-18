package com.mall.goods.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.common.annotation.IgnoreSecurity;
import com.mall.common.result.R;
import com.mall.goods.entity.vo.CommentGoodsVo;
import com.mall.goods.entity.vo.MallBuyingInfoVo;
import com.mall.goods.entity.vo.MallBuyingVo;
import com.mall.goods.mapper.CommentGoodsMapper;
import com.mall.goods.mapper.MallBuyingMapper;
import com.mall.sys.entity.MallGoodsImg;
import com.mall.sys.entity.vo.MallGoodsVo;
import com.mall.sys.mapper.MallGoodsMapper;
import com.mall.sys.service.MallGoodsImgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.mall.goods.entity.MallBuying;
import com.mall.goods.service.MallBuyingService;
import com.mall.goods.entity.queryVo.MallBuyingQueryVo;

import javax.annotation.Resource;
import java.util.List;

/*求购信息表 前端控制器*/
@RestController
@RequestMapping("/goods/mallBuying")
@Api(tags = "求购信息表")
public class MallBuyingController {

    @Autowired
    MallBuyingService iMallBuyingService;

    @Autowired
    MallGoodsImgService mallGoodsImgService;

    @Resource
    MallBuyingMapper mallBuyingMapper;

//    @Autowired
//    CommentGoodsService commentGoodsService;


    @Resource
    MallGoodsMapper mapper;

    @Resource
    CommentGoodsMapper commentGoodsMapper;

    /*查询求购信息表列表*/
    @PostMapping("queryMallBuyingList")
    @ApiOperation("求购信息表列表")
    @IgnoreSecurity
    public R queryMallBuyingList(@RequestBody MallBuyingQueryVo queryVo) {
        return R.ok().data("dataList", mallBuyingMapper.queryPage(queryVo.getPage()));
    }

    /*保存或者更新求购信息表信息*/
    @PostMapping("saveOrUpdateMallBuying")
    @ApiOperation("保存或者更新求购信息表信息")
    public R saveOrUpdateMallBuying(@RequestBody MallBuyingVo entity) {
        if (StringUtils.isEmpty(entity.getId())) {
            iMallBuyingService.saveMallBuyingVo(entity);
        } else {
            iMallBuyingService.updateById(entity);
        }
        return R.ok();
    }


    /*根据id查询求购信息表信息*/
    @GetMapping("getByIdMallBuying/{id}")
    @ApiOperation("根据id查询求购信息表信息")
    @IgnoreSecurity
    public R getByIdMallBuying(@PathVariable("id") String id) {
        MallBuying dataMallBuying = iMallBuyingService.getById(id);
        QueryWrapper<MallGoodsImg> imgQueryWrapper = new QueryWrapper<>();
        imgQueryWrapper.eq("goods_id", id);

        MallBuyingInfoVo mallBuyingInfoVo = BeanUtil.toBean(dataMallBuying, MallBuyingInfoVo.class);
        List<MallGoodsImg> goodsImgs = mallGoodsImgService.list(imgQueryWrapper);
        List<CommentGoodsVo> commentGoods = commentGoodsMapper.getComment(id);
        List<MallGoodsVo> mallGoodsVos = mapper.queryGoods(id);
        mallBuyingInfoVo.setImgList(goodsImgs).setCommentGoods(commentGoods).setGoodsList(mallGoodsVos);
        return R.ok().data("entity", mallBuyingInfoVo);
    }

    /*根据id删除求购信息表信息*/
    @DeleteMapping("removeByIdMallBuying/{id}")
    @ApiOperation("根据id删除求购信息表信息")
    public R removeByIdMallBuying(@PathVariable("id") String id) {
        boolean effect = iMallBuyingService.removeById(id);
        return R.th(effect);
    }

}

