package com.diary.main.service;

import com.diary.main.model.Type;
import com.baomidou.mybatisplus.service.IService;
import com.diary.main.vo.TypeVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hao
 * @since 2019-09-14
 */
public interface TypeService extends IService<Type> {

//   根据 名称 检查 是否存在相同 分类
    Boolean checkType(Type type);
    //   根据 id 检查 是否存在相同 分类
    Boolean checkTypeById(Integer id);
    //保存分类
    Boolean saveType(Type type);
    //修改分类
    void updateType(Type type);
    //删除分类
    void deleteType(Integer id);
    List<TypeVo> findAllType();


}
