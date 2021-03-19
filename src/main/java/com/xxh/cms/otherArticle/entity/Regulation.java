package com.xxh.cms.otherArticle.entity;

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
 * @since 2021-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Regulation对象", description="")
public class Regulation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "分类")
    private Integer cid;

    @ApiModelProperty(value = "用于网页<title></title>")
    private String title;

    @ApiModelProperty(value = "网页meta中的keyword")
    private String keyw;

    @ApiModelProperty(value = "网页meta中的descreiption")
    private String descr;

    @ApiModelProperty(value = "文章的名")
    private String name;

    @ApiModelProperty(value = "文章位置")
    private String location;

    private String att;

    private String pic;

    @ApiModelProperty(value = "来源，出处")
    private String source;

    private String author;

    private String resume;

    private Date date;

    private String content;

    private Integer hits;


}
