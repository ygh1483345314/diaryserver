package com.diary.main.exception;

import com.diary.main.enums.MsgEnum;
import lombok.Getter;

@Getter
public class DiaryException extends  RuntimeException {
    private  String code;
    private  String msg;

    public DiaryException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
    public DiaryException(MsgEnum errorEnum) {
        super(errorEnum.getMsg());
        this.code = errorEnum.getCode();
        this.msg = errorEnum.getMsg();
    }



}
