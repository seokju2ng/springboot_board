<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>석주잉 게시판 - 수정하기</title>
    <link rel="stylesheet" href="/static/css/write_form.css"/>
</head>
<body>
<div class="wrap">
    <jsp:include page="topbar.jsp"/>
    <div id="${article.boardId}" class="form">
        <form name="modify" onsubmit="return false;">
            <span class="subtitle">말머리</span>
            <select name="category" class="swal2-select">
                <c:forEach var="category" items="${categories}">
                    <c:choose>
                        <c:when test="${category.categoryId == 0}">
                            <c:if test="${loginMember.memberId == 0}">
                                <option value="${category.categoryId}" <c:if test="${category.categoryName == article.category}">selected</c:if>>${category.categoryName}</option>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <option value="${category.categoryId}" <c:if test="${category.categoryName == article.category}">selected</c:if>>${category.categoryName}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
            <span class="subtitle">제목</span>
            <input name="title" class="swal2-input" placeholder="title" maxlength="60" value="${article.title}"/>
            <span class="subtitle">내용</span>
            <textarea name="content" class="swal2-textarea" placeholder="content">${article.content}</textarea>
            <div class="btn-submit">
                <button id="btn-submit" class="button1">수정</button>
                <button id="btn-back" class="button1">취소</button>
            </div>
        </form>
    </div>
</div>
<script src="/static/js/modify_form.js"></script>
</body>
</html>
