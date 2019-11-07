package com.diary.main.mapper;

import com.diary.main.config.ExtResultMapper;
import com.diary.main.es.mapper.EsArticleMapper;
import com.diary.main.es.model.ArticleEs;
import com.diary.main.es.service.ArticleEsService;
import com.diary.main.service.TagService;
import com.diary.main.service.TypeAndArticleService;
import com.diary.main.service.TypeService;
import com.diary.main.vo.SearchVo;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/*
Created by hao on 2019/9/24
*/

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ArticleMapperTest {

@Autowired
private ArticleMapper articleMapper;
    @Autowired
    private EsArticleMapper esArticleMapper;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Resource
    private ExtResultMapper extResultMapper;


    @Autowired
    private ArticleEsService articleEsService;
    @Test
    public void findBlogById() {
        Pageable pageable=PageRequest.of(0,11);
        SearchVo searchVo=new SearchVo();
        searchVo.setTitle("123");
//        searchVo.setStatus(Arrays.asList(1));
//        searchVo.setType(Arrays.asList("php"));

        Set<String> settype=new HashSet<>();
        settype.add("C++");
        //默认查询所有
        QueryBuilder queryBuilder1 = QueryBuilders.wildcardQuery(
                "title", "*");
        QueryBuilder queryBuilder = QueryBuilders.wildcardQuery(
                "status", "*");
        QueryBuilder  queryBuilder2=QueryBuilders.wildcardQuery(
                "type", "*");
        if(searchVo!=null){
            List<Integer> statusList=searchVo.getStatus();
            List<String> listType =searchVo.getType();
            queryBuilder1 = QueryBuilders.wildcardQuery(
                    "title", StringUtils.isEmpty(searchVo.getTitle())?"*":searchVo.getTitle()+"*");
//            if(listType!=null&&listType.size()>0){
                queryBuilder2=QueryBuilders.termQuery(
                        "type", settype);
//            }

            if(statusList!=null&&statusList.size()>0){
                queryBuilder=QueryBuilders.termsQuery(
                        "status", statusList);
            }


        }
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(queryBuilder)
                .must(queryBuilder1)
                .must(queryBuilder2);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withSort(new FieldSortBuilder("dateb").order(SortOrder.DESC).unmappedType("date"))
                .withQuery(boolQuery)
                .withPageable(pageable)
                .build();
        Page<ArticleEs> articleEsPage=elasticsearchTemplate.queryForPage(searchQuery, ArticleEs.class, extResultMapper);
        System.out.println(articleEsPage.getContent());
    }



}