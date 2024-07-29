<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Matches</title>
</head>
<body>
<c:set var="currentPage" value="${(param.offset / 5 + 1).intValue()}"/>
<c:set var="nextPage" value="${param.offset + 5}"/>
<c:set var="prevPage" value="${param.offset - 5}"/>
<a href="http://localhost:8080/new-match">new match</a>

<div>
    <form action="http://localhost:8080/matches?player-name=&offset=${currentPage}" method="get">
        <fieldset>
            <input type="search" name="player-name" value="${playerName}">
            <button type="submit">Search</button>
            <table>
                <c:forEach var="m" items="${matches}">
                    <tr>
                        <td>${m.id}</td>
                        <td>${m.playerOne.name}</td>
                        <td>${m.playerTwo.name}</td>
                    </tr>
                </c:forEach>
            </table>

            <c:choose>
                <c:when test="${currentPage == totalPages && totalPages > 1}">
                    <button type="submit" name="offset" value="${prevPage}">prev</button>
                    <span>${currentPage} / ${totalPages}</span>
                    <button type="submit" name="offset" disabled>next</button>
                </c:when>
                <c:when test="${currentPage == 1 && totalPages > 1}">
                    <button type="submit" name="offset" disabled>prev</button>
                    <span>${currentPage} / ${totalPages}</span>
                    <button type="submit" name="offset" value="${nextPage}">next</button>
                </c:when>
                <c:when test="${totalPages == 1}">
                    <button type="submit" name="offset" disabled>prev</button>
                    <span>${currentPage} / ${totalPages}</span>
                    <button type="submit" name="offset" disabled>next</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" name="offset" value="${prevPage}">prev</button>
                    <span>${currentPage} / ${totalPages}</span>
                    <button type="submit" name="offset" value="${nextPage}">next</button>
                </c:otherwise>
            </c:choose>

        </fieldset>
    </form>
</div>
</body>
</html>