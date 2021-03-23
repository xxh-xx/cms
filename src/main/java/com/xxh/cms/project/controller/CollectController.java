package com.xxh.cms.project.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxh.cms.common.resultUtil.Result;
import com.xxh.cms.common.resultUtil.ResultCode;
import com.xxh.cms.common.util.DataFormatUtil;
import com.xxh.cms.common.util.PathsCache;
import com.xxh.cms.common.util.UploadFileUtil;
import com.xxh.cms.project.common.collect.CollectQueryCriteria;
import com.xxh.cms.project.common.collect.CollectUtil;
import com.xxh.cms.project.common.util.ProjectCache;
import com.xxh.cms.project.entity.Collect;
import com.xxh.cms.project.service.impl.CollectServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xxh
 * @since 2021-03-03
 */
@CrossOrigin
@Api(tags = "征集项目Api")
@RestController
@RequestMapping("/collect")
public class CollectController {

    @Value("${page.size}")
    private Integer pageSize;

    private final static String TYPE = "collect";

    private final CollectServiceImpl collectService;
    private final UploadFileUtil uploadFileUtil;

    @Autowired
    public CollectController(CollectServiceImpl collectService,
                             UploadFileUtil uploadFileUtil){
        this.collectService = collectService;
        this.uploadFileUtil = uploadFileUtil;
    }

    @GetMapping("/getCollectBy/{id}")
    @ApiOperation(value = "根据id获取征集项目")
    public Result getCollectBy(@ApiParam(value = "征集项目id") @PathVariable Integer id,
                               @ApiParam(value = "标识") String key){
        if (StrUtil.hasBlank(id.toString())){
            return Result.failure(ResultCode.PARAM_ERROR);
        }

        Collect collect = collectService.getById(id);

        if (collect==null){
            return Result.failure(ResultCode.RESPONSE_ERROR);
        }

        ProjectCache.addProject(TYPE,collect);
        PathsCache.addPath(key,collect.getPic());

        return Result.success(collect);
    }

    @PostMapping("/getCollect/{currentPage}")
    @ApiOperation(value = "获取征集项目")
    public Result getCollect(@ApiParam(value = "查询条件") @RequestBody(required = false)CollectQueryCriteria queryCriteria,
                             @ApiParam(value = "当前页") @PathVariable Integer currentPage){

        if (currentPage==null||currentPage==0){
            currentPage = 1;
        }

        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("date");

        Map<String,Object> allEqMap = new HashMap<>(2);
        allEqMap.put("cid", queryCriteria.getCid());
        allEqMap.put("att", queryCriteria.getAtt());
        queryWrapper.allEq(allEqMap,false);

        String name = queryCriteria.getName();
        queryWrapper.like(!StrUtil.hasBlank(name),"name",name);

        String author = queryCriteria.getAuthor();
        queryWrapper.like(!StrUtil.hasBlank(author),"author",author);

        LocalDateTime startDate = queryCriteria.getStartDate();
        LocalDateTime endDate = queryCriteria.getEndDate();

        if (startDate!=null&&endDate!=null){
            if (startDate.isAfter(endDate)){
                return Result.failure(ResultCode.PARAM_ERROR);
            }
        }

        queryWrapper.between(startDate!=null&&endDate!=null,"date",startDate,endDate);

        Page<Collect> page = new Page<>(currentPage,pageSize);
        page = collectService.page(page, queryWrapper);

        return Result.success(DataFormatUtil.pageDataHandle(page));

    }

    @PostMapping("/addCollect")
    @ApiOperation(value = "添加征集项目")
    public Result addCollect(@ApiParam(value = "内容纯文本",required = true) @RequestParam String text,
                             @ApiParam(value = "图片唯一标识",required = true) @RequestParam String key,
                             @ApiParam(value = "征集项目",required = true) @RequestBody Collect collect){

        if (!CollectUtil.formVerification(collect)){
            return Result.failure(ResultCode.PARAM_ERROR);
        }

        if (handleUpdateAndAdd(collect, key, text)) {
            return Result.failure(ResultCode.PARAM_ERROR);
        }
        collect.setAuthor("xxx");
        collect.setColid(1);

        if (!collectService.save(collect)){
            return Result.failure(ResultCode.ADD_ERROR);
        }

        PathsCache.removePaths(key);

        return Result.success();

    }

    @PutMapping("/updateProject")
    @ApiOperation(value = "修改征集项目")
    public Result updateCollect(@ApiParam(value = "征集项目") @RequestBody Collect collect,
                                @ApiParam(value = "标识") @RequestParam String key,
                                @ApiParam(value = "文本") @RequestParam String text){
        if (!CollectUtil.formVerification(collect)){
            return Result.failure(ResultCode.PARAM_ERROR);
        }

        Integer id = collect.getId();

        if (ProjectCache.getProject(TYPE,id)==null){
            LinkedList<String> paths = PathsCache.getPaths(key);
            paths.removeFirst();
            uploadFileUtil.deleteFile(paths);
            return Result.failure(ResultCode.PARAM_ERROR);
        }

        Collect oldCollect = (Collect) ProjectCache.getProject(TYPE,id);

        if (handleUpdateAndAdd(collect, key, text)) {
            return Result.failure(ResultCode.PARAM_ERROR);
        }
        collect.setDate(oldCollect.getDate());
        collect.setHits(oldCollect.getHits());
        collect.setAuthor(oldCollect.getAuthor());
        collect.setColid(oldCollect.getColid());

        if (!collectService.updateById(collect)){
            return Result.failure(ResultCode.UPDATE_ERROR);
        }

        PathsCache.removePaths(key);
        ProjectCache.removeProject(TYPE,id);

        return Result.success();
    }

    @DeleteMapping("/deleteCollect/{id}")
    @ApiOperation(value = "删除征集项目")
    public Result deleteCollect(@ApiParam(value = "征集项目id") @PathVariable Integer id){
        if (StrUtil.isBlank(id.toString())){
            return Result.failure(ResultCode.PARAM_ERROR);
        }

        Collect collect = collectService.getById(id);
        if (collect==null){
            return Result.failure(ResultCode.DELETE_ERROR);
        }
        if (!collectService.removeById(id)){
            return Result.failure(ResultCode.DELETE_ERROR);
        }

        uploadFileUtil.deleteFile(Arrays.asList(collect.getPic()));

        return Result.success();

    }

    private boolean handleUpdateAndAdd(Collect collect, String key, String text) {
        String content = collect.getContent();
        List<String> pathList = uploadFileUtil.processingPictures(content, key);

        if (pathList.size()!=1){
            return true;
        }

        collect.setPic(pathList.get(0));
        if (text.length()>200){
            text = text.substring(0,200);
        }

        collect.setResume(text);
        return false;
    }

}
