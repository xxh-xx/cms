package com.xxh.cms.article.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 分类
     */
    private Integer cid;

    /**
     * 用于网页<title></title>
     */
    private String title;

    /**
     * 网页meta中的keyword
     */
    private String keyw;

    /**
     * 网页meta中的descreiption
     */
    private String descr;

    /**
     * 文章的名
     */
    private String name;

    /**
     * 文章的子名
     */
    private String subname;

    private String att;

    private String pic;

    private String pic2;

    private String source;

    private String author;

    private String resume;

    private Date date;

    private String content;

    private Integer hits;


}
