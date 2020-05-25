package com.board.demo;

import com.board.demo.repository.MemberLikeBoardRepository;
import com.board.demo.vo.MemberLikeBoard;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberLikeBoardRepositoryTest {

    @Autowired
    private MemberLikeBoardRepository memberLikeBoardRepository;

    @Test
    public void create() {  // click to like
        long boardId = 11L;
        long memberId = 23L;

        MemberLikeBoard mlb = MemberLikeBoard.builder()
                .memberId(memberId)
                .boardId(boardId)
                .build();

        mlb = memberLikeBoardRepository.save(mlb);
        Assert.assertNotEquals(mlb.getId(), 0L);
    }

    @Test
    public void delete() {  // cancel to like
        long boardId = 11L;
        long memberId = 20L;

        Optional<MemberLikeBoard> mlb = memberLikeBoardRepository.findByBoardIdAndMemberId(boardId, memberId);
        mlb.ifPresent(memberLikeBoardRepository::delete);


        Assert.assertFalse(memberLikeBoardRepository.findByBoardIdAndMemberId(boardId, memberId).isPresent());
    }
}
