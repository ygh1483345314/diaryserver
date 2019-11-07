package com.diary.main.vo;/*
Created by hao on 2019/9/20
*/

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class SearchVo {
    private   List<String> type;
    private  String title;
    private  List<Integer> status;
    private  List<String> tags;
    private  Integer page;
    private  Integer size;
    private  Integer total;
}
