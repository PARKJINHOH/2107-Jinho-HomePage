package com.jinho.homepage;

import com.jinho.homepage.entity.BoardEntity;
import com.jinho.homepage.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    public void 게시글저장_불러오기() {
        // given
        BoardEntity buildBoard = BoardEntity.builder()
                .title("테스트 타이틀")
                .content("테스트 본문")
                .writer("test@naver.com")
                .build();
        boardRepository.save(buildBoard);

        // when
        BoardEntity board = boardRepository.findById(buildBoard.getBoardSeq()).get();

        // then
        assertThat(board.getTitle(), is("테스트 타이틀"));
        assertThat(board.getContent(), is("테스트 본문"));


    }

}
