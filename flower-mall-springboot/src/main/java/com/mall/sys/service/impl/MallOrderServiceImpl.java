package com.mall.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.common.handler.GlobalException;
import com.mall.common.result.R;
import com.mall.common.utils.SnowflakeUtil;
import com.mall.common.utils.UserInfoUtil;
import com.mall.sys.entity.MallGoods;
import com.mall.sys.entity.MallGoodsOrder;
import com.mall.sys.entity.MallOrder;
import com.mall.sys.mapper.MallOrderMapper;
import com.mall.goods.entity.UserGoods;
import com.mall.goods.entity.vo.OrderPayEntityVo;
import com.mall.goods.mapper.UserAccountMapper;
import com.mall.goods.service.UserGoodsService;
import com.mall.sys.service.MallGoodsOrderService;
import com.mall.sys.service.MallGoodsService;
import com.mall.sys.service.MallOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单详情表 服务实现类
 */
@Service
public class MallOrderServiceImpl extends ServiceImpl<MallOrderMapper, MallOrder> implements MallOrderService {

    @Autowired
    private MallGoodsService mallGoodsService;

    @Resource
    private UserAccountMapper userAccountMapper;

    @Autowired
    private UserGoodsService userGoodsService;

    @Autowired
    MallGoodsOrderService mallGoodsOrderService;




    @Override
    @Transactional
    public R saveOrder(OrderPayEntityVo orderPayEntityVo, Long userId) {

        List<Long> goodsIdList = new ArrayList<>();

        //商品总数
        int total = 0;

        //金额总和
        BigDecimal sum = new BigDecimal(0);


        //订单编号
        String orderUmber = "OD" + new SimpleDateFormat("yyMMdd").format(new Date()) + SnowflakeUtil.randomLong();

        List<MallGoodsOrder> collect = orderPayEntityVo.getGoodsList().stream().map(res -> {
            MallGoodsOrder mallGoodsOrder = new MallGoodsOrder();
            mallGoodsOrder
                    .setGoodsId(String.valueOf(res.get("productID")))
                    .setGmtCreate(new Date())
                    .setUnivalent(new BigDecimal(res.get("price") + ""))
                    .setQuantity(Integer.valueOf(res.get("num") + ""))
                    .setOrderUmber(orderUmber);
            goodsIdList.add(Long.valueOf(res.get("productID") + ""));
            return mallGoodsOrder;
        }).collect(Collectors.toList());

        synchronized (MallOrderServiceImpl.class) {

            //运算价格等信息
            QueryWrapper<MallGoods> mallGoodsQueryWrapper = new QueryWrapper<>();
            mallGoodsQueryWrapper.in("id", goodsIdList);
            List<MallGoods> list = mallGoodsService.list(mallGoodsQueryWrapper);
            for (MallGoodsOrder mallGoodsOrder : collect) {
                total += mallGoodsOrder.getQuantity();
                for (MallGoods mallGoods : list) {
                    if (mallGoods.getId().equals(Long.valueOf(mallGoodsOrder.getGoodsId()))) {
                        BigDecimal multiply = mallGoods.getGoodsPrice().multiply(BigDecimal.valueOf(mallGoodsOrder.getQuantity()));
                        mallGoodsOrder.setAmount(multiply);
                        if (mallGoods.getGoodsAmount() - Long.valueOf(mallGoodsOrder.getQuantity()) <= 0) {
                            throw new GlobalException(50000,"库存不足");
                        }
                        sum = sum.add(multiply);
                        mallGoodsOrder.setUnivalent(mallGoods.getGoodsPrice());
                    }

                }
            }
        }

        //判断前端运算出来的价格和当前价格是否一致
        if (!sum.equals(orderPayEntityVo.getAmount())) {
            throw new GlobalException(50000, "请勿恶意修改价格");
        }


        //初始化订单详情
        MallOrder mallOrder = new MallOrder();
        mallOrder
                .setOrderUmber(orderUmber)
                .setAmount(sum)
                .setGmtCreate(new Date())
                .setUserId(userId)
                .setAddressId(orderPayEntityVo.getConfirmAddress())
                .setIsPay(2)
                .setQuantity(total);


        boolean b = userAccountMapper.lessAccount(mallOrder.getUserId(), mallOrder.getAmount());
        if (b) {
            mallOrder.setIsPay(1);
            mallOrder.setPaymentTime(new Date());
            //更新库存
            Boolean aBoolean = mallGoodsService.lessStock(collect);
        }

        QueryWrapper<UserGoods> goodsQueryWrapper = new QueryWrapper<>();
        goodsQueryWrapper.eq("type", 2)
                .in("goods_id", goodsIdList)
                .eq("user_id", userId);
        userGoodsService.remove(goodsQueryWrapper);

        mallGoodsOrderService.saveBatch(collect);
        this.save(mallOrder);




        if (b){
            return R.ok("购买成功");
        }
        return R.error(50000, "余额不足");

    }


    @Override
    public void orderPay(String orderId) {
        QueryWrapper<MallOrder> mallOrderQueryWrapper = new QueryWrapper<>();
        mallOrderQueryWrapper.eq("order_umber",orderId);
        MallOrder one = this.getOne(mallOrderQueryWrapper);
        userAccountMapper.lessAccount(Long.valueOf(UserInfoUtil.getUserId()), one.getAmount());
        List<String> as=new ArrayList();
        QueryWrapper<MallGoodsOrder> mallGoodsOrderQueryWrapper = new QueryWrapper<>();
        mallGoodsOrderQueryWrapper.eq("order_umber",orderId);
        List<MallGoodsOrder> list = mallGoodsOrderService.list();
        synchronized (MallOrderServiceImpl.class) {
            list.forEach(res->{
                as.add(res.getGoodsId());
            });
            QueryWrapper<MallGoods> mallGoodsQueryWrapper = new QueryWrapper<>();
            mallGoodsQueryWrapper.in("id", as);
            List<MallGoods> listAll = mallGoodsService.list(mallGoodsQueryWrapper);

            for (MallGoodsOrder mallGoodsOrder : list) {
                for (MallGoods mallGoods : listAll) {
                    if (mallGoods.getId().equals(mallGoodsOrder.getGoodsId())) {
                        if (mallGoods.getGoodsAmount() - Long.valueOf(mallGoodsOrder.getQuantity()) < 0) {
                            throw new GlobalException(50000,"库存不足");
                        }
                    }
                }
            }
        }
        mallGoodsService.lessStock(list);
        this.updateById(one.setIsPay(1).setPaymentTime(new Date()));
    }
}
