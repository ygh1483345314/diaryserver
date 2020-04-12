package com.diary.main.mapper;

import com.diary.main.model.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hao
 * @since 2019-09-13
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

public User findUserByNameAndPass(User user);


}
