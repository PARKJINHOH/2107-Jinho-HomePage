package com.jinho.homepage;

import com.jinho.homepage.dto.Role;
import com.jinho.homepage.entity.UserEntity;
import com.jinho.homepage.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserEntityRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
//    @Rollback(false)
    public void insertUser() throws Exception{
        // given
        UserEntity user = UserEntity.builder()
                .email("test@naver.com")
                .password("1234")
                .nickName("test")
                .role(Role.USER)
                .build();

        // when
        UserEntity findUser = userRepository.save(user);

        //then
        assertThat(findUser.getUserSeq()).isEqualTo(user.getUserSeq());
        assertThat(findUser.getEmail()).isEqualTo(user.getEmail());

        // 트랜잭션 범위내에서 생성과 찾기를 동시에 하기 떄문에 영속성 컨텍스트가 동일함으로 findUser와 user은 동일하다.
        assertThat(findUser).isEqualTo(user);
    }

    @Test
    @Transactional
    public void userOverlap() throws Exception {
        // given
        UserEntity user1 = UserEntity.builder()
                .email("user1@nvaer.com")
                .password("1234")
                .nickName("test")
                .role(Role.USER)
                .build();

        UserEntity user2 = UserEntity.builder()
                .email("user2@nvaer.com")
                .password("1234")
                .nickName("test")
                .role(Role.USER)
                .build();

        UserEntity user3 = UserEntity.builder()
                .email("user2@nvaer.com")
                .password("1234")
                .nickName("test")
                .role(Role.USER)
                .build();


        userRepository.save(user1);
        userRepository.save(user2);
//        userRepository.save(user3);

        // when
        boolean findUser1 = userRepository.existsByEmail(userRepository.findByEmail(user1.getEmail()).get().getEmail());
        boolean findUser2 = userRepository.existsByEmail(userRepository.findByEmail(user2.getEmail()).get().getEmail());
        boolean findUser3 = userRepository.existsByEmail(userRepository.findByEmail(user3.getEmail()).get().getEmail());

        // then
        assertThat(findUser1 == false); // false : 이메일 중복 X
        assertThat(findUser2 == false);
//        assertThat(findUser3 == true);  // true : 이메일 중복 O
    }


}
