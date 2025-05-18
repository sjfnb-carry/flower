package com.mall.goods.service.impl;

import com.mall.goods.entity.MallBuying;
import com.mall.goods.entity.vo.MallBuyingVo;
import com.mall.goods.mapper.MallBuyingMapper;
import com.mall.sys.entity.MallGoodsImg;
import com.mall.sys.service.MallGoodsImgService;
import com.mall.goods.service.MallBuyingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 求购信息表 服务实现类
 */
@Service
public class MallBuyingServiceImpl extends ServiceImpl<MallBuyingMapper, MallBuying> implements MallBuyingService {

    @Autowired
    MallGoodsImgService mallGoodsImgService;

    @Override
    public void saveMallBuyingVo(MallBuyingVo mallBuyingVo) {
        List<String> imgUrlList = mallBuyingVo.getImgUrlList();
        List<MallGoodsImg> mallGoodsImgs = new ArrayList<>();
        this.save(mallBuyingVo);
        for (int i = 0; i < imgUrlList.size(); i++) {
            MallGoodsImg mallGoodsImg = new MallGoodsImg();
            if (i==0){
                mallGoodsImg.setImgType(1);
            }else {
                mallGoodsImg.setImgType(2);
            }
            mallGoodsImg.setGoodsId(String.valueOf(mallBuyingVo.getId()));
            mallGoodsImg.setImgAddress("用户上传_"+i);
            mallGoodsImg.setImgUrl(imgUrlList.get(i));
            mallGoodsImg.setSort(0);
            mallGoodsImg.setStatus(1);
            mallGoodsImg.setCreateTime(new Date());
            mallGoodsImgs.add(mallGoodsImg);
        }
        mallGoodsImgService.saveBatch(mallGoodsImgs);
    }
}
