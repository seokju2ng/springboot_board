package com.board.demo.repository;

import com.board.demo.vo.Replylist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplylistRepository extends JpaRepository<Replylist, Long> {
    List<Replylist> findAllByBoardId(long board_id);

    Page<Replylist> findAllByMemberId(long memberId, Pageable pageRequest);
}
