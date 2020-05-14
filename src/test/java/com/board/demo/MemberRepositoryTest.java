package com.board.demo;

//import org.junit.jupiter.api.Test;

import com.board.demo.repository.MemberRepository;
import com.board.demo.util.HashFunction;
import com.board.demo.vo.Member;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void create() throws NoSuchAlgorithmException {
        Member member = new Member();
        member.setId("ehddnwnd");
        member.setEmail("ehddnwnd@naver.com");
        member.setPwd(HashFunction.sha256("ehdgksmf"));
        member.setNickname("김석중짱짱맨");
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

    @Test
    public void loginSuccess() {
        String id = "Eodqjf5015";
        String pwd = null;
        try {
            pwd = HashFunction.sha256("milk5239");
            Member member = memberRepository.findByIdAndPwd(id, pwd);
            System.out.println(member);
            if (member != null) {
                member.setAttendance(member.getAttendance() + 1);
                memberRepository.save(member);
            }
            Assert.assertNotNull(member);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loginFail(){
        String id = "Eodqjf5015";
        String pwd = null;
        try {
            pwd = HashFunction.sha256("milk5238");
            Member member = memberRepository.findByIdAndPwd(id, pwd);
            System.out.println(member);
            Assert.assertNull(member);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
