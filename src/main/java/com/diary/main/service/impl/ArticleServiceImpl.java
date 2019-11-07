package com.diary.main.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.diary.main.config.DiaryRedisCacheManager;
import com.diary.main.enums.MsgEnum;
import com.diary.main.es.model.ArticleEs;
import com.diary.main.es.service.ArticleEsService;
import com.diary.main.exception.DiaryException;
import com.diary.main.model.Article;
import com.diary.main.mapper.ArticleMapper;
import com.diary.main.model.Tag;
import com.diary.main.model.Type;
import com.diary.main.model.TypeAndArticle;
import com.diary.main.service.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.diary.main.vo.ArticleVo;
import com.diary.main.vo.MenuVo;
import com.diary.main.vo.SearchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.joining;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hao
 * @since 2019-09-14
 */
@Service
@Api(tags = "文章模块service层")
@Slf4j
@CacheConfig(cacheNames = "article")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private TypeAndArticleService typeAndArticleService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private  ArticleMapper articleMapper;
    @Autowired
    private ArticleEsService articleEsService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private DiaryRedisCacheManager diaryRedisCacheManager;

    @Override
    @ApiOperation(value = "文章标题查重",notes = "根据文章标题查询是否已存在 返回布尔值 true为不存在验证通过，false相反。")
    @ApiImplicitParam(name = "article", value = "article 实体类对象", required = true, dataType = "Article")
    public boolean checkArticleByTitle(Article article) {
        if(article==null){
            log.error("新增或保存 文章检查错误，实体类为空。");
            throw  new DiaryException(MsgEnum.MODEL_ERROR);
        }
        Wrapper<Article> wrapper=new EntityWrapper<Article>().eq("title", article.getTitle());
        if(article.getId()!=null&&article.getId()!=0){
            wrapper.notIn("id",article.getId());
        }
        return  selectCount(wrapper)==0;
    }

    @Override
    @ApiOperation(value = "保存文章",notes = "根据传入通过验证的实体类对象保存，到数据库中。")
    @ApiImplicitParam(name = "article", value = "article 实体类对象", required = true, dataType = "Article")
//    @CacheEvict(key = "'articleCount'")
    @Caching(evict = {
            @CacheEvict(key = "'type_all'"),
            @CacheEvict(key = "'tag_all'"),
            @CacheEvict(key = "'pageList'"),
            @CacheEvict(key = "'menus'"),
            @CacheEvict(key = "'articleCount'")
    })
    @Transactional
    public Integer saveArticle(Article article) {
        if(!checkArticleByTitle(article)){
                log.error("文章保存 标题={} 已存在。",article.getTitle());
                throw  new DiaryException(MsgEnum.EXIST_ARTICLE_TITLE);
        }
            //新增文章
        insert(article);
        Set<Integer> typeList =article.getType();

//        批量新增标签
        List<String> tags=article.getDynamicTags();
        saveBatchTypeAndTag(typeList,tags,article.getId());

        //保存到es中
        if(article!=null&&article.getPage()!=null&&article.getPage()==1){
            return  article.getId();
        }
        article.setReading(0);
        ArticleEs articleEs1=saveEs(article);
        log.info("保存文章成功 elasticsearch 保存成功 articleEs={}",articleEs1);
        return  article.getId();
    }



    @Override
    @ApiOperation(value = "批量检查类型",notes ="用于批量检查类型ID是否合法" )
    @ApiImplicitParam(name = "typeList", value = "article SET 集合", required = true, dataType = "Set<Type>")
    public void checkArticleType(Set<Integer> typeList) {
        //        将前端传过来的类型集合 用逗号 串起来 方便 检查处理
        String typesID = typeList.stream()
//                .filter(e -> e.getId() != null)
                .map(String::valueOf).distinct()
                .collect(joining(","));
        if(typesID.equals("")){
            return;
        }

        Wrapper<Type> wrapper=new EntityWrapper<Type>().in("id",typesID);
        Integer count=typeService.selectCount(wrapper);
        if(count!=typeList.size()){//查询出来的类别数据 与前台传入ID 不对应即 为 有未知类型ID
            log.error("ArticleServiceImpl 文章保存 页面存在未知 类型ID");
            throw  new DiaryException(MsgEnum.NOT_TYPE);
        }
    }

//    @Cacheable(key = "'article_'+#id")
    @Override
    @ApiOperation(value = "查询文章",notes ="根据ID查询文章" )
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Integer")
    public Article getArticleList(Integer id) {
       Article article= articleMapper.getArticleList(id);
        return article;
    }


    @Cacheable(key = "'article_'+#id")
    @Override
    @ApiOperation(value = "查询文章",notes ="根据ID查询文章" )
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Integer")
    public Map BlogDetail(Integer id) {
        //查询数据
        ArticleEs article= articleMapper.findBlogById(id);
        ArticleVo nexBlog=articleMapper.getNextBlogId(id);
        ArticleVo onBlog=articleMapper.getOnBlogId(id);
        HashMap<String,Object> map=new HashMap<>();
        map.put("nexBlog", nexBlog);
        map.put("onBlog", onBlog);
        map.put("article", article);
        return map;
    }


    @Cacheable(key = "'article_'+#id")
    @Override
    @ApiOperation(value = "查询文章",notes ="根据ID查询文章" )
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Integer")
    public Map BlogPage(String id) {
        //查询数据
        ArticleEs article= articleMapper.findBlogPageById(id);
        HashMap<String,Object> map=new HashMap<>();
        map.put("article", article);
        return map;
    }







    @Override
    @ApiOperation(value = "修改文章",notes = "根据传入通过验证的实体类对象保存，到数据库中。")
    @ApiImplicitParam(name = "article", value = "article 实体类对象", required = true, dataType = "Article")
    @Transactional
    @Caching(evict = {
            @CacheEvict(key = "'article_'+#article2.id"),   /*后端管理 对象缓存*/
            @CacheEvict(key = "'type_all'"),
            @CacheEvict(key = "'tag_all'"),
            @CacheEvict(key = "'pageList'"),
            @CacheEvict(key = "'menus'"),
            @CacheEvict(value="articleTop", key="'articleTop'")
                })
    public Integer updateArticle(Article article2) {
        Article article=selectById(article2.getId());
        if(article==null){
            log.error("文章不存在，ID={}",article2.getId());
            throw new DiaryException(MsgEnum.NOT_ARTICLE);
        }

        if(!checkArticleByTitle(article2)){
            log.error("文章保存 标题={} 已存在。",article.getTitle());
            throw  new DiaryException(MsgEnum.EXIST_ARTICLE_TITLE);
        }
        //修改
        updateById(article2);
        //先根据 文章ID 删除 类型和 标签 在重新 生成类型和标签
        deleteTypeAndTag(article2);
        Set<Integer> typeList =article2.getType();

//        批量新增标签
        List<String> tags=article2.getDynamicTags();
        saveBatchTypeAndTag(typeList,tags,article2.getId());
        //保存到es中
        ArticleEs articleEs1=saveEs(article);
        log.info("修改文章成功 elasticsearch 修改成功 articleEs={}",articleEs1);
        return  article.getId();
    }

    public ArticleEs saveEs(Article article){
        ArticleEs articleEs =articleMapper.findBlogById(article.getId());
        String newhtml=articleEs.getHtml().replaceAll("<[^>]*>","");
        articleEs.setHtml(newhtml.length()>100?newhtml.substring(0,100)+"......":newhtml);
        ArticleEs articleEs1=articleEsService.save(articleEs);
        return  articleEs1;
    }


    @Override
    @ApiOperation(value = "批量新增类型，标签",notes ="批量新增类型，标签" )
    @ApiImplicitParam(name = "typeList，tags", value = "typeList，tags", required = true, dataType = "Set<Type> List<Tag>")
    public void saveBatchTypeAndTag(Set<Integer> typeList, List<String> tags,Integer arid) {
        //检查类型ID是否合法
        checkArticleType(typeList);
//        批量新增 类型与 文章ID映射表
        List<TypeAndArticle> typeAndArticles=new ArrayList<TypeAndArticle>();
        for (Integer type:typeList) {
            typeAndArticles.add(new TypeAndArticle(type,arid));
        }
        if(typeAndArticles.size()>0) {
            typeAndArticleService.insertBatch(typeAndArticles);
        }
//        批量新增标签
        List<Tag> tagList=new ArrayList<>();
        for (String tag:tags) {
            tagList.add(new Tag(tag,arid));
        }
        if(tagList.size()>0){
            tagService.insertBatch(tagList);
        }

    }


    @Override
    @ApiOperation(value = "删除文章",notes = "根据传入ID，删除文章。")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Integer")
    @Transactional
    @Caching(evict = {
            @CacheEvict(key = "'article_'+#id"),   /*后端管理 对象缓存*/
//            @CacheEvict(key = "'blog'+#id"),        /**前端页面 对象缓存*/
            @CacheEvict(key = "'type_all'"),
            @CacheEvict(key = "'tag_all'"),
            @CacheEvict(key = "'articleCount'"),
            @CacheEvict(key = "'pageList'"),
            @CacheEvict(key = "'menus'"),
            @CacheEvict(value="articleTop", key="'articleTop'"),
    })
    public void deleteArticle(Integer id) {
        Article article=selectById(id);
//        if(article==null){
//            log.error("文章不存在，ID={}",id);
//            throw new DiaryException(MsgEnum.NOT_ARTICLE);
//        }
        if(article!=null){
            deleteTypeAndTag(article);
            deleteById(article.getId());
            commentService.deleteCommentByarid(id);
        }
        articleEsService.delete(String.valueOf(id));
        log.info("删除文章成功 elasticsearch 删除成功 articleEs={}",article);
    }

    @Override
    @ApiOperation(value = "删除类型和标签",notes = "删除类型和标签")
    @ApiImplicitParam(name = "Article", value = "Article", required = true, dataType = "Article")
    public void deleteTypeAndTag(Article article){
        typeAndArticleService.deleteTypeAndArticleByArid(article.getId());
        tagService.deleteTagByArid(article.getId());
    }

    @Override
    @ApiOperation(value = "查询文章列表",notes ="根据文章筛选条件 查询文章列表" )
    @ApiImplicitParam(name = "searchVo", value = "SearchVo", required = true, dataType = "SearchVo")
    public Page<ArticleEs> findListSearch(SearchVo searchVo, Pageable pageable) {
        org.springframework.data.domain.Page<ArticleEs> page =articleEsService.findDistinctByTitleContainingOrHtmlContaining(searchVo,pageable);
        return page;
    }

    @Override
    @Cacheable(key = "'articleCount'")
    public Integer findCountBlog() {
        Wrapper<Article> wrapper=new EntityWrapper();
        wrapper.eq("status",1);
        return articleMapper.selectCount(wrapper);
    }

    @Override
    public List<ArticleEs> findBlogTop() {
        return articleMapper.findBlogTop();
    }

    @Override
    public void updateReadingById(Integer reading, Integer id) {
        articleMapper.updateReadingById(reading,id);
    }

    @Override
    @Cacheable(key = "'pageList'",unless = "#result == null")
    public List<Article> selectListPages() {
        Wrapper<Article> wrapper=new EntityWrapper();
        wrapper.eq("page","1");
        return articleMapper.selectList(wrapper);
    }

    @Override
    @Cacheable(key = "'menus'")
    public List<MenuVo> getMenus() {
        return articleMapper.getMenus();
    }


}
