package com.diary.main.service;/*
Created by hao on 2019/9/30
*/

import com.diary.main.vo.SearchVo;

import java.util.Map;

public interface BlogArchivesService {

    public Map<String,Object> getArticleAll(SearchVo searchVo);



}
