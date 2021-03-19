package com.xxh.cms.article.common.crudutil;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxh.cms.article.common.queryInfo.QueryInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xxh
 */
@Component
public class SelectAndPage {

    @Value("${page.size}")
    private Integer size;

    public Page getPage(int current, QueryInfo queryInfo, IService service,Boolean isDoc){

        if (current<=0){
            current = 1;
        }

        String nameColumn = "name";
        String dateColumn = "date";
        if (isDoc){
            nameColumn = "title";
            dateColumn = "pubdate";
        }

        Page page = new Page(current,size);
        QueryWrapper queryWrapper = new QueryWrapper<>();

        queryWrapper.orderByDesc(dateColumn);

        if (queryInfo!=null&&service!=null&&isDoc!=null){

            Map<String,Object> allEqMap = new HashMap<>(4);
            allEqMap.put("cid",queryInfo.getCid());
            allEqMap.put("att",queryInfo.getAtt());
            queryWrapper.allEq(allEqMap,false);


            String name = queryInfo.getName();
            String author = queryInfo.getAuthor();

            queryWrapper.like(StrUtil.isNotBlank(name),nameColumn,name);
            queryWrapper.like(StrUtil.isNotBlank(author),"author",author);

            LocalDateTime startDate = queryInfo.getStartDate();
            LocalDateTime endDate = queryInfo.getEndDate();

            queryWrapper.between(startDate!=null&&endDate!=null,dateColumn,startDate,endDate);
        }

        return (Page) service.page(page,queryWrapper);
    }

}
