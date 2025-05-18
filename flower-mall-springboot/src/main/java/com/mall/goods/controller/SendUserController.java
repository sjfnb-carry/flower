package com.mall.goods.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.common.result.R;
import com.mall.sys.service.MallGoodsImgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.mall.goods.entity.SendUser;
import com.mall.goods.service.SendUserService;
import com.mall.goods.entity.queryVo.SendUserQueryVo;


@RestController
@RequestMapping("/goods/sendUser")
@Api(tags="用户发布闲置表")
public class SendUserController {

    @Autowired
    SendUserService iSendUserService;

    @Autowired
    MallGoodsImgService mallGoodsImgService;


        /*查询用户发布闲置表列表*/
        @PostMapping("querySendUserList")
        @ApiOperation("用户发布闲置表列表")
        public R querySendUserList(@RequestBody SendUserQueryVo queryVo) {
            QueryWrapper<SendUser> queryWrapper=new QueryWrapper<>();
            Page<SendUser> page = iSendUserService.page(queryVo.getPage(), queryWrapper);
            return R.ok().data("dataList",page);
        }

        /*保存或者更新用户发布闲置表信息*/
        @PostMapping("saveOrUpdateSendUser")
        @ApiOperation("保存或者更新用户发布闲置表信息")
        public R saveOrUpdateSendUser(@RequestBody SendUser entity) {
            boolean b =true;
            if(StringUtils.isEmpty(entity.getId())){
                b= iSendUserService.save(entity);
            }else{
                b=iSendUserService.updateById(entity);
            }
            return R.th(b);
        }


        /*根据id查询用户发布闲置表信息*/
        @GetMapping("getByIdSendUser/{id}")
        @ApiOperation("根据id查询用户发布闲置表信息")
        public R getByIdSendUser(@PathVariable("id") String id) {
             SendUser dataSendUser=  iSendUserService.getById(id);
             return R.ok().data("entity",dataSendUser);

        }
        /*根据id删除用户发布闲置表信息*/
        @DeleteMapping("removeByIdSendUser/{id}")
        @ApiOperation("根据id删除用户发布闲置表信息")
        public R removeByIdSendUser(@PathVariable("id") String id) {
            boolean effect= iSendUserService.removeById(id);
            return R.th(effect);

        }




}

