package com.diary.main.controller;
import com.diary.main.es.model.ArticleEs;
import com.diary.main.interfaces.PassToken;
import com.diary.main.service.ArticleService;
import com.diary.main.service.impl.BaseBlogCount;
import com.diary.main.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
@RequestMapping("/blog/pages")
@Api(tags = "页面模块")
public class BlogPageController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private BaseBlogCount blogCountImpl;


    @RequestMapping(value = "{url}",method = RequestMethod.GET)
    @ApiOperation(value="查看文章", notes="根据ID查找指定文章，返回文章实体类。")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Integer")
    @PassToken
    public ResultVo getBlogById(@Valid @RequestBody @PathVariable("url") String url){

        Map map=articleService.BlogPage(url);
        //article
        ArticleEs articleEs= (ArticleEs) map.get("article");
        Integer reading=blogCountImpl.operation(BaseBlogCount.READING_COUNT,Integer.valueOf(articleEs.getId()));
        articleEs.setReading(reading);
        return ResultVo.GETDATA_SUCCESS(map);
    }

}

