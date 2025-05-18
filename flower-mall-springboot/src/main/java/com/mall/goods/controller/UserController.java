package com.mall.goods.controller;


import com.mall.common.annotation.IgnoreSecurity;
import com.mall.common.result.R;
import com.mall.common.utils.CoreFactory;
import com.mall.common.utils.MD5Util;
import com.mall.common.utils.UserInfoUtil;
import com.mall.goods.entity.vo.ChangePassword;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.mall.goods.entity.User;
import com.mall.goods.service.UserService;

import java.util.Map;


@RestController
@RequestMapping("/goods/user")
@Api(tags = "商城用户表")
public class UserController {

    @Autowired
    UserService iUserService;

    @Autowired
    CoreFactory coreFactory;


    /**
     * 登录接口
     */
    @PostMapping("login")
    @IgnoreSecurity
    @ApiOperation("登录")
    public R login(@RequestBody User user) {
        Map<String, Object> login = iUserService.login(user);
        return R.ok().data(login);
    }

    @GetMapping("userDetail")
    @ApiOperation("用户详情")
    public R getById() {
        return R.ok().data("data", iUserService.getByUsId(Long.valueOf(UserInfoUtil.getUserId())));
    }


    /*注冊并初始化账户*/
    @PostMapping("registered")
    @ApiOperation("注册")
    @IgnoreSecurity
    public R registered(@RequestBody User user) {
        iUserService.registered(user);
        return R.ok();
    }

    /*查询携带余额的用户信息*/
    @GetMapping("getByCarryMoneyDetail")
    public R getByCarryMoneyDetail() {
        return R.ok().data("dataInfo", iUserService.getByCarryMoneyDetail(Long.valueOf(UserInfoUtil.getUserId())));
    }

    @PostMapping("changePassword")
    public R changePassword(@RequestBody ChangePassword changePassword) {
        String userId = UserInfoUtil.getUserId();
        User byId = iUserService.getById(userId);
        MD5Util.verification(byId.getPassword(), byId.getSalt(), changePassword.getDqPassword(), Long.valueOf(userId));
        if (changePassword.getXqPassword().equals(changePassword.getQdPassword())) {
            Map<String, String> stringStringMap = MD5Util.encryptionPass(changePassword.getQdPassword());
            User user = new User();
            user.setId(Long.valueOf(userId));
            user.setPassword(stringStringMap.get("password"));
            user.setSalt(stringStringMap.get("salt"));
            boolean update = iUserService.updateById(user);
        } else {
            return R.error(50000, "两次密码不一致");
        }
        return R.ok();
    }

    @PostMapping("uptUserInfo")
    public R uptUserInfo(@RequestBody User user) {

        iUserService.updateById(user);
        return R.ok();
    }

}

