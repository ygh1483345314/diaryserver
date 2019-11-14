package com.diary.main.vo;/*
Created by hao on 2019/11/13
*/

import com.diary.main.enums.CommentMessageEnum;
import lombok.Data;

@Data
public class CommentMessageVo {
    private String type;
    private String content;

    public CommentMessageVo(String type, String content) {
        this.type = type;
        this.content = content;
    }
    public CommentMessageVo() {

    }
    public String getContentToStr(){
        String name=CommentMessageEnum.valueOf("EMOJI").name();
        if(this.type.equalsIgnoreCase(name)){
            this.content="[表情]";
        }
        return  this.content;
    }


}
