<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>match-results</title>
    <link rel="stylesheet"
          type="text/css"
          href="${pageContext.request.contextPath}/resources/css/styles-match-result.css">
</head>
<body>
<div class="player">
    <span>${resp.playerOne.name}</span>
</div>
<div class="score">
    <div class="match-points">
        <p class="p-value">${resp.playerOneMatchPts}</p>
        <p class="p-value">:</p>
        <p class="p-value">${resp.playerTwoMatchPts}</p>
    </div>

    <div class="set-points">
        <ul class="list">
            <c:forEach var="point" items="${resp.playerOneSetPts}">
                <li>${point}</li>
            </c:forEach>
        </ul>

        <ul class="list">
            <c:forEach var="point" items="${resp.playerTwoSetPts}">
                <li>${point}</li>
            </c:forEach>
        </ul>
    </div>
</div>
<div class="player">
    <span>${resp.playerTwo.name}</span>
</div>
<div>
    <a href="http://localhost:8080/new-match">main</a>
    <a href="http://localhost:8080/matches">matches</a>
</div>

</body>
</html>