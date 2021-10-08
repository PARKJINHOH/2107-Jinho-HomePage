package com.jinho.homepage.config;

import com.jinho.homepage.dto.Role;
import com.jinho.homepage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/message/**","/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // ADMIN, USER
        http
//                .csrf().disable() // disable 하지 않으면 csrf를 수행하기 위한 파라미터가 없다고 Error 발생
                .authorizeRequests()
                    .antMatchers("/logout", "/forum/write").authenticated() // 로그인 필요
                    .antMatchers("/admin/**").hasRole(Role.ADMIN.name())
                    .anyRequest().permitAll()
                .and()
                    .formLogin()
                        .loginPage("/login").permitAll()
                        .usernameParameter("email")
                        .defaultSuccessUrl("/")
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true) // 세션 제거
                    .deleteCookies("JSESSIONID", "SOME", "OTHER", "COOKIES")
                .and()
                    .oauth2Login().loginPage("/login")
                    .userInfoEndpoint()
                    .userService(userService)
                ;

    }

    /* 비밀번호 Encoder */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
