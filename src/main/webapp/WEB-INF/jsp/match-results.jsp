<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.pustovalov.enums.ScoreUnits" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>match-results</title>
    <link rel="stylesheet"
          type="text/css"
          href="${pageContext.request.contextPath}/resources/css/styles-match-result.css">
</head>
<body>
<c:set var="match" value="${requestScope.match}"/>
<c:set var="playerOne" value="${requestScope.match.playerOne}"/>
<c:set var="playerTwo" value="${requestScope.match.playerTwo}"/>
<c:set var="score" value="${requestScope.match.score}"/>

<div class="player">
    <span>${playerOne.name}</span>
</div>
<div class="score">
    <div class="match-points">
        <p class="p-value">${score.getResultPoints(playerOne.id, ScoreUnits.MATCH).get(0)}</p>
        <p class="p-value">:</p>
        <p class="p-value">${score.getResultPoints(playerTwo.id, ScoreUnits.MATCH).get(0)}</p>
    </div>

    <div class="set-points">
        <ul class="list">
            <c:forEach var="point" items="${score.getResultPoints(playerOne.id, ScoreUnits.SET)}">
                <li>${point}</li>
            </c:forEach>
        </ul>

        <ul class="list">
            <c:forEach var="point" items="${score.getResultPoints(playerTwo.id, ScoreUnits.SET)}">
                <li>${point}</li>
            </c:forEach>
        </ul>
    </div>

</div>
<div class="player">
    <span>${match.playerTwo.name}</span>
</div>
<div>
    <a href="http://localhost:8080/new-match">main</a>
    <a href="http://localhost:8080/matches?offset=0&limit=5">matches</a>
</div>

</body>
</html>