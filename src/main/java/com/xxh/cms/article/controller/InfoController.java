package com.xxh.cms.article.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxh.cms.article.common.crudUtil.SelectAndPage;
import com.xxh.cms.article.common.document.InfoQueryInfo;
import com.xxh.cms.article.entity.Info;
import com.xxh.cms.article.service.impl.InfoServiceImpl;
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
@RequestMapping("/info")
public class InfoController {

    @Autowired
    private InfoServiceImpl infoService;
    @Autowired
    private SelectAndPage selectAndPage;

    @PostMapping("/getInfos/{current}")
    @ApiOperation("分页条件查询")
    public Result getDocuments(@ApiParam(name = "infoQueryInfo",value = "查询条件") @RequestBody(required = false) InfoQueryInfo infoQueryInfo,
                               @ApiParam(name = "current",value = "当前页数") @PathVariable int current){

        Map<String,Object> allEqMap = new HashMap<>(4);
        allEqMap.put("cid",infoQueryInfo.getCid());
        allEqMap.put("att",infoQueryInfo.getAtt());
        allEqMap.put("date",infoQueryInfo.getDate());
        allEqMap.put("hits",infoQueryInfo.getHits());

        String name = infoQueryInfo.getName();
        String author = infoQueryInfo.getAuthor();
        Map<String, Object> likeMap = new HashMap<>(2);
        likeMap.put("name",name);
        likeMap.put("author",author);

        Page infoPage = selectAndPage.getPage(current, allEqMap, likeMap,infoService);

        return Result.success(infoPage);

    }

    @PostMapping("/addInfo")
    @ApiOperation("添加")
    public Result addDocument(@ApiParam(name = "info",value = "添加数据") @RequestBody Info info){
        infoService.save(info);
        return Result.success();
    }

}
