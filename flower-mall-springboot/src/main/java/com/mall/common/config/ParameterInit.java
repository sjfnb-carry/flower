package com.mall.common.config;
import com.mall.common.utils.CacheTemplateUtil;
import com.mall.common.utils.Db;
import com.mall.sys.entity.MallParameter;
import com.mall.sys.mapper.MallParameterMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*参数初始化*/
@Configuration
public class ParameterInit {


    @Bean
    public void initTagParameter() {
        MallParameterMapper mallParameterMapper = Db.getMapper(MallParameterMapper.class);
        List<MallParameter> mallParameters = mallParameterMapper.selectList(null);
        List<HashMap<String,String>> hashMapList=new ArrayList<>();

        mallParameters.forEach(res -> {
            if (res.getType().equals("GOODS_TAG")) {
                CacheTemplateUtil.setGoodsTagHash(res.getPKey(), res.getValue());
            } else if (res.getType().equals("HOME_IMG")) {
                CacheTemplateUtil.setGoodsImgHash(res.getPKey(), res.getValue());
            } else if (res.getType().equals("HOME_GOODS_IMG")) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("key", res.getPKey());
                hashMap.put("value", res.getValue());
                hashMapList.add(hashMap);

            }
        });
        CacheTemplateUtil.setHomeGoodsImg(hashMapList);
    }


}
