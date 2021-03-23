package com.xxh.cms.project.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxh.cms.common.resultUtil.Result;
import com.xxh.cms.common.resultUtil.ResultCode;
import com.xxh.cms.common.util.DataFormatUtil;
import com.xxh.cms.project.common.applyfor.ApplyForQueryCriteria;
import com.xxh.cms.project.common.util.ProjectCache;
import com.xxh.cms.project.entity.Applyfor;
import com.xxh.cms.project.service.impl.ApplyforServiceImpl;
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
@RestController
@RequestMapping("/applyfor")
@Api(tags = "项目申报Api")
public class ApplyforController {

    @Value("${page.size}")
    private Integer pageSize;

    private final static String TYPE = "applyfor";

    private final ApplyforServiceImpl applyforService;

    @Autowired
    public ApplyforController(ApplyforServiceImpl applyforService){
        this.applyforService = applyforService;
    }

    @PostMapping("/getApplyFor/{currentPage}")
    @ApiOperation(value = "获取项目申报")
    public Result getApplyFor(@ApiParam(name = "queryCriteria",value = "查询条件") @RequestBody(required = false) ApplyForQueryCriteria queryCriteria,
                              @ApiParam(name = "currentPage",value = "当前页") @PathVariable Integer currentPage){

        if (currentPage==null||currentPage==0){
            currentPage = 1;
        }

        QueryWrapper<Applyfor> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("stadate");

        Map<String,Object> allEqMap = new HashMap<>(4);
        allEqMap.put("pid", queryCriteria.getPid());
        allEqMap.put("rid", queryCriteria.getRid());
        allEqMap.put("att", queryCriteria.getAtt());
        allEqMap.put("state", queryCriteria.getState());
        queryWrapper.allEq(allEqMap,false);

        LocalDate startDate = queryCriteria.getStartDate();
        LocalDate endDate = queryCriteria.getEndDate();
        if (startDate!=null&&endDate!=null){
            if (startDate.isAfter(endDate)){
                return Result.failure(ResultCode.PARAM_ERROR);
            }
        }
        queryWrapper.between(startDate!=null&&endDate!=null,"stadate",startDate,endDate);

        Page<Applyfor> page = new Page<>(currentPage,pageSize);
        page = applyforService.page(page, queryWrapper);

        return Result.success(DataFormatUtil.pageDataHandle(page));

    }

    @GetMapping("/getApplyForBy/{id}")
    @ApiOperation(value = "根据id查找项目申报")
    public Result getApplyForBy(@ApiParam(value = "id") @PathVariable Integer id){
        if (StrUtil.isBlank(id.toString())){
            return Result.failure(ResultCode.PARAM_ERROR);
        }

        Applyfor applyfor = applyforService.getById(id);
        if (applyfor==null){
            return Result.failure(ResultCode.PARAM_ERROR);
        }

        ProjectCache.addProject(TYPE,applyfor);

        return Result.success(applyfor);

    }

}
