package com.board.demo;

import com.board.demo.repository.ReplyRepository;
import com.board.demo.vo.Reply;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import javax.validation.constraints.AssertFalse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Test
    @Transactional
    public void deleteFalse () {
        long replyId = 27L;
        long parent = 25L;
        long memberId = 2L;
        Optional<Reply> opReply = replyRepository.findByReplyIdAndParentAndWriter(replyId, parent, memberId);
        Assert.assertFalse(opReply.isPresent());
    }

    @Test
    @Transactional
    public void deleteParentTrue () { // 기본 댓글일 때
        long replyId = 16L;
        long parent = 16L;
        long memberId = 22L;
        Optional<Reply> opReply = replyRepository.findByReplyIdAndParentAndWriter(replyId, parent, memberId);
        if (!opReply.isPresent()) {
            return;
        }
        if (parent == replyId) {
            List<Reply> lists = replyRepository.findAllByParent(parent);
            if (lists.size() == 1) {
                replyRepository.deleteById(replyId);
                opReply = replyRepository.findById(replyId);
                Assert.assertFalse(opReply.isPresent());
            } else {
                Reply reply = opReply.get();
                reply.setContent("NULL");
                replyRepository.save(reply);
                opReply = replyRepository.findById(replyId);
                Assert.assertEquals(opReply.get().getContent(), "NULL");
                log.info(opReply.get().getContent());
            }
        }
    }


}
