package com.mall.goods.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.common.result.R;
import com.mall.common.utils.UserInfoUtil;
import com.mall.goods.entity.UserAccount;
import com.mall.goods.entity.vo.OrderPayEntityVo;
import com.mall.goods.service.UserAccountService;
import com.mall.goods.service.UserService;
import com.mall.sys.entity.MallOrder;
import com.mall.sys.entity.vo.MallGoodsOrderVo;
import com.mall.sys.entity.vo.MallOrderVo;
import com.mall.sys.mapper.MallGoodsOrderMapper;
import com.mall.sys.mapper.MallOrderMapper;
import com.mall.sys.service.MallOrderService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*用户下单相关*/
@RestController
@RequestMapping("/goods/goodsOrderApp")
@Api(tags = "用户下单相关api接口")
public class GoodsOrderAppController {

    /**
     * 1.下单
     * 2.查询当前登录用户所有订单
     * 3.支付(用户支付失败后调用)
     * 4.保存订单
     */
    @Autowired
    private MallOrderService mallOrderService;

    @Resource
    private MallOrderMapper mallOrderMapper;

    @Resource
    private MallGoodsOrderMapper mallGoodsOrderMapper;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private UserService userService;



    @PostMapping("saveOrder")
    /**
     * 在购物车直接下单
     */
    public R saveOrder(@RequestBody OrderPayEntityVo orderPayEntityVo){

        String userId = UserInfoUtil.getUserId();
        return mallOrderService.saveOrder(orderPayEntityVo, Long.valueOf(userId));


    }

    @PostMapping("orderList")
    /**
     * 订单列表
     */
    public R orderList(){
        String userId = UserInfoUtil.getUserId();
        List<MallOrderVo> mallOrderVos = mallOrderMapper.orderList(userId);
        return R.ok().data("dataList",mallOrderVos);
    }

    @GetMapping("getOrderData/{id}")
    /**
     * 查询携带商品信息的订单记录
     */
    public R getOrderData(@PathVariable("id")String id){
        List<MallGoodsOrderVo> mallGoodsOrderVoList = mallGoodsOrderMapper.getMallGoodsOrderVoList(id);
        QueryWrapper<UserAccount> userAccountQueryWrapper = new QueryWrapper<>();
        String userId = UserInfoUtil.getUserId();
        userAccountQueryWrapper.eq("goods_user_id",userId);
        UserAccount one = userAccountService.getOne(userAccountQueryWrapper);
        QueryWrapper<MallOrder> mallOrderQueryWrapper = new QueryWrapper<>();
        mallOrderQueryWrapper.eq("order_umber",id);
        MallOrder one1 = mallOrderService.getOne(mallOrderQueryWrapper);

        Map<String, Object> hashMap = new HashMap();
        hashMap.put("tableData",mallGoodsOrderVoList);
        hashMap.put("balance",one.getMoney());
        hashMap.put("amount",one1.getAmount());
        return R.ok().data(hashMap);
    }



    @GetMapping("orderPay/{id}")
    /**
     * 订单支付（订单列表中立即支付）
     */
    public R orderPay(@PathVariable("id")String id){

        mallOrderService.orderPay(id);

        return R.ok();

    }

}
