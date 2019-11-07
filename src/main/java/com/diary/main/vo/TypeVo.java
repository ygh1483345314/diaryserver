package com.diary.main.vo;/*
Created by hao on 2019/9/27
*/

import lombok.Data;

import java.io.Serializable;

/**
 *
 */
@Data
public class TypeVo implements Serializable {
    private static final long serialVersionUID = 8712644351310270120L;
    private  Integer id;
    private  String name;
    private  Integer qty;
}



