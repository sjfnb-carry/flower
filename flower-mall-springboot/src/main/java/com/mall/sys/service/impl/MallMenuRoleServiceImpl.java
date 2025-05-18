package com.mall.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.common.query.CommonSave;
import com.mall.sys.entity.MallMenuRole;
import com.mall.sys.mapper.MallMenuRoleMapper;
import com.mall.sys.service.MallMenuRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.sys.service.MallSysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单角色中间表 服务实现类
 */
@Service
public class MallMenuRoleServiceImpl extends ServiceImpl<MallMenuRoleMapper, MallMenuRole> implements MallMenuRoleService {


    @Autowired
    MallSysRoleService mallSysRoleService;


    @Override
    public List<String> getFinallyModule(String id) {
        return baseMapper.getFinallyModule(id);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Boolean saveMenuRoleService(CommonSave commonSave) {

        mallSysRoleService.saveOrUpdate(commonSave.getRoleId());

        QueryWrapper<MallMenuRole> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("role_id",commonSave.getRoleId().getId());
        boolean remove = remove(queryWrapper);
        boolean b = saveBatch(saveRoleList(commonSave));
        return b;


    }


    public List<MallMenuRole> saveRoleList(CommonSave commonSave){
        List<String> list = commonSave.getList();
        Long roleId = commonSave.getRoleId().getId();
        List<MallMenuRole> reList=new ArrayList<>();
        for (String s : list) {
            MallMenuRole hujieitMenuRole = new MallMenuRole();
            hujieitMenuRole.setMenuCode(s);
            hujieitMenuRole.setRoleId(roleId);
            reList.add(hujieitMenuRole);
        }
        return reList;
    }
}
