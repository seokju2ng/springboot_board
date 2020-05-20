package com.board.demo.service;

import com.board.demo.vo.Boardlist;
import org.springframework.data.domain.Page;

public interface BoardService {
    Page<Boardlist> getList(String category, int page, int size);

    boolean write(Long memberId, String title, String content, long category);
}
