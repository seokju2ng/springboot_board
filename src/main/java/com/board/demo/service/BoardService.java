package com.board.demo.service;

import com.board.demo.vo.Boardlist;

import java.util.List;

public interface BoardService {
    List<Boardlist> getList(String category, int page, int size);

    boolean write(Long memberId, String title, String content, long category);
}
