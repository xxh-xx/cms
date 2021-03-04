package com.xxh.cms.project.common.applyfor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author xxh
 */
@Data
@ApiModel(value = "ApplyForQueryCriteria对象",description = "申报项目查询条件")
public class ApplyForQueryCriteria implements Serializable {

    @ApiModelProperty(value = "申报人id")
    private Integer pid;

    @ApiModelProperty(value = "申报项目id ")
    private Integer rid;

    private String att;

    @ApiModelProperty(value = "项目申报日期")
    private LocalDate startDate;
    private LocalDate endDate;

    @ApiModelProperty(value = "项目状态")
    private String state;

}
