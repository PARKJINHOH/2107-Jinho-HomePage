package com.jinho.homepage.service;

import com.jinho.homepage.dto.Role;
import com.jinho.homepage.dto.SessionUser;
import com.jinho.homepage.dto.UserDto;
import com.jinho.homepage.entity.EmailToken;
import com.jinho.homepage.entity.OAuthAttributes;
import com.jinho.homepage.entity.UserEntity;
import com.jinho.homepage.repository.UserRepository;
import com.jinho.homepage.service.email.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService, OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;
    private final ConfirmationTokenService confirmationTokenService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 로그인
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // DB에 User(Email)이 없다면 UsernameNotFoundException 발생.
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));

        log.info("Login User : {}, Certification : {}", userEntity.getEmail(), userEntity.isEnabled());

        return userEntity;
    }

    /**
     * 회원가입
     * @param userDto
     * @return
     */
    public Long userSave(UserDto userDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        confirmationTokenService.createEmailConfirmationToken(userDto.getEmail());

        UserEntity user = UserEntity.builder()
                .email(userDto.getEmail())
                .nickname(userDto.getNickname())
                .role(Role.USER)
                .password(userDto.getPassword()).build();

        return userRepository.save(user).getUserSeq(); // 회원을 저장하고 저장한 회원의 id를 return.
    }

    /**
     * OAuth2 관련 설정
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 서비스 구분 (네이버 로그인인지, 구글 로그인 인지)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // OAuth2 로그인 진행시 키가 되는 필드값(Google : "sub") PK와 같은 값(네이버, 카카오 지원x)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // 가져온 Data
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        UserEntity user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    /**
     * OAuth2 회원가입
     * @param attributes
     * @return
     */
    private UserEntity saveOrUpdate(OAuthAttributes attributes) {
        UserEntity user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getEmail()))
                .orElse(attributes.toEntity());
        user.emailVerifiedSuccess(); // OAuth2로 회원가입시 true

        return userRepository.save(user);
    }



    /**
     * 이메일 인증 로직
     * @param token
     */
    public void confirmEmail(String token) {
        EmailToken findConfirmationToken = confirmationTokenService.findByTokenAndExpirationDateAfterAndExpired(token);
        UserEntity findUserInfo = userRepository.findByEmail(findConfirmationToken.getEmail()).get();

        log.info("confirmEmail| Email : {}", findUserInfo.getEmail());

        findConfirmationToken.useToken();	// 토큰 만료
        confirmationTokenService.updateToken(findConfirmationToken);

        findUserInfo.emailVerifiedSuccess();// 유저의 이메일 인증
        userRepository.save(findUserInfo);

    }

    /**
     * 아이디 중복 확인
     * @param userEmail
     */
    public boolean userEmailCheck(String userEmail) {
        return userRepository.existsByEmail(userEmail);
    }
}
