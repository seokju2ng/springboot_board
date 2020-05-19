package com.board.demo;

import com.board.demo.repository.BoardRepository;
import com.board.demo.repository.BoardlistRepository;
import com.board.demo.vo.Board;
import com.board.demo.vo.Boardlist;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardlistRepository boardlistRepository;

    @Test
    @Transactional
    public void create() {
        String title = "이건 제목입니다"; // 최대 60자
        String content = "이건 내용입니다";
        long category = 2;
        long writer = 22;
        Board board = Board.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .category(category)
                .build();

        try {
            Board newBoard = boardRepository.save(board);
            log.info("insert success : " + newBoard);
        } catch (Exception e) {
            log.warn(e.toString());
        }
    }

    @Test
    public void read() {
        Optional<Board> board = boardRepository.findById(1L);
        board.ifPresent(selectBoard -> {
            System.out.println("board:" + selectBoard);
        });
    }

    @Test
    public void readAll() {
        List<Boardlist> boards = boardlistRepository.findAll();
        boards.forEach(board -> log.info(board.toString()));
    }

    @Test
    public void readPage() {
        String category = "잡담";
        int page = 0;
        int size = 3;
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Boardlist> boards = boardlistRepository.findAllByCategory(category, pageRequest).getContent();
        boards.forEach(board -> log.info(board.toString()));
    }
}
