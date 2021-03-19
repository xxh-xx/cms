package com.xxh.cms.project.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxh.cms.common.resultUtil.Result;
import com.xxh.cms.common.resultUtil.ResultCode;
import com.xxh.cms.common.util.DataFormatUtil;
import com.xxh.cms.common.util.UploadFileUtil;
import com.xxh.cms.project.common.research.ResearchQueryCriteria;
import com.xxh.cms.project.common.research.ResearchUtil;
import com.xxh.cms.project.common.util.ProjectCache;
import com.xxh.cms.project.entity.Research;
import com.xxh.cms.project.service.impl.ResearchServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xxh
 * @since 2021-03-03
 */
@CrossOrigin
@Api(tags = "研究项目Api")
@RestController
@RequestMapping("/research")
public class ResearchController {

    @Value("${page.size}")
    private Integer pageSize;

    private final static String TYPE = "research";

    private final ResearchServiceImpl researchService;
    private final UploadFileUtil uploadFileUtil;

    @Autowired
    public ResearchController(ResearchServiceImpl researchService,
                              UploadFileUtil uploadFileUtil){
        this.researchService = researchService;
        this.uploadFileUtil = uploadFileUtil;
    }

    @PostMapping("/getResearch/{currentPage}")
    @ApiOperation(value = "获取征集项目")
    public Result getResearch(@ApiParam(value = "查询条件") @RequestBody(required = false) ResearchQueryCriteria queryCriteria,
                             @ApiParam(value = "当前页") @PathVariable Integer currentPage){

        if (currentPage==null||currentPage==0){
            currentPage = 1;
        }

        QueryWrapper<Research> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("pubdate");

        Map<String,Object> allEqMap = new HashMap<>(2);
        allEqMap.put("cid", queryCriteria.getCid());
        allEqMap.put("att", queryCriteria.getAtt());
        queryWrapper.allEq(allEqMap,false);

        String name = queryCriteria.getName();
        queryWrapper.like(StrUtil.isNotBlank(name),"name",name);

        String leader = queryCriteria.getLeader();
        queryWrapper.like(StrUtil.isNotBlank(leader),"leader",leader);

        LocalDate startDate = queryCriteria.getStartDate();
        LocalDate endDate = queryCriteria.getEndDate();

        if (startDate.isAfter(endDate)){
            return Result.failure(ResultCode.PARAM_ERROR);
        }

        queryWrapper.between(startDate!=null&&endDate!=null,"pubdate",startDate,endDate);

        Page<Research> page = new Page<>(currentPage,pageSize);
        page = researchService.page(page, queryWrapper);

        return Result.success(DataFormatUtil.pageDataHandle(page));

    }

    @GetMapping("/getResearchBy/{id}")
    @ApiOperation(value = "根据id查找征集项目")
    public Result getResearchBy(@ApiParam(value = "征集项目id") @PathVariable Integer id){
        if (StrUtil.hasBlank(id.toString())){
            return Result.failure(ResultCode.PARAM_ERROR);
        }
        Research research = researchService.getById(id);
        if (research==null){
            return Result.failure(ResultCode.RESPONSE_ERROR);
        }
        ProjectCache.addProject(TYPE,research);
        return Result.success(research);
    }

    @PostMapping("/addResearch")
    @ApiOperation(value = "添加征集项目")
    public Result addCollect(@ApiParam(value = "内容纯文本",required = true) @RequestParam String text,
                             @ApiParam(value = "研究项目",required = true) @RequestBody Research research){

        if (!ResearchUtil.formVerification(research)){
            return Result.failure(ResultCode.PARAM_ERROR);
        }

        if (text.length()>200){
            text = text.substring(0,200);
        }

        research.setBrief(text);
        research.setLeader("xxx");
        research.setLeaderid(1);

        if (!researchService.save(research)){
            return Result.failure(ResultCode.ADD_ERROR);
        }

        return Result.success();

    }

    @PutMapping("/updateResearch")
    @ApiOperation(value = "修改研究项目")
    public Result updateResearch(@ApiParam(value = "内容纯文本",required = true) @RequestParam String text,
                                 @ApiParam(value = "研究项目",required = true) @RequestBody Research research){

        if (!ResearchUtil.formVerification(research)){
            return Result.failure(ResultCode.PARAM_ERROR);
        }

        if (ProjectCache.getProject(TYPE,research.getId())==null){
            return Result.failure(ResultCode.PARAM_ERROR);
        }

        Research oldResearch = (Research) ProjectCache.getProject(TYPE,research.getId());

        if (text.length()>200){
            text = text.substring(0,200);
        }

        research.setBrief(text);
        research.setLeader(oldResearch.getLeader());
        research.setLeaderid(oldResearch.getLeaderid());
        research.setPubdate(oldResearch.getPubdate());
        research.setHits(oldResearch.getHits());

        if (!researchService.updateById(research)){
            return Result.failure(ResultCode.UPDATE_ERROR);
        }

        return Result.success();

    }

    @DeleteMapping("/deleteResearch/{id}")
    @ApiOperation(value = "删除研究项目")
    public Result deleteResearch(@ApiParam(value = "研究项目id") @PathVariable Integer id){
        if (StrUtil.hasBlank(id.toString())){
            return Result.failure(ResultCode.PARAM_ERROR);
        }

        if (!researchService.removeById(id)){
            return Result.failure(ResultCode.DELETE_ERROR);
        }

        return Result.success();

    }

}
