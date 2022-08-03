package com.lws.cmmusic.service.Impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.lws.cmmusic.config.SecurityConfig;
import com.lws.cmmusic.dto.TokenCreateRequest;
import com.lws.cmmusic.dto.UserCreateRequest;
import com.lws.cmmusic.dto.UserDto;
import com.lws.cmmusic.dto.UserUpdateRequest;
import com.lws.cmmusic.entity.User;
import com.lws.cmmusic.exception.BizException;
import com.lws.cmmusic.exception.ExceptionType;
import com.lws.cmmusic.mapper.UserMapper;
import com.lws.cmmusic.repository.UserRepository;
import com.lws.cmmusic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    // 注入
    UserMapper userMapper;

    // 注入
    UserRepository userRepository;

    // 注入 密码加密
    PasswordEncoder passwordEncoder;

    // TODO: 2022/7/21 查
    // 根据用户名查询数据
    @Override
    public User loadUserByUsername(String userName) {
        Optional<User> user = userRepository.findByUsername(userName);
        if (!user.isPresent()) {
            throw new BizException(ExceptionType.USER_NOT_FOUND);
        }
        return user.get();
    }

    // 通过ID查询信息
    @Override
    public UserDto get(String id) {
        return userMapper.toDto(getById(id));
    }

    // 分页
    @Override
    public Page<UserDto> search(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toDto);
    }

    // 校验成功后获取当前用户信息
    @Override
    public UserDto getCurrentUser() {
        // 通过 Security对象上下文获取出用户信息对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = loadUserByUsername(authentication.getName());
        return userMapper.toDto(currentUser);
    }


    // TODO: 2022/7/21 增 
    // 新增用户
    @Override
    public UserDto create(UserCreateRequest userCreateRequest) {
        checkUserName(userCreateRequest.getUsername());
        User user = userMapper.createEntity(userCreateRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public String createToken(TokenCreateRequest tokenCreateRequest) {
        User user = loadUserByUsername(tokenCreateRequest.getUsername());
        if (!passwordEncoder.matches(tokenCreateRequest.getPassword(), user.getPassword())) {
            throw new BizException(ExceptionType.USER_PASSWORD_NOT_MATCH);
        }
        if (!user.isEnabled()) {
            throw new BizException(ExceptionType.USER_NOT_ENABLED);
        }
        if (!user.isAccountNonLocked()) {
            throw new BizException(ExceptionType.USER_LOCKED);
        }

        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConfig.SECRET.getBytes()));
    }


    // ------------改
    // 修改用户
    @Override
    public UserDto update(String id, UserUpdateRequest userUpdateRequest) {
        return userMapper.toDto(userRepository.save(userMapper.updateEntity(getById(id), userUpdateRequest)));
    }


    // ------------删
    @Override
    public void delete(String id) {
        userRepository.delete(getById(id));
    }


    // TODO: 2022/7/21 封装公用方法
    // 判断用户名是否重复
    public void checkUserName(String userName) {
        Optional<User> username = userRepository.findByUsername(userName);
        if (username.isPresent()) {
            throw new BizException(ExceptionType.USER_NAME_DUPLICATE);
        }
    }

    // 通过ID获取用户后台信息(判断是否存在)
    public User getById(String id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new BizException(ExceptionType.USER_NOT_FOUND);
        }
        return user.get();
    }


    // 注入
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 注入
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // 注入
    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
