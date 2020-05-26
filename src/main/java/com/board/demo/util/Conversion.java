package com.board.demo.util;

import com.board.demo.vo.Boardlist;
import com.board.demo.vo.Article;
import com.board.demo.vo.Replylist;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Conversion {
    private static final int DATE_START = 0;
    private static final int DATE_END = 10;
    private static final int TIME_START = 11;
    private static final int TIME_END = 16;
    private static final int BEGIN = 0;
    private static final int MAX_TITLE_LENGTH = 30;
    private static final int MAX_CONTENT_LENGTH = 45;

    public static void convertDateFormatForArticleList(Article article) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());
        if (article.getDate().substring(DATE_START, DATE_END).equals(today)) {
            article.setDate(article.getDate().substring(TIME_START, TIME_END));
        } else {
            article.setDate(article.getDate().substring(DATE_START, DATE_END));
        }
    }

    public static void convertTitleLength(List<Boardlist> boards) {
        boards.forEach(board -> {
            if (board.getTitle().length() > MAX_TITLE_LENGTH) {
                StringBuilder title = new StringBuilder(board.getTitle().substring(BEGIN, MAX_TITLE_LENGTH));
                title.append( "...");
                board.setTitle(title.toString());
            }
        });
    }

    public static void convertContentLength(List<Replylist> replies) {
        replies.forEach(reply -> {
            if (reply.getContent().length() > MAX_CONTENT_LENGTH) {
                StringBuilder content = new StringBuilder(reply.getContent().substring(BEGIN, MAX_CONTENT_LENGTH));
                content.append( "...");
                reply.setContent(content.toString());
            }
        });
    }

    public static int calcStartPage(int page) {
        return ((page - 1) / 10) * 10 + 1;
    }

    public static void convertContent(Article article) {
        String oldContent = article.getContent();
        article.setContent(oldContent.replace("\n", "<br>"));
    }

    public static void convertDateFormatForArticle(Article article) {
        article.setDate(article.getDate().substring(DATE_START, TIME_END));
    }
}
