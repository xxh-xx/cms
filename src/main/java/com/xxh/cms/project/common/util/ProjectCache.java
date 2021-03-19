package com.xxh.cms.project.common.util;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xxh
 */
public class ProjectCache {

    private static final TimedCache<String, Map<Integer,Object>> timedCache;

    static {
        timedCache = CacheUtil.newTimedCache(DateUnit.DAY.getMillis());
        timedCache.schedulePrune(DateUnit.DAY.getMillis());
    }

    public static void addProject(String type,Object project){

        if (StrUtil.isBlank(type)||project==null){
            return;
        }
        Integer id = Integer.parseInt(ReflectUtil.invoke(project,"getId").toString());
        if (timedCache.containsKey(type)){
            Map<Integer, Object> map = timedCache.get(type);
            map.put(id,project);
            return;
        }
        Map<Integer, Object> map = new HashMap<>(16);
        map.put(id,project);
        timedCache.put(type,map);

    }

    public static Object getProject(String type,Integer id){
        if (StrUtil.hasBlank(type)||StrUtil.hasBlank(id.toString())){
            return null;
        }

        if (timedCache.containsKey(type)){
            Map<Integer, Object> map = timedCache.get(type);
            if (map.containsKey(id)){
                return map.get(id);
            }
        }

        return null;
    }

    public static void removeProject(String type,Integer id){
        if (StrUtil.hasBlank(type)||StrUtil.hasBlank(id.toString())){
            return;
        }

        if (timedCache.containsKey(type)){
            Map<Integer, Object> map = timedCache.get(type);
            if (map.containsKey(id)){
                map.remove(id);
            }
        }

    }

}
