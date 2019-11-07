package com.diary.main.utils;/*
Created by hao on 2019/10/14
*/

import com.diary.main.es.model.ArticleEs;
import com.diary.main.model.Article;
import org.springframework.beans.BeanUtils;

public class ArticleEsToBean {
    public static ArticleEs conver(Article article){
        ArticleEs articleEs=new ArticleEs();
        BeanUtils.copyProperties(article,articleEs);
        return articleEs;
    }


}
