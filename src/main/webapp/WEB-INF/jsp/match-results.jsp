<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.pustovalov.enums.ScoreUnits" %>
<html>
<head>
    <title>match-results</title>
    <link rel="stylesheet"
          type="text/css"
          href="${pageContext.request.contextPath}/resources/css/styles-match-result.css">
</head>
<body>
<div class="player">
    <span>${requestScope.match.playerOne.name}</span>
</div>
<div class="score">
    <div class="match-points">
        <p class="p-value">${requestScope.match.score.getResultPoints(requestScope.match.playerOne.id, ScoreUnits.MATCH)}</p>
        <p class="p-value">:</p>
        <p class="p-value">${requestScope.match.score.getResultPoints(requestScope.match.playerTwo.id, ScoreUnits.MATCH)}</p>
    </div>
    <div class="set-points">
        <p class="p-value">${requestScope.match.score.getResultPoints(requestScope.match.playerOne.id, ScoreUnits.SET)}</p>
        <p class="p-value"></p>
        <p class="p-value">${requestScope.match.score.getResultPoints(requestScope.match.playerTwo.id, ScoreUnits.SET)}</p>
    </div>
</div>
<div class="player">
    <span>${requestScope.match.playerTwo.name}</span>
</div>
<div>
    <a href="http://localhost:8080/new-match">main</a>
</div>

</body>
</html>
