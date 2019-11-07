package com.diary.main.controller;


import com.diary.main.enums.MsgEnum;
import com.diary.main.interfaces.PassToken;
import com.diary.main.model.User;
import com.diary.main.service.TokenService;
import com.diary.main.service.UserService;
import com.diary.main.vo.ResultVo;
import com.diary.main.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hao
 * @since 2019-09-13
 */
@RestController
@RequestMapping("/admin/login")
@Slf4j
@Api(tags = "登录模块API")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;


    @PostMapping()
    @PassToken
    @ApiOperation(value="登录功能", notes="根据User对象登录")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    public ResultVo login(@Valid @RequestBody User user, HttpServletResponse response){
        User user1=userService.findUserByNameAndPass(user);
        String toke=tokenService.createJWT(user1);
        response.setHeader("Authorization", toke);
        return ResultVo.SUCCESS(new UserVo(toke,user.getUsername()));
    }



}


