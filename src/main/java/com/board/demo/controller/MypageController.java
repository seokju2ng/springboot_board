package com.board.demo.controller;

import com.board.demo.service.BoardService;
import com.board.demo.service.MemberService;
import com.board.demo.service.MypageService;
import com.board.demo.service.ReplyService;
import com.board.demo.util.Conversion;
import com.board.demo.util.ErrorPage;
import com.board.demo.vo.Boardlist;
import com.board.demo.vo.Member;
import com.board.demo.vo.Mypage;
import com.board.demo.vo.Replylist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

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
    public ModelAndView showMypage(HttpServletRequest request,
               @RequestParam(defaultValue = DEFAULT_TYPE, required = false) String type,
               @RequestParam(defaultValue = DEFAULT_PAGE, required = false) Integer page) {
        ModelAndView mav = new ModelAndView();
        Member loginMember = (Member) request.getSession().getAttribute("loginMember");

        if (Objects.isNull(loginMember)) {
            return ErrorPage.show();
        }

        Mypage mypage = mypageService.getMypageInfo(loginMember.getMemberId());
        mav.addObject("mypage", mypage);

        if (type.equals(DEFAULT_TYPE)) {    // 등록한 게시글 요청
            boardService.getListByMemberId(loginMember.getMemberId(), page-1, DEFAULT_LIST_SIZE, mav);
        }
        else {  // 등록한 댓글 요청
            replyService.getListByMemberId(loginMember.getMemberId(), page-1, DEFAULT_LIST_SIZE, mav);
        }

        int startPage = Conversion.calcStartPage(page);
        mav.addObject("type", type);
        mav.addObject("curPage", page);
        mav.addObject("startPage", startPage);
        mav.setViewName("my_page");
        return mav;
    }
}
