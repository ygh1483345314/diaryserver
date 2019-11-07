package com.diary.main.es.service.impl;/*
Created by hao on 2019/9/23
*/
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.diary.main.config.ExtResultMapper;
import com.diary.main.enums.MsgEnum;
import com.diary.main.es.mapper.EsArticleMapper;
import com.diary.main.es.model.ArticleEs;
import com.diary.main.es.service.ArticleEsService;
import com.diary.main.exception.DiaryException;
import com.diary.main.model.Article;
import com.diary.main.service.ArticleService;
import com.diary.main.vo.SearchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Api(tags = "es 搜索service层")
@Slf4j
public class ArticleEsServiceImpl implements ArticleEsService {

    @Autowired
    private EsArticleMapper esArticleMapper;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Resource
    private ExtResultMapper extResultMapper;
    @Autowired
    private ArticleService articleService;



    @Override
    @ApiOperation(value = "es保存",notes = "保存数据到 es 上")
    @ApiImplicitParam(name = "articleEs", value = "articleEs 实体类对象", required = true, dataType = "articleEs")
    public ArticleEs save(ArticleEs articleEs) {
        return   esArticleMapper.save(articleEs);
    }

    @Override
    @ApiOperation(value = "查询数据",notes = "根据内容来判断查询方法走向")
    @ApiImplicitParam(name = "searchVo pageable", value = "SearchVo  Pageable 实体类对象", required = true, dataType = "SearchVo Pageable")
    public Page<ArticleEs> findDistinctByTitleContainingOrHtmlContaining(SearchVo searchVo, Pageable pageable) {
        //如果为空 则查询所有。 相反 则为模糊查询 所有 标题含有 或者 内容中含有的数据
//        if(StringUtils.isEmpty(searchVo.getTitle())){
////          return   findAllQuery(pageable);
//            return  findByTitleAndTypeAndStatus(pageable,searchVo);
//        };
//        return HighlightBuilderSearch(searchVo,pageable);
        return  findByTitleAndTypeAndStatus(pageable,searchVo);
    }

    @Override
    @ApiOperation(value = "删除es数据",notes = "根据ID删除文档")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "String")
    public void delete(String id) {
        esArticleMapper.deleteById(id);
    }

    @Override
    @ApiOperation(value = "查询所有",notes = "查询所有并返回分页")
    @ApiImplicitParam(name = "pageable", value = "Pageable", required = true, dataType = "Pageable")
    public Page<ArticleEs> findAllQuery( Pageable pageable) {
//        Pageable pageable=PageRequest.of(0,2,Sort.by("dateb").descending());
        Page<ArticleEs> articleEsPage=esArticleMapper.findAll(pageable);
        return articleEsPage;
    }


    @Override
    @ApiOperation(value = "模糊查询",notes = "根据标题或者内容搜索 并完成分页及关键词高亮。")
    @ApiImplicitParam(name = "searchVo pageable", value = "SearchVo Pageable", required = true, dataType = "SearchVo Pageable")
    public Page<ArticleEs> HighlightBuilderSearch(SearchVo searchVo, Pageable pageable) {
        QueryBuilder matchQuery =QueryBuilders.matchAllQuery();
        QueryBuilder queryBuilder =QueryBuilders.matchAllQuery();
        QueryBuilder queryBuilder1 =QueryBuilders.matchAllQuery();

        if(!StringUtils.isEmpty(searchVo.getTitle())) {
            matchQuery = QueryBuilders.multiMatchQuery
                    (searchVo.getTitle(), "title", "html");
//            matchQuery= new QueryStringQueryBuilder(searchVo.getTitle()).field("title").defaultOperator(Operator.AND);
        }
        List<String> listType =searchVo.getType();
        if(listType!=null&&listType.size()>0){
            queryBuilder=QueryBuilders.matchQuery(
                    "type", listType);
        }
        List<String> tags=searchVo.getTags();
        if(tags!=null&&tags.size()>0){
            queryBuilder1=QueryBuilders.matchQuery(
                    "tag", tags);
        }
        BoolQueryBuilder boolQuery =
                QueryBuilders.boolQuery().
                        filter(QueryBuilders.termQuery("status", "1"))
                            .filter(QueryBuilders.termQuery("top", "0"));
        boolQuery.must(matchQuery).must(queryBuilder).must(queryBuilder1);

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withSort(new FieldSortBuilder("dateb").order(SortOrder.DESC).unmappedType("date"))  //这里有两种方法排序 这个是其中之一 。 另一种是通过 PageRequest.of(0,2,Sort.by("dateb").descending()) 这里就是用这种
                .withQuery(boolQuery)
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<span style=\"color:red\">").postTags("</span>"),
                        new HighlightBuilder.Field("html").preTags("<span style=\"color:red\">").postTags("</span>")
                ).withPageable(pageable).build();
        Page<ArticleEs> articleEsPage=elasticsearchTemplate.queryForPage(searchQuery, ArticleEs.class, extResultMapper);
        log.info("首页条件查询 searchVo={}, articleEsPage={}",searchVo,articleEsPage.getContent());
        return articleEsPage;
    }



    @Override
    @ApiOperation(value = "后台多条件查询",notes = "根据标题 类型 状态多条件查询")
    @ApiImplicitParam(name = "searchVo pageable", value = "SearchVo Pageable", required = true, dataType = "SearchVo Pageable")
    public Page<ArticleEs> findByTitleAndTypeAndStatus (Pageable pageable,SearchVo searchVo){
        //默认查询所有
        QueryBuilder queryBuilder1 = QueryBuilders.wildcardQuery(
                "title", "*");
        QueryBuilder queryBuilder = QueryBuilders.wildcardQuery(
                "status", "*");
//        QueryBuilder  queryBuilder2=QueryBuilders.wildcardQuery(
//                "type", "*");


        if(searchVo!=null){
            List<Integer> statusList=searchVo.getStatus();
            if(!StringUtils.isEmpty(searchVo.getTitle())){
                queryBuilder1= new QueryStringQueryBuilder(searchVo.getTitle()).field("title").defaultOperator(Operator.AND);
            }
            if(statusList!=null&&statusList.size()>0){
                queryBuilder=QueryBuilders.termsQuery(
                        "status", statusList);
            }
        }
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(queryBuilder)
                 .must(queryBuilder1);
//                 .must(queryBuilder2);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withSort(new FieldSortBuilder("dateb").order(SortOrder.DESC).unmappedType("date"))
                .withQuery(boolQuery)
                .withPageable(pageable)
                .build();
        Page<ArticleEs> articleEsPage=elasticsearchTemplate.queryForPage(searchQuery, ArticleEs.class, extResultMapper);
        log.info("后台条件查询 searchVo={}, articleEsPage={}",searchVo,articleEsPage.getContent());
        return  articleEsPage;
    }





    @Override
    @ApiOperation(value = "后台多条件查询",notes = "根据类型 标签多条件查询")
    @ApiImplicitParam(name = "searchVo pageable", value = "SearchVo Pageable", required = true, dataType = "SearchVo Pageable")
    public Page<ArticleEs> findByTypeAndTag (Pageable pageable,SearchVo searchVo){
        if(searchVo==null){
            log.error("首页根据类型或标签查询，查询条件为空。");
            throw  new DiaryException(MsgEnum.DATA_CHECK);
        }
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery().filter(QueryBuilders.termQuery("status", "1"));;
        QueryBuilder queryBuilder =QueryBuilders.matchAllQuery();
        QueryBuilder queryBuilder1 =QueryBuilders.matchAllQuery();
        List  listType =searchVo.getType();
        if(listType.size()>0){
            queryBuilder=QueryBuilders.termsQuery(
                        "type", listType);
        }
        List<String> tags=searchVo.getTags();
        if(tags.size()>0){
            queryBuilder1=QueryBuilders.termsQuery(
                    "tag", tags);
        }

        boolQuery.must(queryBuilder)
                 .must(queryBuilder1);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withSort(new FieldSortBuilder("dateb").order(SortOrder.DESC).unmappedType("date"))
                .withQuery(boolQuery)
                .withPageable(pageable)
                .build();
        Page<ArticleEs> articleEsPage=elasticsearchTemplate.queryForPage(searchQuery, ArticleEs.class, extResultMapper);
        log.info("后台条件查询 searchVo={}, articleEsPage={}",searchVo,articleEsPage.getContent());
        return  articleEsPage;
    }

    @Override
    @ApiOperation(value = "查询置顶博客",notes = "查询置顶博客")
    @Cacheable(value="articleTop", key="'articleTop'")
    public List<ArticleEs> selectTopBlog() {

//        Wrapper<Article> wrapper=new EntityWrapper<>();
//        wrapper.eq("top","1");
//        wrapper.eq("status","1");
//        List<Article> articleList=articleService.selectList(wrapper);
//
//        List<ArticleEs> articleEsList = articleList.stream().map(e -> {
//           ArticleEs articleEs1=new ArticleEs();
//           String newhtml=e.getHtml().replaceAll("<[^>]*>","");
//           e.setHtml(newhtml.length()>100?newhtml.substring(0,100)+"......":newhtml);
//           BeanUtils.copyProperties(e,articleEs1);
//           return  articleEs1;
//        }).collect(Collectors.toList());

        List<ArticleEs> articleEsList =articleService.findBlogTop().stream().map(e->{
            String newhtml=e.getHtml().replaceAll("<[^>]*>","");
           e.setHtml(newhtml.length()>100?newhtml.substring(0,100)+"......":newhtml);
           return  e;
        }).collect(Collectors.toList());

        return articleEsList;
    }


}
