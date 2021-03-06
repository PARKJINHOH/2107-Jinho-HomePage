package com.jinho.homepage.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailToken {

    private static final long EMAIL_TOKEN_EXPIRATION_TIME_VALUE = 5L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String token;

    @Column
    private LocalDateTime expirationDate;

    @Column
    private boolean expired;

    @Column
    private String email;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    /**
     * 이메일 인증 토큰 생성
     */
    public static EmailToken createEmailConfirmationToken(String email, String token) {
        EmailToken emailToken = new EmailToken();
        emailToken.token = token;
        emailToken.createDate = LocalDateTime.now();
        emailToken.expirationDate = LocalDateTime.now().plusMinutes(EMAIL_TOKEN_EXPIRATION_TIME_VALUE); // 5분후 만료
        emailToken.lastModifiedDate = LocalDateTime.now();
        emailToken.email = email;
        emailToken.expired = false;
        return emailToken;
    }

    /**
     * 토근 사용시 만료
     */
    public void useToken() {
        expired = true;
    }
}
