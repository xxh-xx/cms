package com.xxh.cms.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MyPC
 */
public class MapPaths {

    private static final Map<String, List<String>> pathMap = new HashMap<>();

    public static void addPaths(String path,String uniqueKey){

        if (StringUtils.isBlank(path)||StringUtils.isBlank(uniqueKey)){
            return;
        }

        if (!pathMap.containsKey(uniqueKey)){
            pathMap.put(uniqueKey,new ArrayList<>());
        }

        pathMap.get(uniqueKey).add(path);
    }

    public static List<String> getPaths(String uniqueKey){

        if (StringUtils.isBlank(uniqueKey)){
            return null;
        }

        return pathMap.get(uniqueKey);
    }

    public static void removePaths(String uniqueKey){
        if (StringUtils.isBlank(uniqueKey)){
            return;
        }

        pathMap.remove(uniqueKey);
    }

}
