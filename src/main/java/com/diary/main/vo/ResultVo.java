package com.diary.main.vo;

import com.diary.main.enums.MsgEnum;
import lombok.Data;

@Data
public class ResultVo <T> {
    private  String code;
    private  String msg;
    private  String status;
    private  T data;

    public ResultVo(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public ResultVo(String code, String msg, String status,T data) {
        this.code = code;
        this.msg = msg;
        this.status = status;
        this.data = data;
    }
    public  static ResultVo ERROR(String code, String msg){
        return  new ResultVo(code,msg,MsgEnum.SERVER_ERROR.getStatus(),null);
    }
    public  static ResultVo ERROR(MsgEnum errorEnum){
        return  new ResultVo(errorEnum.getCode(),errorEnum.getMsg(),errorEnum.getStatus(),null);
    }
    public  static <T> ResultVo SUCCESS(String code, String msg,T data){
        return  new ResultVo(code,msg,data);
    }
    public  static <T> ResultVo SUCCESS(String code, String msg,String status,T data){
        return  new ResultVo(code,msg,status,data);
    }


    public  static <T> ResultVo SUCCESS(T data){
        return  new ResultVo(MsgEnum.SUCCESS.getCode(),MsgEnum.SUCCESS.getMsg(),MsgEnum.SUCCESS.getStatus(),data);
    }

    public  static <T> ResultVo GETDATA_SUCCESS(T data){
        return  new ResultVo(MsgEnum.NEWDATA_SUCCESS.getCode(),MsgEnum.NEWDATA_SUCCESS.getMsg(),MsgEnum.NEWDATA_SUCCESS.getStatus(),data);
    }











}
