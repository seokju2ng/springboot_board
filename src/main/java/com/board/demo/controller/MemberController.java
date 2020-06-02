package com.board.demo.controller;

import com.board.demo.service.MemberService;
import com.board.demo.util.FileIO;
import com.board.demo.vo.Member;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

import static com.board.demo.util.Constants.*;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping("/members")
    public List getMembers() {
        List<Member> list = memberService.getList();
        return list;
    }

    @PostMapping("/login")
    public void login(@RequestParam String id,
                      @RequestParam String pwd,
                      HttpServletRequest request,
                      HttpServletResponse response) throws IOException, NoSuchAlgorithmException {

        Member loginMember = memberService.login(id, pwd);
        JSONObject res = new JSONObject();

        if (Objects.isNull(loginMember)) {
            res.put(RESULT, FAIL);
            log.info("** [" + id + "] Failed to log in **");
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("loginMember", loginMember);
            if (loginMember.getMemberId() == 0) {
                res.put(RESULT, ADMIN_ID);
                log.info("** ADMIN has logged in **");
            } else {
                res.put(RESULT, SUCCESS);
                res.put("nick", loginMember.getNickname());
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
        Member member = (Member) session.getAttribute("loginMember");

        if (Objects.isNull(member)) {
            log.warn("!! Invalid approach !!");
            res.put(RESULT, INVALID_APPROACH);
        } else {
            log.info("** [" + member.getId() + "] has logged out **");
            session.invalidate();
            res.put(RESULT, SUCCESS);
            res.put("nick", member.getNickname());
        }
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(res);
    }

    @GetMapping("/check-duplicate")
    public void checkDuplicate(@RequestParam String id,
                               HttpServletResponse response) throws IOException {
        JSONObject res = new JSONObject();

        if (memberService.isDuplicate(id)) {
            res.put(RESULT, DUPLICATE_ID);
            log.info("Duplicate id : " + id);
        } else {
            res.put(RESULT, SUCCESS);
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

        try {
            Member newMember = memberService.join(id, pwd, email, nick);
            log.info("New Member[ " + newMember.getMemberId() + " : " + id + " ] has just signed up.");
            res.put(RESULT, SUCCESS);
        } catch (Exception e) {
            log.warn(e.toString());
            res.put(RESULT, FAIL);
        }

        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(res);
    }

    @GetMapping("/get-profile")
    public void getProfilePhoto(@RequestParam String middlePath,
                                @RequestParam String imageFileName,
                                HttpServletRequest request,
                                HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        FileIO.loadImage(middlePath, imageFileName, response);
    }
}
