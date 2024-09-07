<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.pustovalov.entity.PointUnits" %>
<html>
<head>
    <title>Match score</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/styles-match-score.css">
</head>
<body>
<c:set var="playerOneId" value="${resp.playerOne().getId()}"/>
<c:set var="playerTwoId" value="${resp.playerTwo().getId()}"/>
<c:set var="score" value="${resp.score()}"/>
<div>
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
                <div class="score">
                    <section class="name">
                        <div class="score-unit">
                            <p>${resp.playerOne().getName()}</p>
                        </div>
                        <div class="score-unit">
                            <p class="game-unit">GAME</p>
                            <p class="point">${score.getPoint(playerOneId,PointUnits.GAME).getValue()}</p>
                        </div>
                        <div class="score-unit">
                            <p class="game-unit">SET</p>
                            <p class="point">${score.getPoint(playerOneId,PointUnits.SET).getValue()}</p>
                        </div>
                        <div class="score-unit">
                            <p class="game-unit">MATCH</p>
                            <p class="point">${score.getPoint(playerOneId,PointUnits.MATCH).getValue()}</p>
                        </div>
                        <div class="score-unit">
                            <p class="game-unit">TIEBREAK</p>
                            <p class="point">${score.getPoint(playerOneId,PointUnits.TIEBREAK).getValue()}</p>
                        </div>

                        <div class="point-button">
                            <button type="submit" value=${playerOneId} name="player-id">Point
                            </button>
                        </div>
                    </section>
                    <section class="name">
                        <div class="score-unit">
                            <p>${resp.playerTwo().getName()}</p>
                        </div>
                        <div class="score-unit">
                            <p class="game-unit">GAME</p>
                            <p class="point">${score.getPoint(playerTwoId,PointUnits.GAME).getValue()}</p>
                        </div>
                        <div class="score-unit">
                            <p class="game-unit">SET</p>
                            <p class="point">${score.getPoint(playerTwoId,PointUnits.SET).getValue()}</p>
                        </div>
                        <div class="score-unit">
                            <p class="game-unit">MATCH</p>
                            <p class="point">${score.getPoint(playerTwoId,PointUnits.MATCH).getValue()}</p>
                        </div>
                        <div class="score-unit">
                            <p class="game-unit">TIEBREAK</p>
                            <p class="point">${score.getPoint(playerTwoId,PointUnits.TIEBREAK).getValue()}</p>
                        </div>

                        <div class="point-button">
                            <button type="submit" value=${playerTwoId} name="player-id">Point
                            </button>
                        </div>
                    </section>
                </div>
            </form>
        </c:otherwise>
    </c:choose>


    <a href="http://localhost:8080/new-match">back</a>
</div>
</body>
</html>