package com.diary.main.service.impl;

import com.diary.main.model.TypeAndArticle;
import com.diary.main.mapper.TypeAndArticleMapper;
import com.diary.main.service.TypeAndArticleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hao
 * @since 2019-09-17
 */
@Service
public class TypeAndArticleServiceImpl extends ServiceImpl<TypeAndArticleMapper, TypeAndArticle> implements TypeAndArticleService {

    @Autowired
    private  TypeAndArticleMapper andArticleMapper;
    @Override
    public void deleteTypeAndArticleByArid(Integer arid) {
        andArticleMapper.deleteTypeAndArticleByArid(arid);
    }

    @Override
    public List<TypeAndArticle> findTypeAndArticleByTyid(Integer tyid) {
        return andArticleMapper.findTypeAndArticleByTyid(tyid);
    }
}
