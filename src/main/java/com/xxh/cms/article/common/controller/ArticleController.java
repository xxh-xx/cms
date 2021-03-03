package com.xxh.cms.article.common.controller;

import com.alibaba.fastjson.JSON;
import com.xxh.cms.article.common.crudUtil.ArticleUtil;
import com.xxh.cms.article.common.queryInfo.QueryInfo;
import com.xxh.cms.article.controller.DocumentController;
import com.xxh.cms.article.controller.HotController;
import com.xxh.cms.article.controller.InfoController;
import com.xxh.cms.article.controller.NoticeController;
import com.xxh.cms.article.entity.Document;
import com.xxh.cms.article.entity.Hot;
import com.xxh.cms.article.entity.Info;
import com.xxh.cms.article.entity.Notice;
import com.xxh.cms.common.resultUtil.Result;
import com.xxh.cms.common.resultUtil.ResultCode;
import com.xxh.cms.common.util.MapPaths;
import com.xxh.cms.common.util.UploadFileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xxh
 */
@CrossOrigin
@RestController
@RequestMapping("/article")
@Api(value = "ArticleController",tags = "文章的管理Api")
public class ArticleController {

    private final DocumentController docController;
    private final HotController hotController;
    private final InfoController infoController;
    private final NoticeController noticeController;
    private final UploadFileUtil uploadFileUtil;
    private final ArticleUtil articleUtil;

    @Autowired
    public ArticleController(DocumentController docController, HotController hotController,
                             InfoController infoController, NoticeController noticeController,
                             UploadFileUtil uploadFileUtil,ArticleUtil articleUtil){
        this.docController = docController;
        this.hotController = hotController;
        this.infoController = infoController;
        this.noticeController = noticeController;
        this.uploadFileUtil = uploadFileUtil;
        this.articleUtil = articleUtil;
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

    @PostMapping("/addArticle")
    public Result addArticle(@ApiParam(name = "type",value = "文章类型",required = true) @RequestParam String type,
                             @ApiParam(name = "key",value = "标识",required = true) @RequestParam String key,
                             @ApiParam(name = "article",value = "文章信息",required = true) @RequestBody Hot hot){

        if (StringUtils.isBlank(type)||hot==null){
            return Result.failure(ResultCode.PARAM_ERROR);
        }

        if (!articleUtil.verification(hot)){
            return Result.failure(ResultCode.PARAM_ERROR);
        }

        List<String> picList = articleUtil.processingPictures(hot.getContent(), key);

        MapPaths.removePaths(key);

        String hotString = JSON.toJSONString(hot);

        String[] types = {"document","hot","info","notice"};
        if (types[0].equals(type)){
            Document document = new Document();
            document.setTitle(hot.getName());
            document.setSubtitle(hot.getSubname());
            document.setSource(hot.getSource());
            document.setContent(hot.getContent());
            document.setCid(hot.getCid());
            document.setAtt(hot.getAtt());
            document.setResume(hot.getResume());
            docController.addDocument(document,picList);
            return  Result.success();
        }
        if (types[1].equals(type)){
            hotController.addHot(hot,picList);
            return  Result.success();
        }
        if (types[2].equals(type)){
            Info info = JSON.parseObject(hotString, Info.class);
            infoController.addInfo(info,picList);
            return  Result.success();
        }
        if (types[3].equals(type)){
            Notice notice = JSON.parseObject(hotString, Notice.class);
            noticeController.addNotice(notice,picList);
            return  Result.success();
        }

        return Result.success();
    }

    @PostMapping("/updateFile")
    @ApiOperation("上传图片")
    public Result uploadImage(@RequestParam MultipartFile multipartFile,
                              @RequestParam String key){

        String path = "images/article/"+ LocalDate.now();

        String location = uploadFileUtil.uploadImage(multipartFile, path);

        if (StringUtils.isBlank(location)){
            return Result.failure(ResultCode.UPLOAD_IMAGE_ERROR);
        }

        Map<String,String> result = new HashMap<>(1);

        result.put("location",location);
        MapPaths.addPaths(location,key);

        return Result.success(result);

    }

}
