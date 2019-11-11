package com.diary.main.service;

import com.diary.main.es.model.ArticleEs;
import com.diary.main.model.Article;
import com.baomidou.mybatisplus.service.IService;
import com.diary.main.vo.MenuVo;
import com.diary.main.vo.SearchVo;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hao
 * @since 2019-09-14
 */
public interface ArticleService extends IService<Article> {
    boolean checkArticleByTitle(Article article);
    Integer  saveArticle(Article article);
    void  checkArticleType(Set<Integer> typeList);
    Article getArticleList(Integer id);
    Map BlogDetail(Integer id);
    Map BlogPage(String id);
    Integer updateArticle(Article article);
    void saveBatchTypeAndTag(Set<Integer> typeList,List<String> tags,Integer arid);
    Article deleteArticle(Integer id);
    public Article delArticle(Article article);
    void deleteTypeAndTag(Article article);
    org.springframework.data.domain.Page<ArticleEs> findListSearch(SearchVo searchVo, Pageable pageable);
    Integer findCountBlog();
    List<ArticleEs> findBlogTop();
    void updateReadingById(Integer reading,Integer id);
    List<Article> selectListPages();
    List<MenuVo> getMenus();
//    ArticleEs getArticleEs(Integer id);
}
