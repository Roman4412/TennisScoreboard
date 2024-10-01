<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Matches</title>
    <link rel="stylesheet" href="../../resources/css/matches.css">
</head>
<body>
<c:set var="page" value="${resp.currentPage()}"/>
<c:set var="pageNum" value="${resp.currentPage() + 1}"/>
<c:set var="totalPages" value="${resp.totalPages()}"/>
<c:set var="isLast" value="${pageNum == totalPages}"/>


<form action="http://localhost:8080/matches" method="get">
    <div class="content">
        <div class="search">
            <input type="search" name="filter-by-player-name" placeholder="Enter the player name"
                   value="${resp.filterByPlayerName()}">
            <button type="submit">Search</button>
        </div>
        <div class="matches-page">
            <c:forEach var="m" items="${resp.matches()}">
                <div class="row">
                    <p>${m.playerOne.name}</p>
                    <p>${m.playerTwo.name}</p>
                </div>
            </c:forEach>
        </div>
        <div class="navigation">
            <c:choose>
                <c:when test="${totalPages == 0}">
                    <p>Matches not found</p>
                </c:when>
                <c:when test="${pageNum == 1 && isLast}">
                    <button type="submit" name="page" disabled>prev</button>
                    <span>${pageNum} / ${totalPages}</span>
                    <button type="submit" name="page" disabled>next</button>
                </c:when>
                <c:when test="${isLast}">
                    <button type="submit" name="page" value="${page - 1}">prev</button>
                    <span>${pageNum} / ${totalPages}</span>
                    <button type="submit" name="page" disabled>next</button>
                </c:when>
                <c:when test="${pageNum == 1}">
                    <button type="submit" name="page" disabled>prev</button>
                    <span>${pageNum} / ${totalPages}</span>
                    <button type="submit" name="page" value="${page + 1}">next</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" name="page" value="${page - 1}">prev</button>
                    <span>${pageNum} / ${totalPages}</span>
                    <button type="submit" name="page" value="${page + 1}">next</button>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</form>
</body>
</html>