package com.xxh.cms.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author xxh
 */
@Component
public class UploadFileUtil {

    @Value("${upload.path}")
    private String uploadPath;

    /**
     * 上传图片
     * @param multipartFile
     * @param path 上传的相对路径
     * @return
     */
    public String uploadImage(MultipartFile multipartFile,String path){

        if (multipartFile.isEmpty()&& StringUtils.isBlank(path)){
            return "";
        }

        List<String> contentTypes = new ArrayList<>();
        contentTypes.add("image/gif");
        contentTypes.add("image/jpeg");
        contentTypes.add("image/png");

        if (!contentTypes.contains(multipartFile.getContentType())){
            return "";
        }

        String fileName = multipartFile.getOriginalFilename();
        fileName = fileName.substring(fileName.indexOf("."));
        fileName = UUID.randomUUID().toString()+fileName;

        String filePath = uploadPath+path+"/";

        File file = new File(filePath);
        if (!file.exists()){
            file.mkdirs();
        }

        filePath = filePath +fileName;
        file = new File(filePath);

        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        String resultPath = "/"+path+"/"+fileName;

        return resultPath;

    }

}
