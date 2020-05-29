package com.board.demo.controller;

import com.board.demo.service.BoardService;
import com.board.demo.service.MemberService;
import com.board.demo.service.MypageService;
import com.board.demo.service.ReplyService;
import com.board.demo.util.Conversion;
import com.board.demo.util.FileIO;
import com.board.demo.vo.Member;
import com.board.demo.vo.Mypage;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.board.demo.util.Constants.*;

@Slf4j
@Controller
@RequestMapping("/mypage")
public class MypageController {
    private final String DEFAULT_PAGE = "1";
    private final int DEFAULT_LIST_SIZE = 10;
    private final String DEFAULT_TYPE = "board";

    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private MypageService mypageService;

    @GetMapping
    public ModelAndView showMypage(@RequestParam(defaultValue = DEFAULT_TYPE, required = false) String type,
                                   @RequestParam(defaultValue = DEFAULT_PAGE, required = false) Integer page,
                                   @RequestParam(value = "id", required = false) Long memberId,
                                   HttpServletRequest request) {
        log.info("mypage > id: "+memberId);
        ModelAndView mav = new ModelAndView();

        if (Objects.isNull(memberId)) {
            Member loginMember = (Member) request.getSession().getAttribute("loginMember");
            if (Objects.isNull(loginMember)) {
                mav.setViewName("redirect:/board");
                return mav;
            }
            memberId = loginMember.getMemberId();
        }

        Mypage mypage = mypageService.getMypageInfo(memberId);
        mav.addObject("mypage", mypage);

        if (type.equals(DEFAULT_TYPE)) {    // 등록한 게시글 요청
            boardService.getListByMemberId(memberId, page - 1, DEFAULT_LIST_SIZE, mav);
        } else {  // 등록한 댓글 요청
            replyService.getListByMemberId(memberId, page - 1, DEFAULT_LIST_SIZE, mav);
        }

        int startPage = Conversion.calcStartPage(page);
        mav.addObject("type", type);
        mav.addObject("curPage", page);
        mav.addObject("startPage", startPage);
        mav.setViewName("my_page");
        return mav;
    }

    @PostMapping(value = "/set-profile")
    public void setProfileImage(@RequestParam("file") MultipartFile file,
                                HttpServletRequest request,
                                HttpServletResponse response) throws IOException {
//        log.info("setProfileImage entered.. ");
//        log.info(file.getOriginalFilename());
//        log.info(file.getName());
        Member loginMember = (Member) request.getSession().getAttribute("loginMember");
        JSONObject res = new JSONObject();
        if (Objects.isNull(loginMember) || file.isEmpty()) {
            // exception
            res.put("result", INVALID_APPROACH);
        }
        String folder = loginMember.getMemberId() + "";
        String filename = Conversion.convertImageName(file.getOriginalFilename());
        if (FileIO.saveImage(folder, filename, file.getBytes())) {
            if (memberService.setProfilePhoto(loginMember.getMemberId(), filename)) {
                res.put("result", SUCCESS);
            } else {
                res.put("result", FAIL);
            }
        } else {
            res.put("result", FAIL);
        }

        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(res);
    }
}
