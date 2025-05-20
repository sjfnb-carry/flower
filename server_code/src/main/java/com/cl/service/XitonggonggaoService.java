package com.cl.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.cl.utils.PageUtils;
import com.cl.entity.XitonggonggaoEntity;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.XitonggonggaoView;


/**
 * 系统公告
 *
 * @author 
 * @email 
 * @date 2025-04-10 10:21:34
 */
public interface XitonggonggaoService extends IService<XitonggonggaoEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<XitonggonggaoView> selectListView(Wrapper<XitonggonggaoEntity> wrapper);
   	
   	XitonggonggaoView selectView(@Param("ew") Wrapper<XitonggonggaoEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<XitonggonggaoEntity> wrapper);
   	
   
}

