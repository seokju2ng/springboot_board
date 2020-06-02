<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>마이페이지 - ${mypage.nickname}</title>
    <link rel="stylesheet" href="/static/css/common.css"/>
    <link rel="stylesheet" href="/static/css/my_page.css"/>
    <script src="http://code.jquery.com/jquery-3.5.0.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
    <script src="/static/js/common.js"></script>
</head>
<body>
<div class="wrap">
    <div class="login">
        <button class="button1" onclick="$.home()">홈</button>
    </div>
    <div class="profile_info">
        <div class="profile_box">
        <c:choose>
            <c:when test="${mypage.profilePhoto == null}">
                <img src="/static/img/null_profile.png" class="profile_photo <c:if test="${mypage.memberId == loginMember.memberId}">my_profile" onclick="$.fileUpload()</c:if>"/>
            </c:when>
            <c:otherwise>
                <img src="/member/get-profile?middlePath=${mypage.memberId}&imageFileName=${mypage.profilePhoto}" class="profile_photo
                <c:choose>
                    <c:when test="${mypage.memberId == loginMember.memberId}">
                        " onclick="$.fileUpload()">
                    </c:when>
                    <c:otherwise>
                        view_profile">
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
        </div>
        <div class="text">
            <div id="mem${mypage.memberId}" class="nick_area">${mypage.nickname}(${mypage.id})</div>
            <div class="desc">
                <div>
                    <span class="count">총 방문<em class="num">${mypage.attendance}</em>회</span>
                    <span class="count">총 게시글<em class="num">${mypage.boardNum}</em>개</span>
                    <span class="count">총 댓글<em class="num">${mypage.replyNum}</em>개</span>
                </div>
                <div>
                    <c:if test="${mypage.memberId == loginMember.memberId and mypage.profilePhoto != null}">
                    <span class="btn_default_profile">기본 프로필 사진으로 변경</span>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
    <div class="content_area">
        <div>
            <c:choose>
                <c:when test="${type == 'board'}">
                    <span id="board" class="content_subtitle selected">등록한 게시글</span>
                    <span id="reply" class="content_subtitle not_selected">등록한 댓글</span>
                </c:when>
                <c:otherwise>
                    <span id="board" class="content_subtitle not_selected">등록한 게시글</span>
                    <span id="reply" class="content_subtitle selected">등록한 댓글</span>
                </c:otherwise>
            </c:choose>
        </div>
        <c:choose>
            <c:when test="${type == 'board'}">
                <div class="content_post">
                    <table class="board">
                        <tr>
                            <th>글번호</th>
                            <th>제목</th>
                            <th>작성일</th>
                            <th>조회</th>
                            <th><img src="/static/img/heart_full.png"></th>
                        </tr>
                        <c:if test="${fn:length(boards) == 0}">
                            <tr><td colspan="5">작성하신 게시글이 없습니다.</td></tr>
                        </c:if>
                        <c:forEach var="board" items="${boards}">
                            <tr>
                                <td>${board.boardId}</td>
                                <td id="${board.boardId}" class="shortcuts">
                                    <c:if test="${board.category != '없음'}">
                                        <span class="category">[${board.category}]</span>
                                    </c:if>
                                        ${board.title}
                                    <c:if test="${board.replies != 0}">
                                        <span class="replies">[${board.replies}]</span>
                                    </c:if>
                                </td>
                                <td>${board.date}</td>
                                <td>${board.views}</td>
                                <td>${board.likes}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </c:when>
            <c:otherwise>
                <div class="content_reply">
                    <table class="reply">
                        <tr>
                            <th>댓글</th>
                            <th>작성일</th>
                        </tr>
                        <c:set var="num_replies" value="${fn:length(replies)}" />
                        <c:if test="${num_replies == 0}">
                            <tr><td colspan="2">작성하신 댓글이 없습니다.</td></tr>
                        </c:if>
                        <c:forEach var="i" begin="1" end="${num_replies}" step="1">
                            <c:set var="reply" value="${replies[num_replies-i]}" />
                            <tr>
                                <td id="${reply.boardId}" class="shortcuts">${reply.content}</td>
                                <td>${reply.date}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
        <div class="bottom-bar">
            <div>
                <c:if test="${loginMember != null}">
                <button id="write" class="button1">글쓰기</button>
                </c:if>
            </div>
            <div>
                <c:if test="${totalPages != 0}">
                <c:if test="${startPage != 1}">
                    <button id="prev" class="button1" onclick="$.prev(${startPage})">이전</button>
                </c:if>
                <c:choose>
                    <c:when test="${totalPages > (startPage + 9)}">
                        <c:set var="endPage" value="${startPage + 9}"></c:set>
                    </c:when>
                    <c:otherwise>
                        <c:set var="endPage" value="${totalPages}"></c:set>
                    </c:otherwise>
                </c:choose>
                <c:forEach begin="${startPage}" end="${endPage}" varStatus="status">
                    <span class="page-num <c:if test="${status.index == curPage}">cur-page</c:if>" onclick="$.reload(this.innerHTML)">${status.index}</span>
                </c:forEach>
                    <c:if test="${endPage != totalPages}">
                    <button id="next" class="button1" onclick="$.next(${endPage})">다음</button>
                </c:if>
                </c:if>
            </div>
        </div>
    </div>
</div>
<script> const isMypage = ${mypage.memberId == loginMember.memberId}; </script>
<script src="/static/js/my_page.js"></script>
</body>
</html>
