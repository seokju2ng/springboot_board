package com.board.demo.util;

import org.springframework.web.servlet.ModelAndView;

public class ErrorPage {
    public static ModelAndView show() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("err_404");
        mav.addObject("err_msg", "요청하신 페이지를 찾을 수 없습니다.");
        return mav;
    }
}
