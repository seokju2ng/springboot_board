package com.board.demo.repository;

import com.board.demo.vo.Boardlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardlistRepository extends JpaRepository<Boardlist, Long>{
}
