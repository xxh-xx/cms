package com.xxh.cms.users.common;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.util.StrUtil;

/**
 * @author xxh
 */
public class TokenCache {

    private final static TimedCache<String, String> timedCache;

    static {
        timedCache = CacheUtil.newTimedCache(DateUnit.WEEK.getMillis());
        timedCache.schedulePrune(DateUnit.DAY.getMillis());
    }

    public static void add(String key,String token){
        if (!StrUtil.hasBlank(key)&&!StrUtil.hasBlank(token)){
            timedCache.put(key,token);
        }
    }

    public static String get(String key){
        if (!StrUtil.hasBlank(key)){
            return timedCache.get(key,false);
        }
        return null;
    }

    public static void remove(String key){
        if (!StrUtil.hasBlank(key)){
            timedCache.remove(key);
        }
    }

}
