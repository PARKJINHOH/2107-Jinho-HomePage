package com.jinho.homepage.service.email;

import com.jinho.homepage.entity.EmailToken;
import com.jinho.homepage.exception.BadRequestException;
import com.jinho.homepage.repository.EmailTokenRepository;
import com.jinho.homepage.util.ValidationConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {

    private final EmailTokenRepository emailTokenRepository;
    private final EmailSenderService emailSenderService;

    public String createEmailConfirmationToken(String email) {

        Assert.hasText(email, "receiver Email은 필수 입니다.");

        String token = this.generateAuthToken(); // Token 생성
        EmailToken emailConfirmationToken = EmailToken.createEmailConfirmationToken(email, token);
        emailTokenRepository.save(emailConfirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("회원가입 이메일 인증");
        // Todo : localhost 변경하기
        mailMessage.setText("http://localhost:8080/confirm-email?token=" + token);
        emailSenderService.sendEmail(mailMessage);

        return emailConfirmationToken.getId();
    }

    /**
     * 유효한 토큰 가져오기
     *
     * @param token
     * @return
     */
    public EmailToken findByTokenAndExpirationDateAfterAndExpired(String token) {
        Optional<EmailToken> confirmationToken = emailTokenRepository.findByTokenAndExpirationDateAfterAndExpired(token, LocalDateTime.now(), false);
        EmailToken emailToken = null;
        try {
            emailToken = confirmationToken.orElseThrow(() -> new BadRequestException(ValidationConstant.TOKEN_NOT_FOUND));
        } catch (BadRequestException e) {
            e.printStackTrace();
        }
        return emailToken;
    }

    /**
     * 인증된 이메일 변경
     */
    public void updateToken(EmailToken authenticationEmail) {
        emailTokenRepository.save(authenticationEmail);
    }

    /**
     * 인증 토큰 생성 메소드
     */
    protected String generateAuthToken() {
        int SIZE = 12;
        char[] charSet = new char[]{
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
        };

        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());

        int idx = 0;
        int len = charSet.length;
        for (int i = 0; i < SIZE; i++) {
            idx = sr.nextInt(len);
            sb.append(charSet[idx]);
        }

        return sb.toString();
    }

}
