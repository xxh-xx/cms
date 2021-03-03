package com.xxh.cms.project.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxh.cms.common.resultUtil.Result;
import com.xxh.cms.common.util.DataFormatUtil;
import com.xxh.cms.project.common.collect.CollectQueryCriteria;
import com.xxh.cms.project.entity.Collect;
import com.xxh.cms.project.service.impl.CollectServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
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
@Api(value = "CollectController",tags = "征集项目Api")
@RestController
@RequestMapping("/collect")
public class CollectController {

    @Value("${page.size}")
    private Integer pageSize;

    private final CollectServiceImpl collectService;

    @Autowired
    public CollectController(CollectServiceImpl collectService){
        this.collectService = collectService;
    }

    @PostMapping("/getCollect/{currentPage}")
    @ApiOperation("获取征集项目")
    public Result getCollect(@ApiParam(name = "queryCriteria",value = "查询条件") @RequestBody(required = false)CollectQueryCriteria queryCriteria,
                             @ApiParam(name = "currentPage",value = "当前页") @PathVariable Integer currentPage){

        if (currentPage==null||currentPage==0){
            currentPage = 1;
        }

        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("date");

        Map<String,Object> allEqMap = new HashMap<>(2);
        allEqMap.put("cid", queryCriteria.getCid());
        allEqMap.put("att", queryCriteria.getAtt());
        queryWrapper.allEq(allEqMap,false);

        String name = queryCriteria.getName();
        queryWrapper.like(StringUtils.isNotBlank(name),"name",name);

        String author = queryCriteria.getAuthor();
        queryWrapper.like(StringUtils.isNotBlank(author),"author",author);

        Date startDate = queryCriteria.getStartDate();
        Date endDate = queryCriteria.getEndDate();
        queryWrapper.between(startDate!=null&&endDate!=null,"date",startDate,endDate);

        Integer startHits = queryCriteria.getStartHits();
        Integer endHits = queryCriteria.getEndHits();
        queryWrapper.between(startHits!=null&&endHits!=null,"hits",startHits,endHits);

        Page<Collect> page = new Page<>(currentPage,pageSize);
        page = collectService.page(page, queryWrapper);

        return Result.success(DataFormatUtil.pageDataHandle(page));

    }

}
