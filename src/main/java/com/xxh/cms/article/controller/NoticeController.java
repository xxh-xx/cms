package com.xxh.cms.article.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxh.cms.article.common.crudUtil.SelectAndPage;
import com.xxh.cms.article.common.queryInfo.QueryInfo;
import com.xxh.cms.article.entity.Notice;
import com.xxh.cms.article.service.impl.NoticeServiceImpl;
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
public class NoticeController {

    private final NoticeServiceImpl noticeService;
    private final SelectAndPage selectAndPage;

    @Autowired
    public NoticeController(NoticeServiceImpl noticeService,SelectAndPage selectAndPage){
        this.noticeService = noticeService;
        this.selectAndPage = selectAndPage;
    }

    public Result addNotice(Notice notice, List<String> picList){
        int picMaxNumber = 2;
        int picMinNumber = 1;

        if (picList.size()>picMaxNumber||picList.size()<=0){
            return Result.failure(ResultCode.PARAM_ERROR);
        }

        if (picList.size()==picMaxNumber){
            notice.setPic(picList.get(0));
            notice.setPic2(picList.get(1));
        }

        if (picList.size()==picMinNumber){
            notice.setPic(picList.get(0));
        }
        notice.setTitle(notice.getName());
        notice.setAuthor("xxx");
        noticeService.save(notice);
        return Result.success();
    }

    public Map<String ,Object> getNotices(int current, QueryInfo queryInfo){
        Page page = selectAndPage.getPage(current, queryInfo, noticeService, false);
        return DataFormatUtil.pageDataHandle(page);
    }

}
