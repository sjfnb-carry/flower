package com.mall.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.common.result.R;
import com.mall.sys.entity.MallParameter;
import com.mall.sys.entity.queryVo.MallParameterQueryVo;
import com.mall.sys.service.MallParameterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

/**
 * 参数表 前端控制器
 */
@RestController
@RequestMapping("/sys/mallParameter")
@Api(tags="参数表")
public class MallParameterController {

    @Autowired
    MallParameterService iMallParameterService;

        /**
        * 查询参数表列表
        */
        @PostMapping("queryMallParameterList")
        @ApiOperation("参数表列表")
        public R queryMallParameterList(@RequestBody MallParameterQueryVo queryVo) {
            QueryWrapper<MallParameter> queryWrapper=new QueryWrapper<>();
            Page<MallParameter> page = iMallParameterService.page(queryVo.getPage(), queryWrapper);
            return R.ok().data("dataList",page);
        }

        /**
         * 保存或者更新参数表信息
         */
        @PostMapping("saveOrUpdateMallParameter")
        @ApiOperation("保存或者更新参数表信息")
        public R saveOrUpdateMallParameter(@RequestBody MallParameter entity) {
            boolean b =true;
            if(StringUtils.isEmpty(entity.getId())){
                b= iMallParameterService.save(entity);
            }else{
                b=iMallParameterService.updateById(entity);
            }
            return R.th(b);
        }


        /**
         * 根据id查询参数表信息
         */
        @GetMapping("getByIdMallParameter/{id}")
        @ApiOperation("根据id查询参数表信息")
        public R getByIdMallParameter(@PathVariable("id") String id) {
             MallParameter dataMallParameter=  iMallParameterService.getById(id);
             return R.ok().data("entity",dataMallParameter);

        }
        /**
         * 根据id删除参数表信息
         */
        @DeleteMapping("removeByIdMallParameter/{id}")
        @ApiOperation("根据id删除参数表信息")
        public R removeByIdMallParameter(@PathVariable("id") String id) {
            boolean effect= iMallParameterService.removeById(id);
            return R.th(effect);

        }

}

