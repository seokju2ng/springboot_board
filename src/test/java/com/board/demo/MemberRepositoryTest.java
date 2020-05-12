package com.board.demo;

//import org.junit.jupiter.api.Test;

import com.board.demo.repository.MemberRepository;
import com.board.demo.vo.Member;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void create() {
        Member member = new Member();
        member.setId("gyetol23");
        member.setEmail("gyetol@naver.com");
        member.setPwd("gyetol");
        member.setNickname("계토리");
        Member newMember = memberRepository.save(member);
        System.out.println(newMember);
    }

    @Test
    public void read() {
        Optional<Member> member = memberRepository.findById(0L);
        member.ifPresent(selectMember -> {
            System.out.println("member: " + selectMember);
        });
    }

    @Test
//    @Transactional
    public void update() {
        Optional<Member> member = memberRepository.findById(9L);

        member.ifPresent(selectMember -> {
            selectMember.setId("tororo");
            selectMember.setNickname("토로로");
            Member newMember = memberRepository.save(selectMember);
            System.out.println("user : " + newMember);
        });
    }

    @Test
    @Transactional
    public void delete() {
        Optional<Member> member = memberRepository.findById(7L);

        Assert.assertTrue(member.isPresent());
        member.ifPresent(selectMember -> {
            memberRepository.delete(selectMember);
        });

        Optional<Member> deleteMember = memberRepository.findById(7L);
        Assert.assertFalse(deleteMember.isPresent());
    }
}
