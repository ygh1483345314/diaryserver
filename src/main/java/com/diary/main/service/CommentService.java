package com.diary.main.service;

import com.diary.main.model.Comment;
import com.baomidou.mybatisplus.service.IService;
import com.diary.main.vo.CommentVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hao
 * @since 2019-10-05
 */
public interface CommentService extends IService<Comment> {
    Map<String, Object> getCommentsByArId(Integer arid);
    void saveComment(Comment comment);
    void  deleteCommentByid(Integer arid,Integer id);
    void    deleteCommentByarid(Integer arid);
    void testEmail(int count);
}
