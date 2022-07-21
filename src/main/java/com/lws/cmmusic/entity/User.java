package com.lws.cmmusic.entity;

import com.lws.cmmusic.enums.Gender;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class User extends AbstractEntity implements UserDetails {

    @Column(unique = true)
    private String username; // 用户名

    private String nickname; // 昵称

    private String password; // 密码

    // 给当前设置的实体操作另一个实体的权限
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles; // 权限

    // 在new实例的时候，就会因映射为字符串类型
    @Enumerated(EnumType.STRING)
    private Gender gender; // 性别

    private Boolean locked = false; // 该账号是否被锁定

    private Boolean enabled = true; // 该账号是否启动

    private String lastLoginIp; // 最后登录IP

    private Date lastLoginTime; // 最后登录时间




    // UserDetails提供的方法
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    // 用户是否过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 用户是否被锁定
    @Override
    public boolean isAccountNonLocked() {
        return !getLocked();
    }

    // 凭证是否过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 账号是否启用
    @Override
    public boolean isEnabled() {
        return getEnabled();
    }
}
