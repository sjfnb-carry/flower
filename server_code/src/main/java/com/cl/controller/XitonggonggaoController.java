package com.cl.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import com.cl.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cl.annotation.IgnoreAuth;

import com.cl.entity.XitonggonggaoEntity;
import com.cl.entity.view.XitonggonggaoView;

import com.cl.service.XitonggonggaoService;
import com.cl.service.TokenService;
import com.cl.utils.PageUtils;
import com.cl.utils.R;
import com.cl.utils.MPUtil;
import com.cl.utils.MapUtils;
import com.cl.utils.CommonUtil;

/**
 * 系统公告
 * 后端接口
 * @author 
 * @email 
 * @date 2025-04-10 10:21:34
 */
@RestController
@RequestMapping("/xitonggonggao")
public class XitonggonggaoController {
    @Autowired
    private XitonggonggaoService xitonggonggaoService;









    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,XitonggonggaoEntity xitonggonggao,
                                                                                            HttpServletRequest request){
                                    EntityWrapper<XitonggonggaoEntity> ew = new EntityWrapper<XitonggonggaoEntity>();
                                                                                                                                        
        
        
        PageUtils page = xitonggonggaoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, xitonggonggao), params), params));
        return R.ok().put("data", page);
    }







    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,XitonggonggaoEntity xitonggonggao,
		HttpServletRequest request){
        EntityWrapper<XitonggonggaoEntity> ew = new EntityWrapper<XitonggonggaoEntity>();

		PageUtils page = xitonggonggaoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, xitonggonggao), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( XitonggonggaoEntity xitonggonggao){
       	EntityWrapper<XitonggonggaoEntity> ew = new EntityWrapper<XitonggonggaoEntity>();
      	ew.allEq(MPUtil.allEQMapPre( xitonggonggao, "xitonggonggao")); 
        return R.ok().put("data", xitonggonggaoService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(XitonggonggaoEntity xitonggonggao){
        EntityWrapper< XitonggonggaoEntity> ew = new EntityWrapper< XitonggonggaoEntity>();
 		ew.allEq(MPUtil.allEQMapPre( xitonggonggao, "xitonggonggao")); 
		XitonggonggaoView xitonggonggaoView =  xitonggonggaoService.selectView(ew);
		return R.ok("查询系统公告成功").put("data", xitonggonggaoView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        XitonggonggaoEntity xitonggonggao = xitonggonggaoService.selectById(id);
		xitonggonggao = xitonggonggaoService.selectView(new EntityWrapper<XitonggonggaoEntity>().eq("id", id));
        return R.ok().put("data", xitonggonggao);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        XitonggonggaoEntity xitonggonggao = xitonggonggaoService.selectById(id);
		xitonggonggao = xitonggonggaoService.selectView(new EntityWrapper<XitonggonggaoEntity>().eq("id", id));
        return R.ok().put("data", xitonggonggao);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody XitonggonggaoEntity xitonggonggao, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(xitonggonggao);
        xitonggonggaoService.insert(xitonggonggao);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody XitonggonggaoEntity xitonggonggao, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(xitonggonggao);
        xitonggonggaoService.insert(xitonggonggao);
        return R.ok();
    }



    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody XitonggonggaoEntity xitonggonggao, HttpServletRequest request){
        //ValidatorUtils.validateEntity(xitonggonggao);
        xitonggonggaoService.updateById(xitonggonggao);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        xitonggonggaoService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	









}
