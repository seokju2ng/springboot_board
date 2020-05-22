package com.board.demo.service;

import com.board.demo.vo.Member;
import com.board.demo.vo.Replylist;

import java.util.List;

public interface ReplyService {
    List<Replylist> getRepliesByBoardId(long boardId);

    boolean writeReply(long boardId, long parent, String content, Member member);

    boolean deleteReply(long replyId, long parent, Member member);
}
