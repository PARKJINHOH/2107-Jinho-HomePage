package com.jinho.homepage.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Table(name = "tbl_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity implements UserDetails {

    @Id
    @Column(name = "user_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 데이터베이스에 위임
    private Long userSeq;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "nickname")
    private String nickName;

    @Column(name = "password")
    private String password;

    @Column(name = "authority")
    private String authority;

    @OneToMany(mappedBy = "user") // mappedBy : 읽기 전용
    private List<BoardEntity> boards = new ArrayList<>();

    @Builder
    public UserEntity(String email, String nickName, String password, String authority) {
        this.email = email;
        this.nickName = nickName;
        this.password = password;
        this.authority = authority;
    }

    // 사용자 권한을 반환,
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (String role : authority.split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }

    // 사용자의 id를 반환 (unique한 값)
    @Override
    public String getUsername() {
        return email;
    }

    // 사용자의 password를 반환
    @Override
    public String getPassword() {
        return password;
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        // 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠금되었는지 확인하는 로직
        return true; // true -> 잠금되지 않았음
    }

    // 패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        return true; // true -> 사용 가능
    }
}
