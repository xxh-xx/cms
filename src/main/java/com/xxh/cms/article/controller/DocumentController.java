package com.xxh.cms.article.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxh.cms.article.common.document.DocumentQueryInfo;
import com.xxh.cms.article.entity.Document;
import com.xxh.cms.article.service.impl.DocumentServiceImpl;
import com.xxh.cms.common.resultUtil.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@Api("管理Document")
public class DocumentController {

    @Autowired
    private DocumentServiceImpl docService;

    @PostMapping("/getDocs")
    @ApiOperation("分页条件查询")
    public Result getDocuments(DocumentQueryInfo docQueryInfo){

        Page<Document> page = new Page<>(1,30);

        QueryWrapper<Document> queryWrapper = new QueryWrapper<>();
        Map<String,Object> allEqMap = new HashMap<>(5);
        allEqMap.put("cid",docQueryInfo.getCid());
        allEqMap.put("att",docQueryInfo.getAtt());
        allEqMap.put("pubdate",docQueryInfo.getPubdate());
        allEqMap.put("hits",docQueryInfo.getHits());
        queryWrapper.allEq(allEqMap,false);
        String title = docQueryInfo.getTitle();
        String author = docQueryInfo.getAuthor();
        if (StringUtils.isNotBlank(title)){
            queryWrapper.like("title",title);
        }

        if (StringUtils.isNotBlank(author)){
            queryWrapper.like("author",author);
        }

        Page<Document> documentPage = docService.page(page, queryWrapper);

        return Result.success(documentPage);

    }

    @PostMapping("/addDoc")
    @ApiOperation("添加")
    public Result addDocument(Document document){
        docService.save(document);
        return Result.success();
    }

}
