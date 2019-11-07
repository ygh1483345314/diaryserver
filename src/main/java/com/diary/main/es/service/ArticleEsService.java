package com.diary.main.es.service;/*
Created by hao on 2019/9/23
*/

import com.diary.main.es.model.ArticleEs;
import com.diary.main.vo.SearchVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleEsService {
        ArticleEs save(ArticleEs articleEs);
        Page<ArticleEs> findDistinctByTitleContainingOrHtmlContaining(SearchVo searchVo, Pageable pageable);
        void delete(String id);
        Page<ArticleEs> findAllQuery(Pageable pageable);
        Page<ArticleEs> HighlightBuilderSearch(SearchVo searchVo, Pageable pageable);
        Page<ArticleEs> findByTitleAndTypeAndStatus(Pageable pageable,SearchVo searchVo);
        Page<ArticleEs> findByTypeAndTag (Pageable pageable,SearchVo searchVo);
        List<ArticleEs> selectTopBlog();
}
