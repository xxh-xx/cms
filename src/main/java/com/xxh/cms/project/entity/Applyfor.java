package com.xxh.cms.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
 * @since 2021-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Applyfor对象", description="")
public class Applyfor implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目申报库")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "申报人id")
    private Integer pid;

    @ApiModelProperty(value = "申报项目id ")
    private Integer rid;

    private String att;

    @ApiModelProperty(value = "项目申报日期")
    private Date stadate;

    @ApiModelProperty(value = "项目申报文档")
    private String document;

    @ApiModelProperty(value = "项目状态")
    private String state;

    @ApiModelProperty(value = "项目合同")
    private String contract;

    private Integer hits;


}
