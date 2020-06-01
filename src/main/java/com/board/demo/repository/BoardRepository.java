package com.board.demo.repository;


import com.board.demo.vo.Board;
import com.board.demo.vo.Boardlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query(value = "SELECT * FROM (SELECT * FROM board list WHERE board_id < :boardId ORDER BY board_id desc LIMIT 1) A UNION SELECT * FROM (SELECT * FROM board list WHERE board_id > :boardId ORDER BY board_id asc LIMIT 1) B", nativeQuery = true)
    List<Board> findPrevAndNextBoardIdByBoardId(@Param("boardId") long boardId);
}
