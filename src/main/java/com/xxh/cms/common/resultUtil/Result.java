package com.xxh.cms.common.resultUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xxh
 */
@Data
@ApiModel(value = "统一返回格式")
public class Result implements Serializable {

    @ApiModelProperty(value = "状态码")
    private Integer code;
    @ApiModelProperty(value = "返回信息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private Object data;

    private Result(ResultCode resultCode,Object data){
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }
    private Result(ResultCode resultCode){
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    /**
     * 返回成功
     * @return
     */
    public static Result success(){
        Result result = new Result(ResultCode.SUCCESS);
        return result;
    }

    /**
     * 返回成功
     * @param data 返回数据
     * @return
     */
    public static Result success(Object data){
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * 返回失败
     * @param resultCode 返回失败的状态码和信息
     * @return
     */
    public static Result failure(ResultCode resultCode){
        Result result = new Result(resultCode);
        return result;
    }

    /**
     * 返回失败
     * @param resultCode 返回失败的状态码和信息
     * @param data 返回数据
     * @return
     */
    public static Result failure(ResultCode resultCode,Object data){
        Result result = new Result(resultCode,data);
        return result;
    }

}
