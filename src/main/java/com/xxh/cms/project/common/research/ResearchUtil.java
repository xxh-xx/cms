package com.xxh.cms.project.common.research;

import com.xxh.cms.project.entity.Research;

import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * @author xxh
 */
public class ResearchUtil {

    public static Boolean formVerification(Research research){

        Integer cid = research.getCid();
        String regex = "^\\d{1}$";
        if (!Pattern.matches(regex,cid.toString())){
            return false;
        }

        String name = research.getName();
        regex = "^[\\s\\S]{1,200}$";
        if (!Pattern.matches(regex,name)){
            return false;
        }

        String att = research.getAtt();
        regex = "^[abcde]{1}$";
        if (!Pattern.matches(regex,att)){
            return false;
        }

        LocalDate staDate = research.getStadate();
        LocalDate stoDate = research.getStodate();
        if (staDate==null||stoDate==null||!(stoDate instanceof LocalDate)||!(staDate instanceof LocalDate)
                || staDate.isAfter(stoDate)){
            return false;
        }

        String tel = research.getTel();
        regex = "^(13[0-9]|14[5|7|9]|15[0-3|5-9]|18[0-9]|17[1-3|5-8]|166|19[8-9])\\d{8}$";
        if (!Pattern.matches(regex,tel)){
            return false;
        }

        return true;
    }

}
