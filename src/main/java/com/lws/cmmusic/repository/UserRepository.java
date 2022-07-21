package com.lws.cmmusic.repository;

import com.lws.cmmusic.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JPA操作数据 JpaRepository提供功能
public interface UserRepository extends JpaRepository<User, String> {

    // 通过用户名查询用户信息
    Optional<User> findByUsername(String username);

    // 通过用户ID获取用户信息
    User getById(String id);

    // 分页检索
    Page<User> findAll(Pageable pageable);

}
