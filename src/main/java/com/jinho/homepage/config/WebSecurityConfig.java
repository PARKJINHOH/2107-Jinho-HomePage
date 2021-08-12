package com.jinho.homepage.config;

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

    //https://shinsunyoung.tistory.com/78

    private UserService userService;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/mybatis/**","/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .csrf().disable() // disable 하지 않으면 csrf를 수행하기 위한 파라미터가 없다고 Error 발생
                .authorizeRequests()
                    .antMatchers("/logout").authenticated() // 로그인 필요
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().permitAll()
                .and()
                    .formLogin()
                        .loginPage("/login")
                        .usernameParameter("email")
                        .defaultSuccessUrl("/")
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true) // 세션 제거
                    .deleteCookies("JSESSIONID", "SOME", "OTHER", "COOKIES");
    }

    /* 비밀번호 Encoder */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
