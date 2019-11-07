package com.diary.main.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.diary.main.model.Tag;
import com.diary.main.mapper.TagMapper;
import com.diary.main.service.TagService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hao
 * @since 2019-09-14
 */
@Service
@CacheConfig(cacheNames = "tag")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Autowired
    private  TagMapper tagMapper;

    @Override
    public void deleteTagByArid(Integer arid) {
        tagMapper.deleteTagByArid(arid);
    }

    @Override
    @Cacheable(key = "'tag_all'")
    public List<Tag> findAllTag() {
        Wrapper<Tag> wrapper=new EntityWrapper<Tag>();
        List<Tag> tags=tagMapper.selectList(wrapper);
        return tags;
    }

    @Override
    @CacheEvict(key = "'tag_all'")
    public void deleteTag(Integer id) {
        tagMapper.deleteById(id);
    }
}
