package com.xxh.cms.article.common.queryInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author xxh
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer cid;

    /**
     * 文章的名
     */
    private String name;

    private String att;

    private String author;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer startHits;

    private Integer endHits;

}
