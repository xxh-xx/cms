package com.xxh.cms.article.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer cid;

    private String title;

    private String subtitle;

    private String att;

    private String pic;

    private String source;

    private String author;

    private String resume;

    @TableField(fill = FieldFill.INSERT)
    private LocalDate pubdate;

    private String content;

    @TableField(fill = FieldFill.INSERT)
    private Integer hits;


}
