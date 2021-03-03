package com.xxh.cms.article.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author xxh
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Hot对象")
public class Hot implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(name = "cid",value = "分类")
    private Integer cid;

    @ApiModelProperty(name = "title",value = "用于网页<title></title>")
    private String title;

    @ApiModelProperty(name = "keyw",value = "网页meta中的keyword")
    private String keyw;

    @ApiModelProperty(name = "descr",value = "网页meta中的descreiption")
    private String descr;

    @ApiModelProperty(name = "name",value = "文章的名")
    private String name;

    @ApiModelProperty(name = "subname",value = "文章的子名")
    private String subname;

    private String att;

    private String pic;

    private String pic2;

    private String source;

    private String author;

    private String resume;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    private String content;

    @TableField(fill = FieldFill.INSERT)
    private Integer hits;


}
