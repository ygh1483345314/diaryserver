package com.diary.main.service.impl;/*
Created by hao on 2019/9/30
*/

import com.diary.main.es.model.ArticleEs;
import com.diary.main.es.service.ArticleEsService;
import com.diary.main.service.BlogArchivesService;
import com.diary.main.vo.ArchivesMonth;
import com.diary.main.vo.ArchivesVo;
import com.diary.main.vo.ArchivesVoList;
import com.diary.main.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BlogArchivesServiceImpl implements BlogArchivesService {

    @Autowired
    private ArticleEsService articleEsService;
    @Override
    public Map<String, Object> getArticleAll(SearchVo searchVo) {
        Page<ArticleEs> articles=articleEsService.HighlightBuilderSearch(searchVo,PageRequest.of(0,searchVo.getSize()));
        List<ArticleEs> articleEs=articles.getContent();
        List<ArchivesVo>archivesVoList=articleEs.stream().map(e->new ArchivesVo(e))
                .collect(Collectors.toList());

        TreeMap<String, LinkedHashMap<String, List<ArchivesVo>>> archivesGroup
                = archivesVoList.stream().collect
                (Collectors.groupingBy
                        (t -> t.getYear(),TreeMap :: new, Collectors.groupingBy(t -> t.getMonth(),LinkedHashMap :: new,Collectors.toList())
                        )
                );

        Map<String, LinkedHashMap<String, List<ArchivesVo>>>  map=  archivesGroup.descendingMap();

        List<ArchivesVoList> list=map.entrySet().stream().map(e ->
                new ArchivesVoList(e.getKey(),e.getValue().entrySet().stream()
                        .map(m->new ArchivesMonth(m.getKey(),m.getValue())).collect(Collectors.toList())))
                .collect(Collectors.toList());
        Map<String,Object> map2=new HashMap<>();
        map2.put("size",articles.getSize());
        map2.put("page",searchVo.getPage());
        map2.put("total",articles.getTotalElements());
        map2.put("articlesArr",list);
        return map2;
    }
}
