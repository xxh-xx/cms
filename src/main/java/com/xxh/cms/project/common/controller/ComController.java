package com.xxh.cms.project.common.controller;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.xxh.cms.common.resultUtil.Result;
import com.xxh.cms.common.resultUtil.ResultCode;
import com.xxh.cms.common.util.UploadFileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Map;

/**
 * @author xxh
 */
@Api(tags = "项目公共api")
@CrossOrigin
@RestController
@RequestMapping("/project")
public class ComController {

    private final UploadFileUtil uploadFileUtil;

    @Autowired
    public ComController(UploadFileUtil uploadFileUtil){
        this.uploadFileUtil = uploadFileUtil;
    }

    @PostMapping("/updateFile")
    @ApiOperation("上传图片")
    public Result uploadImage(@RequestParam MultipartFile multipartFile,
                              @RequestParam String key){

        String path = "images/project/"+ LocalDateTimeUtil.format(LocalDate.now(),"yyyy/MM/dd");

        Map<String, String> result = uploadFileUtil.uploadImage(multipartFile, path, key);

        if (result==null){
            return Result.failure(ResultCode.UPLOAD_IMAGE_ERROR);
        }

        return Result.success(result);

    }

}
