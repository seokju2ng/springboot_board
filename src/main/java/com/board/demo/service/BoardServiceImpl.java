package com.board.demo.service;

import com.board.demo.repository.BoardRepository;
import com.board.demo.repository.BoardlistRepository;
import com.board.demo.util.Conversion;
import com.board.demo.vo.Board;
import com.board.demo.vo.Boardlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BoardServiceImpl implements BoardService {
    private final String ALL_POSTS = "전체보기";
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardlistRepository boardlistRepository;


    @Override
    public List<Boardlist> getList(String category, int page, int size) {
        List<Boardlist> boardlist = null;
        PageRequest pageRequest = PageRequest.of(page, size);
        if (ALL_POSTS.equals(category)) {
            boardlist = boardlistRepository.findAll(pageRequest).getContent();
        } else {
            boardlist = boardlistRepository.findAllByCategory(category, pageRequest).getContent();
        }

        Conversion.convertDateFormat(boardlist);
        Conversion.convertTitleLength(boardlist);
        return boardlist;
    }

    @Override
    public boolean write(Long memberId, String title, String content, long category) {
        Board board = Board.builder()
                .writer(memberId)
                .title(title)
                .content(content)
                .category(category)
                .build();
        board = boardRepository.save(board);
        return !Objects.isNull(board);
    }
}
