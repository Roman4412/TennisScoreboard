<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.pustovalov.entity.PointUnits" %>
<html>
<head>
    <title>Match score</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/match-score.css">
</head>
<body>
<c:set var="playerOneId" value="${resp.playerOne().getId()}"/>
<c:set var="playerTwoId" value="${resp.playerTwo().getId()}"/>
<c:set var="score" value="${resp.score()}"/>
    <c:choose>
        <c:when test="${resp.finished()}">
            <form action="http://localhost:8080/matches" method="post"
                  name="player-number">
                <div class="score">
                    <section class="name">
                        <div class="score-unit">
                            <p>${resp.playerOne().getName()}</p>
                        </div>
                        <ul class="list">
                            <c:forEach var="point" items="${score.getResultScore(playerOneId,PointUnits.SET)}">
                                <li>${point}</li>
                            </c:forEach>
                        </ul>
                        <div class="score-unit">
                            <p class="game-unit">MATCH</p>
                            <p class="point">${score.getResultScore(playerOneId,PointUnits.MATCH).get(0)}</p>
                        </div>
                    </section>
                    <section class="name">
                        <div class="score-unit">
                            <p>${resp.playerTwo().getName()}</p>
                        </div>
                        <div class="set-points">
                            <ul class="list">
                                <c:forEach var="point" items="${score.getResultScore(playerTwoId,PointUnits.SET)}">
                                    <li>${point}</li>
                                </c:forEach>
                            </ul>
                        </div>
                        <div class="score-unit">
                            <p class="game-unit">MATCH</p>
                            <p class="point">${score.getResultScore(playerTwoId,PointUnits.MATCH).get(0)}</p>
                        </div>

                    </section>
                    <div class="point-button">
                        <button type="submit" value=${resp.uuid()} name="uuid">Save</button>
                    </div>
                </div>
            </form>
        </c:when>

        <c:otherwise>
            <form action="http://localhost:8080/match-score?uuid=${param.uuid}" method="post"
                  name="player-number">
                <div class="stats-footer">
                    <p class="player-one">${resp.playerOne().getName()}</p>
                    <div class="game-stats">
                        <p class="point">${score.getPoint(playerOneId,PointUnits.GAME).getValue()}</p>
                        <p>:</p>
                        <p class="point">${score.getPoint(playerTwoId,PointUnits.GAME).getValue()}</p>
                    </div>
                    <p class="player-two">${resp.playerTwo().getName()}</p>
                </div>
                <div class="set-stats">
                    <p class="point">${score.getPoint(playerOneId,PointUnits.SET).getValue()}</p>
                    <p>:</p>
                    <p class="point">${score.getPoint(playerTwoId,PointUnits.SET).getValue()}</p>
                </div>
                <div class="match-stats">
                    <p class="point">${score.getPoint(playerOneId,PointUnits.MATCH).getValue()}</p>
                    <p>:</p>
                    <p class="point">${score.getPoint(playerTwoId,PointUnits.MATCH).getValue()}</p>
                </div>
                <div class="buttons">
                    <button type="submit" value=${playerOneId} name="player-id">Point</button>
                    <button type="submit" value=${playerTwoId} name="player-id">Point</button>
                </div>
            </form>
        </c:otherwise>
    </c:choose>
</body>
</html>