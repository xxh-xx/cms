package com.xxh.cms.project.entity;

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
 * @since 2021-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Collect对象", description="")
public class Collect implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "征集项目库")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "分类")
    private Integer cid;

    @ApiModelProperty(value = "项目考核指标")
    private String target;

    @ApiModelProperty(value = "项目选题思路")
    private String idea;

    @ApiModelProperty(value = "项目要解决的问题")
    private String prob;

    @ApiModelProperty(value = "项目名")
    private String name;

    @ApiModelProperty(value = "成果的作用")
    private String effect;

    private String att;

    @ApiModelProperty(value = "提交者id")
    private Integer colid;

    private String pic;

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
