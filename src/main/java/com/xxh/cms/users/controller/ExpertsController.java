package com.xxh.cms.users.controller;


import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.xxh.cms.common.resultUtil.Result;
import com.xxh.cms.common.resultUtil.ResultCode;
import com.xxh.cms.common.util.MyCaptchaUtil;
import com.xxh.cms.users.common.ExpertsCache;
import com.xxh.cms.users.common.ValidateForm;
import com.xxh.cms.users.entity.Experts;
import com.xxh.cms.users.service.impl.ExpertsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xxh
 * @since 2021-03-17
 */
@RestController
@RequestMapping("/users/experts")
@Api(tags = "专家管理")
public class ExpertsController {

    @Value("${upload.path}")
    private String uploadPath;

    private final ExpertsServiceImpl expertsService;

    public ExpertsController(ExpertsServiceImpl expertsService){
        this.expertsService = expertsService;
    }

    @PostMapping("/register")
    @ApiOperation(value = "专家注册")
    public Result addExpert(@ApiParam(value = "是否阅读",required = true) @RequestParam Boolean isRead,
                            @ApiParam(value = "验证码",required = true) @RequestParam String code,
                            @ApiParam(value = "唯一标识",required = true) @RequestParam String key,
                            @ApiParam(value = "专家信息",required = true) @RequestBody Experts experts) {

        if (!isRead|| StrUtil.hasBlank(code)||experts==null|| !ValidateForm.validateExperts(experts)
                || StrUtil.hasBlank(key)){
            return Result.failure(ResultCode.PARAM_ERROR);
        }

        if (!MyCaptchaUtil.verification(key,code)){
            return Result.failure(ResultCode.CODE_ERROR);
        }

        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        long id = snowflake.nextId();
        experts.setId(id);

        ExpertsCache.addExpert(key,experts);

        return Result.success();

    }

    @GetMapping("/getCaptcha")
    @ApiOperation(value = "生成验证码")
    public Result getCaptcha(){
        return Result.success(MyCaptchaUtil.createLineCaptcha());
    }

    @PostMapping("/uploadFile")
    @ApiOperation(value = "上传文件")
    public Result uploadFile(@ApiParam(value = "身份证件",required = true) @RequestPart MultipartFile[] idNumberPic,
                             @ApiParam(value = "一寸登记照",required = true) @RequestPart MultipartFile oneInchPic,
                             @ApiParam(value = "毕业证书照片",required = true) @RequestPart MultipartFile[] diplomaPic,
                             @ApiParam(value = "同等中（高）级职称水平或未开展评定的专业提供行业协会出具的相关证明附件") @RequestPart(required = false) MultipartFile[] proveFile,
                             @ApiParam(value = "奖励、表彰证明文件") @RequestPart(required = false) MultipartFile[] honorFile,
                             @ApiParam(value = "唯一标识",required = true) @RequestParam String key) throws IOException {

        if (!ValidateForm.validateExpertsFile(idNumberPic,oneInchPic,diplomaPic,proveFile,honorFile)||StrUtil.hasBlank(key)){
            return Result.failure(ResultCode.PARAM_ERROR);
        }

        Experts experts = ExpertsCache.getExpert(key);

        if (experts==null){
            return Result.failure(ResultCode.PARAM_ERROR);
        }

        String path = "/users/" + LocalDate.now() + "/" + experts.getId() + "/";

        experts.setIdNumberPic(ValidateForm.validateMultipartFiles(idNumberPic,path));

        String oneInchFilename = oneInchPic.getOriginalFilename();
        experts.setOneInchPic(path+IdUtil.simpleUUID()+oneInchFilename.substring(oneInchFilename.lastIndexOf(".")));

        experts.setDiplomaPic(ValidateForm.validateMultipartFiles(diplomaPic,path));

        if (proveFile!=null){
            experts.setProveFile(ValidateForm.validateMultipartFiles(proveFile,path));
        }

        if (honorFile!=null){
            experts.setHonorFile(ValidateForm.validateMultipartFiles(honorFile,path));
        }

        if (!expertsService.save(experts)){
            return Result.failure(ResultCode.ADD_ERROR);
        }

        for (MultipartFile m : idNumberPic){
            String filename = m.getOriginalFilename();
            m.transferTo(new File(uploadPath+path+IdUtil.simpleUUID()+filename.substring(filename.lastIndexOf("."))));
        }

        oneInchPic.transferTo(new File(uploadPath+oneInchFilename.substring(oneInchFilename.lastIndexOf("."))));

        for (MultipartFile m : diplomaPic){
            String filename = m.getOriginalFilename();
            m.transferTo(new File(uploadPath+path+IdUtil.simpleUUID()+filename.substring(filename.lastIndexOf("."))));
        }

        if (proveFile!=null){
            for (MultipartFile m : proveFile){
                String filename = m.getOriginalFilename();
                m.transferTo(new File(uploadPath+path+IdUtil.simpleUUID()+filename.substring(filename.lastIndexOf("."))));
            }
        }

        if (honorFile!=null){
            for (MultipartFile m : honorFile){
                String filename = m.getOriginalFilename();
                m.transferTo(new File(uploadPath+path+IdUtil.simpleUUID()+filename.substring(filename.lastIndexOf("."))));
            }
        }

        return Result.success();

    }

}
