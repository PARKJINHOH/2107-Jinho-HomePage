package com.jinho.homepage.service;

import com.jinho.homepage.dto.UserDto;
import com.jinho.homepage.entity.UserEntity;
import com.jinho.homepage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

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
                .authority("USER")
                .password(userDto.getPassword()).build();

        return userRepository.save(user).getUserSeq(); // 회원을 저장하고 저장한 회원의 id를 return.
    }
}
