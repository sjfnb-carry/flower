package com.cl.dao;

import com.cl.entity.XitonggonggaoEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.XitonggonggaoView;


/**
 * 系统公告
 * 
 * @author 
 * @email 
 * @date 2025-04-10 10:21:34
 */
public interface XitonggonggaoDao extends BaseMapper<XitonggonggaoEntity> {
	
	List<XitonggonggaoView> selectListView(@Param("ew") Wrapper<XitonggonggaoEntity> wrapper);

	List<XitonggonggaoView> selectListView(Pagination page,@Param("ew") Wrapper<XitonggonggaoEntity> wrapper);
	
	XitonggonggaoView selectView(@Param("ew") Wrapper<XitonggonggaoEntity> wrapper);


}
