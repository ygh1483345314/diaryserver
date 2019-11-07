package com.diary.main.config;

import com.diary.main.enums.MsgEnum;
import com.diary.main.exception.DiaryException;
import com.diary.main.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
@ResponseBody
@Slf4j
public class ExceptionAdvice {


    @ExceptionHandler(value=Exception.class)
    public ResultVo<String> exceptionHandler(HttpServletRequest request, Exception e){
        log.error("ExceptionAdvice  error :",e);
        if(e instanceof DiaryException) {
            DiaryException ex = (DiaryException)e;
            return ResultVo.ERROR(ex.getCode(), ex.getMsg());
        }else if( e instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException me = (MethodArgumentNotValidException)e;
            List<ObjectError> errors = me.getBindingResult().getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            return ResultVo.ERROR(MsgEnum.DATA_CHECK.getCode(),msg);
        }else {
            return ResultVo.ERROR(MsgEnum.SERVER_ERROR);
        }
    }


}


