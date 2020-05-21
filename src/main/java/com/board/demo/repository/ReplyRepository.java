package com.board.demo.repository;

import com.board.demo.vo.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long>{
    List<Reply> findAllByBoard(long board_id);
}
