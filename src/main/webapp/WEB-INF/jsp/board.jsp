<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>석주잉 게시판</title>
    <link rel="stylesheet" href="/static/css/board.css"/>
</head>
<body>
    <div class="wrap">
        <jsp:include page="topbar.jsp"/>
        <div class="title">석주잉 게시판</div>
        <div class="top-bar">
            <div>
                <select id="category" class="swal2-select">
                    <option value=전체보기>전체보기</option>
                    <c:forEach var="category" items="${categories}">
                        <c:if test="${category.categoryId != 0}">
                            <c:choose>
                                <c:when test="${selectCategory == category.categoryName}">
                                    <option value="${category.categoryName}" selected>${category.categoryName}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${category.categoryName}">${category.categoryName}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
            <div>
                <select id="list-size" class="swal2-select">
                    <c:choose>
                        <c:when test="${selectSize == 1}">
                            <option value="1" selected>1개씩 보기</option>
                        </c:when>
                        <c:otherwise>
                            <option value="1">1개씩 보기</option>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${selectSize == 5}">
                            <option value="5" selected>5개씩 보기</option>
                        </c:when>
                        <c:otherwise>
                            <option value="5">5개씩 보기</option>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${selectSize == 10}">
                            <option value="10" selected>10개씩 보기</option>
                        </c:when>
                        <c:otherwise>
                            <option value="10">10개씩 보기</option>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${selectSize == 15}">
                            <option value="15" selected>15개씩 보기</option>
                        </c:when>
                        <c:otherwise>
                            <option value="15">15개씩 보기</option>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${selectSize == 20}">
                            <option value="20" selected>20개씩 보기</option>
                        </c:when>
                        <c:otherwise>
                            <option value="20">20개씩 보기</option>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${selectSize == 30}">
                            <option value="30" selected>30개씩 보기</option>
                        </c:when>
                        <c:otherwise>
                            <option value="30">30개씩 보기</option>
                        </c:otherwise>
                    </c:choose>
                </select>
            </div>
        </div>
        <div class="board">
            <table class="board">
                <tr>
                    <th>글번호</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>작성일</th>
                    <th>조회</th>
                    <th><img src="/static/img/heart_full.png"></th>
                </tr>
            <c:if test="${fn:length(boards) == 0}">
                <tr><td colspan="6">해당하는 게시글이 없습니다.</td></tr>
            </c:if>
            <c:forEach var="notice" items="${notices}" varStatus="status">
                <tr class="notice">
                    <td>${notice.category}</td>
                    <td id="${notice.boardId}">
                        ${notice.title}
                        <c:if test="${notice.replies != 0}">
                            <span class="replies">[${notice.replies}]</span>
                        </c:if>
                    </td>
                    <td>
                        <span>${notice.writerNickname}</span>
                    </td>
                    <td>${notice.date}</td>
                    <td>${notice.views}</td>
                    <td>${notice.likes}</td>
                </tr>
            </c:forEach>
            <c:forEach var="topLike" items="${topLikes}" varStatus="status">
                <tr class="top_like">
                    <td>추천</td>
                    <td id="${topLike.boardId}">
                        <c:if test="${topLike.category != '없음'}">
                            <span class="category">[${topLike.category}]</span>
                        </c:if>
                            ${topLike.title}
                        <c:if test="${topLike.replies != 0}">
                            <span class="replies">[${topLike.replies}]</span>
                        </c:if>
                    </td>
                    <td class="member" id="m${topLike.writerId}">
                        <c:if test="${topLike.profile != null}">
                            <img src="/member/get-profile?middlePath=${topLike.writerId}&imageFileName=${topLike.profile}" class="profile_photo">
                            <%--<img id="profileL${status.index}" src="" class="profile_photo"/>--%>
                            <%--<input type="hidden" id="imgValueL${status.index}" value="${topLike.writerId}:${topLike.profile}"/>--%>
                        </c:if>
                        <span>${topLike.writerNickname}</span>
                    </td>
                    <td>${topLike.date}</td>
                    <td>${topLike.views}</td>
                    <td>${topLike.likes}</td>
                </tr>
            </c:forEach>
            <c:forEach var="board" items="${boards}" varStatus="status">
                <tr>
                    <td>${board.boardId}</td>
                    <td id="${board.boardId}">
                        <c:if test="${board.category != '없음'}">
                            <span class="category">[${board.category}]</span>
                        </c:if>
                            ${board.title}
                    <c:if test="${board.replies != 0}">
                        <span class="replies">[${board.replies}]</span>
                    </c:if>
                    </td>
                    <td class="member" id="m${board.writerId}">
                        <c:if test="${board.profile != null}">
                            <img src="/member/get-profile?middlePath=${board.writerId}&imageFileName=${board.profile}" class="profile_photo">
                            <%--<img id="profileA${status.index}" src="" class="profile_photo"/>--%>
                            <%--<input type="hidden" id="imgValueA${status.index}" value="${board.writerId}:${board.profile}"/>--%>
                        </c:if>
                        <span>${board.writerNickname}</span>
                    </td>
                    <td>${board.date}</td>
                    <td>${board.views}</td>
                    <td>${board.likes}</td>
                </tr>
            </c:forEach>
            </table>
        </div>
        <div class="bottom-bar">
            <div>
                <button id="write" class="button1">글쓰기</button>
            </div>
            <div>
                <c:if test="${startPage != 1}">
                    <button id="prev" class="button1" onclick="$.prev(${startPage})">이전</button>
                </c:if>
                <c:choose>
                    <c:when test="${totalPage > (startPage + 9)}">
                        <c:set var="endPage" value="${startPage + 9}"></c:set>
                    </c:when>
                    <c:otherwise>
                        <c:set var="endPage" value="${totalPage}"></c:set>
                    </c:otherwise>
                </c:choose>
                <c:forEach begin="${startPage}" end="${endPage}" varStatus="status">
                    <span class="page-num <c:if test="${status.index == curPage}">cur-page</c:if>" onclick="$.reload(this.innerHTML)">${status.index}</span>
                </c:forEach>
                <c:if test="${endPage != totalPage}">
                    <button id="next" class="button1" onclick="$.next(${endPage})">다음</button>
                </c:if>
            </div>
        </div>
    </div>
</body>
<script src="/static/js/board.js"></script>
</html>
