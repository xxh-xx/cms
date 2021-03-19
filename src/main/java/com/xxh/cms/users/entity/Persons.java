package com.xxh.cms.users.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

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
@ApiModel(value="Persons对象", description="")
public class Persons implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "注册人员库")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "注册账号")
    private String name;

    @ApiModelProperty(value = "用户名")
    private String tname;

    @ApiModelProperty(value = "月")
    private Integer agemm;

    @ApiModelProperty(value = "年")
    private Date ageyy;

    private String sex;

    private String phone;

    private String email;

    @ApiModelProperty(value = "所在省市")
    private String province;

    @ApiModelProperty(value = "所在市")
    private String city;

    @ApiModelProperty(value = "所在区县")
    private String county;

    private String address;

    private String pwd;

    @ApiModelProperty(value = "积分")
    private Integer points;

    private String pic;

    private String ip;

    @ApiModelProperty(value = "申请提出时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdDate;

    @ApiModelProperty(value = "a公开，b不公开，c有限公开，d单位，e个人")
    private String att;

    @ApiModelProperty(value = "个人简介")
    private String introd;

    @ApiModelProperty(value = "0等待进入核准，1在核准过程中，2不通过核准，3通过核准")
    @TableField(fill = FieldFill.INSERT)
    private Integer verify;

    @ApiModelProperty(value = "申请通过时间")
    private Date oktime;

    @ApiModelProperty(value = "审核管理者")
    private String createdBy;

    @ApiModelProperty(value = "专业")
    private String profession;

    @ApiModelProperty(value = "工作单位")
    private String job;

    @ApiModelProperty(value = "特长")
    private String speciality;

    @ApiModelProperty(value = "身体状况")
    private String health;

    @ApiModelProperty(value = "人员类别,基地人员，基地专家，志愿人员，志愿专家，合作伙伴")
    private String category;


}
