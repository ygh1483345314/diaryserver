package com.diary.main.controller;/*
Created by hao on 2019/9/25
*/


import com.diary.main.config.DiaryRedisCacheManager;
import com.diary.main.es.model.ArticleEs;
import com.diary.main.es.service.ArticleEsService;
import com.diary.main.interfaces.PassToken;
import com.diary.main.model.Article;
import com.diary.main.model.Tag;
import com.diary.main.service.ArticleService;
import com.diary.main.service.TagService;
import com.diary.main.service.TypeService;
import com.diary.main.service.impl.BaseBlogCount;
import com.diary.main.utils.BaiDuSend;
import com.diary.main.vo.MenuVo;
import com.diary.main.vo.ResultVo;
import com.diary.main.vo.SearchVo;
import com.diary.main.vo.TypeVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private ArticleEsService articleEsService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private BaseBlogCount blogCountImpl;
    @Autowired
    private BaiDuSend baiDuSend;

    @PassToken
    @RequestMapping(value = "/bolgList",method = RequestMethod.POST)
    @ApiOperation(value="查询博客列表", notes="根据分页返回博客列表。")
    @ApiImplicitParam(name = "searchVo", value = "searchVo", required = false, dataType = "SearchVo")
    public ResultVo getArticleAll(@RequestBody SearchVo searchVo){
        Integer page=searchVo.getPage();
        if(page>0){
            page=page-1;
        }
        List<ArticleEs> articleEsList=articleEsService.selectTopBlog();
        Page<ArticleEs> articles=articleEsService.HighlightBuilderSearch(searchVo,PageRequest.of(page,searchVo.getSize()));
        List<ArticleEs> articleList =articles.getContent();
        List<ArticleEs> articleEsListTop=articleEsService.selectTopBlog();
        articleEsListTop.addAll(articleList);
        Map<String,Object> map=new HashMap<>();
        map.put("size",articles.getSize());
        map.put("page",searchVo.getPage());
        map.put("total",articles.getTotalElements());
        map.put("articlesArr",articleEsListTop);
        return  ResultVo.GETDATA_SUCCESS(map);
    }

    @PassToken
    @RequestMapping(method=RequestMethod.GET,value = "/typeList")
    @ApiOperation(value = "查询首页所有类别",notes = "查询首页所有类别")
    public ResultVo findTypeAll(){
        List<TypeVo> types=typeService.findAllType();
         List<Tag> tags=tagService.findAllTag();
         Map<String,Object>map=new HashMap<>();
         map.put("taglist",tags);
         map.put("typelist",types);
        return  ResultVo.GETDATA_SUCCESS(map);
    }

    @PassToken
    @RequestMapping(method=RequestMethod.POST,value = "/search")
    @ApiOperation(value = "首页类别或者标签查询",notes = "首页根据类别或者标签查询")
    @ApiImplicitParam(name = "searchVo", value = "searchVo", required = false, dataType = "SearchVo")
    public ResultVo findBlogListByTypeOrTag(@RequestBody SearchVo searchVo){
        Integer page=searchVo.getPage();
        if(page>0){
            page=page-1;
        }
        Page<ArticleEs> articles=articleEsService.findByTypeAndTag(PageRequest.of(page,searchVo.getSize()),searchVo);
        Map<String,Object> map=new HashMap<>();
        map.put("size",articles.getSize());
        map.put("page",searchVo.getPage());
        map.put("total",articles.getTotalElements());
        map.put("articlesArr",articles.getContent());
        return  ResultVo.GETDATA_SUCCESS(map);
    }


    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    @ApiOperation(value="查看文章", notes="根据ID查找指定文章，返回文章实体类。")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Integer")
    @PassToken
    public ResultVo getBlogById(@Valid @RequestBody @PathVariable("id") Integer id){
        Integer reading=blogCountImpl.operation(BaseBlogCount.READING_COUNT,id);
        Map map=articleService.BlogDetail(id);
        //article
        ArticleEs articleEs= (ArticleEs) map.get("article");
        articleEs.setReading(reading);
        return ResultVo.GETDATA_SUCCESS(map);
    }





    @RequestMapping(value = "/right",method = RequestMethod.GET)
    @ApiOperation(value="导航栏右侧数据统计", notes="导航栏右侧数据统计。")
    @PassToken
    public ResultVo getRightData(){
        List<TypeVo> types=typeService.findAllType();
        List<Tag> tags=tagService.findAllTag();
        Map<String,Object>map=new HashMap<>();
//      Integer blogCount=types.stream().map(e->e.getQty()).reduce((e1,e2)->e1+e2).get();
        Integer blogCount=articleService.findCountBlog();
        map.put("blogCount",blogCount);
        map.put("typeCount",types.size());
        map.put("tagCount",tags.size());
        return  ResultVo.GETDATA_SUCCESS(map);
    }


    @RequestMapping(value = "/menu",method = RequestMethod.GET)
    @ApiOperation(value="获取导航栏", notes="获取导航栏。")
    @PassToken
    public ResultVo getMenuData(){
//        List<TypeVo> types=typeService.findAllType();
//        List<Tag> tags=tagService.findAllTag();
//        Map<String,Object>map=new HashMap<>();
//        Integer blogCount=articleService.findCountBlog();
//        map.put("blogCount",blogCount);
//        map.put("typeCount",types.size());
//        map.put("tagCount",tags.size());

        List<MenuVo> menus=articleService.getMenus();
        return  ResultVo.GETDATA_SUCCESS(menus);
    }

    @RequestMapping("/baidu")
    @PassToken
    @ApiOperation(value="发送百度实时推送", notes="发送百度实时推送")
    public ResultVo sendBaidu(){
       return ResultVo.SUCCESS("200","发送成功",baiDuSend.sendPost());
    }

//    public Map<String,Integer> getOldCountQtyById(String blogCountByid){
//        Map<String,Integer>map=new HashMap<>();
//        String sp[]=blogCountByid.split(":");
//        if(sp.length<2){
//            map.put("lookCount",0);
//            map.put("qty",0);
//            return  map;
//        }
//        Integer lookCount=Integer.valueOf(sp[0]);//浏览量
//        Integer qty=Integer.valueOf(sp[1]);//统计浏览数 每过10次 写入mysql中
//        map.put("lookCount",lookCount);
//        map.put("qty",qty);
//        return  map;
//    }
//



}
