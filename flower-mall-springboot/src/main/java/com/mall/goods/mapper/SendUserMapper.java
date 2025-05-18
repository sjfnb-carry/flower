package com.mall.goods.mapper;

import com.mall.goods.entity.SendUser;
import com.mall.sys.entity.vo.MallGoodsVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*用户发布闲置表 Mapper 接口*/
public interface SendUserMapper extends BaseMapper<SendUser> {

   List<MallGoodsVo> getByUserGoods(@Param("userId") Long userId);
}
