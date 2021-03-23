package com.xxh.cms.article.common.controller;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import com.xxh.cms.article.common.crudutil.ArticleCache;
import com.xxh.cms.article.common.crudutil.ArticleUtil;
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
import com.xxh.cms.common.util.PathsCache;
import com.xxh.cms.common.util.UploadFileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

/**
 * @author xxh
 */
@CrossOrigin
@RestController
@RequestMapping("/article")
@Api(tags = "文章管理")
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

    @GetMapping("/getArticleBy/{id}")
    @ApiOperation(value = "根据id查询文章")
    public Result getArticleById(@ApiParam(value = "文章id",required = true) @PathVariable Integer id,
                                 @ApiParam(value = "文章类型",required = true) @RequestParam String type,
                                 @ApiParam(value = "操作唯一标识") @RequestParam String key){
        if (!StrUtil.hasBlank(type)&&id!=0&&id!=null&&!StrUtil.hasBlank(key)){
            String[] types = {"document","hot","info","notice"};
            if (types[0].equals(type)){
                Document document = docController.getDocumentBy(id);
                PathsCache.addPath(key,document.getPic());
                ArticleCache.add(type,document.getId(),document);
                return  Result.success(document);
            }
            if (types[1].equals(type)){
                Hot hot = hotController.getHotBy(id);
                PathsCache.addPath(key,hot.getPic());
                if (StrUtil.isNotBlank(hot.getPic2())){
                    PathsCache.addPath(key,hot.getPic2());
                }
                ArticleCache.add(type,hot.getId(),hot);
                return  Result.success(hot);
            }
            if (types[2].equals(type)){
                Info info = infoController.getInfoBy(id);
                PathsCache.addPath(key,info.getPic());
                if (StrUtil.isNotBlank(info.getPic2())){
                    PathsCache.addPath(key,info.getPic2());
                }
                ArticleCache.add(type,info.getId(),info);
                return  Result.success(info);
            }
            if (types[3].equals(type)){
                Notice notice = noticeController.getNoticeBy(id);
                PathsCache.addPath(key,notice.getPic());
                if (StrUtil.isNotBlank(notice.getPic2())){
                    PathsCache.addPath(key,notice.getPic2());
                }
                ArticleCache.add(type,notice.getId(),notice);
                return  Result.success(notice);
            }
        }
        return Result.failure(ResultCode.PARAM_ERROR);
    }

    @PostMapping("/getArticle/{current}")
    @ApiOperation(value = "查询文章")
    public Result getArticleByType(@ApiParam(value = "文章类型",required = true) @RequestParam String type,
                                   @ApiParam(value = "查询条件") @RequestBody(required = false) QueryInfo queryInfo,
                                   @ApiParam(value = "当前页",required = true) @PathVariable int current){
        if (StrUtil.isNotBlank(type)&&queryInfo!=null&&current!=0){
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
    @ApiOperation(value = "添加文章")
    public Result addArticle(@ApiParam(value = "文章类型",required = true) @RequestParam String type,
                             @ApiParam(value = "唯一标识",required = true) @RequestParam String key,
                             @ApiParam(value = "文章信息",required = true) @RequestBody Object article){

        boolean b = articleUtil.updateAndAdd(article, type, key, "add");
        if (!b){
            return Result.failure(ResultCode.ADD_ERROR);
        }

        return Result.success();
    }

    @PutMapping("/updateArticle")
    @ApiOperation(value = "修改文章")
    public Result updateArticle(@ApiParam(value = "文章类型",required = true) @RequestParam String type,
                                @ApiParam(value = "标识",required = true) @RequestParam String key,
                                @ApiParam(value = "文章信息",required = true) @RequestBody Object article){

        boolean b = articleUtil.updateAndAdd(article, type, key, "update");

        if (!b){
            return Result.failure(ResultCode.UPDATE_ERROR);
        }

        return Result.success();

    }

    @DeleteMapping("/deleteArticle/{id}")
    public Result deleteArticle(@ApiParam(value = "文章id",required = true) @PathVariable Integer id,
                                @ApiParam(value = "文章类型",required = true) @RequestParam String type){

        if (StrUtil.isNotBlank(type)&&id!=0&&id!=null){
            String[] types = {"document","hot","info","notice"};
            if (types[0].equals(type)){
                Document document = docController.getDocumentBy(id);
                if (docController.deleteDocument(id)){
                    uploadFileUtil.deleteFile(Arrays.asList(document.getPic()));
                    return Result.success();
                }
            }
            if (types[1].equals(type)){
                Hot hot = hotController.getHotBy(id);
                if (hotController.deleteHot(id)){
                    uploadFileUtil.deleteFile(Arrays.asList(hot.getPic(),hot.getPic2()));
                    return Result.success();
                }
            }
            if (types[2].equals(type)){
                Info info = infoController.getInfoBy(id);
                if (infoController.deleteInfo(id)){
                    uploadFileUtil.deleteFile(Arrays.asList(info.getPic(),info.getPic2()));
                    return Result.success();
                }
            }
            if (types[3].equals(type)){
                Notice notice = noticeController.getNoticeBy(id);
                if (noticeController.deleteNotice(id)){
                    uploadFileUtil.deleteFile(Arrays.asList(notice.getPic(),notice.getPic2()));
                    return Result.success();
                }
            }
        }
        return Result.failure(ResultCode.PARAM_ERROR);

    }



    @PostMapping("/updateFile")
    @ApiOperation(value = "上传文章图片")
    public Result uploadImage(@ApiParam(value = "图片",required = true) @RequestPart MultipartFile multipartFile,
                              @ApiParam(value = "标识",required = true) @RequestParam String key){

        StrBuilder strBuilder = StrBuilder.create();
        strBuilder.append("images/article/")
                  .append(LocalDateTimeUtil.format(LocalDate.now(),"yyyy/MM/dd"));
        String path = strBuilder.toString();

        Map<String, String> result = uploadFileUtil.uploadImage(multipartFile, path, key);

        if (result==null){
            return Result.failure(ResultCode.UPLOAD_IMAGE_ERROR);
        }

        return Result.success(result);

    }

}
