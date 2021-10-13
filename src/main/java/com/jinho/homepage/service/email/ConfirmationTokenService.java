package com.jinho.homepage.service.email;

import com.jinho.homepage.entity.EmailToken;
import com.jinho.homepage.exception.BadRequestException;
import com.jinho.homepage.util.ValidationConstant;
import com.jinho.homepage.repository.EmailTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {

    private final EmailTokenRepository emailTokenRepository;
    private final EmailSenderService emailSenderService;

    public String createEmailConfirmationToken(String email){

        Assert.hasText(email,"receiver Email은 필수 입니다.");

        EmailToken emailConfirmationToken = EmailToken.createEmailConfirmationToken(email);
        emailTokenRepository.save(emailConfirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("회원가입 이메일 인증");
        // Todo : localhost 변경하기
        mailMessage.setText("http://localhost:8080/confirm-email?token="+emailConfirmationToken.getId());
        emailSenderService.sendEmail(mailMessage);

        return emailConfirmationToken.getId();
    }

    /**
     * 유효한 토큰 가져오기
     * @param confirmationTokenId
     * @return
     */
    public EmailToken findByIdAndExpirationDateAfterAndExpired(String confirmationTokenId) {
        Optional<EmailToken> confirmationToken = emailTokenRepository.findByIdAndExpirationDateAfterAndExpired(confirmationTokenId, LocalDateTime.now(),false);
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

}
