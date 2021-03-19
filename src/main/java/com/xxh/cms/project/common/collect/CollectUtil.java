package com.xxh.cms.project.common.collect;

import com.xxh.cms.project.entity.Collect;

import java.util.regex.Pattern;

/**
 * @author xxh
 */
public class CollectUtil {

    public static Boolean formVerification(Collect collect){

        if (collect==null){
            return false;
        }

        Integer cid = collect.getCid();
        String regEx = "^\\d{1}$";
        if (!Pattern.matches(regEx,cid.toString())){
            return false;
        }

        String target = collect.getTarget();
        String source = collect.getSource();
        regEx = "^[\\s\\S]{1,200}$";
        if (!Pattern.matches(regEx,target)||!Pattern.matches(regEx,source)){
            return false;
        }

        String idea = collect.getIdea();
        String prob = collect.getProb();
        regEx = "^[\\s\\S]{0,500}$";
        if (!Pattern.matches(regEx,idea)||!Pattern.matches(regEx,prob)) {
            return false;
        }

        String name = collect.getName();
        regEx = "^[\\s\\S]{0,255}$";
        if (!Pattern.matches(regEx,name)){
            return false;
        }

        String effect = collect.getEffect();
        regEx = "^[\\s\\S]{1,500}$";
        if (!Pattern.matches(regEx,effect)){
            return false;
        }

        String att = collect.getAtt();
        regEx = "^[abcde]{1}$";
        if (!Pattern.matches(regEx,att)){
            return false;
        }

        String content = collect.getContent();
        regEx = "^[\\s\\S]{1,}$";
        if (!Pattern.matches(regEx,content)){
            return false;
        }

        return true;

    }

}
