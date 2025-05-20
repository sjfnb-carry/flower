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

import com.cl.entity.ShangpinEntity;
import com.cl.entity.view.ShangpinView;

import com.cl.service.ShangpinService;
import com.cl.service.TokenService;
import com.cl.utils.PageUtils;
import com.cl.utils.R;
import com.cl.utils.MPUtil;
import com.cl.utils.MapUtils;
import com.cl.utils.CommonUtil;
import com.cl.service.StoreupService;
import com.cl.entity.StoreupEntity;

/**
 * 商品
 * 后端接口
 * @author 
 * @email 
 * @date 2025-04-10 10:21:34
 */
@RestController
@RequestMapping("/shangpin")
public class ShangpinController {
    @Autowired
    private ShangpinService shangpinService;

    @Autowired
    private StoreupService storeupService;








    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ShangpinEntity shangpin,
                                                                                                                                                                                                                                                                HttpServletRequest request){
                    String tableName = request.getSession().getAttribute("tableName").toString();
                                                                                                                                                            if(tableName.equals("shangjia")) {
                    shangpin.setShangjiamingcheng((String)request.getSession().getAttribute("username"));
                                    }
                                                                                                                                                                                                                                                                                                                                                                                                                EntityWrapper<ShangpinEntity> ew = new EntityWrapper<ShangpinEntity>();
                                                                                                                                                                                                                                                                                                                                                                                                                    
        
        
        PageUtils page = shangpinService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shangpin), params), params));
        return R.ok().put("data", page);
    }







    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,ShangpinEntity shangpin,
		HttpServletRequest request){
        EntityWrapper<ShangpinEntity> ew = new EntityWrapper<ShangpinEntity>();

		PageUtils page = shangpinService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shangpin), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ShangpinEntity shangpin){
       	EntityWrapper<ShangpinEntity> ew = new EntityWrapper<ShangpinEntity>();
      	ew.allEq(MPUtil.allEQMapPre( shangpin, "shangpin")); 
        return R.ok().put("data", shangpinService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ShangpinEntity shangpin){
        EntityWrapper< ShangpinEntity> ew = new EntityWrapper< ShangpinEntity>();
 		ew.allEq(MPUtil.allEQMapPre( shangpin, "shangpin")); 
		ShangpinView shangpinView =  shangpinService.selectView(ew);
		return R.ok("查询商品成功").put("data", shangpinView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ShangpinEntity shangpin = shangpinService.selectById(id);
		shangpin.setClicktime(new Date());
		shangpinService.updateById(shangpin);
		shangpin = shangpinService.selectView(new EntityWrapper<ShangpinEntity>().eq("id", id));
        return R.ok().put("data", shangpin);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ShangpinEntity shangpin = shangpinService.selectById(id);
		shangpin.setClicktime(new Date());
		shangpinService.updateById(shangpin);
		shangpin = shangpinService.selectView(new EntityWrapper<ShangpinEntity>().eq("id", id));
        return R.ok().put("data", shangpin);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ShangpinEntity shangpin, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(shangpin);
        shangpinService.insert(shangpin);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody ShangpinEntity shangpin, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(shangpin);
        shangpinService.insert(shangpin);
        return R.ok();
    }



    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody ShangpinEntity shangpin, HttpServletRequest request){
        //ValidatorUtils.validateEntity(shangpin);
        shangpinService.updateById(shangpin);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        shangpinService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
    /**
     * 提醒接口
     */
	@RequestMapping("/remind/{columnName}/{type}")
	public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request, 
						 @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
		map.put("column", columnName);
		map.put("type", type);
		
		if(type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if(map.get("remindstart")!=null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date()); 
				c.add(Calendar.DAY_OF_MONTH,remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if(map.get("remindend")!=null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}
		
		Wrapper<ShangpinEntity> wrapper = new EntityWrapper<ShangpinEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(MPUtil.camelToSnake(columnName), map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(MPUtil.camelToSnake(columnName), map.get("remindend"));
		}

		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("shangjia")) {
			wrapper.eq("shangjiamingcheng", (String)request.getSession().getAttribute("username"));
		}

		int count = shangpinService.selectCount(wrapper);
		return R.ok().put("count", count);
	}
	
	/**
     * 前端智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params,ShangpinEntity shangpin, HttpServletRequest request,String pre){
        EntityWrapper<ShangpinEntity> ew = new EntityWrapper<ShangpinEntity>();
        Map<String, Object> newMap = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
		Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			String newKey = entry.getKey();
			if (pre.endsWith(".")) {
				newMap.put(pre + newKey, entry.getValue());
			} else if (StringUtils.isEmpty(pre)) {
				newMap.put(newKey, entry.getValue());
			} else {
				newMap.put(pre + "." + newKey, entry.getValue());
			}
		}
		params.put("sort", "clicktime");
        params.put("order", "desc");
		PageUtils page = shangpinService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shangpin), params), params));
        return R.ok().put("data", page);
    }









}
