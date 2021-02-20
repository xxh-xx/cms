package com.xxh.cms.article.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxh.cms.article.common.crudUtil.SelectAndPage;
import com.xxh.cms.article.common.document.HotQueryInfo;
import com.xxh.cms.article.entity.Hot;
import com.xxh.cms.article.service.impl.HotServiceImpl;
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
@RequestMapping("/hot")
@Api("Hot管理")
public class HotController {

    @Autowired
    private HotServiceImpl hotService;
    @Autowired
    private SelectAndPage selectAndPage;

    @PostMapping("/getHots/{current}")
    @ApiOperation("分页条件查询")
    public Result getDocuments(@ApiParam(name = "hotQueryInfo",value = "查询条件") @RequestBody(required = false) HotQueryInfo hotQueryInfo,
                               @ApiParam(name = "current",value = "当前页数") @PathVariable int current){

        Map<String,Object> allEqMap = new HashMap<>(4);
        allEqMap.put("cid",hotQueryInfo.getCid());
        allEqMap.put("att",hotQueryInfo.getAtt());
        allEqMap.put("date",hotQueryInfo.getDate());
        allEqMap.put("hits",hotQueryInfo.getHits());

        String name = hotQueryInfo.getName();
        String author = hotQueryInfo.getAuthor();
        Map<String, Object> likeMap = new HashMap<>(2);
        likeMap.put("name",name);
        likeMap.put("author",author);

        Page hotPage = selectAndPage.getPage(current, allEqMap, likeMap,hotService);

        return Result.success(hotPage);

    }

    @PostMapping("/addHot")
    @ApiOperation("添加")
    public Result addDocument(@ApiParam(name = "hot",value = "添加数据") @RequestBody Hot hot){
        hotService.save(hot);
        return Result.success();
    }

}
