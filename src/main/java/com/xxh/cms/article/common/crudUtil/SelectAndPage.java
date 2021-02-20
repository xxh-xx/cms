package com.xxh.cms.article.common.crudUtil;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * @author xxh
 */
@Component
public class SelectAndPage {

    @Value("${page.size}")
    private Integer size;

    public Page getPage(int current,Map<String,Object> eqMap, Map<String,Object> likeMap, IService service){
        Page page = new Page(current,size);

        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(eqMap,false);

        Set<String> keySet = likeMap.keySet();

        for (String key:keySet){
            if (ObjectUtils.isNotEmpty(likeMap.get(key))){
                queryWrapper.like(key,likeMap.get(key));
            }
        }

        return (Page) service.page(page, queryWrapper);
    }

}
