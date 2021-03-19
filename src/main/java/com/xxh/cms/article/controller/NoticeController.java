package com.xxh.cms.article.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxh.cms.article.common.crudutil.ArticleCache;
import com.xxh.cms.article.common.crudutil.ArticleUtil;
import com.xxh.cms.article.common.crudutil.SelectAndPage;
import com.xxh.cms.article.common.queryInfo.QueryInfo;
import com.xxh.cms.article.entity.Notice;
import com.xxh.cms.article.service.impl.NoticeServiceImpl;
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
public class NoticeController {

    private final NoticeServiceImpl noticeService;
    private final SelectAndPage selectAndPage;

    @Autowired
    public NoticeController(NoticeServiceImpl noticeService,SelectAndPage selectAndPage){
        this.noticeService = noticeService;
        this.selectAndPage = selectAndPage;
    }

    public boolean addNotice(Notice notice, List<String> picList){

        if (ArticleUtil.processingAdd(notice,picList)==null){
            return false;
        }
        notice.setTitle(notice.getName());
        notice.setAuthor("xxx");
        return noticeService.save(notice);
    }

    public boolean updateNotice(Notice notice, List<String> picList){

        if (ArticleUtil.processingAdd(notice,picList)==null){
            return false;
        }

        if (!noticeService.updateById(notice)){
            return false;
        }
        ArticleCache.remove("notice",notice.getId());
        return true;
    }

    public Map<String ,Object> getNotices(int current, QueryInfo queryInfo){
        Page page = selectAndPage.getPage(current, queryInfo, noticeService, false);
        return DataFormatUtil.pageDataHandle(page);
    }

    public Notice getNoticeBy(Integer id){
        Notice notice = noticeService.getById(id);
        return notice;
    }

    public boolean deleteNotice(Integer id){
        if (id!=0&&id!=null){
            return noticeService.removeById(id);
        }
        return false;
    }

}
