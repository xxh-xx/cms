package com.xxh.cms.article.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxh.cms.article.common.crudUtil.SelectAndPage;
import com.xxh.cms.article.common.queryInfo.QueryInfo;
import com.xxh.cms.article.entity.Info;
import com.xxh.cms.article.service.impl.InfoServiceImpl;
import com.xxh.cms.common.resultUtil.Result;
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
public class InfoController {

    private final InfoServiceImpl infoService;
    private final SelectAndPage selectAndPage;

    @Autowired
    public InfoController(InfoServiceImpl infoService,SelectAndPage selectAndPage){
        this.infoService = infoService;
        this.selectAndPage = selectAndPage;
    }

    @ApiOperation("分页条件查询")
    public Result getDocuments(@ApiParam(name = "infoQueryInfo",value = "查询条件") @RequestBody(required = false) QueryInfo queryInfo,
                               @ApiParam(name = "current",value = "当前页数") @PathVariable int current){

        return Result.success(getInfo(current,queryInfo));

    }

    @ApiOperation("添加")
    public Result addDocument(@ApiParam(name = "info",value = "添加数据") @RequestBody Info info){
        infoService.save(info);
        return Result.success();
    }

    public Map<String ,Object> getInfo(int current, QueryInfo queryInfo){
        Page page = selectAndPage.getPage(current, queryInfo, infoService, false);
        return selectAndPage.baleResult(page);
    }

}
