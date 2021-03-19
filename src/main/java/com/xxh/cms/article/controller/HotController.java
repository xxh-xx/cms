package com.xxh.cms.article.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxh.cms.article.common.crudutil.ArticleCache;
import com.xxh.cms.article.common.crudutil.ArticleUtil;
import com.xxh.cms.article.common.crudutil.SelectAndPage;
import com.xxh.cms.article.common.queryInfo.QueryInfo;
import com.xxh.cms.article.entity.Hot;
import com.xxh.cms.article.service.impl.HotServiceImpl;
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
public class HotController {

    private final HotServiceImpl hotService;
    private final SelectAndPage selectAndPage;

    @Autowired
    public HotController(HotServiceImpl hotService,SelectAndPage selectAndPage){
        this.hotService = hotService;
        this.selectAndPage = selectAndPage;
    }

    public boolean addHot(Hot hot, List<String> picList){

        if (ArticleUtil.processingAdd(hot,picList)==null){
            return false;
        }

        hot.setTitle(hot.getName());
        hot.setAuthor("xxx");
        return hotService.save(hot);
    }

    public boolean updateHot(Hot hot, List<String> picList){
        if (ArticleUtil.processingAdd(hot,picList)==null){
            return false;
        }
        if (!hotService.updateById(hot)){
            return false;
        }
        ArticleCache.remove("hot",hot.getId());
        return true;
    }

    public Map<String ,Object> getHots(int current, QueryInfo queryInfo){
        Page page = selectAndPage.getPage(current, queryInfo, hotService, false);
        return DataFormatUtil.pageDataHandle(page);
    }

    public Hot getHotBy(Integer id){
        Hot hot = hotService.getById(id);
        return hot;
    }

    public boolean deleteHot(Integer id){
        if (id!=0&&id!=null){
            return hotService.removeById(id);
        }
        return false;
    }

}
