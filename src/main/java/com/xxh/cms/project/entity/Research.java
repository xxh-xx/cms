package com.xxh.cms.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    private Date stadate;

    @ApiModelProperty(value = "项目完成日期")
    private Date stodate;

    private String tel;

    @ApiModelProperty(value = "项目简介")
    private String brief;

    @ApiModelProperty(value = "项目申报日期")
    private Date pubdate;

    private String content;

    private Integer hits;


}
