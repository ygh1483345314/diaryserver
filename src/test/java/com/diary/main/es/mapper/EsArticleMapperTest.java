package com.diary.main.es.mapper;

import com.diary.main.config.ExtResultMapper;
import com.diary.main.es.model.ArticleEs;
import com.diary.main.es.service.ArticleEsService;
import com.diary.main.vo.ArchivesVo;
import com.diary.main.vo.SearchVo;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class EsArticleMapperTest {

    @Autowired
    private EsArticleMapper esArticleMapper;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Resource
    private ExtResultMapper extResultMapper;

    @Autowired
    private ArticleEsService articleEsService;



    @Test
    public void save(){


        ArticleEs articleEs=new ArticleEs("2","有关于你java", Arrays.asList("java","php"),"0",
                Arrays.asList("单例设计模式","设计模式"),"java程序",new Date());

        ArticleEs articleEs2=new ArticleEs("1","有关于mysql", Arrays.asList("mysql","html"),"1",
                Arrays.asList("mysql优化"),"你数据库设计很重要,适当建立索引",new Date());

        ArticleEs articleEs3=new ArticleEs("3","有关于前端", Arrays.asList("html"),"1",
                Arrays.asList("css3"),"美工",new Date());

        esArticleMapper.save(articleEs);
        esArticleMapper.save(articleEs2);
        esArticleMapper.save(articleEs3);
//        esArticleMapper.deleteById("1");
//        esArticleMapper.deleteById("2");

    }

    @Test
    public void findDistinctByTitleContainingOrHtmlContaining() {
//
//        esArticleMapper.deleteById("1");
//        esArticleMapper.deleteById("2");

//        Pageable pageable = PageRequest.of(0, 20);
//        Page<ArticleEs> articleEsPage= esArticleMapper.findDistinctByTitleContaining("你", pageable);
//        Page<ArticleEs> articleEsPage=esArticleMapper.findDistinctByTitleContainingOrHtmlContaining("有关于mysql", "java", pageable);
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//        QueryBuilder  matchQuery = QueryBuilders.matchAllQuery();
        QueryBuilder matchQuery = QueryBuilders.multiMatchQuery("你","title","html");
//        MatchAllQueryBuilder matchQuery = QueryBuilders.matchAllQuery();

        boolQuery.must(matchQuery);
        Pageable pageRequest = PageRequest.of(0, 10,Sort.by("dateb").ascending());
//        SortBuilder sortBuilder = SortBuilders.fieldSort("tfp_save_time") .order(SortOrder.DESC).ignoreUnmapped(true);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withSort(new FieldSortBuilder("dateb").order(SortOrder.ASC))
                .withQuery(boolQuery)
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<span style=\"color:red\">").postTags("</span>"),
                        new HighlightBuilder.Field("html").preTags("<span style=\"color:red\">").postTags("</span>")
                        ).withPageable(pageRequest)
                        .build();

        Page<ArticleEs> articleEsPage=elasticsearchTemplate.queryForPage(searchQuery, ArticleEs.class, extResultMapper);
        List<ArticleEs> list=articleEsPage.getContent();
        for (ArticleEs a:list) {
            System.out.println(a);
        }


    }


    @Test
    public void findAll() {
//        Sort sort = new Sort(Sort.Direction.ASC,"dateb");
        Pageable pageable=PageRequest.of(0,2,Sort.by("dateb").descending());
        Page<ArticleEs> articleEsPage=esArticleMapper.findAll(pageable);
        List<ArticleEs> list=articleEsPage.getContent();
        for (ArticleEs a:list) {
            System.out.println(a);
        }

    }

    @Test
    public void findAllTitle() {
        //查询所有的里面指定排序的字段，并可以继续调用排序方式（升ascending降descending）,默认降序


        Iterable<ArticleEs> items = esArticleMapper.findAll(Sort.by("dateb").descending());
        for (ArticleEs i:items) {
            System.out.println(i);
        }
    }
    @Test
    public void findAllByTitleContainingAndTypeInAndStatusEquals(){
//        Pageable pageable=PageRequest.of(0,2,Sort.by("dateb").descending());
//        ExampleMatcher matcher = ExampleMatcher.matching()
//
//        Page<ArticleEs> articleEsPage=esArticleMapper.findAllByTitleContainingAndTypeInAndStatusEquals("my",Arrays.asList("mysql"),"1",pageable);
//        List<ArticleEs> list=articleEsPage.getContent();
//        for (ArticleEs a:list) {
//            System.out.println(a);
//        }


        Set<String> set = new HashSet<>();
        set.add("C++");
        set.add("php");

//        //模糊查询
        WildcardQueryBuilder queryBuilder1 = QueryBuilders.wildcardQuery(
                "title", "*");//搜索名字中含有jack的文档

        QueryBuilder queryBuilder = QueryBuilders.wildcardQuery(
                "status", "*");//搜索interest中含有read的文档

        QueryBuilder queryBuilder2 = QueryBuilders.termQuery(
                "type", set);

//        QueryBuilder queryBuilder3 = QueryBuilders.wildcardQuery(
//                "type", "*");


        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        boolQuery
//                .must(queryBuilder1)
         .must(queryBuilder).must(queryBuilder1)
//                .must(queryBuilder3)
                .must(queryBuilder2);


        Pageable pageRequest = PageRequest.of(0, 10);
//        SortBuilder sortBuilder = SortBuilders.fieldSort("tfp_save_time") .order(SortOrder.DESC).ignoreUnmapped(true);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withSort(new FieldSortBuilder("dateb").order(SortOrder.ASC).unmappedType("date"))
                .withQuery(boolQuery)
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<span style=\"color:red\">").postTags("</span>"),
                        new HighlightBuilder.Field("html").preTags("<span style=\"color:red\">").postTags("</span>")
                ).withPageable(pageRequest)
                .build();

        Page<ArticleEs> articleEsPage=elasticsearchTemplate.queryForPage(searchQuery, ArticleEs.class, extResultMapper);
        List<ArticleEs> list=articleEsPage.getContent();
        for (ArticleEs a:list) {
            System.out.println(a);
        }

//         .should(QueryBuilders.matchQuery("now_home","山西省太原市"));


    }

    @Test
    public void findBlogGroupByDateb(){
        SearchVo searchVo=new SearchVo();
        searchVo.setPage(0);
        searchVo.setSize(10);
        Page<ArticleEs> articles=articleEsService.HighlightBuilderSearch(searchVo,PageRequest.of(0,searchVo.getSize()));
        List<ArticleEs> articleEs=articles.getContent();
        List<ArchivesVo>archivesVoList=articleEs.stream().map(e->new ArchivesVo(e)).collect(Collectors.toList());
        Map<String, Map<String, List<ArchivesVo>>> groupMap
                = archivesVoList.stream().collect
                        (Collectors.groupingBy
                                    (t -> t.getYear(), Collectors.groupingBy(t -> t.getMonth()))
                        );
        System.out.println(groupMap);
    }




}