package com.board.demo.repository;

import com.board.demo.vo.Replylist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplylistRepository extends JpaRepository<Replylist, Long> {
    List<Replylist> findAllByBoardId(long board_id);
}
