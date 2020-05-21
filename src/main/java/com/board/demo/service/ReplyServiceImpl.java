package com.board.demo.service;

import com.board.demo.repository.ReplylistRepository;
import com.board.demo.util.Conversion;
import com.board.demo.vo.Replylist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    private ReplylistRepository replylistRepository;


    @Override
    public List<Replylist> getRepliesByBoardId(long boardId) {
        List<Replylist> replies = replylistRepository.findAllByBoardId(boardId);
        replies.stream()
               .peek(Conversion::convertContent)
               .forEach(Conversion::convertDateFormatForArticle);
        return replies;
    }
}
