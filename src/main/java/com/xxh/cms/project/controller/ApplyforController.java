package com.xxh.cms.project.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxh.cms.common.resultUtil.Result;
import com.xxh.cms.common.util.DataFormatUtil;
import com.xxh.cms.project.common.applyfor.ApplyForQueryCriteria;
import com.xxh.cms.project.entity.Applyfor;
import com.xxh.cms.project.service.impl.ApplyforServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xxh
 * @since 2021-03-03
 */
@RestController
@RequestMapping("/applyfor")
@Api(value = "ApplyforController",tags = "项目申报Api")
public class ApplyforController {

    @Value("${page.size}")
    private Integer pageSize;

    private final ApplyforServiceImpl applyforService;

    @Autowired
    public ApplyforController(ApplyforServiceImpl applyforService){
        this.applyforService = applyforService;
    }

    @PostMapping("/getApplyFor/{currentPage}")
    @ApiOperation("获取项目申报")
    public Result getApplyFor(@ApiParam(name = "queryCriteria",value = "查询条件") @RequestBody(required = false) ApplyForQueryCriteria queryCriteria,
                              @ApiParam(name = "currentPage",value = "当前页") @PathVariable Integer currentPage){

        if (currentPage==null||currentPage==0){
            currentPage = 1;
        }

        QueryWrapper<Applyfor> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("stadate");

        Map<String,Object> allEqMap = new HashMap<>(4);
        allEqMap.put("pid", queryCriteria.getPid());
        allEqMap.put("rid", queryCriteria.getRid());
        allEqMap.put("att", queryCriteria.getAtt());
        allEqMap.put("state", queryCriteria.getState());
        queryWrapper.allEq(allEqMap,false);

        Date startDate = queryCriteria.getStartDate();
        Date endDate = queryCriteria.getEndDate();
        queryWrapper.between(startDate!=null&&endDate!=null,"stadate",startDate,endDate);

        Integer startHits = queryCriteria.getStartHits();
        Integer endHits = queryCriteria.getEndHits();
        queryWrapper.between(startHits!=null&&endHits!=null,"hits",startHits,endHits);

        Page<Applyfor> page = new Page<>(currentPage,pageSize);
        page = applyforService.page(page, queryWrapper);

        return Result.success(DataFormatUtil.pageDataHandle(page));

    }

}