package com.board.demo.service;

import com.board.demo.vo.Boardlist;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface BoardService {
    Page<Boardlist> getList(String category, int page, int size);

    boolean write(Long memberId, String title, String content, long category);

    Boardlist getPostById(long boardId);

    boolean addViews(long boardId);
}
