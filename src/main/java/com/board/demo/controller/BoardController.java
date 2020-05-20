package com.board.demo.controller;

import com.board.demo.service.BoardService;
import com.board.demo.service.CategoryService;
import com.board.demo.util.Conversion;
import com.board.demo.vo.Boardlist;
import com.board.demo.vo.Category;
import com.board.demo.vo.Member;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.board.demo.util.Constants.*;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {
    private final String DEFAULT_CATEGORY = "전체보기";
    private final String DEFAULT_PAGE = "1";
    private final String DEFAULT_LIST_SIZE = "10";

    @Autowired
    private BoardService boardService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ModelAndView showBoard(
            @RequestParam(defaultValue = DEFAULT_CATEGORY, required = false) String category,
            @RequestParam(defaultValue = DEFAULT_PAGE, required = false) Integer page,
            @RequestParam(defaultValue = DEFAULT_LIST_SIZE, required = false) Integer size) {
        ModelAndView mav = new ModelAndView();
        Page<Boardlist> boardlistPage = boardService.getList(category, page - 1, size);
        List<Category> categories = categoryService.getList();
        int totalPage = boardlistPage.getTotalPages();
        int startPage = Conversion.calcStartPage(page);
        List<Boardlist> boards = boardlistPage.getContent();
        Conversion.convertDateFormat(boards);
        Conversion.convertTitleLength(boards);

        mav.setViewName("board");
        mav.addObject("boards", boards);
        mav.addObject("categories", categories);
        mav.addObject("selectCategory", category);
        mav.addObject("selectSize", size);
        mav.addObject("curPage", page);
        mav.addObject("totalPage", totalPage);
        mav.addObject("startPage", startPage);
        return mav;
    }

    @GetMapping("/write")
    public ModelAndView showWriteForm(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        Member loginMember = (Member) request.getSession().getAttribute("loginMember");

        if (Objects.isNull(loginMember)) {
            mav.setViewName("redirect:/board");
        } else {
            List<Category> list = categoryService.getList();
            mav.setViewName("write_form");
            mav.addObject("categories", list);
        }
        return mav;
    }

    @PostMapping("/write")
    public void write(@RequestParam String title,
                      @RequestParam String content,
                      @RequestParam int category,
                      HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        JSONObject res = new JSONObject();
        Member loginMember = (Member) request.getSession().getAttribute("loginMember");

        if (Objects.isNull(loginMember)) {
            res.put(RESULT, INVALID_APPROACH);
        } else {
            boolean result = boardService.write(loginMember.getMemberId(), title, content, category);
            if (result) {
                res.put(RESULT, SUCCESS);
            } else {
                res.put(RESULT, FAIL);
            }
        }

        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(res);
    }
}
