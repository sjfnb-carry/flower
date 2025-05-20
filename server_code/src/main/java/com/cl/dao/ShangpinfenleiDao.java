package com.cl.dao;

import com.cl.entity.ShangpinfenleiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.ShangpinfenleiView;


/**
 * 商品分类
 * 
 * @author 
 * @email 
 * @date 2025-04-10 10:21:34
 */
public interface ShangpinfenleiDao extends BaseMapper<ShangpinfenleiEntity> {
	
	List<ShangpinfenleiView> selectListView(@Param("ew") Wrapper<ShangpinfenleiEntity> wrapper);

	List<ShangpinfenleiView> selectListView(Pagination page,@Param("ew") Wrapper<ShangpinfenleiEntity> wrapper);
	
	ShangpinfenleiView selectView(@Param("ew") Wrapper<ShangpinfenleiEntity> wrapper);


    List<Map<String, Object>> selectValue(@Param("params") Map<String, Object> params,@Param("ew") Wrapper<ShangpinfenleiEntity> wrapper);

    List<Map<String, Object>> selectTimeStatValue(@Param("params") Map<String, Object> params,@Param("ew") Wrapper<ShangpinfenleiEntity> wrapper);

    List<Map<String, Object>> selectGroup(@Param("params") Map<String, Object> params,@Param("ew") Wrapper<ShangpinfenleiEntity> wrapper);



}
