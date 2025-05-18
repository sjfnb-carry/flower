package com.mall.sys.service.impl;

import com.mall.common.handler.GlobalException;
import com.mall.common.handler.GlobalExceptionEnum;
import com.mall.common.utils.CodeUtils;
import com.mall.sys.entity.MallMenu;
import com.mall.sys.entity.vo.MallMenuGet;
import com.mall.sys.entity.vo.MallMenuVo;
import com.mall.sys.mapper.MallMenuMapper;
import com.mall.sys.service.MallMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单 服务实现类
 */
@Service
public class MallMenuServiceImpl extends ServiceImpl<MallMenuMapper, MallMenu> implements MallMenuService {

    @Override
    public List<MallMenuVo> getListMode(String roleId) {
        List<MallMenuVo> listMode = baseMapper.getListMode(roleId);
        return tree(listMode, "00");
    }


    /**
     *返回菜单树
     */
    List<MallMenuVo> tree(List<MallMenuVo> orgVos, String parentIds) {
        //返回的容器
        List<MallMenuVo> mylist = new ArrayList<>();
        for (MallMenuVo org : orgVos
        ) {
            String moduleId = org.getMenuCode();
            String parentId = org.getParentId();
            if (parentIds.equals(parentId)) {
                List<MallMenuVo> below = tree(orgVos, moduleId);
                org.setChildren(below);
                mylist.add(org);
            }
        }
        return mylist;
    }


    @Override
    public List<MallMenuVo> getMenuAll() {
        return tree(baseMapper.findMenuAll(), "00");
    }

    @Override
    public String getListModeBtn(String roleId) {

        List<String> allBtn = baseMapper.getAllBtn(roleId);
        return String.join(",", allBtn);
    }

    @Override
    public MallMenuGet getByModel(String id) {
        return baseMapper.getByModel(id);
    }

    @Override
    public Boolean saveModel(MallMenu mallMenu) {
        //生成当前菜单编码
        String s = baseMapper.maxModel(mallMenu.getParentId());
        String replace = CodeUtils.getMaxIncrease(s,mallMenu.getParentId());
        mallMenu.setMenuCode(replace);
        return save(mallMenu);
    }


    @Override
    public boolean delModel(String id) {

        Integer isnull = baseMapper.isnull(id);
        if (isnull != 0) {
            throw new GlobalException(GlobalExceptionEnum.FAILED_TO_DELETE.getCode(), "有子菜单无法删除");
        }
        return removeById(id);
    }

}
