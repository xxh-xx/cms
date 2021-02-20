package com.xxh.cms.article.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxh.cms.article.common.crudUtil.SelectAndPage;
import com.xxh.cms.article.common.document.DocumentQueryInfo;
import com.xxh.cms.article.entity.Document;
import com.xxh.cms.article.service.impl.DocumentServiceImpl;
import com.xxh.cms.common.resultUtil.Result;
import io.swagger.annotations.Api;
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
@RequestMapping("/document")
@Api("Document管理")
public class DocumentController {

    @Autowired
    private DocumentServiceImpl docService;
    @Autowired
    private SelectAndPage selectAndPage;

    @PostMapping("/getDocs/{current}")
    @ApiOperation("分页条件查询")
    public Result getDocuments(@ApiParam(name = "docQueryInfo",value = "查询条件") @RequestBody(required = false) DocumentQueryInfo docQueryInfo,
                               @ApiParam(name = "current",value = "当前页数") @PathVariable int current){

        Map<String,Object> allEqMap = new HashMap<>(4);
        allEqMap.put("cid",docQueryInfo.getCid());
        allEqMap.put("att",docQueryInfo.getAtt());
        allEqMap.put("pubdate",docQueryInfo.getPubdate());
        allEqMap.put("hits",docQueryInfo.getHits());

        String title = docQueryInfo.getTitle();
        String author = docQueryInfo.getAuthor();
        Map<String, Object> likeMap = new HashMap<>(2);
        likeMap.put("title",title);
        likeMap.put("author",author);

        Page documentPage = selectAndPage.getPage(current, allEqMap, likeMap,docService);

        return Result.success(documentPage);

    }

    @PostMapping("/addDoc")
    @ApiOperation("添加")
    public Result addDocument(@ApiParam(name = "document",value = "添加数据") @RequestBody Document document){
        docService.save(document);
        return Result.success();
    }

}
