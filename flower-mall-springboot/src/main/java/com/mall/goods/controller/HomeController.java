package com.mall.goods.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.mall.common.annotation.IgnoreSecurity;
import com.mall.common.result.R;
import com.mall.common.utils.CacheTemplateUtil;
import com.mall.sys.mapper.QiTa;
import com.mall.sys.entity.vo.MallGoodsTypeVo;
import com.mall.sys.mapper.MallGoodsTypeMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*主页相关接口*/
@RestController
@RequestMapping("goods/home")
@Api("商城主页api")
public class HomeController {

    @Resource
    private MallGoodsTypeMapper mapper;

    @Autowired
    private QiTa qiTa;

    @GetMapping("homeCarousel")
    @IgnoreSecurity
    //获取轮播图
    public R homeCarousel() {
        HashMap<String, String> goodsImgHash = CacheTemplateUtil.getGoodsImgHash();
        List list = new ArrayList();
        for (String s : goodsImgHash.keySet()) {
            Map<String, String> stringHashMap = new HashMap<>();
            stringHashMap.put("key", s);
            stringHashMap.put("value", goodsImgHash.get(s));
            list.add(stringHashMap);
        }

        return R.ok().data("dataList", list);
    }

    @GetMapping("homeGoodsList")
    @IgnoreSecurity
    public R getHomeGoodsList() {
        List<MallGoodsTypeVo> mallGoodsTypeVo = mapper.getMallGoodsTypeVo();

        return R.ok().data("dataList", mallGoodsTypeVo).data("dataMap", CacheTemplateUtil.getHomeGoodsImg());
    }

    @GetMapping("verify")
    @IgnoreSecurity
    public R verify(HttpServletResponse httpServletResponse) throws Exception {
        OutputStream outputStream = httpServletResponse.getOutputStream();
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        //图形验证码写出，可以写出到文件，也可以写出到流
        lineCaptcha.write(outputStream);

        outputStream.close();
        return R.ok();
    }



}
