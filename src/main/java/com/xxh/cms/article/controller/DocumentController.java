package com.xxh.cms.article.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxh.cms.article.common.crudUtil.SelectAndPage;
import com.xxh.cms.article.common.queryInfo.QueryInfo;
import com.xxh.cms.article.entity.Document;
import com.xxh.cms.article.service.impl.DocumentServiceImpl;
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
@Api("Document管理")
public class DocumentController {

    private final DocumentServiceImpl docService;
    private final SelectAndPage selectAndPage;

    @Autowired
    public DocumentController(DocumentServiceImpl docService,SelectAndPage selectAndPage){
        this.docService = docService;
        this.selectAndPage = selectAndPage;
    }

    @ApiOperation("分页条件查询")
    public Result getDocuments(@ApiParam(name = "docQueryInfo",value = "查询条件") @RequestBody(required = false) QueryInfo queryInfo,
                               @ApiParam(name = "current",value = "当前页数") @PathVariable int current){

        return Result.success(getDoc(current,queryInfo));

    }

    @ApiOperation("添加")
    public Result addDocument(@ApiParam(name = "document",value = "添加数据") @RequestBody Document document){
        docService.save(document);
        return Result.success();
    }

    public Map<String ,Object> getDoc(int current,QueryInfo queryInfo){
        Page documentPage = selectAndPage.getPage(current,queryInfo,docService,true);
        return selectAndPage.baleResult(documentPage);
    }

}
