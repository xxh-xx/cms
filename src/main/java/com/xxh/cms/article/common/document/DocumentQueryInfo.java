package com.xxh.cms.article.common.document;

import lombok.Data;

import java.util.Date;

/**
 * @author xxh
 */
@Data
public class DocumentQueryInfo {

    private Integer cid;

    private String title;

    private String att;

    private String author;

    private Date pubdate;

    private Integer hits;

}
