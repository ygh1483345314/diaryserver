package com.diary.main.vo;/*
Created by hao on 2019/9/20
*/

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class ArticlesPageVo {
    private  String title;
    private Date dateb;
    private List<Integer> tyid;
    private  Integer reading;
    private  Integer status;
//    h.title,h.dateb,h.reading,t.tyid,h.status
//

}
