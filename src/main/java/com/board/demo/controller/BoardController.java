package com.board.demo.controller;

import com.board.demo.repository.BoardRepository;
import com.board.demo.repository.BoardlistRepository;
import com.board.demo.util.DateFormatConversion;
import com.board.demo.vo.Boardlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class BoardController {

    @Autowired
    private BoardlistRepository boardlistRepository;
    @Autowired
    private BoardRepository boardRepository;


    @GetMapping("/")
    public ModelAndView hello(){
        ModelAndView mav = new ModelAndView();
        List<Boardlist> boards = boardlistRepository.findAll();

        DateFormatConversion.conversion(boards);

        mav.setViewName("board");
        mav.addObject("boards", boards);
        return mav;
    }
}
