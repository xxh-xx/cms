package com.xxh.cms.article.common.crudUtil;

import com.xxh.cms.article.entity.Hot;
import com.xxh.cms.common.util.MapPaths;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xxh
 */
@Component
public class ArticleUtil {

    @Value("${upload.path}")
    private String uploadPath;

    public List<String> processingPictures(String content,String key){
        if (StringUtils.isBlank(content)){
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

        List<String> paths = MapPaths.getPaths(key);
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

    public Boolean verification(Hot hot){

        String pattern = "^[\\s\\S]{10,200}$";
        String name = hot.getName();
        if (StringUtils.isBlank(name)|| !Pattern.matches(pattern,name)){
            return false;
        }

        String subname = hot.getSubname();
        if (StringUtils.isBlank(subname)|| !Pattern.matches(pattern,subname)){
            return false;
        }

        String source = hot.getSource();
        if (StringUtils.isBlank(source)|| !Pattern.matches(pattern,source)){
            return false;
        }

        String content = hot.getContent();
        pattern = "^[\\s\\S]{300,}$";
        if (StringUtils.isBlank(content)|| !Pattern.matches(pattern,content)){
            return false;
        }

        Integer cid = hot.getCid();
        pattern = "^[01]?$";
        if (cid==null|| !Pattern.matches(pattern,cid.toString())){
            return false;
        }

        String att = hot.getAtt();
        pattern = "^[abcde]?$";
        if (StringUtils.isBlank(att)|| !Pattern.matches(pattern,att)){
            return false;
        }

        String resume = hot.getResume();
        pattern = "^[\\s\\S]{1,150}$";
        if (StringUtils.isBlank(resume)&&!Pattern.matches(pattern,resume)){
            return false;
        }

        return true;
    }

}
