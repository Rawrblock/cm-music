package com.lws.cmmusic.service;

import com.lws.cmmusic.dto.TokenCreateRequest;
import com.lws.cmmusic.dto.UserCreateRequest;
import com.lws.cmmusic.dto.UserDto;
import com.lws.cmmusic.dto.UserUpdateRequest;
import com.lws.cmmusic.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    //----- 查 -----//
    // Security内置的身份验证类方法 （通过用户名查看该用户是否存在）
    @Override
    User loadUserByUsername(String userName);

    // id查询用户信息
    UserDto get(String id);

    // 分页检索查询
    Page<UserDto> search(Pageable pageable);

    // 获取当前登录用户信息
    UserDto getCurrentUser();

    //----- 增 -----//
    // 新增用户
    UserDto create(UserCreateRequest userCreateRequest);
    // 创建token
    String createToken(TokenCreateRequest tokenCreateRequest);

    //----- 改 -----//
    // 通过id修改用户信息
    UserDto update(String id, UserUpdateRequest userUpdateRequest);


    //----- 删 -----//
    void delete(String id);


}
