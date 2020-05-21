package com.board.demo;

import com.board.demo.repository.BoardRepository;
import com.board.demo.repository.BoardlistRepository;
import com.board.demo.repository.ReplylistRepository;
import com.board.demo.util.CurrentArticle;
import com.board.demo.vo.Board;
import com.board.demo.vo.Boardlist;
import com.board.demo.vo.Replylist;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
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

    @Autowired
    private ReplylistRepository replylistRepository;

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

    @Test
    public void viewArticle() {
        long board_id = 6L;
        Optional<Boardlist> board = boardlistRepository.findById(board_id);
        if (board.isPresent()) {
            log.info(board.get().toString());
            List<Replylist> replies = replylistRepository.findAllByBoardId(board_id);
            replies.forEach(reply -> log.info(reply.toString()));
        } else {
            log.info("Board id = '" + board_id + "' is not exist.");
        }
    }

    @Test
    public void selectPrevAndNextArticle() {
        long board_id = 7L;
        List<Board> boards = boardRepository.findPrevAndNextBoardIdByBoardId(board_id);
        Assert.assertNotEquals(boards.size(), 0);
//        log.info(boards.toString());
        if (boards.size() == 1) {
            log.info("?? : " + boards.get(0).getBoardId());
        } else {
            long prevArticle = boards.get(0).getBoardId();
            long nextArticle = boards.get(1).getBoardId();
            log.info("prev Article No : " + prevArticle);
            log.info("next Article No : " + nextArticle);
        }
    }
}
