package com.diary.main.mapper;

import com.diary.main.model.Comment;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.diary.main.vo.CommentVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hao
 * @since 2019-10-05
 */
@Repository
public interface CommentMapper extends BaseMapper<Comment> {
   List<CommentVo> getCommentsByArId(Integer arid);

}
