package com.xxh.cms.common.util;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.util.StrUtil;

import java.util.LinkedList;

/**
 * @author xxh
 */
public class PathsCache {

    private static final TimedCache<String, LinkedList<String>> timedCache;

    static {
        timedCache = CacheUtil.newTimedCache(DateUnit.DAY.getMillis());
        timedCache.schedulePrune(DateUnit.DAY.getMillis());
    }

    public static boolean addPath(String key,String path){

        if (StrUtil.hasBlank(path)||StrUtil.hasBlank(key)){
            return false;
        }

        if (!timedCache.containsKey(key)){
            timedCache.put(key,new LinkedList<>());
        }
        timedCache.get(key,false).add(path);

        return true;
    }

    public static LinkedList<String> getPaths(String key){

        if (StrUtil.hasBlank(key)||!timedCache.containsKey(key)){
            return null;
        }

        return timedCache.get(key);
    }

    public static void removePaths(String key){
        if (StrUtil.hasBlank(key)||!timedCache.containsKey(key)){
            return;
        }

        timedCache.remove(key);
    }

}
