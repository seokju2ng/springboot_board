package com.board.demo.controller;

import com.board.demo.repository.MemberRepository;
import com.board.demo.util.HashFunction;
import com.board.demo.vo.Member;
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

@RestController
@RequestMapping("/member")
public class MemberController {
    private final String FAIL = "FAIL";
    private final String ADMIN = "ADMIN";
    private final String SUCCESS = "SUCCESS";
    private Log logger = LogFactory.getLog(MemberController.class);

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
//        logger.info("login method entered..");

        pwd = HashFunction.sha256(pwd);
        Member member = memberRepository.findByIdAndPwd(id, pwd);
        JSONObject res = new JSONObject();

        if (member == null) {
            res.put("result", FAIL);
            logger.info("** [" + id + "] Fail to log in **");
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("loginMember", member);
            member.setAttendance(member.getAttendance() + 1);
            memberRepository.save(member);
            if (member.getMemberId() == 0) {
                res.put("result", ADMIN);
                logger.info("** ADMIN has logged in **");
            } else {
                res.put("result", SUCCESS);
                res.put("nick", member.getNickname());
                logger.info("** [" + id + "] has logged in **");
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

        if (member == null) {
            logger.warn("!! Invalid approach !!");
            res.put("result", "INVALID_APPROACH");
        } else {
            logger.info("** [" + member.getId() + "] has logged out **");
            session.invalidate();
            res.put("result", "SUCCESS");
            res.put("nick", member.getNickname());
        }
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(res);
    }
}
