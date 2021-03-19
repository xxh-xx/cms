package com.xxh.cms.article.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxh.cms.article.common.crudutil.ArticleCache;
import com.xxh.cms.article.common.crudutil.SelectAndPage;
import com.xxh.cms.article.common.queryInfo.QueryInfo;
import com.xxh.cms.article.entity.Document;
import com.xxh.cms.article.service.impl.DocumentServiceImpl;
import com.xxh.cms.common.util.DataFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xxh
 * @since 2021-02-19
 */
@Component
public class DocumentController {

    private final DocumentServiceImpl docService;
    private final SelectAndPage selectAndPage;

    @Autowired
    public DocumentController(DocumentServiceImpl docService,SelectAndPage selectAndPage){
        this.docService = docService;
        this.selectAndPage = selectAndPage;
    }

    public boolean addDocument(LinkedHashMap<String,Object> articleMap, List<String> picList){

        if (picList.size()!=1||articleMap==null){
            return false;
        }
        Document document = handleDocData(articleMap);
        document.setPic(picList.get(0));
        document.setAuthor("xxx");
        return docService.save(document);
    }

    public boolean updateDocument(Document document, List<String> picList){
        if (picList.size()!=1){
            return false;
        }
        document.setPic(picList.get(0));
        if (docService.updateById(document)){
            ArticleCache.remove("document",document.getId());
            return true;
        }
        return false;
    }

    public Map<String ,Object> getDoc(int current,QueryInfo queryInfo){
        Page documentPage = selectAndPage.getPage(current,queryInfo,docService,true);
        return DataFormatUtil.pageDataHandle(documentPage);
    }

    public Document getDocumentBy(Integer id){
        Document document = docService.getById(id);
        return document;
    }

    public boolean deleteDocument(Integer id){
        if (id!=null&&id!=0){
            return docService.removeById(id);
        }
        return false;
    }

    private Document handleDocData(LinkedHashMap<String,Object> articleMap){
        String title = articleMap.get("name").toString();
        String subTitle = articleMap.get("subname").toString();
        articleMap.remove("name");
        articleMap.remove("subname");
        articleMap.put("title",title);
        articleMap.put("subTitle",subTitle);
        return JSON.parseObject(JSON.toJSONString(articleMap), Document.class);
    }

}
