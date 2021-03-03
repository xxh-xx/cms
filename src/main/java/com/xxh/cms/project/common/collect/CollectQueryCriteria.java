package com.xxh.cms.project.common.collect;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xxh
 */
@Data
@ApiModel(value = "CollectQueryCriteria对象",description = "征集项目查询条件")
public class CollectQueryCriteria implements Serializable {

    @ApiModelProperty(value = "分类")
    private Integer cid;

    @ApiModelProperty(value = "项目名")
    private String name;

    private String att;

    private String author;

    private Date startDate;
    private Date endDate;

    private Integer startHits;
    private Integer endHits;



}
