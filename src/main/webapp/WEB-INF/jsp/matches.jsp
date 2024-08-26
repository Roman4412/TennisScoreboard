<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Matches</title>
</head>
<body>
<c:set var="page" value="${resp.currentPage}"/>
<c:set var="pageNum" value="${resp.currentPage + 1}"/>
<c:set var="totalPages" value="${resp.totalPages + 1}"/>

<a href="http://localhost:8080/new-match">new match</a>

<div>
    <form action="http://localhost:8080/matches" method="get">
        <fieldset>
            <input type="search" name="filter-by-player-name" value="${resp.filterByPlayerName}">
            <button type="submit">Search</button>
            <table>
                <c:forEach var="m" items="${resp.matches}">
                    <tr>
                        <td>${m.id}</td>
                        <td>${m.playerOne.name}</td>
                        <td>${m.playerTwo.name}</td>
                    </tr>
                </c:forEach>
            </table>

            <c:choose>
                <c:when test="${pageNum == totalPages && totalPages > 1}">
                    <button type="submit" name="page" value="${page - 1}">prev</button>
                    <span>${pageNum} / ${totalPages}</span>
                    <button type="submit" name="page" disabled>next</button>
                </c:when>
                <c:when test="${pageNum == 1 && totalPages >= 1}">
                    <button type="submit" name="page" disabled>prev</button>
                    <span>${pageNum} / ${totalPages}</span>
                    <button type="submit" name="page" value="${page + 1}">next</button>
                </c:when>
                <c:when test="${totalPages == 1}">
                    <button type="submit" name="page" disabled>prev</button>
                    <span>${pageNum} / ${totalPages}</span>
                    <button type="submit" name="page" disabled>next</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" name="page" value="${page - 1}">prev</button>
                    <span>${pageNum} / ${totalPages}</span>
                    <button type="submit" name="page" value="${page + 1}">next</button>
                </c:otherwise>
            </c:choose>

        </fieldset>
    </form>
</div>
</body>
</html>