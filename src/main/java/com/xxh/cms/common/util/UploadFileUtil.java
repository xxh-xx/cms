package com.xxh.cms.common.util;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public Map<String,String> uploadImage(MultipartFile multipartFile,String path,String key){

        if (multipartFile.isEmpty()|| StrUtil.hasBlank(path)||StrUtil.hasBlank(key)){
            return null;
        }

        List<String> contentTypes = new ArrayList<>();
        contentTypes.add("image/gif");
        contentTypes.add("image/jpeg");
        contentTypes.add("image/png");

        if (!contentTypes.contains(multipartFile.getContentType())){
            return null;
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
            return null;
        }

        String resultPath = "/"+path+"/"+fileName;

        Map<String,String> result = new HashMap<>(2);

        result.put("location",resultPath);
        result.put("key",key);

        if (!PathsCache.addPath(key,resultPath)){
            return null;
        }

        return result;

    }

    public List<String> processingPictures(String content,String key){
        if (StringUtils.isBlank(content)||StringUtils.isBlank(key)){
            return null;
        }

        List<String> imgPath = new ArrayList<>();
        String regex = "<img .+?/>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()){
            imgPath.add(matcher.group());
        }

        regex = "src=\".+?\"";
        pattern = Pattern.compile(regex);
        for (int i = 0;i<imgPath.size();i++){
            matcher = pattern.matcher(imgPath.get(i));
            if (matcher.find()){
                imgPath.set(i,matcher.group(0).substring(matcher.group(0).indexOf("/"),matcher.group(0).lastIndexOf("\"")));
            }
        }

        List<String> paths = PathsCache.getPaths(key);
        if (paths!=null){
            for (int i = 0;i<paths.size();i++){
                if (!imgPath.contains(paths.get(i))){
                    File file = new File(uploadPath+paths.get(i));
                    if (file.exists()){
                        file.delete();
                    }
                    System.out.println(paths.get(i));
                }
            }
        }

        System.out.println(imgPath);

        return imgPath;
    }

    public void deleteFile(List<String> paths){
        if (!paths.isEmpty()){
            for (String path : paths){
                if (path!=null){
                    File file = new File(uploadPath+path);
                    if (file.exists()){
                        file.delete();
                    }
                }
            }
        }

    }

}
