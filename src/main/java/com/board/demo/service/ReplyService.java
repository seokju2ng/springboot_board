package com.board.demo.service;

import com.board.demo.vo.Replylist;

import java.util.List;

public interface ReplyService {
    List<Replylist> getRepliesByBoardId(long boardId);
}
