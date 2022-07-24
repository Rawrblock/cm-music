package com.lws.cmmusic.controller;

import com.lws.cmmusic.dto.UserCreateRequest;
import com.lws.cmmusic.dto.UserDto;
import com.lws.cmmusic.dto.UserUpdateRequest;
import com.lws.cmmusic.mapper.UserMapper;
import com.lws.cmmusic.service.UserService;
import com.lws.cmmusic.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/users")
@Api(tags = "用户")
public class UserController {

    // 注入 业务层调用
    UserService userService;

    // 注入 实体类转换
    UserMapper userMapper;

    // TODO: 2022/7/21 增
    // 用户新增接口
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    UserVo create(@Validated @RequestBody UserCreateRequest userCreateRequest) {
        return userMapper.toVo(userService.create(userCreateRequest));
    }

    // TODO: 2022/7/21 改
    // 修改用户
    @PutMapping("/{id}")
    UserVo update(
            @PathVariable String id,
            @Validated @RequestBody UserUpdateRequest userUpdateRequest) {
        return userMapper.toVo(userService.update(id, userUpdateRequest));
    }

    // TODO: 2022/7/21 删
    // 删除后台用户
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void delete(@PathVariable String id) {
        userService.delete(id);
    }


    // TODO: 2022/7/21 查
    // 分页检索查询
    @GetMapping
    @ApiOperation("用户检索")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Page<UserVo> search(@PageableDefault(sort = {"createdTime"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return userService.search(pageable).map(userMapper::toVo);
    }

    // 获取后台用户数据
    @GetMapping("/{id}")
    UserVo get(@PathVariable String id) {
        return userMapper.toVo(userService.get(id));
    }

    // 获取当前用户信息(通过权限后)
    @GetMapping("/me")
    UserVo me() {
        UserDto currentUser = userService.getCurrentUser();
        return userMapper.toVo(currentUser);
    }


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
