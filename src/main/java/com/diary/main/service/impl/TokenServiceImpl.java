package com.diary.main.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.diary.main.config.JwtConfig;
import com.diary.main.enums.MsgEnum;
import com.diary.main.exception.DiaryException;
import com.diary.main.model.User;
import com.diary.main.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class TokenServiceImpl implements TokenService {



    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public String getToken(User user) {

        String token="";
        token= JWT.create().withAudience(user.getUsername()).withExpiresAt(new Date())
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }


    @Override
    public String createJWT(User user) {
        /** token 过期时间: 2个小时 */
//        Calendar.DECEMBER 对 小时操作
//        int calendarField = Calendar.DECEMBER;
        int calendarField = Calendar.MINUTE; //分钟
        int calendarInterval = jwtConfig.getExpiration();
        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();
        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create().withHeader(map) // header
                .withClaim("id", user.getId())
                .withClaim("user", user.getUsername())
                .withClaim("password", user.getPassword())
                .withIssuedAt(iatDate) // sign time
                .withExpiresAt(expiresDate) // expire time
                .sign(Algorithm.HMAC256(jwtConfig.getSecret())); // signature

        return token;

    }

    @Override
    public Map<String, Claim> verifyToken(String token) {

        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtConfig.getSecret())).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            log.error("TokenServiceImpl token不通过:",e);
            // token 校验失败, 抛出Token验证非法异常
            throw new DiaryException(MsgEnum.NOT_TOKEN);
        }
        return jwt.getClaims();
    }

    @Override
    public Long getAppUID(String token) {
             Map<String, Claim> claims = verifyToken(token);
             Claim user_id_claim = claims.get("id");
             Claim user_password_claim = claims.get("password");
            if (null == user_id_claim || StringUtils.isEmpty(user_id_claim.asString())) {
               throw new DiaryException(MsgEnum.NOT_TOKEN);
            }
            return user_id_claim.asLong();
    }
}
