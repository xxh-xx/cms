package com.xxh.cms.users.common;

import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.*;
import com.xxh.cms.users.entity.Experts;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xxh
 */
public class ValidateForm {

    public static boolean validateExperts(Experts experts){

        String name = experts.getName();
        String regex = "^[\\s\\S]{1,20}$";
        if (!ReUtil.isMatch(regex,name)){
            return false;
        }

        Long idNumber = experts.getIdNumber();
        if (!IdcardUtil.isValidCard(idNumber.toString())){
            return false;
        }

        Integer sex = experts.getSex();
        Integer workingCondition = experts.getWorkingCondition();
        Integer category = experts.getCategory();
        regex = "^[01]{1}$";
        if (!ReUtil.isMatch(regex,sex.toString())||!ReUtil.isMatch(regex,workingCondition.toString())
                ||!ReUtil.isMatch(regex,category.toString())){
            return false;
        }

        LocalDate birthday = experts.getBirthday();

        if (!ReUtil.isMatch(PatternPool.BIRTHDAY,birthday.toString())){
            return false;
        }

        String health = experts.getHealth();
        String education = experts.getEducation();
        String politicalOutlook = experts.getPoliticalOutlook();
        if (birthday==null||StrUtil.hasBlank(health)||StrUtil.hasBlank(education)||StrUtil.hasBlank(politicalOutlook)){
            return false;
        }

        Long phone = experts.getPhone();
        if (!PhoneUtil.isMobile(phone.toString())){
            return false;
        }

        String password = experts.getPassword();
        regex = "^[a-zA-Z0-9_]{6,20}$";
        if (!ReUtil.isMatch(regex,password)){
            return false;
        }

        String profession = experts.getProfession();
        String workUnit = experts.getWorkUnit();
        String unitAddress = experts.getUnitAddress();
        String postalAddress = experts.getPostalAddress();
        String participatingRegions = experts.getParticipatingRegions();
        String title = experts.getTitle();
        String assessmentMajor = experts.getAssessmentMajor();
        regex = "^[\\s\\S]{1,100}$";
        if (!ReUtil.isMatch(regex,profession)||!ReUtil.isMatch(regex,workUnit)
            ||!ReUtil.isMatch(regex,unitAddress)||!ReUtil.isMatch(regex,postalAddress)
            ||!ReUtil.isMatch(regex,participatingRegions)||!ReUtil.isMatch(regex,title)
            ||!ReUtil.isMatch(regex,assessmentMajor)){
            return false;
        }

        Integer unitNature = experts.getUnitNature();
        regex = "^[123]{1}$";
        if (!ReUtil.isMatch(regex,unitNature.toString())){
            return false;
        }

        Integer unitZipCode = experts.getUnitZipCode();
        Integer postalCode = experts.getPostalCode();
        if (!ReUtil.isMatch(PatternPool.ZIP_CODE,unitZipCode.toString())
            ||!ReUtil.isMatch(PatternPool.ZIP_CODE,postalCode.toString())){
            return false;
        }

        String presentPosition = experts.getPresentPosition();
        regex = "^[\\s\\S]{1,50}$";
        if (!ReUtil.isMatch(regex,presentPosition)){
            return false;
        }
        Long officeTelephone = experts.getOfficeTelephone();
        if (!PhoneUtil.isPhone(officeTelephone.toString())){
            return false;
        }

        String prove = experts.getProve();
        regex = "^[\\s\\S]{0,100}$";
        if (!ReUtil.isMatch(regex,prove)){
            return false;
        }

        Integer workingYears = experts.getWorkingYears();
        if (workingYears<0||workingYears>50){
            return false;
        }

        String resume = experts.getResume();
        String experience = experts.getExperience();
        String evasiveInformation = experts.getEvasiveInformation();
        regex = "^[\\s\\S]{1,255}$";
        if (!ReUtil.isMatch(regex,resume)||!ReUtil.isMatch(regex,experience)
                ||!ReUtil.isMatch(regex,evasiveInformation)){
            return false;
        }

        String honor = experts.getHonor();
        regex = "^[\\s\\S]{0,255}$";
        if (!ReUtil.isMatch(regex,honor)){
            return false;
        }

        String att = experts.getAtt();
        regex = "^[abcde]?$";
        if (!ReUtil.isMatch(regex,att)){
            return false;
        }

        if (!IdcardUtil.getBirthByIdCard(experts.getIdNumber().toString()).equals(experts.getBirthday())
            ||IdcardUtil.getGenderByIdCard(experts.getIdNumber().toString())!=experts.getSex()){
            return false;
        }

        return true;
    }

    public static boolean validateExpertsFile(MultipartFile[] idNumberPic,MultipartFile oneInchPic,MultipartFile[] diplomaPic,
                                              MultipartFile[] proveFile,MultipartFile[] honorFile){

        if (idNumberPic==null||oneInchPic.isEmpty()||diplomaPic==null){
            return false;
        }

        if (idNumberPic.length<1||idNumberPic.length>2||diplomaPic.length<1||diplomaPic.length>10){
            return false;
        }

        if (proveFile!=null){
            if (proveFile.length<1||proveFile.length>10){
                return false;
            }
        }

        if (honorFile!=null){
            if (honorFile.length<1||honorFile.length>10){
                return false;
            }
        }

        List<String> contentTypes = new ArrayList<>();
        contentTypes.add("image/jpeg");
        contentTypes.add("image/png");

        for (MultipartFile m : idNumberPic){
            if (!contentTypes.contains(m.getContentType())){
                return false;
            }
        }

        if (!contentTypes.contains(oneInchPic.getContentType())){
            return false;
        }

        for (MultipartFile m : diplomaPic){
            if (!contentTypes.contains(m.getContentType())){
                return false;
            }
        }



        return true;
    }

    public static String validateMultipartFiles(MultipartFile[] multipartFiles,String path){
        StrBuilder strBuilder = StrBuilder.create();
        for (MultipartFile m : multipartFiles){
            strBuilder.append(path);
            strBuilder.append(IdUtil.simpleUUID());
            String fileName = m.getOriginalFilename();
            strBuilder.append(fileName.substring(fileName.lastIndexOf(".")));
            strBuilder.append(";");
        }
        return strBuilder.toString();
    }

}
