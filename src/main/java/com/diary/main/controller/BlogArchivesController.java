package com.diary.main.controller;/*
Created by hao on 2019/9/29
*/

import com.diary.main.enums.MsgEnum;
import com.diary.main.es.model.ArticleEs;
import com.diary.main.es.service.ArticleEsService;
import com.diary.main.interfaces.PassToken;
import com.diary.main.service.ArticleService;
import com.diary.main.service.BlogArchivesService;
import com.diary.main.vo.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@RestController
@RequestMapping("/blog")
public class BlogArchivesController {

    @Autowired
    private ArticleEsService articleEsService;
    @Autowired
    private BlogArchivesService blogArchivesService;

    @PassToken
    @RequestMapping(value = "/ArchivesList",method = RequestMethod.POST)
    @ApiOperation(value="查询博客归档列表", notes="查询博客归档列表。")
    @ApiImplicitParam(name = "searchVo", value = "searchVo", required = false, dataType = "SearchVo")
    public ResultVo getArticleAll(@RequestBody SearchVo searchVo) {
        return  ResultVo.GETDATA_SUCCESS(blogArchivesService.getArticleAll(searchVo));
    }







}
