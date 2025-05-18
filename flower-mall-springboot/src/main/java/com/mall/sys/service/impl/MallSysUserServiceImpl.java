package com.mall.sys.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.common.handler.GlobalException;
import com.mall.common.handler.GlobalExceptionEnum;
import com.mall.common.utils.CacheTemplateUtil;
import com.mall.common.utils.JwtUtils;
import com.mall.common.utils.MD5Util;
import com.mall.common.utils.SnowflakeUtil;
import com.mall.sys.entity.MallMenuRole;
import com.mall.sys.entity.MallSysUser;
import com.mall.sys.entity.queryVo.MallSysUserQueryVo;
import com.mall.sys.mapper.MallSysUserMapper;
import com.mall.sys.service.MallMenuRoleService;
import com.mall.sys.service.MallMenuService;
import com.mall.sys.service.MallSysUserService;
import com.mall.common.utils.*;
import com.mall.sys.service.MallSysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户表 服务实现类
 */
@Service
public class MallSysUserServiceImpl extends ServiceImpl<MallSysUserMapper, MallSysUser> implements MallSysUserService {


    @Autowired
    MallSysRoleService mallSysRoleService;

    @Autowired
    MallMenuRoleService mallMenuRoleService;

    @Autowired
    MallMenuService mallMenuService;


    @Override
    public Map login(MallSysUser user, HttpServletRequest request) {
        QueryWrapper<MallSysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        MallSysUser userBs = baseMapper.selectOne(queryWrapper);
        if (userBs == null) {
            throw new GlobalException(GlobalExceptionEnum.LOGIN_FAILED.getCode(), "无此用户");
        }
        Object o = CacheTemplateUtil.get("locking_data_" + userBs.getId());
        if (o != null) {
            throw new GlobalException(GlobalExceptionEnum.LOGIN_FAILED.getCode(), "账户已被锁定请联系管理员"
            );
        }

        MD5Util.verification(userBs.getPassword(), userBs.getSalt(), user.getPassword(), userBs.getId());

        String s = IdUtil.simpleUUID();

        long l = System.currentTimeMillis();
        CacheTemplateUtil.set("login_key_" + userBs.getId(), s);
        //token
        String token = JwtUtils.createJWT(SnowflakeUtil.randomLong() + "", "hujie", "{'userId':" + userBs.getId() + ",'username':" + userBs.getUsername() + ",'type':" + userBs.getType() + ",'roleId':" + userBs.getRoleId() + ",loginDateLong:" + l + ",loginKey:" + s + "}", 60 * 1000 * 30);
        CacheTemplateUtil.del("locking_data_" + userBs.getId(), "login_error_frequency_" + userBs.getId());


        Map map = new HashMap();
        map.put("token", token);
        map.put("modul", mallMenuService.getListMode(userBs.getRoleId()));
        map.put("btu", mallMenuService.getListModeBtn(userBs.getRoleId()));
        map.put("userName", userBs.getUsername());


        return map;
    }


    @Override
    public IPage queryPage(MallSysUserQueryVo mallSysUser) {
        return baseMapper.queryPage(mallSysUser.getPage(), mallSysUser);
    }

    @Override
    public void uptUser(MallSysUser user) {
        boolean b = saveOrUpdate(user);
        if (!b) throw new GlobalException(GlobalExceptionEnum.SAVE_FAILED.getCode(), "更新失败");
    }

    @Override
    public void addUser(MallSysUser user) {

        boolean name = isName(user.getUsername());
        if (!name && StringUtils.isEmpty(user.getId())) {
            throw new GlobalException(GlobalExceptionEnum.SAVE_FAILED.getCode(), "用户名重复");
        }

        if (StringUtils.isEmpty(user.getSalt())) {
            Map<String, String> map = MD5Util.encryptionPass(user.getPassword());
            user.setPassword(map.get("password"));
            user.setSalt(map.get("salt"));
        }
        boolean save = saveOrUpdate(user);
        if (!save) {
            throw new GlobalException(GlobalExceptionEnum.SAVE_FAILED.getCode(), "保存失败");
        }
    }


    @Override
    public boolean isName(String name) {
        QueryWrapper<MallSysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", name);
        int count = count(queryWrapper);
        if (count == 0) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delUser(String id) {
        QueryWrapper<MallMenuRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("menu_code", id);
        mallMenuRoleService.remove(queryWrapper);
        return removeById(id);
    }

    @Override
    public Map<String, Object> userDetail(String id) {
        HashMap<String, Object> returnTotalMap = new HashMap<>();
        //基本信息
        MallSysUser byId = baseMapper.getUserInfo(id);
        returnTotalMap.put("userInfoAll", byId);
        return returnTotalMap;
    }


}
