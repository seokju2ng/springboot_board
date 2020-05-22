package com.board.demo;

import com.board.demo.repository.ReplyRepository;
import com.board.demo.vo.Reply;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
//    @Transactional
    public void create() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date today = new Date();
        long boardId = 16L;
        long parent = 0L;
        long writer = 22L;
        String date = sdf.format(today);
        String content = "형 지나가다 뒤통수 조심하세요..";

        Reply reply = Reply.builder()
                .parent(parent)
                .board(boardId)
                .writer(writer)
                .content(content)
                .date(date)
                .build();

        try {
            reply = replyRepository.save(reply);
            if (parent == 0) {
                reply.setParent(reply.getReplyId());
                reply = replyRepository.save(reply);
            }
            log.info("insert success: " + reply);
        } catch (Exception e) {
            log.warn(e.toString());
        }
    }

}
