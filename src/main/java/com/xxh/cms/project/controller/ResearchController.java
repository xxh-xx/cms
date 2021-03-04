package com.xxh.cms.project.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxh.cms.common.resultUtil.Result;
import com.xxh.cms.common.util.DataFormatUtil;
import com.xxh.cms.project.common.research.ResearchQueryCriteria;
import com.xxh.cms.project.entity.Research;
import com.xxh.cms.project.service.impl.ResearchServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
@CrossOrigin
@Api(value = "ResearchController",tags = "研究项目Api")
@RestController
@RequestMapping("/research")
public class ResearchController {

    @Value("${page.size}")
    private Integer pageSize;

    private final ResearchServiceImpl researchService;

    @Autowired
    public ResearchController(ResearchServiceImpl researchService){
        this.researchService = researchService;
    }

    @PostMapping("/getResearch/{currentPage}")
    @ApiOperation("获取征集项目")
    public Result getResearch(@ApiParam(name = "queryCriteria",value = "查询条件") @RequestBody(required = false) ResearchQueryCriteria queryCriteria,
                             @ApiParam(name = "currentPage",value = "当前页") @PathVariable Integer currentPage){

        if (currentPage==null||currentPage==0){
            currentPage = 1;
        }

        QueryWrapper<Research> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("pubdate");

        Map<String,Object> allEqMap = new HashMap<>(2);
        allEqMap.put("cid", queryCriteria.getCid());
        allEqMap.put("att", queryCriteria.getAtt());
        queryWrapper.allEq(allEqMap,false);

        String name = queryCriteria.getName();
        queryWrapper.like(StringUtils.isNotBlank(name),"name",name);

        String leader = queryCriteria.getLeader();
        queryWrapper.like(StringUtils.isNotBlank(leader),"leader",leader);

        LocalDate startDate = queryCriteria.getStartDate();
        LocalDate endDate = queryCriteria.getEndDate();
        queryWrapper.between(startDate!=null&&endDate!=null,"pubdate",startDate,endDate);

        Page<Research> page = new Page<>(currentPage,pageSize);
        page = researchService.page(page, queryWrapper);

        return Result.success(DataFormatUtil.pageDataHandle(page));

    }

}
