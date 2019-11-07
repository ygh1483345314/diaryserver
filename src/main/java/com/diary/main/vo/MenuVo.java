package com.diary.main.vo;/*
Created by hao on 2019/11/5
*/

import lombok.Data;

import java.io.Serializable;

@Data
public class MenuVo implements Serializable {
    private static final long serialVersionUID = -8572151773824035880L;
    private  Integer id;
    private  String name;
    private  String url;
    private  String icon;
}
