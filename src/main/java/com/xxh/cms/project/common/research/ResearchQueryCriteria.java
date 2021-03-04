package com.xxh.cms.project.common.research;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author xxh
 */
@Data
@ApiModel(value = "ResearchQueryCriteria对象",description = "研究项目查询对象")
public class ResearchQueryCriteria {

    @ApiModelProperty(value = "项目分类")
    private Integer cid;

    @ApiModelProperty(value = "项目名称")
    private String name;

    @ApiModelProperty(value = "项目承担人")
    private String leader;

    private String att;

    @ApiModelProperty(value = "项目申报日期")
    private LocalDate startDate;
    private LocalDate endDate;

}
