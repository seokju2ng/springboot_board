package com.board.demo;

import com.board.demo.repository.BoardlistRepository;
import com.board.demo.vo.Boardlist;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardlistRepositoryTest {

    @Autowired
    private BoardlistRepository boardlistRepository;

    @Test
    public void readAll(){
        List<Boardlist> boards = boardlistRepository.findAll();
        for(Boardlist board : boards) {
            System.out.println(board);
        }
    }
}
