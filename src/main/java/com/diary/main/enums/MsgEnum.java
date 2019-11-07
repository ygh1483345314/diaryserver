package com.diary.main.enums;

import io.swagger.annotations.Api;
import lombok.Getter;

@Getter
@Api(tags = "返回前端消息 枚举类")
public enum MsgEnum {
    NOT_TOKEN("401","无法访问资源.","warning"),

    NOT_USER("40401","用户不存在.","error"),
    EXIST_TYPE("40402","类别已存在.","error"),
    NOT_TYPE("40403","类别不存在.","error"),
    EXIST_ARTICLE_TITLE("40404","文章标题已存在.","error"),
    NOT_ARTICLE("40405","文章不存在。","error"),

    TOKEN_ERRPR("50002","访问异常.","error"),
    SERVER_ERROR("50001","服务端异常.","error"),
    DATA_CHECK("50003","表单校验异常.","error"),
    MODEL_ERROR("50004","单据异常","error"),
    DELETE_TYPE_ERROR("50005","该类别下已有文章，必须先删除文章。","error"),
    ERROR("50006","网络异常","error"),//网络异常，万能错误。

    NOT_File("40406","文件不能为空","error"),
    NEWDATA_SUCCESS("200","",""),
    SUCCESS("200","操作成功.","success");

    private  String code;
    private  String msg;
    private  String status;

    MsgEnum(String code, String msg,String status) {
        this.code = code;
        this.msg = msg;
        this.status=status;
    }
}
