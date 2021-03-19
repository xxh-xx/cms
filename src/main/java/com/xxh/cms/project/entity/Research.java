package com.xxh.cms.project.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2021-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Research对象", description="")
public class Research implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "研究项目")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "项目分类")
    private Integer cid;

    @ApiModelProperty(value = "项目名称")
    private String name;

    @ApiModelProperty(value = "项目承担人")
    private String leader;

    @ApiModelProperty(value = "项目承担人id用注册者id ")
    private Integer leaderid;

    private String att;

    @ApiModelProperty(value = "项目开始日期")
    private LocalDate stadate;

    @ApiModelProperty(value = "项目完成日期")
    private LocalDate stodate;

    private String tel;

    @ApiModelProperty(value = "项目简介")
    private String brief;

    @ApiModelProperty(value = "项目申报日期")
    @TableField(fill = FieldFill.INSERT)
    private LocalDate pubdate;

    private String content;

    @TableField(fill = FieldFill.INSERT)
    private Integer hits;


}
