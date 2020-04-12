package com.diary.main.service.impl;

import com.diary.main.service.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;

    @Test
    public void testEmail() {
        commentService.testEmail(2);
    }
}