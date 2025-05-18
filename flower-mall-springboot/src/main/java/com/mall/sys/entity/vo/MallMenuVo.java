package com.mall.sys.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * 菜单增强类
 */
@Data
public class MallMenuVo implements Comparable<MallMenuVo>{
    private List<MallMenuVo> Children;

    private String menuCode;
    private String icon;
    private String menuPath;
    private String parentId;
    private Integer sort;
    private String menuName;
    private String id;


    @Override
    public int compareTo(MallMenuVo o) {
        return this.sort -o.sort;
    }
}
