package com.board.demo.repository;

import com.board.demo.vo.Boardlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardlistRepository extends JpaRepository<Boardlist, Long>{
    Page<Boardlist> findAllByCategory(String category, Pageable pageRequest);
    Page<Boardlist> findAllByWriterId(long writerId, Pageable pageRequest);
    List<Boardlist> findAllByCategory(String category);
    List<Boardlist> findTop5ByOrderByLikesDesc();
}
