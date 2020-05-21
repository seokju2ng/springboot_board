package com.board.demo.service;

import com.board.demo.repository.BoardRepository;
import com.board.demo.repository.BoardlistRepository;
import com.board.demo.util.Conversion;
import com.board.demo.vo.Board;
import com.board.demo.vo.Boardlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService {
    private final String ALL_POSTS = "전체보기";
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardlistRepository boardlistRepository;


    @Override
    public Page<Boardlist> getList(String category, int page, int size) {
        Page<Boardlist> boardlistPage = null;
        PageRequest pageRequest = PageRequest.of(page, size);
        if (ALL_POSTS.equals(category)) {
            boardlistPage = boardlistRepository.findAll(pageRequest);
        } else {
            boardlistPage = boardlistRepository.findAllByCategory(category, pageRequest);
        }
        return boardlistPage;
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

    @Override
    public Boardlist getPostById(long boardId) {
        Optional<Boardlist> opBoard = boardlistRepository.findById(boardId);
        if (!opBoard.isPresent()) {
            return null;
        }
        Boardlist board = opBoard.get();
        Conversion.convertContent(board);
        Conversion.convertDateFormatForArticle(board);
        return board;
    }

    @Override
    public boolean addViews(long boardId) {
        Optional<Board> opBoard = boardRepository.findById(boardId);
        if (!opBoard.isPresent()) {
            return false;
        }
        Board board = opBoard.get();
        long views = board.getViews() + 1;
        board.setViews(views);
        board = boardRepository.save(board);

        return board.getViews() == views;
    }
}
