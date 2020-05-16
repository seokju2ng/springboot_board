package com.board.demo.repository;

import com.board.demo.vo.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>{
    Member findByIdAndPwd(String id, String pwd);
    Member findById(String id);
}
