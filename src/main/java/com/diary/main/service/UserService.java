package com.diary.main.service;

import com.diary.main.model.User;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hao
 * @since 2019-09-13
 */
public interface UserService extends IService<User> {

    public User findUserByNameAndPass(User user);

}
