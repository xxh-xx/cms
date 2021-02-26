package com.xxh.cms.article.common.controller;

import com.xxh.cms.article.common.queryInfo.QueryInfo;
import com.xxh.cms.article.controller.DocumentController;
import com.xxh.cms.article.controller.HotController;
import com.xxh.cms.article.controller.InfoController;
import com.xxh.cms.article.controller.NoticeController;
import com.xxh.cms.common.resultUtil.Result;
import com.xxh.cms.common.resultUtil.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xxh
 */
@CrossOrigin
@RestController
@RequestMapping("/article")
@Api("所有文章的管理")
public class ArticleController {

    private final DocumentController docController;
    private final HotController hotController;
    private final InfoController infoController;
    private final NoticeController noticeController;

    @Autowired
    public ArticleController(DocumentController docController, HotController hotController,
                             InfoController infoController, NoticeController noticeController){
        this.docController = docController;
        this.hotController = hotController;
        this.infoController = infoController;
        this.noticeController = noticeController;
    }

    @GetMapping("/getArticles")
    @ApiOperation("查询所有文章")
    public Result getArticles(){
        int current = 1;
        Map<String, Object> doc = docController.getDoc(current, null);
        Map<String, Object> hots = hotController.getHots(current, null);
        Map<String, Object> info = infoController.getInfo(current, null);
        Map<String, Object> notices = noticeController.getNotices(current, null);
        Map<String,Object> resultData = new HashMap<>(4);
        resultData.put("document",doc);
        resultData.put("hot",hots);
        resultData.put("info",info);
        resultData.put("notice",notices);
        return  Result.success(resultData);
    }

    @PostMapping("/getArticle/{current}")
    @ApiOperation("查询指定类型指定条件的文章")
    public Result getArticleByType(@ApiParam(name = "type",value = "文章类型",required = true) @RequestParam String type,
                                   @ApiParam(name = "queryInfo",value = "查询条件") @RequestBody QueryInfo queryInfo,
                                   @ApiParam(name = "current",value = "当前页",required = true) @PathVariable int current){
        if (StringUtils.isNotBlank(type)&&queryInfo!=null&&current!=0){
            String[] types = {"document","hot","info","notice"};
            if (types[0].equals(type)){
                Map<String, Object> doc = docController.getDoc(current, queryInfo);
                return  Result.success(doc);
            }
            if (types[1].equals(type)){
                Map<String, Object> hots = hotController.getHots(current, queryInfo);
                return  Result.success(hots);
            }
            if (types[2].equals(type)){
                Map<String, Object> info = infoController.getInfo(current, queryInfo);
                return  Result.success(info);
            }
            if (types[3].equals(type)){
                Map<String, Object> notices = noticeController.getNotices(current, queryInfo);
                return  Result.success(notices);
            }
        }
        return Result.failure(ResultCode.PARAM_ERROR);
    }

}
