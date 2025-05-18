package com.mall.goods.mapper;

import com.mall.goods.entity.CommentGoods;
import com.mall.goods.entity.vo.CommentGoodsVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*评分 Mapper 接口*/
public interface CommentGoodsMapper extends BaseMapper<CommentGoods> {

    List<CommentGoodsVo> getComment(@Param("id") String id);

}
