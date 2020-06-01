package com.board.demo.service;

import com.board.demo.repository.BoardRepository;
import com.board.demo.repository.BoardlistRepository;
import com.board.demo.util.Conversion;
import com.board.demo.util.CurrentArticle;
import com.board.demo.vo.Board;
import com.board.demo.vo.Boardlist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService {
    private final String ALL_POSTS = "전체보기";
    private final String NOTICE = "공지";
    private final int PREV_OR_NEXT = 0;
    private final int PREV_ARTICLE = 0;
    private final int NEXT_ARTICLE = 1;

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardlistRepository boardlistRepository;


    @Override
    public Page<Boardlist> getList(String category, int page, int size) {
        Page<Boardlist> boardlistPage;
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
        return !Objects.isNull(boardRepository.save(board));
    }

    @Override
    public boolean modify(Board article, String title, String content, long category) {
        article.setTitle(title);
        article.setContent(content);
        article.setCategory(category);
        return !Objects.isNull(boardRepository.save(article));
    }

    @Override
    public void deleteArticle(long boardId) {
        try {
            boardRepository.deleteById(boardId);
        } catch (Exception e) {
            log.error("\n >> " + e.toString() + "\n >> There isn't a board no." + boardId);
        }
    }

    @Override
    public int getListByMemberId(long memberId, int page, int size, ModelAndView mav) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Boardlist> boardlistPage = boardlistRepository.findAllByWriterId(memberId, pageRequest);
        List<Boardlist> boards = boardlistPage.getContent();
        boards.forEach(Conversion::convertDateFormatForArticleList);
        Conversion.convertTitleLength(boards);
        int totalPages = boardlistPage.getTotalPages();
        mav.addObject("boards", boards);
        mav.addObject("totalPages", totalPages);
        return boards.size();
    }

    @Override
    public List<Boardlist> getNotices() {
        return boardlistRepository.findAllByCategory(NOTICE);
    }

    @Override
    public List<Boardlist> getTopLikes() {
        return boardlistRepository.findTopLikes();
    }

    @Override
    public void convertArticleFormat(List<Boardlist> articles) {
        articles.forEach(Conversion::convertDateFormatForArticleList);
        Conversion.convertTitleLength(articles);
    }

    @Override
    public Boardlist getPostByIdForViewArticle(long boardId) {
        Boardlist board = getPostById(boardId);
        Conversion.convertContent(board);
        Conversion.convertDateFormatForArticle(board);
        return board;
    }

    @Override
    public Boardlist getPostById(long boardId) {
        return boardlistRepository.findById(boardId).orElse(null);
    }

    @Override
    public Board getBoardById(long boardId) {
        return boardRepository.findById(boardId).orElse(null);
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

    @Override
    public CurrentArticle getPrevAndNextArticle(long boardId) {
        CurrentArticle currentArticle;
        List<Board> boards = boardRepository.findPrevAndNextBoardIdByBoardId(boardId);
        switch (boards.size()) {
            case 0:
                currentArticle = new CurrentArticle();
                break;
            case 1:
                long prevOrNext = boards.get(PREV_OR_NEXT).getBoardId();
                if (prevOrNext > boardId) {
                    currentArticle = CurrentArticle.builder()
                            .next(prevOrNext)
                            .build();
                    break;
                }
                currentArticle = CurrentArticle.builder()
                        .prev(prevOrNext)
                        .build();
                break;
            case 2:
                currentArticle = CurrentArticle.builder()
                        .prev(boards.get(PREV_ARTICLE).getBoardId())
                        .next(boards.get(NEXT_ARTICLE).getBoardId())
                        .build();
                break;
            default:
                currentArticle = null;
                break;
        }
        return currentArticle;
    }
}
