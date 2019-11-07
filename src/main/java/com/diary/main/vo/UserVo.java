package com.diary.main.vo;
import lombok.Data;
@Data
public class UserVo {
    private  String token;
    private  String username;
    public UserVo() {
    }
    public UserVo(String token,String username) {
        this.token = token;
        this.username=username;
    }
}
