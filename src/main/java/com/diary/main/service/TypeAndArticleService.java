package com.diary.main.service;

import com.diary.main.model.TypeAndArticle;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hao
 * @since 2019-09-17
 */
public interface TypeAndArticleService extends IService<TypeAndArticle> {
   void deleteTypeAndArticleByArid(Integer arid);
   List<TypeAndArticle> findTypeAndArticleByTyid(Integer tyid);
}
