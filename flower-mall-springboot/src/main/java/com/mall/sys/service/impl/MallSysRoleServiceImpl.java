package com.mall.sys.service.impl;

import com.mall.sys.entity.MallSysRole;
import com.mall.sys.mapper.MallSysRoleMapper;
import com.mall.sys.service.MallSysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色信息表 服务实现类
 */
@Service
public class MallSysRoleServiceImpl extends ServiceImpl<MallSysRoleMapper, MallSysRole> implements MallSysRoleService {

    @Override
    public List<MallSysRole> queryRoleAll() {
        return list();
    }
}
