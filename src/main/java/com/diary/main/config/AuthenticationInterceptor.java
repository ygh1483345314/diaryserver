package com.diary.main.config;

import com.auth0.jwt.interfaces.Claim;
import com.diary.main.enums.MsgEnum;
import com.diary.main.exception.DiaryException;
import com.diary.main.interfaces.PassToken;
import com.diary.main.model.User;
import com.diary.main.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
//
//    @Autowired
//    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private  JwtConfig jwtConfig;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {

        String token = httpServletRequest.getHeader("Authorization");// 从 http 请求头中取出 token
//         如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod) object;
        Method method=handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        // 执行认证
                if (token == null) {
                    throw new DiaryException(MsgEnum.NOT_TOKEN);
                }
        //  获取 token 中的 user id
                Map<String, Claim> mapUser=tokenService.verifyToken(token);
                Integer   userId=mapUser.get("id").asInt();
                String password=mapUser.get("password").asString();
                String username=mapUser.get("user").asString();
                Long exp=mapUser.get("exp").asLong();//到期时间戳
                User user=new User(userId,username,password);
                LocalDateTime dateTime =LocalDateTime.ofEpochSecond(exp,0, ZoneOffset.ofHours(8));//token到期时间
                LocalDateTime now = LocalDateTime.now();//当前时间
                int minutes= (int) Duration.between(now,dateTime).toMinutes();
                Integer refresh =jwtConfig.getRefresh();
                if(minutes<refresh){
                    //token 续签.
                    String newtoken=tokenService.createJWT(user);
                    httpServletResponse.setHeader("Authorization", newtoken);
                }
                return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }


}
