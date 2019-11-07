package com.diary.main.service.impl;

import com.diary.main.enums.MsgEnum;
import com.diary.main.exception.DiaryException;
import com.diary.main.model.User;
import com.diary.main.mapper.UserMapper;
import com.diary.main.service.UserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import sun.misc.BASE64Encoder;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hao
 * @since 2019-09-13
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private  UserMapper userMapper;

    @Override
    public User findUserByNameAndPass(User user) {

//Qpf0SxOVUjUkWySXOZ16kw==
        String newPassword=new BASE64Encoder().encode(DigestUtils.md5Digest(user.getPassword().getBytes()));
        user.setPassword(newPassword);
        User user1=userMapper.findUserByNameAndPass(user);
        if(user1==null){
            log.error("账号密码错误,账号:{},密码:{}",user.getUsername(),user.getPassword());
            throw  new DiaryException(MsgEnum.NOT_USER);
        }
        return user1;
    }



}
