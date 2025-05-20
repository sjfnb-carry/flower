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

import com.cl.entity.CartEntity;
import com.cl.entity.view.CartView;

import com.cl.service.CartService;
import com.cl.service.TokenService;
import com.cl.utils.PageUtils;
import com.cl.utils.R;
import com.cl.utils.MPUtil;
import com.cl.utils.MapUtils;
import com.cl.utils.CommonUtil;

/**
 * 购物车
 * 后端接口
 * @author 
 * @email 
 * @date 2025-04-10 10:21:34
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;









    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,CartEntity cart,
                                                                                                                        HttpServletRequest request){
                                    if(!request.getSession().getAttribute("role").toString().equals("管理员")) {
                    cart.setUserid((Long)request.getSession().getAttribute("userId"));
                }
                            EntityWrapper<CartEntity> ew = new EntityWrapper<CartEntity>();
                                                                                                                                                                                            
        
        
        PageUtils page = cartService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, cart), params), params));
        return R.ok().put("data", page);
    }







    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,CartEntity cart,
		HttpServletRequest request){
        EntityWrapper<CartEntity> ew = new EntityWrapper<CartEntity>();

		PageUtils page = cartService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, cart), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( CartEntity cart){
       	EntityWrapper<CartEntity> ew = new EntityWrapper<CartEntity>();
      	ew.allEq(MPUtil.allEQMapPre( cart, "cart")); 
        return R.ok().put("data", cartService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(CartEntity cart){
        EntityWrapper< CartEntity> ew = new EntityWrapper< CartEntity>();
 		ew.allEq(MPUtil.allEQMapPre( cart, "cart")); 
		CartView cartView =  cartService.selectView(ew);
		return R.ok("查询购物车成功").put("data", cartView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        CartEntity cart = cartService.selectById(id);
		cart = cartService.selectView(new EntityWrapper<CartEntity>().eq("id", id));
        return R.ok().put("data", cart);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        CartEntity cart = cartService.selectById(id);
		cart = cartService.selectView(new EntityWrapper<CartEntity>().eq("id", id));
        return R.ok().put("data", cart);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CartEntity cart, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(cart);
    	cart.setUserid((Long)request.getSession().getAttribute("userId"));
        cartService.insert(cart);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody CartEntity cart, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(cart);
        cartService.insert(cart);
        return R.ok();
    }



    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody CartEntity cart, HttpServletRequest request){
        //ValidatorUtils.validateEntity(cart);
        cartService.updateById(cart);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        cartService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	









}
