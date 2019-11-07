package com.diary.main.mapper;

import com.diary.main.model.Tag;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.diary.main.vo.TypeVo;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hao
 * @since 2019-09-14
 */
@Repository
public interface TagMapper extends BaseMapper<Tag> {
        void deleteTagByArid(Integer arid);
}
