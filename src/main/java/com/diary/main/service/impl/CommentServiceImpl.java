package com.diary.main.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.diary.main.config.RabbitConfig;
import com.diary.main.enums.MsgEnum;
import com.diary.main.exception.DiaryException;
import com.diary.main.model.Comment;
import com.diary.main.mapper.CommentMapper;
import com.diary.main.model.EmailModel;
import com.diary.main.model.Type;
import com.diary.main.service.CommentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.diary.main.service.EmailService;
import com.diary.main.service.FanoutProducerService;
import com.diary.main.vo.CommentMessageVo;
import com.diary.main.vo.CommentVo;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hao
 * @since 2019-10-05
 */
@Service
@CacheConfig(cacheNames = "comment")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private  CommentMapper commentMapper;


    @Autowired
    private EmailModel emailModel;

    @Autowired
    private FanoutProducerService fanoutProducerService;


    @Override
    @Cacheable(key = "'comment_'+#arid")
    public Map<String, Object> getCommentsByArId(Integer arid) {
        Wrapper<Comment> wrapper= new EntityWrapper();
        wrapper.eq("arid", arid);
        Integer count=commentMapper.selectCount(wrapper);
        Map<String,Object>map=new HashMap<>();
        map.put("count", count);
        map.put("commentList", commentMapper.getCommentsByArId(arid));
        return map;
    }

    @Override
    @CacheEvict(key = "'comment_'+#comment.arid")
    @Transactional
    public void saveComment(Comment comment) {
        emailModel.setSubject("博客新评论.");
        String contentListStr=comment.getContent();
        java.lang.reflect.Type type =new TypeToken<List<CommentMessageVo>>() {}.getType();
        Gson gson=new Gson();
        List<CommentMessageVo> commentMessageVoList=gson.fromJson(contentListStr,type);
        StringBuilder contentBuffer=new StringBuilder();
        for (CommentMessageVo c:commentMessageVoList) {
            contentBuffer.append(c.getContentToStr());
        }
        String msg=comment.getName()+"||"+comment.getEmail()+" : "+contentBuffer;
        emailModel.setContent(msg);
        String msgStr=gson.toJson(emailModel);
        fanoutProducerService.send(RabbitConfig.FANOUT_EMAIL_QUEUE, msgStr);
//        emailService.sendEmailMessage(emailModel);
        commentMapper.insert(comment);
    }



    @Override
    @CacheEvict(key = "'comment_'+#arid")
    @Transactional
    public void deleteCommentByid(Integer arid,Integer id) {
        if(arid==null||id==null||id==0||arid==0){
            throw  new DiaryException(MsgEnum.ERROR);
        }
        Wrapper<Comment> wrapper= new EntityWrapper();
        wrapper.eq("parent_id", id);
        commentMapper.delete(wrapper);//删除子评论
        commentMapper.deleteById(id);
    }

    @Override
    @CacheEvict(key = "'comment_'+#arid")
    public void deleteCommentByarid(Integer arid) {
        if(arid==null){
            throw  new DiaryException(MsgEnum.ERROR);
        }
        Wrapper<Comment> wrapper= new EntityWrapper();
        wrapper.eq("arid", arid);
        commentMapper.delete(wrapper);//删除所有评论
    }



    public  void testEmail(int count){
        Gson gson=new Gson();
        for (int i = 0; i < count; i++) {
            emailModel.setSubject("测试评论:"+i);
            emailModel.setContent(i+"");
            String msgStr = gson.toJson(emailModel);
            sendEmail(msgStr);
        }
    }

    @Async
    public void sendEmail(String msgStr){
        System.out.println("发送邮件"+msgStr);
        fanoutProducerService.send(RabbitConfig.FANOUT_EMAIL_QUEUE, msgStr);
    }

}
