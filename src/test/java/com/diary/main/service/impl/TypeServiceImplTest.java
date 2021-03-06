package com.diary.main.service.impl;

import com.diary.main.utils.BaiDuSend;
import com.diary.main.vo.TypeVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.List;


/*
Created by hao on 2019/9/27
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TypeServiceImplTest {
  @Autowired
   private  TypeServiceImpl typeService;
  @Autowired
  private BaiDuSend baiDuSend;
    @Test
    public void findAllType() {
        List<TypeVo> typeVos =typeService.findAllType();
        for (TypeVo typeVo:typeVos) {
            System.out.println(typeVo);
        }
    }


    @Test
    public void sendPost(){
        baiDuSend.sendPost();
    }


}