<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Board</title>
    <link rel="stylesheet" href="/static/css/board.css"/>
    <script src="http://code.jquery.com/jquery-3.5.0.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
    <script src="/static/js/board.js"></script>
</head>
<body>
    <div class="wrap">
        <div class="login">
            <button class="login">로그인</button>
        </div>
        <div class="title">게시판</div>
        <div class="board">
            <table class="board">
                <tr>
                    <th>글번호</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>작성일</th>
                    <th>조회</th>
                    <th>좋아요</th>
                </tr>
            <c:forEach var="board" items="${boards}">
                <tr>
                    <td>${board.boardId}</td>
                    <td>
                        <span class="category">[${board.category}]</span>
                            ${board.title}
                    <c:if test="${board.replies != 0}">
                        <span class="replies">[${board.replies}]</span>
                    </c:if>
                    </td>
                    <td>${board.writer}</td>
                    <td>${board.date}</td>
                    <td>${board.views}</td>
                    <td>${board.likes}</td>
                </tr>
            </c:forEach>
            </table>
        </div>
    </div>
</body>
</html>
