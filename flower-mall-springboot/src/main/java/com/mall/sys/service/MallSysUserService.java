package com.mall.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.sys.entity.MallSysUser;
import com.mall.sys.entity.queryVo.MallSysUserQueryVo;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 用户表 服务类
 */
public interface MallSysUserService extends IService<MallSysUser> {

    Map login(MallSysUser user, HttpServletRequest request);



    /**
     *分页查询
     */
    IPage queryPage(MallSysUserQueryVo commonQuery);

    /**
     *更新or保存
     */
    void uptUser(MallSysUser user);


    void addUser(MallSysUser hujieitUser);


    /**
     * 查询用户是否存在
     */
    boolean isName(String name);


    /**
     *删除用户
     */
    boolean delUser(String id);


    /**
     *用户个人中心页面
     */
    Map<String, Object> userDetail(String id);
}
