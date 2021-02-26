package com.xxh.cms.article.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxh.cms.article.common.crudUtil.SelectAndPage;
import com.xxh.cms.article.common.queryInfo.QueryInfo;
import com.xxh.cms.article.entity.Notice;
import com.xxh.cms.article.service.impl.NoticeServiceImpl;
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
public class NoticeController {

    private final NoticeServiceImpl noticeService;
    private final SelectAndPage selectAndPage;

    @Autowired
    public NoticeController(NoticeServiceImpl noticeService,SelectAndPage selectAndPage){
        this.noticeService = noticeService;
        this.selectAndPage = selectAndPage;
    }

    @ApiOperation("分页条件查询")
    public Result getDocuments(@ApiParam(name = "noticeQueryInfo",value = "查询条件") @RequestBody(required = false) QueryInfo queryInfo,
                               @ApiParam(name = "current",value = "当前页数") @PathVariable int current){

        return Result.success(getNotices(current, queryInfo));

    }

    @ApiOperation("添加")
    public Result addDocument(@ApiParam(name = "notice",value = "添加数据") @RequestBody Notice notice){
        noticeService.save(notice);
        return Result.success();
    }

    public Map<String ,Object> getNotices(int current, QueryInfo queryInfo){
        Page page = selectAndPage.getPage(current, queryInfo, noticeService, false);
        return selectAndPage.baleResult(page);
    }

}
