package com.board.demo.repository;

import com.board.demo.vo.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long>{
    List<Reply> findAllByBoard(long board_id);

    void deleteByReplyIdAndParentAndWriter(long replyId, long parent, long memberId);

    Optional<Reply> findByReplyIdAndParentAndWriter(long replyId, long parent, long memberId);

    List<Reply> findAllByParent(long parent);
}
