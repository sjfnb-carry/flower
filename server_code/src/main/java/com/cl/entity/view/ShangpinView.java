package com.cl.entity.view;

import com.cl.entity.ShangpinEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import java.io.Serializable;
import com.cl.utils.EncryptUtil;
 

/**
 * 商品
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2025-04-10 10:21:34
 */
@TableName("shangpin")
public class ShangpinView  extends ShangpinEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public ShangpinView(){
	}
 
 	public ShangpinView(ShangpinEntity shangpinEntity){
 	try {
			BeanUtils.copyProperties(this, shangpinEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}



}
