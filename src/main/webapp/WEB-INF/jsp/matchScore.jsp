<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Match score</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>
<div>
    <form action="http://localhost:8080/match-score?uuid=${param.uuid}" method="post" name="player-number">
        <div class="score">
            <section class="player-info">
                <div class="score-unit">
                    <p>${requestScope.match.playerOne.name}</p>
                </div>
                <div class="score-unit">
                    <p class="game-unit">Point</p>
                    <p class="point">${requestScope.match.score.getPlayersScore(requestScope.match.playerOne.id, "Point")}</p>
                </div>
                <div class="score-unit">
                    <p class="game-unit">Game</p>
                    <p class="point">${requestScope.match.score.getPlayersScore(requestScope.match.playerOne.id, "Game")}</p>
                </div>
                <div class="score-unit">
                    <p class="game-unit">Set</p>
                    <p class="point">${requestScope.match.score.getPlayersScore(requestScope.match.playerOne.id, "Set")}</p>
                </div>
                <div class="score-unit">
                    <p class="game-unit">Tiebreak</p>
                    <p class="point">${requestScope.match.score.getPlayersScore(requestScope.match.playerOne.id,"Tiebreak")}</p>
                </div>

                <div class="point-button">
                    <button type="submit" value=${requestScope.match.playerOne.id} name="player-id">Point
                    </button>
                </div>
            </section>
            <section class="player-info">
                <div class="score-unit">
                    <p>${requestScope.match.playerTwo.name}</p>
                </div>
                <div class="score-unit">
                    <p class="game-unit">Point</p>
                    <p class="point">${requestScope.match.score.getPlayersScore(requestScope.match.playerTwo.id, "Point")}</p>
                </div>
                <div class="score-unit">
                    <p class="game-unit">Game</p>
                    <p class="point">${requestScope.match.score.getPlayersScore(requestScope.match.playerTwo.id, "Game")}</p>
                </div>
                <div class="score-unit">
                    <p class="game-unit">Set</p>
                    <p class="point">${requestScope.match.score.getPlayersScore(requestScope.match.playerTwo.id,"Set")}</p>
                </div>
                <div class="score-unit">
                    <p class="game-unit">Tiebreak</p>
                    <p class="point">${requestScope.match.score.getPlayersScore(requestScope.match.playerTwo.id,"Tiebreak")}</p>
                </div>

                <div class="point-button">
                    <button type="submit" value=${requestScope.match.playerTwo.id} name="player-id">Point
                    </button>
                </div>
            </section>
        </div>
    </form>

    <a href="http://localhost:8080/new-match">back</a>
</div>
</body>
</html>