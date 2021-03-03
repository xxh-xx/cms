package com.xxh.cms.common.resultUtil;

/**
 * @author xxh
 */
public enum ResultCode {

    SUCCESS(2000,"成功"),
    PARAM_ERROR(1000,"参数错误"),
    UPLOAD_IMAGE_ERROR(1001,"上传图片失败");

    /**
     * code:状态码
     * message:消息
     */
    private Integer code;
    private String message;

    ResultCode(Integer code,String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode(){
        return this.code;
    }

    public String getMessage(){
        return this.message;
    }

}
