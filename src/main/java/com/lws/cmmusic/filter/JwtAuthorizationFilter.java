package com.lws.cmmusic.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.lws.cmmusic.config.SecurityConfig;
import com.lws.cmmusic.entity.User;
import com.lws.cmmusic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 自定义权限过滤器
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserService userService) {
        super(authenticationManager);
        this.userService = userService;
    }

    UserService userService;

    // token登录
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 1. 获取出请求头中的token
        String header = request.getHeader(SecurityConfig.HEADER_STRING);
        // 2. 判断是否存在token && 是否以指定的前缀
        if(header == null || !header.startsWith(SecurityConfig.TOKEN_PREFIX)) {
            // 如果没有token 就直接跳转到下一个filter
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(header);
        // 获取当前登录的用户信息
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    // 验证JWT token 根据header构建新的Authenticatio
    private UsernamePasswordAuthenticationToken getAuthentication(String header) {
        if(header != null) {
            // 解构出token中的用户名信息
            String username = JWT.require(Algorithm.HMAC512(SecurityConfig.SECRET.getBytes()))
                    .build()
                    .verify(header.replace(SecurityConfig.TOKEN_PREFIX, ""))
                    .getSubject();
            if (username != null) {
                // 通过用户名获取用户信息
                User user = userService.loadUserByUsername(username);
                // 获取设置好的权限
                return new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
            }
        }
        return null;
    }
}
