package com.diary.main.service;

import com.diary.main.model.Tag;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hao
 * @since 2019-09-14
 */
public interface TagService extends IService<Tag> {
   void deleteTagByArid(Integer arid);

   List<Tag> findAllTag();

   void deleteTag(Integer id);
}
