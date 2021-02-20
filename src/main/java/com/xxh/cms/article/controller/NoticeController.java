package com.xxh.cms.article.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxh.cms.article.common.crudUtil.SelectAndPage;
import com.xxh.cms.article.common.document.NoticeQueryInfo;
import com.xxh.cms.article.entity.Notice;
import com.xxh.cms.article.service.impl.NoticeServiceImpl;
import com.xxh.cms.common.resultUtil.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xxh
 * @since 2021-02-19
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeServiceImpl noticeService;
    @Autowired
    private SelectAndPage selectAndPage;

    @PostMapping("/getNotices/{current}")
    @ApiOperation("分页条件查询")
    public Result getDocuments(@ApiParam(name = "noticeQueryInfo",value = "查询条件") @RequestBody(required = false) NoticeQueryInfo noticeQueryInfo,
                               @ApiParam(name = "current",value = "当前页数") @PathVariable int current){

        Map<String,Object> allEqMap = new HashMap<>(4);
        allEqMap.put("cid",noticeQueryInfo.getCid());
        allEqMap.put("att",noticeQueryInfo.getAtt());
        allEqMap.put("date",noticeQueryInfo.getDate());
        allEqMap.put("hits",noticeQueryInfo.getHits());

        String name = noticeQueryInfo.getName();
        String author = noticeQueryInfo.getAuthor();
        Map<String, Object> likeMap = new HashMap<>(2);
        likeMap.put("name",name);
        likeMap.put("author",author);

        Page noticePage = selectAndPage.getPage(current, allEqMap, likeMap,noticeService);

        return Result.success(noticePage);

    }

    @PostMapping("/addNotice")
    @ApiOperation("添加")
    public Result addDocument(@ApiParam(name = "notice",value = "添加数据") @RequestBody Notice notice){
        noticeService.save(notice);
        return Result.success();
    }

}
