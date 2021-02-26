package com.xxh.cms.article.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxh.cms.article.common.crudUtil.SelectAndPage;
import com.xxh.cms.article.common.queryInfo.QueryInfo;
import com.xxh.cms.article.entity.Hot;
import com.xxh.cms.article.service.impl.HotServiceImpl;
import com.xxh.cms.common.resultUtil.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xxh
 * @since 2021-02-19
 */
@Api("Hot管理")
public class HotController {

    private final HotServiceImpl hotService;
    private final SelectAndPage selectAndPage;

    @Autowired
    public HotController(HotServiceImpl hotService,SelectAndPage selectAndPage){
        this.hotService = hotService;
        this.selectAndPage = selectAndPage;
    }

    @ApiOperation("分页条件查询")
    public Result getDocuments(@ApiParam(name = "hotQueryInfo",value = "查询条件") @RequestBody(required = false) QueryInfo queryInfo,
                               @ApiParam(name = "current",value = "当前页数") @PathVariable int current){

        return Result.success(getHots(current,queryInfo));

    }

    @ApiOperation("添加")
    public Result addDocument(@ApiParam(name = "hot",value = "添加数据") @RequestBody Hot hot){
        hotService.save(hot);
        return Result.success();
    }

    public Map<String ,Object> getHots(int current, QueryInfo queryInfo){
        Page page = selectAndPage.getPage(current, queryInfo, hotService, false);
        return selectAndPage.baleResult(page);
    }

}
