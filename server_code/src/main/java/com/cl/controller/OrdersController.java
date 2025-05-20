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

import com.cl.entity.OrdersEntity;
import com.cl.entity.view.OrdersView;

import com.cl.service.OrdersService;
import com.cl.service.TokenService;
import com.cl.utils.PageUtils;
import com.cl.utils.R;
import com.cl.utils.MPUtil;
import com.cl.utils.MapUtils;
import com.cl.utils.CommonUtil;

/**
 * 商品订单
 * 后端接口
 * @author 
 * @email 
 * @date 2025-04-10 10:21:34
 */
@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;









    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,OrdersEntity orders,
                                                                                                                                                                                                                                                                                                                                HttpServletRequest request){
                    String tableName = request.getSession().getAttribute("tableName").toString();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    if(tableName.equals("shangjia")) {
                    orders.setShangjiamingcheng((String)request.getSession().getAttribute("username"));
                                            if(orders.getUserid()!=null) {
                            orders.setUserid(null);
                        }
                                    }
                                        else {
                        if(!request.getSession().getAttribute("role").toString().equals("管理员")) {
                            orders.setUserid((Long)request.getSession().getAttribute("userId"));
                        }
                    }
                                                                                            EntityWrapper<OrdersEntity> ew = new EntityWrapper<OrdersEntity>();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
        
        
        PageUtils page = ordersService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, orders), params), params));
        return R.ok().put("data", page);
    }







    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,OrdersEntity orders,
		HttpServletRequest request){
        EntityWrapper<OrdersEntity> ew = new EntityWrapper<OrdersEntity>();

		PageUtils page = ordersService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, orders), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( OrdersEntity orders){
       	EntityWrapper<OrdersEntity> ew = new EntityWrapper<OrdersEntity>();
      	ew.allEq(MPUtil.allEQMapPre( orders, "orders")); 
        return R.ok().put("data", ordersService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(OrdersEntity orders){
        EntityWrapper< OrdersEntity> ew = new EntityWrapper< OrdersEntity>();
 		ew.allEq(MPUtil.allEQMapPre( orders, "orders")); 
		OrdersView ordersView =  ordersService.selectView(ew);
		return R.ok("查询商品订单成功").put("data", ordersView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        OrdersEntity orders = ordersService.selectById(id);
		orders = ordersService.selectView(new EntityWrapper<OrdersEntity>().eq("id", id));
        return R.ok().put("data", orders);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        OrdersEntity orders = ordersService.selectById(id);
		orders = ordersService.selectView(new EntityWrapper<OrdersEntity>().eq("id", id));
        return R.ok().put("data", orders);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody OrdersEntity orders, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(orders);
    	orders.setUserid((Long)request.getSession().getAttribute("userId"));
        ordersService.insert(orders);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody OrdersEntity orders, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(orders);
        ordersService.insert(orders);
        return R.ok();
    }



    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody OrdersEntity orders, HttpServletRequest request){
        //ValidatorUtils.validateEntity(orders);
        ordersService.updateById(orders);//全部更新
        if(StringUtils.isNotBlank(orders.getGroupno()) && "拼团中".equals(orders.getStatus())) {
            List<OrdersEntity> list = ordersService.selectList(new EntityWrapper<OrdersEntity>().eq("goodid", orders.getGoodid()).eq("status", "拼团中"));
            for(OrdersEntity o : list) {
                o.setStatus("已支付");
                o.setGroupno(orders.getGroupno());
            }
            ordersService.updateBatchById(list);
        }
        return R.ok();
    }

    /**
     * 审核
     */
    @RequestMapping("/shBatch")
    @Transactional
    public R update(@RequestBody Long[] ids, @RequestParam String sfsh, @RequestParam String shhf){
        List<OrdersEntity> list = new ArrayList<OrdersEntity>();
        for(Long id : ids) {
            OrdersEntity orders = ordersService.selectById(id);
            orders.setSfsh(sfsh);
            orders.setShhf(shhf);
            list.add(orders);
        }
        ordersService.updateBatchById(list);
        return R.ok();
    }


    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        ordersService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	





    /**
     * （按值统计）
     */
    @RequestMapping("/value/{xColumnName}/{yColumnName}")
    public R value(@PathVariable("yColumnName") String yColumnName, @PathVariable("xColumnName") String xColumnName,HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", MPUtil.camelToSnake(xColumnName));
        params.put("yColumn", MPUtil.camelToSnake(yColumnName));
        EntityWrapper<OrdersEntity> ew = new EntityWrapper<OrdersEntity>();
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("shangjia")) {
            ew.eq("shangjiamingcheng", (String)request.getSession().getAttribute("username"));
		}
            ew.in("status", new String[]{"已支付","已发货","已完成"}).ne("type",2);
        List<Map<String, Object>> result = MPUtil.snakeListToCamel(ordersService.selectValue(params, ew));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Map<String, Object> m : result) {
            for(String k : m.keySet()) {
                if(m.get(k) instanceof Date) {
                    m.put(k, sdf.format((Date)m.get(k)));
                }
            }
        }

        Collections.sort(result, (map1, map2) -> {
            // 假设 total 总是存在并且是数值类型
            Number total1 = (Number) map1.get("total");
            Number total2 = (Number) map2.get("total");
            if(total1==null)
            {
                total1 = 0;
            }
            if(total2==null)
            {
                total2 = 0;
            }
            String order = request.getParameter("order");
            if(StringUtils.isNotBlank(order)&&order.equals("desc")){
                return Double.compare(total2.doubleValue(), total1.doubleValue());
            }
            return Double.compare(total1.doubleValue(), total2.doubleValue());
        });

        return R.ok().put("data", result);
    }

    /**
     * （按值统计(多)）
     */
    @RequestMapping("/valueMul/{xColumnName}")
    public R valueMul(@PathVariable("xColumnName") String xColumnName,@RequestParam String yColumnNameMul, HttpServletRequest request) {
        String[] yColumnNames = MPUtil.camelToSnake(yColumnNameMul).split(",");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", MPUtil.camelToSnake(xColumnName));
        List<List<Map<String, Object>>> result2 = new ArrayList<List<Map<String,Object>>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        EntityWrapper<OrdersEntity> ew = new EntityWrapper<OrdersEntity>();
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("shangjia")) {
            ew.eq("shangjiamingcheng", (String)request.getSession().getAttribute("username"));
        }
        for(int i=0;i<yColumnNames.length;i++) {
            params.put("yColumn", yColumnNames[i]);
            List<Map<String, Object>> result = MPUtil.snakeListToCamel(ordersService.selectValue(params, ew));
            for(Map<String, Object> m : result) {
                for(String k : m.keySet()) {
                    if(m.get(k) instanceof Date) {
                        m.put(k, sdf.format((Date)m.get(k)));
                    }
                }
            }
            result2.add(result);
        }
        return R.ok().put("data", result2);
    }

    /**
     * （按值统计）时间统计类型
     */
    @RequestMapping("/value/{xColumnName}/{yColumnName}/{timeStatType}")
    public R valueDay(@PathVariable("yColumnName") String yColumnName, @PathVariable("xColumnName") String xColumnName, @PathVariable("timeStatType") String timeStatType,HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", MPUtil.camelToSnake(xColumnName));
        params.put("yColumn", MPUtil.camelToSnake(yColumnName));
        params.put("timeStatType", timeStatType);
        EntityWrapper<OrdersEntity> ew = new EntityWrapper<OrdersEntity>();
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("shangjia")) {
            ew.eq("shangjiamingcheng", (String)request.getSession().getAttribute("username"));
        }
            ew.in("status", new String[]{"已支付","已发货","已完成"}).ne("type",2);
        List<Map<String, Object>> result = MPUtil.snakeListToCamel(ordersService.selectTimeStatValue(params, ew));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Map<String, Object> m : result) {
            for(String k : m.keySet()) {
                if(m.get(k) instanceof Date) {
                    m.put(k, sdf.format((Date)m.get(k)));
                }
            }
        }
        return R.ok().put("data", result);
    }

    /**
     * （按值统计）时间统计类型(多)
     */
    @RequestMapping("/valueMul/{xColumnName}/{timeStatType}")
    public R valueMulDay(@PathVariable("xColumnName") String xColumnName, @PathVariable("timeStatType") String timeStatType,@RequestParam String yColumnNameMul,HttpServletRequest request) {
        String[] yColumnNames = MPUtil.camelToSnake(yColumnNameMul).split(",");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", xColumnName);
        params.put("timeStatType", timeStatType);
        List<List<Map<String, Object>>> result2 = new ArrayList<List<Map<String,Object>>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        EntityWrapper<OrdersEntity> ew = new EntityWrapper<OrdersEntity>();
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("shangjia")) {
            ew.eq("shangjiamingcheng", (String)request.getSession().getAttribute("username"));
        }
        for(int i=0;i<yColumnNames.length;i++) {
            params.put("yColumn", yColumnNames[i]);
            List<Map<String, Object>> result = MPUtil.snakeListToCamel(ordersService.selectTimeStatValue(params, ew));
            for(Map<String, Object> m : result) {
                for(String k : m.keySet()) {
                    if(m.get(k) instanceof Date) {
                        m.put(k, sdf.format((Date)m.get(k)));
                    }
                }
            }
            result2.add(result);
        }
        return R.ok().put("data", result2);
    }

    /**
     * 分组统计
     */
    @RequestMapping("/group/{columnName}")
    public R group(@PathVariable("columnName") String columnName,HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("column", MPUtil.camelToSnake(columnName));
        EntityWrapper<OrdersEntity> ew = new EntityWrapper<OrdersEntity>();
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("shangjia")) {
            ew.eq("shangjiamingcheng", (String)request.getSession().getAttribute("username"));
        }
            ew.in("status", new String[]{"已支付","已发货","已完成"}).ne("type",2);
        List<Map<String, Object>> result = MPUtil.snakeListToCamel(ordersService.selectGroup(params, ew));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Map<String, Object> m : result) {
            for(String k : m.keySet()) {
                if(m.get(k) instanceof Date) {
                    m.put(k, sdf.format((Date)m.get(k)));
                }
            }
        }
        return R.ok().put("data", result);
    }




    /**
     * 总数量
     */
    @RequestMapping("/count")
    public R count(@RequestParam Map<String, Object> params,OrdersEntity orders, HttpServletRequest request){
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("shangjia")) {
            orders.setShangjiamingcheng((String)request.getSession().getAttribute("username"));
        }
        EntityWrapper<OrdersEntity> ew = new EntityWrapper<OrdersEntity>();
        int count = ordersService.selectCount(MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, orders), params), params));
        return R.ok().put("data", count);
    }



}
