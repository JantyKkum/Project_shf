package com.janty.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/17 10:58
 * @description:
 */

@Configuration                  //声明当前是配置类，也要被扫描
@EnableWebSecurity              //开启自动配置
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    //设置用户名和密码
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("admin")
//                .password(new BCryptPasswordEncoder().encode("123456"))
//                .roles("");
//
//    }

    //创建加密器到IOC
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //重写父类，允许iframe嵌套显示
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);                            //必须调用父类方法，否则认证失效,若配置了认证和授权方法则不需父类
        http.headers().frameOptions().sameOrigin();       //配置允许iframe嵌套显示
        http.authorizeRequests().antMatchers("/static/**", "/login").permitAll().anyRequest().authenticated();
        http.formLogin().loginPage("/login")                //配置自定义访问路径
                .defaultSuccessUrl("/");                    //配置登录成功时前往页面
        http.logout().logoutUrl("/logout")                  //配置登出
                .logoutSuccessUrl("/login");
        http.csrf().disable();                              //关闭跨越请求伪造功能
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());
    }
}

