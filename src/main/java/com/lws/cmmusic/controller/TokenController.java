package com.lws.cmmusic.controller;

import com.lws.cmmusic.dto.TokenCreateRequest;
import com.lws.cmmusic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tokens")
public class TokenController {

    // 注入
    UserService userService;

    // 获取token
    @PostMapping
    public String create(@RequestBody TokenCreateRequest tokenCreateRequest) {
        return userService.createToken(tokenCreateRequest);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
