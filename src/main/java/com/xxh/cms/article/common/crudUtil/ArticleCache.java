package com.xxh.cms.article.common.crudutil;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.date.DateUnit;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xxh
 */
public class ArticleCache {

    private static final TimedCache<String, Map<Integer,Object>> timedCache;

    static {
        timedCache = CacheUtil.newTimedCache(DateUnit.DAY.getMillis());
        timedCache.schedulePrune(DateUnit.DAY.getMillis());
    }

    public static void add(String type,Integer id,Object article){
        if (!timedCache.containsKey(type)){
            HashMap<Integer,Object> map = new HashMap<>(16);
            map.put(id,article);
            timedCache.put(type,map);
            return;
        }
        Map<Integer, Object> map = timedCache.get(type);
        if (!map.containsKey(id)){
            map.put(id,article);
            return;
        }
    }

    public static Object get(String type,Integer id){

        if (timedCache.containsKey(type)){
            Map<Integer, Object> map = timedCache.get(type);
            if (map.containsKey(id)){
                if (map.get(id)!=null){
                    return map.get(id);
                }
            }
        }

        return null;
    }

    public static void remove(String type,Integer id){
        if (timedCache.containsKey(type)){
            Map<Integer, Object> map = timedCache.get(type);
            if (map.containsKey(id)){
                map.remove(id);
            }
        }
    }

}
