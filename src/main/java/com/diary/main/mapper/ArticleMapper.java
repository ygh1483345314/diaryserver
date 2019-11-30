package com.diary.main.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.diary.main.es.model.ArticleEs;
import com.diary.main.model.Article;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.diary.main.vo.ArticleVo;
import com.diary.main.vo.MenuVo;
import com.diary.main.vo.SearchVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hao
 * @since 2019-09-14
 */
@Repository
public interface ArticleMapper extends BaseMapper<Article> {

  Article getArticleList(Integer id);


  List<ArticleVo> findListSearch(Page<ArticleVo> page, SearchVo searchVo);


  ArticleEs findBlogById(Integer id);

  ArticleEs findBlogPageById(String id);

  List<ArticleEs> findBlogTop();

  ArticleVo getNextBlogId(Integer id);

  ArticleVo getOnBlogId(Integer id);

  void updateReadingById(@Param("reading") Integer reading, @Param("id")  Integer id);

  List<MenuVo> getMenus();

  List<Integer> findListByStatusAndEntry();

  void updateEntity();

//  ArticleVo BlogDetailById(Integer id);
}
