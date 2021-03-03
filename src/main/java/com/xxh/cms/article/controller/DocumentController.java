package com.xxh.cms.article.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxh.cms.article.common.crudUtil.SelectAndPage;
import com.xxh.cms.article.common.queryInfo.QueryInfo;
import com.xxh.cms.article.entity.Document;
import com.xxh.cms.article.service.impl.DocumentServiceImpl;
import com.xxh.cms.common.resultUtil.Result;
import com.xxh.cms.common.resultUtil.ResultCode;
import com.xxh.cms.common.util.DataFormatUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

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

    public Result addDocument(Document document, List<String> picList){
        if (picList.size()!=1){
            return Result.failure(ResultCode.PARAM_ERROR);
        }
        document.setPic(picList.get(0));
        document.setAuthor("xxx");
        docService.save(document);
        return Result.success();
    }

    public Map<String ,Object> getDoc(int current,QueryInfo queryInfo){
        Page documentPage = selectAndPage.getPage(current,queryInfo,docService,true);
        return DataFormatUtil.pageDataHandle(documentPage);
    }

}
