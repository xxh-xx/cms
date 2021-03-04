package com.xxh.cms.article.common.queryInfo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author xxh
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "QueryInfo对象",description = "文章的查询条件")
public class QueryInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer cid;

    /**
     * 文章的名
     */
    private String name;

    private String att;

    private String author;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

}
