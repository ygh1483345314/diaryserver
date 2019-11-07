package com.diary.main.controller;


import com.diary.main.interfaces.PassToken;
import com.diary.main.model.Comment;
import com.diary.main.service.CommentService;
import com.diary.main.vo.ResultVo;
import com.diary.main.vo.SearchVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hao
 * @since 2019-10-05
 */
@RestController
@RequestMapping("/blog")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PassToken
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    @ApiOperation(value="新增评论", notes="新增评论。")
    @ApiImplicitParam(name = "comment", value = "Comment", required = false, dataType = "Comment")
    public ResultVo saveComment(@Valid @RequestBody(required = false) Comment comment){
        commentService.saveComment(comment);
        return  ResultVo.SUCCESS(null);
    }


    @PassToken
    @RequestMapping(value = "/comment",method = RequestMethod.GET)
    @ApiOperation(value="查询评论", notes="查询评论。")
    @ApiImplicitParam(name = "arid", value = "arid", required = false, dataType = "Integer")
    public ResultVo getComment(Integer arid){
//        commentService.insert(comment);
        return  ResultVo.GETDATA_SUCCESS(commentService.getCommentsByArId(arid));
    }



    @RequestMapping(value = "/{arid}/comment/{id}",method = RequestMethod.DELETE)
    @ApiOperation(value="删除评论", notes="删除评论。")
    @ApiImplicitParam(name = "id", value = "id", required = false, dataType = "Integer")
    public ResultVo delComment(@RequestBody  @PathVariable("arid") Integer arid,@PathVariable("id") Integer id){
        commentService.deleteCommentByid(arid,id);
        return  ResultVo.SUCCESS(null);
    }















}

