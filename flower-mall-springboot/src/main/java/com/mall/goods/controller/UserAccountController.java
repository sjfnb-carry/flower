package com.mall.goods.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.common.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.mall.goods.entity.UserAccount;
import com.mall.goods.service.UserAccountService;
import com.mall.goods.entity.queryVo.UserAccountQueryVo;


@RestController
@RequestMapping("/goods/userAccount")
@Api(tags="用户账户表")
public class UserAccountController {

    @Autowired
    UserAccountService iUserAccountService;

        /*查询用户账户表列表*/
        @PostMapping("queryUserAccountList")
        @ApiOperation("用户账户表列表")
        public R queryUserAccountList(@RequestBody UserAccountQueryVo queryVo) {
            QueryWrapper<UserAccount> queryWrapper=new QueryWrapper<>();
            Page<UserAccount> page = iUserAccountService.page(queryVo.getPage(), queryWrapper);
            return R.ok().data("dataList",page);
        }

        /*保存或者更新用户账户表信息*/
        @PostMapping("saveOrUpdateUserAccount")
        @ApiOperation("保存或者更新用户账户表信息")
        public R saveOrUpdateUserAccount(@RequestBody UserAccount entity) {
            boolean b =true;
            if(StringUtils.isEmpty(entity.getId())){
                b= iUserAccountService.save(entity);
            }else{
                b=iUserAccountService.updateById(entity);
            }
            return R.th(b);
        }


        /*根据id查询用户账户表信*/
        @GetMapping("getByIdUserAccount/{id}")
        @ApiOperation("根据id查询用户账户表信息")
        public R getByIdUserAccount(@PathVariable("id") String id) {
             UserAccount dataUserAccount=  iUserAccountService.getById(id);
             return R.ok().data("entity",dataUserAccount);

        }
        /*根据id删除用户账户表信息*/
        @DeleteMapping("removeByIdUserAccount/{id}")
        @ApiOperation("根据id删除用户账户表信息")
        public R removeByIdUserAccount(@PathVariable("id") String id) {
            boolean effect= iUserAccountService.removeById(id);
            return R.th(effect);

        }

}

