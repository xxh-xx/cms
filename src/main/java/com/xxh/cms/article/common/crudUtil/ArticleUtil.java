package com.xxh.cms.article.common.crudutil;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.xxh.cms.article.controller.DocumentController;
import com.xxh.cms.article.controller.HotController;
import com.xxh.cms.article.controller.InfoController;
import com.xxh.cms.article.controller.NoticeController;
import com.xxh.cms.article.entity.Document;
import com.xxh.cms.article.entity.Hot;
import com.xxh.cms.article.entity.Info;
import com.xxh.cms.article.entity.Notice;
import com.xxh.cms.common.util.PathsCache;
import com.xxh.cms.common.util.UploadFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author xxh
 */
@Component
public class ArticleUtil {

    private final DocumentController docController;
    private final HotController hotController;
    private final InfoController infoController;
    private final NoticeController noticeController;
    private final UploadFileUtil uploadFileUtil;

    @Autowired
    public ArticleUtil(DocumentController docController, HotController hotController,
                       InfoController infoController, NoticeController noticeController,
                       UploadFileUtil uploadFileUtil){
        this.docController = docController;
        this.hotController = hotController;
        this.infoController = infoController;
        this.noticeController = noticeController;
        this.uploadFileUtil = uploadFileUtil;
    }

    public static Boolean verification(Object article, String key){

        if (article==null|| StrUtil.isBlank(key)){
            return false;
        }

        LinkedHashMap<String,Object> articleMap = (LinkedHashMap<String, Object>) article;

        String name = articleMap.get("name").toString();
        String subname = articleMap.get("subname").toString();

        String source = articleMap.get("source").toString();
        String content = articleMap.get("content").toString();
        Integer cid = (Integer) articleMap.get("cid");
        String att = articleMap.get("att").toString();
        String resume = articleMap.get("resume").toString();
        String pattern = "^[\\s\\S]{1,200}$";
        if (StrUtil.isBlank(name)||!Pattern.matches(pattern,name)||
                StrUtil.isBlank(subname)||!Pattern.matches(pattern,subname)||
                StrUtil.isBlank(source)||!Pattern.matches(pattern,source)){
            return false;
        }

        pattern = "^[\\s\\S]+$";
        if (StrUtil.isBlank(content)|| !Pattern.matches(pattern,content)){
            return false;
        }

        pattern = "^[01]?$";
        if (cid==null|| !Pattern.matches(pattern,cid.toString())){
            return false;
        }

        pattern = "^[abcde]?$";
        if (StrUtil.isBlank(att)|| !Pattern.matches(pattern,att)){
            return false;
        }

        pattern = "^[\\s\\S]{1,150}$";
        if (StrUtil.isBlank(resume)||!Pattern.matches(pattern,resume)){
            return false;
        }

        return true;
    }

    public static <T> T processingAdd(T article, List<String> picList){

        int picMaxNumber = 2;
        int picMinNumber = 1;

        if (picList.size()>picMaxNumber||picList.size()<=0){
            return null;
        }

        Class<?> aClass = article.getClass();

        try {
            Method setPic = aClass.getMethod("setPic", String.class);
            Method setPic2 = aClass.getMethod("setPic2", String.class);
            if (picList.size()==picMaxNumber){
                setPic.invoke(article,picList.get(0));
                setPic2.invoke(article,picList.get(1));
            }
            if (picList.size()==picMinNumber){
                setPic.invoke(article,picList.get(0));
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return article;
    }

    public boolean updateAndAdd(Object article, String type, String key, String model) {

        if (StrUtil.isBlank(type) || StrUtil.isBlank(key) ||
            article == null || StrUtil.isBlank(model)||!ArticleUtil.verification(article, key)) {
            return false;
        }

        String addModel = "add";
        String updateModel = "update";

        LinkedHashMap<String,Object> articleMap = (LinkedHashMap<String, Object>) article;

        List<String> picList = uploadFileUtil.processingPictures(articleMap.get("content").toString(), key);

        if (picList == null){
            return false;
        }

        LinkedList<String> types = new LinkedList<>();
        types.add("document");
        types.add("hot");
        types.add("info");
        types.add("notice");

        if (updateModel.equals(model)){
            return processUpdate(articleMap,type,key,picList,types);
        }

        if (addModel.equals(model)){
            return processAdd(type,types,articleMap,picList);
        }

        PathsCache.removePaths(key);

        return true;

    }

    public static <T> T handleUpdateData(T article, Map<String,Object> articleMap){

        Class<?> aClass = article.getClass();

        String name = "setName";
        String subname = "setSubname";

        if (article instanceof Document){
            name = "setTitle";
            subname = "setSubtitle";
        }
        try {
            Method setCid = aClass.getMethod("setCid", Integer.class);
            Method setTitle = aClass.getMethod(name, String.class);
            Method setSubtitle = aClass.getMethod(subname, String.class);
            Method setAtt = aClass.getMethod("setAtt", String.class);
            Method setSource = aClass.getMethod("setSource", String.class);
            Method setResume = aClass.getMethod("setResume", String.class);
            Method setContent = aClass.getMethod("setContent", String.class);

            setCid.invoke(article,articleMap.get("cid"));
            setTitle.invoke(article,articleMap.get("name"));
            setSubtitle.invoke(article,articleMap.get("subname"));
            setAtt.invoke(article,articleMap.get("att"));
            setSource.invoke(article,articleMap.get("source"));
            setResume.invoke(article,articleMap.get("resume"));
            setContent.invoke(article,articleMap.get("content"));

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }

        return article;

    }

    private boolean processUpdate(LinkedHashMap<String,Object> articleMap,String type,String key,List<String> picList,LinkedList<String> types){
        String doc = "document";
        Integer id = Integer.parseInt(articleMap.get("id").toString());
        if (ArticleCache.get(type,id)==null){
            LinkedList<String> paths = PathsCache.getPaths(key);
            if (paths!=null){
                if (doc.equals(type)){
                    paths.removeFirst();
                }else {
                    paths.remove(0);
                    paths.remove(1);
                }
                uploadFileUtil.deleteFile(paths);
            }
            return false;
        }

        if (types.get(0).equals(type)){
            Document document = handleUpdateData((Document) ArticleCache.get(type, id), articleMap);
            return docController.updateDocument(document, picList);
        }

        if (types.get(1).equals(type)){
            Hot hot = handleUpdateData((Hot) ArticleCache.get(type, id),articleMap);
            return hotController.updateHot(hot,picList);
        }

        if (types.get(2).equals(type)){
            Info info = handleUpdateData((Info) ArticleCache.get(type, id), articleMap);
            return infoController.updateInfo(info,picList);
        }

        if (types.get(3).equals(type)){
            Notice notice = handleUpdateData((Notice) ArticleCache.get(type, id), articleMap);
            return noticeController.updateNotice(notice,picList);
        }
        return true;

    }

    private boolean processAdd(String type,LinkedList<String> types,LinkedHashMap<String,Object> articleMap,List<String> picList){

        if (types.get(0).equals(type)) {
            return docController.addDocument(articleMap,picList);
        }

        String stringArticle = JSON.toJSONString(articleMap);

        if (types.get(1).equals(type)) {
            return hotController.addHot(JSON.parseObject(stringArticle, Hot.class),picList);
        }

        if (types.get(2).equals(type)) {
            return infoController.addInfo(JSON.parseObject(stringArticle, Info.class),picList);
        }

        if (types.get(3).equals(type)) {
            return noticeController.addNotice(JSON.parseObject(stringArticle,Notice.class),picList);
        }

        return true;
    }

}
