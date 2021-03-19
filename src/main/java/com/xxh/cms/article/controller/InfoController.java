package com.xxh.cms.article.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxh.cms.article.common.crudutil.ArticleCache;
import com.xxh.cms.article.common.crudutil.ArticleUtil;
import com.xxh.cms.article.common.crudutil.SelectAndPage;
import com.xxh.cms.article.common.queryInfo.QueryInfo;
import com.xxh.cms.article.entity.Info;
import com.xxh.cms.article.service.impl.InfoServiceImpl;
import com.xxh.cms.common.util.DataFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
public class InfoController {

    private final InfoServiceImpl infoService;
    private final SelectAndPage selectAndPage;

    @Autowired
    public InfoController(InfoServiceImpl infoService,SelectAndPage selectAndPage){
        this.infoService = infoService;
        this.selectAndPage = selectAndPage;
    }

    public boolean addInfo(Info info, List<String> picList){

        if (ArticleUtil.processingAdd(info,picList)==null){
            return false;
        }
        info.setTitle(info.getName());
        info.setAuthor("xxx");
        return infoService.save(info);
    }

    public boolean updateInfo(Info info, List<String> picList){
        if (ArticleUtil.processingAdd(info,picList)==null){
            return false;
        }
        if (!infoService.updateById(info)){
            return false;
        }
        ArticleCache.remove("info",info.getId());
        return true;
    }

    public Map<String ,Object> getInfo(int current, QueryInfo queryInfo){
        Page page = selectAndPage.getPage(current, queryInfo, infoService, false);
        return DataFormatUtil.pageDataHandle(page);
    }

    public Info getInfoBy(Integer id){
        Info info = infoService.getById(id);
        return info;
    }

    public boolean deleteInfo(Integer id){
        if (id!=0&&id!=null){
            return infoService.removeById(id);
        }
        return false;
    }

}
