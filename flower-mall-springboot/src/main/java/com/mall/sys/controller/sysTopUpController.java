package com.mall.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.common.result.R;
import com.mall.goods.entity.TopUp;
import com.mall.goods.entity.queryVo.TopUpQueryVo;
import com.mall.goods.service.TopUpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 充值记录表 前端控制器
 */
@RestController
@RequestMapping("/sys/topUp")
@Api(tags = "充值记录表")
public class sysTopUpController {

    @Autowired
    TopUpService iTopUpService;

    /**
     * 查询充值记录表列表
     */
    @PostMapping("queryTopUpList")
    @ApiOperation("充值记录表列表")
    public R queryTopUpList(@RequestBody TopUpQueryVo queryVo) {
        QueryWrapper<TopUp> queryWrapper = new QueryWrapper<>();
        if (queryVo.getAccountId() != null) {
            queryWrapper.eq("user_id", queryVo.getAccountId());
        }
        Page<TopUp> page = iTopUpService.page(queryVo.getPage(), queryWrapper);
        return R.ok().data("dataList", page);
    }

    /**
     * 保存或者更新充值记录表信息
     */
    @PostMapping("saveOrUpdateTopUp")
    @ApiOperation("保存或者更新充值记录表信息")
    public R saveOrUpdateTopUp(@RequestBody TopUp entity) {
        boolean b;
        if (StringUtils.isEmpty(entity.getId())) {
            b = iTopUpService.save(entity);
        } else {
            b = iTopUpService.updateById(entity);
        }
        return R.th(b);
    }


    /**
     * 根据id查询充值记录表信息
     */
    @GetMapping("getByIdTopUp/{id}")
    @ApiOperation("根据id查询充值记录表信息")
    public R getByIdTopUp(@PathVariable("id") String id) {
        TopUp dataTopUp = iTopUpService.getById(id);
        return R.ok().data("entity", dataTopUp);

    }

    /**
     * 根据id删除充值记录表信息
     */
    @DeleteMapping("removeByIdTopUp/{id}")
    @ApiOperation("根据id删除充值记录表信息")
    public R removeByIdTopUp(@PathVariable("id") String id) {
        boolean effect = iTopUpService.removeById(id);
        return R.th(effect);

    }


    @PostMapping("uptGoodsUserAccount")
    public R uptGoodsUserAccount(@RequestBody TopUp topUp) {
        iTopUpService.uptGoodsUserAccount(topUp);
        return R.ok();
    }
}

