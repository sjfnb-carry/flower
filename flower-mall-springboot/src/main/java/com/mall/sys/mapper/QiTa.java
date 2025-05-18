package com.mall.sys.mapper;

import com.mall.sys.entity.MallGoodsS;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QiTa {

    List<MallGoodsS> selectShop();

}
