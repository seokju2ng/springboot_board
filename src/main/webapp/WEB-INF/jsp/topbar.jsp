<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/static/css/common.css"/>
<script src="http://code.jquery.com/jquery-3.5.0.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
<script src="/static/js/common.js"></script>
<script src="/static/js/login.js"></script>

<div class="login">
    <c:choose>
        <c:when test="${loginMember == null}">
            <button id="login" class="button1" onclick="$.btnClick(this)">로그인</button>
        </c:when>
        <c:otherwise>
            <c:if test="${loginMember.memberId == 0}">
                <button id="admin" class="button1" onclick="$.btnClick(this)">관리자</button>
            </c:if>
            <button id="mypage" class="button1" onclick="$.btnClick(this)">마이페이지</button>
            <button id="logout" class="button1" onclick="$.btnClick(this)">로그아웃</button>
        </c:otherwise>
    </c:choose>
</div>
