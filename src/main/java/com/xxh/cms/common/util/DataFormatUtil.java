package com.xxh.cms.common.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xxh
 */
public class DataFormatUtil {

    public static Map<String,Object> pageDataHandle(Page page){
        Map<String,Object> results = new HashMap<>(4);
        results.put("items",page.getRecords());
        results.put("total",page.getTotal());
        results.put("current",page.getCurrent());
        results.put("size",page.getSize());

        return results;
    }

}
