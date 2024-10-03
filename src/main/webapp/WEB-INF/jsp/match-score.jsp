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
<c:set var="isTiebreakStarted"
       value="${score.getPoint(playerTwoId,PointUnits.SET).getValue() == 6 &&
       score.getPoint(playerOneId,PointUnits.SET).getValue() == 6}"/>

<c:choose>
    <c:when test="${resp.finished()}">
        <form action="http://localhost:8080/matches" method="post" name="player-number">
            <table class="result-stats">
                <tr>
                    <td class="A1">${resp.playerOne().getName()}</td>
                    <td class="B1">
                        <p class="point">${score.getPoint(playerOneId,PointUnits.MATCH).getValue()}</p>
                    </td>
                    <c:forEach var="point"
                               items="${score.getResultScore(playerOneId,PointUnits.SET)}">
                        <td class="C">${point}</td>
                    </c:forEach>
                </tr>
                <tr>
                    <td class="A2">${resp.playerTwo().getName()}</td>
                    <td class="B2">
                        <p class="point">${score.getPoint(playerTwoId,PointUnits.MATCH).getValue()}</p>
                    </td>
                    <c:forEach var="point"
                               items="${score.getResultScore(playerTwoId,PointUnits.SET)}">
                        <td class="C">${point}</td>
                    </c:forEach>
                </tr>
            </table>
            <button class="button-save" type="submit" value=${resp.uuid()} name="uuid">Save</button>
        </form>
    </c:when>

    <c:otherwise>
        <form action="http://localhost:8080/match-score?uuid=${param.uuid}" method="post"
              name="player-number">
            <div class="stats-footer">
                <p class="player-one">${resp.playerOne().getName()}</p>
                <c:choose>
                    <c:when test="${isTiebreakStarted}">
                        <div class="game-stats">
                            <p class="point">${score.getPoint(playerOneId,PointUnits.TIEBREAK).getValue()}</p>
                            <p>:</p>
                            <p class="point">${score.getPoint(playerTwoId,PointUnits.TIEBREAK).getValue()}</p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="game-stats">
                            <p class="point">${score.getPoint(playerOneId,PointUnits.GAME).getValue()}</p>
                            <p>:</p>
                            <p class="point">${score.getPoint(playerTwoId,PointUnits.GAME).getValue()}</p>
                        </div>
                    </c:otherwise>
                </c:choose>
                <p class="player-two">${resp.playerTwo().getName()}</p>
            </div>
            <table class="general-stats">
                <tr>
                    <th class="A1"></th>
                    <th class="B1"><span>Total score</span></th>
                    <th class="C1"><span>Set</span></th>
                </tr>
                <tr>
                    <td class="A2">${resp.playerOne().getName()}</td>
                    <td class="B2">${score.getPoint(playerOneId,PointUnits.MATCH).getValue()}</td>
                    <td class="C2">${score.getPoint(playerOneId,PointUnits.SET).getValue()}</td>
                </tr>
                <tr>
                    <td class="A3">${resp.playerTwo().getName()}</td>
                    <td class="B3">${score.getPoint(playerTwoId,PointUnits.MATCH).getValue()}</td>
                    <td class="C3">${score.getPoint(playerTwoId,PointUnits.SET).getValue()}</td>
                </tr>
            </table>
            <div class="buttons">
                <button class="button-point" type="submit" value=${playerOneId} name="player-id">
                    Point
                </button>
                <button class="button-point" type="submit" value=${playerTwoId} name="player-id">
                    Point
                </button>
            </div>
        </form>
    </c:otherwise>
</c:choose>
</body>
</html>