package com.diary.main.vo;/*
Created by hao on 2019/9/20
*/

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ArticleVo implements Serializable{
   private static final long serialVersionUID = -8573177703824035880L;
   private  Integer id;
   private  Integer reading;
   private  String title;
   private  String type;
   private  Integer status;
   private Date dateb;
}
