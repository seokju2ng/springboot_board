package com.board.demo.service;

import com.board.demo.repository.ReplyRepository;
import com.board.demo.repository.ReplylistRepository;
import com.board.demo.util.Conversion;
import com.board.demo.vo.Member;
import com.board.demo.vo.Reply;
import com.board.demo.vo.Replylist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ReplyServiceImpl implements ReplyService {
    private final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    @Autowired
    private ReplylistRepository replylistRepository;

    @Autowired
    private ReplyRepository replyRepository;


    @Override
    public List<Replylist> getRepliesByBoardId(long boardId) {
        List<Replylist> replies = replylistRepository.findAllByBoardId(boardId);
        replies.stream()
               .peek(Conversion::convertContent)
               .forEach(Conversion::convertDateFormatForArticle);
        return replies;
    }

    @Override
    public boolean writeReply(long boardId, long parent, String content, Member member) {
        if (Objects.isNull(member)) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String date = sdf.format(new Date());

        Reply reply = Reply.builder()
                .parent(parent)
                .board(boardId)
                .writer(member.getMemberId())
                .content(content)
                .date(date)
                .build();

        reply = replyRepository.save(reply);
        if (parent == 0) {
            reply.setParent(reply.getReplyId());
            reply = replyRepository.save(reply);
        }
        return true;
    }
}
