package com.mall.sys.mapper;

import com.mall.sys.entity.MallGoodsType;
import com.mall.sys.entity.vo.MallGoodsTypeVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 商品类型表 Mapper 接口
 */
public interface MallGoodsTypeMapper extends BaseMapper<MallGoodsType> {

    /*查询后端设置了为主页显示，即home=1的类型*/
    List<MallGoodsTypeVo> getMallGoodsTypeVo();

}
