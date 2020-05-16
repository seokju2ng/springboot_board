package com.board.demo.controller;

import com.board.demo.repository.MemberRepository;
import com.board.demo.util.HashFunction;
import com.board.demo.vo.Member;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {
    private final int DUPLICATE_ID = -3;
    private final int INVALID_APPROACH = -2;
    private final int FAIL = -1;
    private final int ADMIN = 0;
    private final int SUCCESS = 1;

    @Autowired
    private MemberRepository memberRepository;

    @RequestMapping("/members")
    public List getMembers(){
        System.out.println("aaa");
        List<Member> list = memberRepository.findAll();
        return list;
    }

    @PostMapping("/login")
    public void login(@RequestParam String id,
                      @RequestParam String pwd,
                      HttpServletRequest request,
                      HttpServletResponse response) throws IOException, NoSuchAlgorithmException {

        Optional<Member> loginMember = memberRepository.findByIdAndPwd(id, HashFunction.sha256(pwd));
        JSONObject res = new JSONObject();

        if (!loginMember.isPresent()) {
            res.put("result", FAIL);
            log.info("** [" + id + "] Failed to log in **");
        } else {
            Member member = loginMember.get();
            HttpSession session = request.getSession();
            session.setAttribute("loginMember", member);
            member.setAttendance(member.getAttendance() + 1);
            memberRepository.save(member);
            if (member.getMemberId() == 0) {
                res.put("result", ADMIN);
                log.info("** ADMIN has logged in **");
            } else {
                res.put("result", SUCCESS);
                res.put("nick", member.getNickname());
                log.info("** [" + id + "] has logged in **");
            }
        }

        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(res);
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        JSONObject res = new JSONObject();
        Member member = (Member)session.getAttribute("loginMember");

        if (Objects.isNull(member)) {
            log.warn("!! Invalid approach !!");
            res.put("result", INVALID_APPROACH);
        } else {
            log.info("** [" + member.getId() + "] has logged out **");
            session.invalidate();
            res.put("result", SUCCESS);
            res.put("nick", member.getNickname());
        }
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(res);
    }

    @GetMapping("/check-duplicate")
    public void checkDuplicate(@RequestParam String id,
                     HttpServletResponse response) throws IOException {
        Optional<Member> member = memberRepository.findById(id);
        JSONObject res = new JSONObject();

        if (member.isPresent()) {
            res.put("result", DUPLICATE_ID);
            log.info("Duplicate id : " + id);
        } else {
            res.put("result", SUCCESS);
            log.info("Available id : " + id);
        }
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(res);
    }

    @PostMapping("/join")
    public void join(@RequestParam String id,
                     @RequestParam String pwd,
                     @RequestParam String email,
                     @RequestParam String nick,
                     HttpServletResponse response) throws IOException, NoSuchAlgorithmException {
        JSONObject res = new JSONObject();
        Member newMember = Member.builder()
                .id(id)
                .pwd(HashFunction.sha256(pwd))
                .email(email)
                .nickname(nick)
                .build();

        try {
            newMember = memberRepository.save(newMember);
            log.info("New Member[" + newMember.getMemberId() + " : "+ id + "] has just signed up.");
            res.put("result", SUCCESS);
        } catch (Exception e) {
            log.warn(e.toString());
            res.put("result", FAIL);
        }

        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(res);
    }
}
