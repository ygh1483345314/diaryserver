package com.diary.main.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.diary.main.es.model.ArticleEs;
import com.diary.main.interfaces.PassToken;
import com.diary.main.model.Article;
import com.diary.main.service.ArticleService;
import com.diary.main.service.impl.BaseBlogCount;
import com.diary.main.vo.ResultVo;
import com.diary.main.vo.SearchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
@RequestMapping("/admin/page")
@Api(tags = "页面模块")
public class PageController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private BaseBlogCount blogCountImpl;


    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value="查询页面", notes="根据文章列表。")
     public ResultVo getArticleAll(){
       List<Article> articleList=articleService.selectListPages();
        return  ResultVo.GETDATA_SUCCESS(articleList);
     }


//    @RequestMapping(value = "{id}",method = RequestMethod.GET)
//    @ApiOperation(value="查看文章", notes="根据ID查找指定文章，返回文章实体类。")
//    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Integer")
//    @PassToken
//    public ResultVo getBlogById(@Valid @RequestBody @PathVariable("id") Integer id){
//        Integer reading=blogCountImpl.operation(BaseBlogCount.READING_COUNT,id);
//        Map map=articleService.BlogDetail(id);
//        //article
//        ArticleEs articleEs= (ArticleEs) map.get("article");
//        articleEs.setReading(reading);
//        return ResultVo.GETDATA_SUCCESS(map);
//    }
//
}

