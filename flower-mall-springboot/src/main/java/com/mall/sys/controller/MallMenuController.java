package com.mall.sys.controller;



import com.mall.common.result.R;
import com.mall.common.utils.UserInfoUtil;
import com.mall.sys.entity.MallMenu;
import com.mall.sys.service.MallMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单 前端控制器
 */
@RestController
@RequestMapping("/sys/mallMenu")
@Api(tags = "菜单")
public class MallMenuController {


    @Autowired
    MallMenuService mallMenuService;





    /**
     返回菜单列表
     */
    @GetMapping("list")
    @ApiOperation("当前用户拥有菜单列表")
    public R getListMode() {
        String roleId = UserInfoUtil.getRoleId();
        return R.ok().data("tree", mallMenuService.getListMode(roleId)).data("btn", mallMenuService.getListModeBtn(roleId));
    }


    /**
     保存菜单信息
     */
    @PostMapping("save")
    @ApiOperation("保存菜单")
    public R getAll(@RequestBody MallMenu hujieitMenu) {
        return R.th(mallMenuService.saveModel(hujieitMenu));
    }

    @PostMapping("upt")
    @ApiOperation("更新")
    public R getUpt(@RequestBody MallMenu hujieitMenu) {
        return R.th(mallMenuService.updateById(hujieitMenu));
    }

    @DeleteMapping("del/{id}")
    @ApiOperation("删除")
    public R getUpt(@PathVariable String id) {
        return R.th(mallMenuService.delModel(id));
    }
    
}

