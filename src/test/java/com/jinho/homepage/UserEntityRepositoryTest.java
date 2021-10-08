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
    public void testUser() throws Exception{
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


}
