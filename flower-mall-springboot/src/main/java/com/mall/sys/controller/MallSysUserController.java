package com.mall.sys.controller;


import cn.hutool.json.JSONUtil;
import com.mall.common.annotation.IgnoreSecurity;
import com.mall.common.result.R;
import com.mall.common.utils.JwtUtils;
import com.mall.common.utils.SnowflakeUtil;
import com.mall.common.utils.UserInfoUtil;
import com.mall.common.utils.UserLogInfoDto;
import com.mall.sys.entity.MallSysUser;
import com.mall.sys.entity.queryVo.MallSysUserQueryVo;
import com.mall.sys.service.MallMenuService;
import com.mall.sys.service.MallSysUserService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 用户表 前端控制器
 */
@RestController
@RequestMapping("/sys/mallSysUser")
@Api(tags = "用户表")
public class MallSysUserController {

    @Autowired
    MallSysUserService mallSysUserService;


    @Autowired
    MallMenuService mallMenuService;




    /**
     * 登录接口
     */
    @PostMapping("login")
    @IgnoreSecurity
    @ApiOperation("登录")
    public R login(@RequestBody MallSysUser user, HttpServletRequest request) {

        Map login = mallSysUserService.login(user, request);

        return R.ok().data(login);
    }

    @PostMapping("uptToken")
    @IgnoreSecurity
    @ApiOperation("uptToken")
    public R aaa(@RequestBody String tokenS) {
        Claims claims = JwtUtils.validateJWT(tokenS);
        String issuedAt = claims.getSubject();
        UserLogInfoDto userLogInfoDto = JSONUtil.toBean(issuedAt, UserLogInfoDto.class);
        String token = JwtUtils.createJWT(SnowflakeUtil.randomLong() + "", "hujie", "{'userId':" + userLogInfoDto.getUserId() + ",'username':" + userLogInfoDto.getUsername() + ",'type':" + userLogInfoDto.getType() + ",'roleId':" + userLogInfoDto.getRoleId() + ",loginDateLong:" + userLogInfoDto.getLoginDateLong() + ",loginKey:" + userLogInfoDto.getLoginKey() + "}", 60 * 1000 * 30);


        return R.ok().data("token", token);
    }


    /**
     分页查询
     */
    @PostMapping("queryPage")
    @ApiOperation("查看用户列表")
    public R queryPage(@RequestBody MallSysUserQueryVo mallSysUserQueryVo) {
        return R.ok().data("list", mallSysUserService.queryPage(mallSysUserQueryVo));
    }


    /**
     更新用户状态
     */
    @PutMapping("/users/{uid}/state/{status}")
    @ApiOperation("更新用户状态")
    public R uptStatus(@PathVariable("uid") Long uid, @PathVariable("status") Boolean b) {
        MallSysUser hujieitUser = new MallSysUser();
        hujieitUser.setId(uid);
        hujieitUser.setMgState(b);
        mallSysUserService.uptUser(hujieitUser);
        return R.ok();
    }

    @PostMapping("save")
    @ApiOperation("添加用户")
    public R saveUser(@RequestBody MallSysUser mallSysUser) {
        if (StringUtils.isEmpty(mallSysUser.getId())) {
            mallSysUserService.addUser(mallSysUser);
        } else {
            mallSysUserService.saveOrUpdate(mallSysUser);
        }

        return R.ok();
    }

    @DeleteMapping("del/{id}")
    @ApiOperation("删除用户")
    public R delUser(@PathVariable String id) {
        boolean b = mallSysUserService.delUser(id);
        return R.th(b);
    }


    @GetMapping("getById/{id}")
    @ApiOperation("查询用户详情")
    public R getByIdUser(@PathVariable String id) {
        return R.ok().data(mallSysUserService.userDetail(id));
    }

    @GetMapping("getByIdThis")
    @ApiOperation("查询用户详情")
    public R getByIdUserThis() {
        return R.ok().data(mallSysUserService.userDetail(UserInfoUtil.getUserId()));
    }

}

