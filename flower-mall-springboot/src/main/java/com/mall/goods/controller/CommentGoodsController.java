package com.mall.goods.controller;


import com.mall.common.result.R;
import com.mall.common.utils.UserInfoUtil;
import com.mall.goods.mapper.CommentGoodsMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.mall.goods.entity.CommentGoods;
import com.mall.goods.service.CommentGoodsService;

import javax.annotation.Resource;
import java.util.Date;


@RestController
@RequestMapping("/goods/commentGoods")
@Api(tags="评分")
public class CommentGoodsController {

    @Autowired
    CommentGoodsService iCommentGoodsService;

    @Resource
    CommentGoodsMapper commentGoodsMapper;

        /*查询评分列表*/
        @GetMapping("queryCommentGoodsList/{goodsId}")
        @ApiOperation("评分列表")
        public R queryCommentGoodsList(@PathVariable("goodsId") String goodsId) {
//            QueryWrapper<CommentGoods> queryWrapper=new QueryWrapper<>();
//            queryWrapper.eq("mall_goods_id",goodsId);
//            List<CommentGoods> list = iCommentGoodsService.list(queryWrapper);
            return R.ok().data("dataList",commentGoodsMapper.getComment(goodsId));
        }

        /*保存或者更新评分信息*/
        @PostMapping("saveOrUpdateCommentGoods")
        @ApiOperation("保存或者更新评分信息")
        public R saveOrUpdateCommentGoods(@RequestBody CommentGoods entity) {
            boolean b =true;
            if(StringUtils.isEmpty(entity.getId())){
                entity.setGoodsUserId(Long.valueOf(UserInfoUtil.getUserId()));
                entity.setCreationTime(new Date());
                b= iCommentGoodsService.save(entity);
            }else{
                b=iCommentGoodsService.updateById(entity);
            }
            return R.th(b);
        }


        /*根据id查询评分信息*/
        @GetMapping("getByIdCommentGoods/{id}")
        @ApiOperation("根据id查询评分信息")
        public R getByIdCommentGoods(@PathVariable("id") String id) {
             CommentGoods dataCommentGoods=  iCommentGoodsService.getById(id);
             return R.ok().data("entity",dataCommentGoods);

        }
        /*根据id删除评分信息*/
        @DeleteMapping("removeByIdCommentGoods/{id}")
        @ApiOperation("根据id删除评分信息")
        public R removeByIdCommentGoods(@PathVariable("id") String id) {
            boolean effect= iCommentGoodsService.removeById(id);
            return R.th(effect);

        }

}

