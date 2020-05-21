<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${title}</title>
    <link rel="stylesheet" href="/static/css/common.css"/>
    <link rel="stylesheet" href="/static/css/view_article.css"/>
    <script src="http://code.jquery.com/jquery-3.5.0.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
    <script src="/static/js/common.js"></script>
    <script src="/static/js/login.js"></script>
    <script src="/static/js/view_article.js"></script>
</head>
<body>
<div class="wrap">
    <div class="login">
        <c:choose>
            <c:when test="${loginMember == null}">
                <button id="login" class="button1" onclick="$.btnClick1(this)">로그인</button>
            </c:when>
            <c:otherwise>
                <button id="logout" class="button1" onclick="$.btnClick1(this)">로그아웃</button>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="article_wrap">
        <div class="article_content_box">
            <div class="article_header">
                <div class="article_title">
                    <span><c:if test="${article.category != '없음'}">[${article.category}]</c:if> ${article.title}</span>
                </div>
                <div class="writer_info">
                    <div>
                        <div class="writer_profile_box">
                            <img src="/static/img/null_profile.png" class="writer_profile"></img>
                        </div>
                        <div class="profile_area">
                            <div class="writer_nickname">
                                <span>${article.writerNickname}</span>
                            </div>
                            <div class="write_time">
                                <span>${article.date}</span>
                            </div>
                        </div></div>
                    <div class="views">
                        <span>조회수 ${article.views}</span>
                    </div>
                </div>
            </div>
            <div class="article_container">
                <div class="article_viewer">
                    ${article.content}
                </div>
                <div class="reply_box">
                    <div class="like_article">
                        <span class="heart">
                          ♥
                        </span>
                        <span>좋아요</span>
                        <span>${article.likes}</span>
                    </div>
                    <div class="reply_article">댓글 ${article.replies}</div>
                </div>
                <div class="comment_box">
                    <ul class="comment_list">
                        <c:forEach var="reply" items="${replies}">
                            <li class="comment_item <c:if test="${reply.parent != reply.replyId}">comment_item_reply</c:if>">
                                <div class="comment_area">
                                    <c:choose>
                                        <c:when test="${reply.profilePhoto == null}">
                                            <c:set var="profilePhoto" value="null_profile.png"></c:set>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="profilePhoto" value="${reply.profilePhoto}"></c:set>
                                        </c:otherwise>
                                    </c:choose>
                                    <img src="/static/img/${profilePhoto}" class="writer_profile"></img>
                                    <div class="comment_content">
                                        <div class="comment_nick_box">
                                            <span class="comment_nickname">${reply.nickname}</span>
                                        </div>
                                        <div class="comment_text_box">
                                            <span class="text_comment">${reply.content}</span>
                                        </div>
                                        <div class="comment_info_box">
                                            <span class="comment_info_date">${reply.date}</span>
                                            <c:if test="${loginMember != null}">
                                            <span class="commente_info_button">답글쓰기</span>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <c:if test="${loginMember != null}">
                <div class="comment_writer">
                    <div class="comment_writer_name">${loginMember.nickname}</div>
                    <textarea class="comment_write_input" placeholder="댓글을 남겨보세요" onkeydown="resize(this)"></textarea>
                    <div class="comment_writer_button">
                        <button class="button2">등록</button>
                    </div>
                </div>
                </c:if>
            </div>
            <div class="article_bottom_bar">
                <div class="article_bottom_left">
                    <button class="button1" onclick="viewBoard()">목록</button>
                </div>
                <div class="article_bottom_right">
                    <c:if test="${prev_article != 0}">
                    <button class="button1" onclick="viewArticle(${prev_article})">이전글</button>
                    </c:if>
                    <c:if test="${next_article != 0}">
                    <button class="button1" onclick="viewArticle(${next_article})">다음글</button>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
