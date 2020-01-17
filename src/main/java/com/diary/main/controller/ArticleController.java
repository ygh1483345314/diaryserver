package com.diary.main.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.diary.main.es.model.ArticleEs;
import com.diary.main.interfaces.PassToken;
import com.diary.main.model.Article;
import com.diary.main.model.ArticleModel;
import com.diary.main.service.ArticleService;
import com.diary.main.vo.ArticleVo;
import com.diary.main.vo.ResultVo;
import com.diary.main.vo.SearchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hao
 * @since 2019-09-14
 */
@RestController
@RequestMapping("/admin/article")
@Api(tags = "文章模块")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value="新增文章", notes="新增文章方法")
    @ApiImplicitParam(name = "article", value = "文章详细实体article", required = true, dataType = "Article")
    public ResultVo saveArticle(@Valid @RequestBody Article article){
        Integer Id=articleService.saveArticle(article);
        return ResultVo.SUCCESS(Id);
    }



    @RequestMapping(method = RequestMethod.PUT)
    @ApiOperation(value="修改文章", notes="修改文章方法")
    @ApiImplicitParam(name = "article", value = "文章详细实体article", required = true, dataType = "Article")
    public ResultVo updateArticle(@Valid @RequestBody Article article){
        Integer Id=articleService.updateArticle(article);
        return ResultVo.SUCCESS(Id);
    }

    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    @ApiOperation(value="删除文章", notes="根据Id删除文章方法")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Integer")
    public ResultVo deleteArticle(@Valid @RequestBody @PathVariable("id") Integer id){
        articleService.deleteArticle(id);
        return ResultVo.SUCCESS(null);
    }



    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    @ApiOperation(value="查看文章", notes="根据ID查找指定文章，返回文章实体类。")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Integer")
    public ResultVo getArticleById(@Valid @RequestBody @PathVariable("id") Integer id){
        Article article=articleService.getArticleList(id);
        return ResultVo.GETDATA_SUCCESS(article);
    }



    @RequestMapping(value = "/search",method = RequestMethod.POST)
    @ApiOperation(value="查询文章", notes="根据分页返回文章列表。")
    @ApiImplicitParam(name = "searchVo", value = "searchVo", required = false, dataType = "SearchVo")
     public ResultVo getArticleAll(@RequestBody  SearchVo searchVo){
        Integer page=searchVo.getPage();
        if(page>0){
            page=page-1;
        }
        org.springframework.data.domain.Page<ArticleEs> articles=articleService.findListSearch(searchVo, PageRequest.of(page,searchVo.getSize()));
        List<ArticleEs> articleList =articles.getContent();
        Map<String,Object> map=new HashMap<>();
        map.put("size",articles.getSize());
        map.put("page",searchVo.getPage());
        map.put("total",articles.getTotalElements());
        map.put("articlesArr",articleList);
        return  ResultVo.GETDATA_SUCCESS(map);
     }




  @RequestMapping(value = "/findtest")
  @PassToken
  public ArticleModel selectArticleModelAndTagsById(Integer id){
      ArticleModel articleModel = articleService.selectArticleModelAndTagsById(id);
      return    articleModel;
  }


    @RequestMapping(value = "/findtest2")
    @PassToken
    public List<ArticleModel> selectArticleModelAndTagsById(){
        List<ArticleModel> articleModel = articleService. selectArticleModelListAndTagsById();
        return    articleModel;
    }











}

