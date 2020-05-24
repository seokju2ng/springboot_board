package com.board.demo.service;

import com.board.demo.util.CurrentArticle;
import com.board.demo.vo.Board;
import com.board.demo.vo.Boardlist;
import org.springframework.data.domain.Page;

public interface BoardService {
    Page<Boardlist> getList(String category, int page, int size);

    boolean write(Long memberId, String title, String content, long category);

    Boardlist getPostByIdForViewArticle(long boardId);

    Boardlist getPostById(long boardId);

    boolean addViews(long boardId);

    CurrentArticle getPrevAndNextArticle(long boardId);

    Board getBoardById(long boardId);

    boolean modify(Board article, String title, String content, long category);
}
