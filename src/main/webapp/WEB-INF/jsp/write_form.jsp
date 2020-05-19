<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>석주잉 게시판 - 글쓰기</title>
    <link rel="stylesheet" href="/static/css/common.css"/>
    <link rel="stylesheet" href="/static/css/write_form.css"/>
    <script src="http://code.jquery.com/jquery-3.5.0.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
    <script src="/static/js/common.js"></script>
    <script src="/static/js/login.js"></script>
    <script src="/static/js/write_form.js"></script>
</head>
<body>
<div class="wrap">
    <div class="login">
        <c:choose>
            <c:when test="${loginMember == null}">
                <button id="login" class="button1" onclick="$.btnClick(this)">로그인</button>
            </c:when>
            <c:otherwise>
                <button id="logout" class="button1" onclick="$.btnClick(this)">로그아웃</button>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="form">
        <form name="write" onsubmit="return false;">
            <span class="subtitle">말머리</span>
            <select name="category" class="swal2-select">
                <c:forEach var="category" items="${categories}">
                    <c:if test="${category.categoryId != 9999}">
                    <option value="${category.categoryId}">${category.categoryName}</option>
                    </c:if>
                </c:forEach>
            </select>
            <span class="subtitle">제목</span>
            <input name="title" class="swal2-input" placeholder="title" maxlength="60"/>
            <span class="subtitle">내용</span>
            <textarea name="content" class="swal2-textarea" placeholder="content"></textarea>
            <div class="btn-submit">
                <button id="btn-submit" class="button1">작성</button>
                <button id="btn-back" class="button1">취소</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
