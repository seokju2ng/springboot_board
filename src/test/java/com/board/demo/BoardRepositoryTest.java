package com.board.demo;

import com.board.demo.repository.BoardRepository;
import com.board.demo.vo.Board;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void create(){
        Board board = new Board();

        board.setCategory(2L);
        board.setTitle("따끈 따끈한 글이랍니다");
        board.setContent("내용무");
        board.setWriter(12L);

        Board newBoard = boardRepository.save(board);
        System.out.println(newBoard);

    }

    @Test
    public void read(){
        Optional<Board> board = boardRepository.findById(1L);
        board.ifPresent(selectBoard -> {
            System.out.println("board:"+selectBoard);
        });
    }

    @Test
    public void readAll(){
        List<Board> boards = boardRepository.findAll();
        for(Board board : boards) {
            System.out.println(board);
        }
    }
}
