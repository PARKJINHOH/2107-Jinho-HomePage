package com.jinho.homepage.entity;

import com.jinho.homepage.dto.Role;
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
    private String nickname;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "EmailCertification")
    private boolean emailCertification; // Default = False

    @OneToMany(mappedBy = "user") // mappedBy : 읽기 전용
    private List<BoardEntity> boards = new ArrayList<>();

    @Builder
    public UserEntity(String email, String nickname, String password, Role role) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
        this.emailCertification = false;
    }

    // 사용자 권한을 반환,
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        auth.add(new SimpleGrantedAuthority(role.getKey()));
        return auth;
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
        return emailCertification;
    }

    public UserEntity update(String email) {
        this.email = email;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    /**
     * Email 인증 버튼 클릭 Event
     */
    public void emailVerifiedSuccess() {
        this.emailCertification = true;
    }
}
