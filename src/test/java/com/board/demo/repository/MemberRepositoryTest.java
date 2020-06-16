package com.board.demo.repository;

import com.board.demo.util.HashFunction;
import com.board.demo.vo.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    public void create() throws NoSuchAlgorithmException {
        String id = "user";
        String pwd = "password";
        String email = "email@email.com";
        String nick = "nickname";

        Member member = Member.builder()
                .id(id)
                .pwd(HashFunction.sha256(pwd))
                .email(email)
                .nickname(nick)
                .build();

        try {
            Member newMember = memberRepository.save(member);
            log.info("New Member[" + newMember.getMemberId() + " : " + id + "] has just signed up.");
        } catch (Exception e) {
            log.warn(e.toString());
        }
    }

    @Test
    public void read() {
        long memberId = 1L;
        String title = "select member(" + memberId + ") : ";
        Optional<Member> member = memberRepository.findById(memberId);
        member.ifPresent(selectMember -> log.info(title + selectMember));
    }

    @Test
    @Transactional
    public void update() {
        Optional<Member> member = memberRepository.findById(1L);

        member.ifPresent(selectMember -> {
            String id = "update_id";
            String nick = "update_nick";
            selectMember.setId(id);
            selectMember.setNickname(nick);
            Member newMember = memberRepository.save(selectMember);
            log.info("update user : " + newMember);
        });
    }

    @Test
    @Transactional
    public void delete() {
        long id = 1L;

        Optional<Member> member = memberRepository.findById(id);

        Assert.assertTrue(member.isPresent());
        member.ifPresent(memberRepository::delete);

        Optional<Member> deleteMember = memberRepository.findById(id);
        Assert.assertFalse(deleteMember.isPresent());
    }

    @Test
    public void login() throws NoSuchAlgorithmException {
        String id = "gyetol";
        String pwd = "gyetol2";
        Optional<Member> loginMember = memberRepository.findByIdAndPwd(id, HashFunction.sha256(pwd));

        if (loginMember.isPresent()) {
            Member member = loginMember.get();
            log.info("Successfully logged in");
            log.info("Attendance before update : " + member.getAttendance());
            member.setAttendance(member.getAttendance() + 1);
            member = memberRepository.save(member);
            log.info("Attendance after update : " + member.getAttendance());
        } else {
            log.warn("Login failed");
        }
    }

    @Test
    public void checkDuplicate() {
        String id = "ehddnwnd2";

        Optional<Member> member = memberRepository.findById(id);

        if (member.isPresent()) {
            log.info("[" + id + "] is duplicate ");
        } else {
            log.info("[" + id + "] is available");
        }
    }
}
