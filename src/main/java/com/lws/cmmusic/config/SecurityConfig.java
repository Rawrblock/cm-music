package com.lws.cmmusic.config;

import com.lws.cmmusic.exception.RestAuthenticationEntryPoint;
import com.lws.cmmusic.filter.JwtAuthorizationFilter;
import com.lws.cmmusic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

// 自定义 Security配置类
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String SECRET = "CmMusic";
    public static final long EXPIRATION_TIME = 864000000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String CREATE_TOKEN_URL = "/tokens";
    public static final String SITE_SETTING_URL = "/settings/site";

    // 自定义 身份验证类
    UserService userService;

    // 自定义配置的身份验证方案（使用构造的方法注入）
    RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    /**
     * 身份认证接口
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * cors() : 配置跨域资源共享（ CORS ）
         * csrf() : 添加CSRF支持,默认开启
         * authorizeRequests() : 只有 通过了验证可以访问所有请求
         * antMatchers() : 指定路径(Url)
         * permitAll() : 设置可以不用走验证流程
         * anyRequest() : 匹配任意url
         * authenticated() : 对所有签名成功的用户允许方法
         * exceptionHandling : 允许配置错误处理
         */
        http.cors().and().csrf().disable() // CSRF禁用，因为不使用session
                .authorizeRequests() // 限定通过签名的请求
                .antMatchers(CREATE_TOKEN_URL).permitAll() // 设置该请求可以直接访问
                .antMatchers(SITE_SETTING_URL).permitAll()
//                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .anyRequest().authenticated() // 表示除了上面定义的URL模式之外，用户访问其他URL都必须认证后访问(登录后访问)
                .and()
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userService)) // 添加权限过滤器
                .exceptionHandling() // 允许配置错误处理
                .authenticationEntryPoint(restAuthenticationEntryPoint) // 开始身份验证过程
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //调整为让 Spring Security 不创建和使用 session

    }

    // 配置定义 处理身份认证的类  认证管理器
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    // 配置白名单(Swagger3.0)
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/swagger**/**")
                .antMatchers("/webjars/**")
                .antMatchers("/v3/**")
                .antMatchers("/doc.html")
                .antMatchers("/wechat/**");
    }


    // 注入自定义身份验证方案
    @Autowired
    public void setRestAuthenticationEntryPoint(RestAuthenticationEntryPoint restAuthenticationEntryPoint) {
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
    }

    // 注入自定义身份验证类
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
