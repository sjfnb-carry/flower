package com.mall.goods.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.goods.entity.MallBuying;
import com.mall.goods.entity.vo.MallBuyingVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/*求购信息表 Mapper 接口*/
public interface MallBuyingMapper extends BaseMapper<MallBuying> {

    Page<MallBuyingVo> queryPage(Page page);

}
