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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // DB에 User(Email)이 없다면 UsernameNotFoundException 발생.
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }


    public Long userSave(UserDto userDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userDto.setPassword(encoder.encode(userDto.getPassword()));

        UserEntity user = UserEntity.builder()
                .email(userDto.getEmail())
                .nickName(userDto.getNickName())
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

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        UserEntity user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private UserEntity saveOrUpdate(OAuthAttributes attributes) {
        UserEntity user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getEmail()))
                .orElse(attributes.toEntity());
        user.emailVerifiedSuccess(); // true 변경

        return userRepository.save(user);
    }



    /**
     * 이메일 인증 로직
     * @param token
     */
    public void confirmEmail(String token) {
        EmailToken findConfirmationToken = confirmationTokenService.findByIdAndExpirationDateAfterAndExpired(token);
        UserEntity findUserInfo = userRepository.findByEmail(findConfirmationToken.getEmail()).get();
        findConfirmationToken.useToken();	// 토큰 만료 로직을 구현해주면 된다. ex) expired 값을 true로 변경
        findUserInfo.emailVerifiedSuccess();	// 유저의 이메일 인증 값 변경 로직을 구현해주면 된다. ex) emailVerified 값을 true로 변경
    }
}
