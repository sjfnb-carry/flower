package com.mall.goods.controller;

import com.mall.common.utils.FuyouResult;
import com.mall.common.utils.JsonUtils;
import com.mall.common.utils.RedisOperator;
import com.mall.goods.entity.ShopCat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Api(value = "购物车接口controller", tags = {"购物车接口相关的api"})
@RequestMapping("shopcart")
@RestController
public class  ShopCatController extends BaseController{

    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
    @PostMapping("/add")
    public FuyouResult add(@RequestBody ShopCat shopCat,
                                           HttpServletRequest request,
                                           HttpServletResponse response) {
        if (StringUtils.isBlank(shopCat.getUserId())) {
            return FuyouResult.errorMsg("");
        }
        System.out.println(shopCat);
        //前端用户在登录的情况下，添加商品到购物车，会同时在后端同步购物车到redis缓存
        //需要判断当前购物车中已经存在的商品，如果存在，则累加数量
        String shopcartJson = redisOperator.get(FUYOU_SHOPCART + ":" +shopCat.getUserId());
        List<ShopCat> shopCatsList = null;
        if (StringUtils.isNotBlank(shopcartJson)){
            //redis中已经有购物车了
            shopCatsList = JsonUtils.jsonToList(shopcartJson,ShopCat.class);
            //判断购物车中是否存在已有商品，如果有的话number直接替换
            boolean isHaving = false;
            for (ShopCat sc : shopCatsList){
                String tmpSpecId = sc.getId();
                if (tmpSpecId.equals(shopCat.getId())) {
                    sc.setNumber(sc.getNumber() + shopCat.getNumber());
                    if (sc.getNumber() <= sc.getStock()){
                        isHaving = true;
                    }else {
                        return FuyouResult.errorMsg("数量超过库存了哦！");
                    }
                }
            }
            if (!isHaving){
                shopCatsList.add(shopCat);
            }
        }else {
            //redis中没有购物车
            shopCatsList = new ArrayList<>();
            //直接添加到购物车中
            shopCatsList.add(shopCat);
        }
        //覆盖现有redis中的购物车
        redisOperator.set(FUYOU_SHOPCART + ":" +shopCat.getUserId(),JsonUtils.objectToJson(shopCatsList));
        return FuyouResult.ok();
    }

    @ApiOperation(value = "修改购物车商品数量", notes = "修改购物车商品数量", httpMethod = "GET")
    @GetMapping("/selectNumber")
    public FuyouResult selectNumber(@RequestParam String userId,
                                    @RequestParam String activityNumber,
                                    @RequestParam Integer number,
                                    HttpServletRequest request,
                                    HttpServletResponse response){

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(activityNumber)) {
            return FuyouResult.errorMsg("参数不能为空");
        }
        String shopcartJson = redisOperator.get(FUYOU_SHOPCART+":"+userId);
        if (StringUtils.isNotBlank(shopcartJson)){
            //redis中已经有了购物车
            List<ShopCat> shopCatList = JsonUtils.jsonToList(shopcartJson,ShopCat.class);
            //判断购物车中是否存在已有商品，如果有的话则删除
            for (ShopCat sc : shopCatList){
                String tmpSpecId = sc.getAcitivityNumber();
                if (tmpSpecId.equals(activityNumber)){
                    sc.setNumber(number);
                    //覆盖现有redis中的购物车数据
                    redisOperator.set(FUYOU_SHOPCART+":"+userId,JsonUtils.objectToJson(shopCatList));
                    break;
                }
            }
        }
        return FuyouResult.ok("数量修改成功");
    }

    @ApiOperation(value = "从购物车中删除商品", notes = "从购物车中删除商品", httpMethod = "POST")
    @PostMapping("/del")
    public FuyouResult del(@RequestParam String userId,
                                           @RequestParam String activityNumber,
                                           HttpServletRequest request,
                                           HttpServletResponse response) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(activityNumber)) {
            return FuyouResult.errorMsg("参数不能为空");
        }
        // TODO 用户在页面删除购物车中的商品数据，如果此时用户已经登录，则需要同步删除后端购物车中的商品
        String shopcartJson = redisOperator.get(FUYOU_SHOPCART+":"+userId);
        if (StringUtils.isNotBlank(shopcartJson)){
            //redis中已经有购物车了
            List<ShopCat> shopCatList = JsonUtils.jsonToList(shopcartJson,ShopCat.class);
            //判断购物车中是否存在已有商品，如果有的话则删除
            for (ShopCat sc : shopCatList){
                String tmpSpecId = sc.getAcitivityNumber();
                if (tmpSpecId.equals(activityNumber)){
                    shopCatList.remove(sc);
                    break;
                }
            }
            //覆盖现有redis中的购物车数据
            redisOperator.set(FUYOU_SHOPCART+":"+userId,JsonUtils.objectToJson(shopCatList));
        }
        return FuyouResult.ok("删除成功");
    }

    @ApiOperation(value = "查询购物车的商品", notes = "查询购物车的商品", httpMethod = "GET")
    @GetMapping("/get")
    public JSONArray get(@RequestParam String userId,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        String shopcartJson =redisOperator.get(FUYOU_SHOPCART+":"+userId);
        JSONArray jsonArray = JSONObject.parseArray(shopcartJson);
        System.out.println(jsonArray);
        return jsonArray;
    }

}
