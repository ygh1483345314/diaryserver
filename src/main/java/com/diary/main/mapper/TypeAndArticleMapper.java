package com.diary.main.mapper;

import com.diary.main.model.TypeAndArticle;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hao
 * @since 2019-09-17
 */
@Repository
public interface TypeAndArticleMapper extends BaseMapper<TypeAndArticle> {
   void deleteTypeAndArticleByArid(Integer arid);
   List<TypeAndArticle> findTypeAndArticleByTyid(Integer tyid);
}
