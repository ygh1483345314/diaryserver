package com.diary.main.controller;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.diary.main.enums.MsgEnum;
import com.diary.main.model.Type;
import com.diary.main.service.TypeService;
import com.diary.main.vo.ResultVo;
import com.diary.main.vo.TypeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hao
 * @since 2019-09-14
 */
@RestController
@RequestMapping("/admin/type")
@Api(tags = "类别模块API")
public class TypeController {
    @Autowired
    private TypeService typeService;




    @PostMapping()
    @ApiOperation(value = "保存类别",notes = "根据前端的Type对象保存类别")
    @ApiImplicitParam(name = "type", value = "用户详细实体type", required = true, dataType = "Type")
    public ResultVo save(@RequestBody @Valid Type type){
        typeService.saveType(type);
        return  ResultVo.SUCCESS(null);
    }

    @GetMapping
    @ApiOperation(value = "查询所有类别",notes = "查询所有类别返回页面")
    public ResultVo getData(){
        Wrapper<Type> wrapper= new EntityWrapper();
        List<Type> typeList=typeService.selectList(wrapper);
        return  ResultVo.GETDATA_SUCCESS(typeList);
    }

    @ApiOperation(value = "修改类别",notes = "根据前端的Type对象修改类别")
    @ApiImplicitParam(name = "type", value = "用户详细实体type", required = true, dataType = "Type")
    @RequestMapping(method = RequestMethod.PUT)
    public ResultVo updateType(@RequestBody @Valid Type type){
        typeService.updateType(type);
        return  ResultVo.SUCCESS(null);
    }

    @ApiOperation(value = "删除类别",notes = "根据前端的id删除类别")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Integer")
    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public ResultVo deleteType(@PathVariable("id") Integer id){
        typeService.deleteType(id);
        return  ResultVo.SUCCESS(null);
    }







}

