package com.xxh.cms.article.common.document;

import lombok.Data;

import java.util.Date;

/**
 * @author xxh
 */
@Data
public class HotQueryInfo {

    private Integer cid;

    /**
     * 文章的名
     */
    private String name;

    private String att;

    private String author;

    private Date date;

    private Integer hits;

}
