package com.mall.sys.entity.vo;

import com.mall.sys.entity.MallMenu;
import lombok.Data;


@Data
public class MallMenuGet extends MallMenu {

    private String parentName;
}
