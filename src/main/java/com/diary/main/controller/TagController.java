package com.diary.main.controller;


import com.diary.main.service.TagService;
import com.diary.main.vo.ResultVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hao
 * @since 2019-09-14
 */
@RestController
@RequestMapping("/admin/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @ApiOperation(value = "删除标签",notes = "根据前端的id删除类别")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Integer")
    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public ResultVo del(@PathVariable("id") Integer id){
        tagService.deleteTag(id);
        return  ResultVo.SUCCESS(null);
    }








}

