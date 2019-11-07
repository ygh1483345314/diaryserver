package com.diary.main.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.diary.main.enums.MsgEnum;
import com.diary.main.exception.DiaryException;
import com.diary.main.model.Tag;
import com.diary.main.model.Type;
import com.diary.main.mapper.TypeMapper;
import com.diary.main.model.TypeAndArticle;
import com.diary.main.service.TypeAndArticleService;
import com.diary.main.service.TypeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.diary.main.vo.TypeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hao
 * @since 2019-09-14
 */
@Service
@Slf4j
@Api(tags = "类别模块Service")
@CacheConfig(cacheNames = "type")
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {
    @Autowired
    private TypeAndArticleService typeAndArticleService;
    @Autowired
    private  TypeMapper typeMapper;

    @Override
    @ApiOperation(value = "根据名称验证类别",notes = "根据名称验证 类别不存在则返回true通过校验 false则相反。")
    @ApiImplicitParam(name = "type", value = "type 实体类对象", required = true, dataType = "Type")
    public Boolean checkType(Type type) {
        Wrapper<Type> wrapper=new EntityWrapper<Type>().eq("name", type.getName());
        Integer count=selectCount(wrapper);
        return count==0;
    }

    @Override
    @ApiOperation(value = "根据ID验证类别",notes = "根据ID验类别 防止修改不存在的ID 存在则返回true通过校验 false则相反。")
    @ApiImplicitParam(name = "type", value = "type 实体类对象", required = true, dataType = "Type")
    public Boolean checkTypeById(Integer id) {
        Type type1=selectById(id);
        return type1==null;
    }


    @Override
    @ApiOperation(value = "新增类别",notes = "新增类别")
    @ApiImplicitParam(name = "type", value = "type 实体类对象", required = true, dataType = "Type")
    @Transactional
    public Boolean saveType(Type type) {
        if(!checkType(type)){
            log.error("新增类别:类别={} 已存在",type.getName());
            throw  new DiaryException(MsgEnum.EXIST_TYPE);
        }
        insert(type);
        return true;
    }

    @Override
    @ApiOperation(value = "修改类别",notes = "修改类别")
    @ApiImplicitParam(name = "type", value = "type 实体类对象", required = true, dataType = "Type")
    @Transactional
    @CacheEvict(key = "'type_all'")
    public void updateType(Type type) {
        if(checkTypeById(type.getId())) {
            log.error("修改类别,ID={}类别不存在",type.getId());
            throw  new DiaryException(MsgEnum.NOT_TYPE);
        }

        if(!checkType(type)){
            log.error("修改类别:类别={} 已存在",type.getName());
            throw  new DiaryException(MsgEnum.EXIST_TYPE);
        }

        updateById(type);
    }

    @Override
    @ApiOperation(value = "删除类别",notes = "删除类别")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Integer")
    @Transactional
    @CacheEvict(key = "'type_all'")
    public void deleteType(Integer id) {
        if(checkTypeById(id)) {
            log.error("删除类别,ID={}类别不存在",id);
            throw  new DiaryException(MsgEnum.NOT_TYPE);
        }
        List<TypeAndArticle> typeAndArticleList =typeAndArticleService.findTypeAndArticleByTyid(id);
        if(typeAndArticleList.size()>0){
            log.error("删除类别,ID={} 该类别 已存在文章，必须先删除文章。",id);
            throw  new DiaryException(MsgEnum.DELETE_TYPE_ERROR);
        }
        deleteById(id);
    }

    @Override
    @Cacheable(key = "'type_all'")
    public List<TypeVo> findAllType() {
        return typeMapper.findAllType();
    }



}
