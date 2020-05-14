package com.board.demo.util;

import com.board.demo.vo.Boardlist;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DateFormatConversion {
    public static void conversion(List<Boardlist> boards) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());

        for (Boardlist board : boards) {

            if (board.getDate().substring(0, 10).equals(today)) {
                board.setDate(board.getDate().substring(11, 16));
            } else {
                board.setDate(board.getDate().substring(0, 10));
            }
        }
    }
}
