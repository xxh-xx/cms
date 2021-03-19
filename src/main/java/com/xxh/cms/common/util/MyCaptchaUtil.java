package com.xxh.cms.common.util;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.IdUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xxh
 */
public class MyCaptchaUtil {

    private static final TimedCache<String, LineCaptcha> timedCache;

    static {
        timedCache = CacheUtil.newTimedCache(DateUnit.MINUTE.getMillis()*10);
        timedCache.schedulePrune(DateUnit.MINUTE.getMillis()*10);
    }

    public static Map<String,String> createLineCaptcha(){
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200,100);
        String key = IdUtil.simpleUUID();
        timedCache.put(key,lineCaptcha);
        Map<String,String> map = new HashMap<>(2);
        map.put("key",key);
        map.put("code",lineCaptcha.getImageBase64());
        Console.log(lineCaptcha.getCode());
        return map;
    }

    public static boolean verification(String key,String code){
        LineCaptcha lineCaptcha = timedCache.get(key,false);
        if (lineCaptcha==null){
            return false;
        }
        if (!lineCaptcha.verify(code)){
            return false;
        }

        return true;
    }

}
