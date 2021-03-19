package com.xxh.cms.users.common;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.util.StrUtil;
import com.xxh.cms.users.entity.Experts;

/**
 * @author xxh
 */
public class ExpertsCache {

    private static final TimedCache<String, Experts> timedCache;

    static {
        timedCache = CacheUtil.newTimedCache(DateUnit.DAY.getMillis());
        timedCache.schedulePrune(DateUnit.DAY.getMillis());
    }

    public static void addExpert(String key,Experts experts){
        if (experts!=null){
            timedCache.put(key,experts);
        }
    }

    public static Experts getExpert(String key){
        if (!StrUtil.hasBlank(key.toString())){
            return timedCache.get(key,false);
        }

        return null;
    }

}
