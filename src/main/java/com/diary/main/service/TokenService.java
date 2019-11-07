package com.diary.main.service;

import com.auth0.jwt.interfaces.Claim;
import com.diary.main.model.User;

import java.util.Map;

public interface TokenService {

     String getToken(User user);

     String createJWT(User user);

     Map<String, Claim> verifyToken(String token);

     Long getAppUID(String token);


}
