package com.mall.sys.service.impl;

import java.util.Date;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.common.utils.FieldAssert;
import com.mall.common.utils.UserInfoUtil;
import com.mall.sys.entity.MallGoods;
import com.mall.sys.entity.MallGoodsImg;
import com.mall.sys.entity.MallGoodsOrder;
import com.mall.sys.entity.queryVo.MallIdleGoodesSaveVo;
import com.mall.sys.entity.vo.MallGoodsVo;
import com.mall.sys.mapper.MallGoodsMapper;
import com.mall.sys.service.MallGoodsImgService;
import com.mall.goods.entity.BuyingGoods;
import com.mall.goods.entity.SendUser;
import com.mall.goods.service.BuyingGoodsService;
import com.mall.goods.service.SendUserService;
import com.mall.sys.service.MallGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品表 服务实现类
 */
@Service
public class MallGoodsServiceImpl extends ServiceImpl<MallGoodsMapper, MallGoods> implements MallGoodsService {

    @Autowired
    MallGoodsImgService mallGoodsImgService;


    @Autowired
    BuyingGoodsService buyingGoodsService;

    @Autowired
    SendUserService sendUserService;

    @Override
    public IPage<MallGoodsVo> queryPage(Page page, MallGoods mallGoods) {
        return baseMapper.queryPage(page, mallGoods);
    }

    @Override
    public Boolean lessStock(List<MallGoodsOrder> list) {

        List<String> strings = new ArrayList<>();
        for (MallGoodsOrder stringObjectMap : list) {
            String s = "when (t.id = " + stringObjectMap.getGoodsId() + " and (t.goods_amount -" + stringObjectMap.getQuantity() + " > 0)) then (t.goods_amount -" + stringObjectMap.getQuantity() + ")";
            strings.add(s);
        }


        return baseMapper.lessStock(strings);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveIdleGoods(MallIdleGoodesSaveVo mallIdleGoodesSaveVo) {
        List<String> imgUrlList = mallIdleGoodesSaveVo.getImgUrlList();
        FieldAssert.isNull(imgUrlList, "请传入至少一张图片");
        mallIdleGoodesSaveVo.setGoodsAmount(1L);
        mallIdleGoodesSaveVo.setGoodsPayType(1L);
        mallIdleGoodesSaveVo.setStatus(1);
        mallIdleGoodesSaveVo.setGoodsMarkCode("10");
        mallIdleGoodesSaveVo.setCreateTime(new Date());
        mallIdleGoodesSaveVo.setGoodeUserType(1);
        mallIdleGoodesSaveVo.setGoodsTypeCode(String.valueOf(-1));
        this.save(mallIdleGoodesSaveVo);
        List<MallGoodsImg> mallGoodsImgs = new ArrayList<>();

        if (!StringUtils.isEmpty(mallIdleGoodesSaveVo.getBuyingId())) {
            BuyingGoods buyingGoods = new BuyingGoods();
            buyingGoods.setBuyingId(Long.valueOf(mallIdleGoodesSaveVo.getBuyingId()));
            buyingGoods.setGoodsId(mallIdleGoodesSaveVo.getId());
            buyingGoodsService.save(buyingGoods);
        }
        SendUser sendUser = new SendUser();
        sendUser.setGoodsId(mallIdleGoodesSaveVo.getId());
        sendUser.setGoodsUserId(Long.valueOf(UserInfoUtil.getUserId()));
        sendUserService.save(sendUser);
        for (int i = 0; i < imgUrlList.size(); i++) {
            MallGoodsImg mallGoodsImg = new MallGoodsImg();
            if (i == 0) {
                mallGoodsImg.setImgType(1);
            } else {
                mallGoodsImg.setImgType(2);
            }
            mallGoodsImg.setGoodsId(String.valueOf(mallIdleGoodesSaveVo.getId()));
            mallGoodsImg.setImgAddress("用户上传_" + i);
            mallGoodsImg.setImgUrl(imgUrlList.get(i));
            mallGoodsImg.setSort(0);
            mallGoodsImg.setStatus(1);
            mallGoodsImg.setCreateTime(new Date());
            mallGoodsImgs.add(mallGoodsImg);

        }
        mallGoodsImgService.saveBatch(mallGoodsImgs);

    }
}
