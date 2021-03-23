package com.xxh.cms.common.resultUtil;

/**
 * @author xxh
 */
public enum ResultCode {

    SUCCESS(2000,"成功"),
    PARAM_ERROR(1000,"参数错误"),
    UPLOAD_IMAGE_ERROR(1001,"上传图片失败"),
    ADD_ERROR(1002,"添加失败"),
    RESPONSE_ERROR(1003,"响应错误"),
    UPDATE_ERROR(1004,"修改失败"),
    DELETE_ERROR(1005,"删除失败"),
    CODE_ERROR(1006,"验证码错误"),
    LOGIN_ERROR(1007,"登录失败"),
    TOKEN_ERROR(1008,"token错误");

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
