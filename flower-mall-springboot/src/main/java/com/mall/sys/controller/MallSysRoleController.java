package com.mall.sys.controller;


import com.mall.common.result.R;
import com.mall.common.utils.UserInfoUtil;
import com.mall.sys.entity.MallSysRole;
import com.mall.sys.service.MallSysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 角色信息表 前端控制器
 */
@RestController
@RequestMapping("/sys/mallSysRole")
@Api(tags="角色信息表")
public class MallSysRoleController {

    @Autowired
    MallSysRoleService mallSysRoleService;



    /**
    查询角色列表
     */
    @GetMapping("queryPage")
    @ApiOperation("查询校色列表")
    public R queryPage(){
        return R.ok().data("list",mallSysRoleService.queryRoleAll());
    }

    @GetMapping("queryAll")
    @ApiOperation("返回所有角色")
    public R queryAll(){
        return R.ok().data("data",mallSysRoleService.list());
    }


    @PostMapping("save")
    @ApiOperation("保存角色")
    public R saveRole(@RequestBody MallSysRole mallSysRole){
        mallSysRole.setCreateId(UserInfoUtil.getUserId());
        mallSysRole.setCreateTime(new Date());
        mallSysRoleService.saveOrUpdate(mallSysRole);
        return R.ok();
    }
}

