package com.mall.sys.controller;


import com.mall.common.query.CommonSave;
import com.mall.common.result.R;
import com.mall.sys.service.MallMenuRoleService;
import com.mall.sys.service.MallMenuService;
import com.mall.sys.service.MallSysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单角色中间表 前端控制器
 */
@RestController
@RequestMapping("/sys/mallMenuRole")
@Api(tags="菜单角色中间表")
public class MallMenuRoleController {


    @Autowired
    MallMenuService mallMenuService;

    @Autowired
    MallMenuRoleService mallMenuRoleService;

    @Autowired
    MallSysRoleService mallSysRoleService;


    @GetMapping("getByModuleCode/{id}")
    @ApiOperation("返回编辑菜单时所需数据")
    public R getByModuleCode(@PathVariable String id) {

        return R.ok()
                .data("tree", mallMenuService.getMenuAll())
                .data("finallyList", mallMenuRoleService.getFinallyModule(id))
                .data("role", mallSysRoleService.getById(id));
    }


    @PostMapping("save")
    @ApiOperation("保存/为角色赋予权限")
    public R save(@RequestBody CommonSave commonSave) {
        return R.th(mallMenuRoleService.saveMenuRoleService(commonSave));
    }

    @GetMapping("getById/{id}")
    @ApiOperation("查询详情")
    public R getBy(@PathVariable String id){
        return R.ok().data("data",mallMenuService.getByModel(id));
    }


}

