package com.xxh.cms.users.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author xxh
 * @since 2021-03-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Experts对象", description="")
public class Experts implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.NONE)
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "身份证号")
    @TableField("ID_number")
    private Long idNumber;

    @ApiModelProperty(value = "身份证正反图片")
    @TableField("ID_number_pic")
    private String idNumberPic;

    @ApiModelProperty(value = "一寸登记照")
    private String oneInchPic;

    @ApiModelProperty(value = "性别 1：男 0：女")
    private Integer sex;

    @ApiModelProperty(value = "生日")
    private LocalDate birthday;

    @ApiModelProperty(value = "手机号")
    private Long phone;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "健康状况")
    private String health;

    @ApiModelProperty(value = "文化程度")
    private String education;

    @ApiModelProperty(value = "毕业院校或专业")
    private String profession;

    @ApiModelProperty(value = "毕业证书照片")
    private String diplomaPic;

    @ApiModelProperty(value = "政治面貌")
    private String politicalOutlook;

    @ApiModelProperty(value = "工作状态 1：在职 2：退休")
    private Integer workingCondition;

    @ApiModelProperty(value = "工作单位")
    private String workUnit;

    @ApiModelProperty(value = "单位性质 1：国家机关 2：事业单位 3：企业")
    private Integer unitNature;

    @ApiModelProperty(value = "单位地址")
    private String unitAddress;

    @ApiModelProperty(value = "单位邮编")
    private Integer unitZipCode;

    @ApiModelProperty(value = "现任职务")
    private String presentPosition;

    @ApiModelProperty(value = "办公电话")
    private Long officeTelephone;

    @ApiModelProperty(value = "通讯地址")
    private String postalAddress;

    @ApiModelProperty(value = "邮政编码")
    private Integer postalCode;

    @ApiModelProperty(value = "愿意参加的区域")
    private String participatingRegions;

    @ApiModelProperty(value = "中（高）级职称")
    private String title;

    @ApiModelProperty(value = "同等中（高）级职称水平或未开展评定的专业提供行业协会出具的相关证明")
    private String prove;

    @ApiModelProperty(value = "同等中（高）级职称水平或未开展评定的专业提供行业协会出具的相关证明附件")
    private String proveFile;

    @ApiModelProperty(value = "拟申请专业")
    private String assessmentMajor;

    @ApiModelProperty(value = "从事专业相关工作年限")
    private Integer workingYears;

    @ApiModelProperty(value = "主要工作简历")
    private String resume;

    @ApiModelProperty(value = "实践经验")
    private String experience;

    @ApiModelProperty(value = "获得省级以上课题研究、技能比赛等体现专业技术水平的奖励、表彰")
    private String honor;

    @ApiModelProperty(value = "奖励、表彰证明文件")
    private String honorFile;

    @ApiModelProperty(value = "本人认为需要申请回避的信息")
    private String evasiveInformation;

    @ApiModelProperty(value = "申请提出时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDate createdDate;

    @ApiModelProperty(value = "a公开，b不公开，c有限公开，d单位，e个人")
    private String att;

    @ApiModelProperty(value = "0等待进入核准，1在核准过程中，2不通过核准，3通过核准")
    @TableField(fill = FieldFill.INSERT)
    private Integer verify;

    @ApiModelProperty(value = "申请通过时间")
    private LocalDate oktime;

    @ApiModelProperty(value = "审核管理者")
    private String createdBy;

    @ApiModelProperty(value = "1：评审专家 2：参研专家")
    private Integer category;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer deleted;

    @ApiModelProperty(value = "乐观锁：当要更新一条记录的时候，希望这条记录没有被别人更新")
    @Version
    private Integer version;


}
