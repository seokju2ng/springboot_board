package com.board.demo.repository;

import com.board.demo.vo.Mypage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MypageRepository extends JpaRepository<Mypage, Long> {
}
