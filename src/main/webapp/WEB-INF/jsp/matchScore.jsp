<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Match score</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div>
    <form action="http://localhost:8080/match-score?uuid=${param.uuid}" method="post" name="player-number">
        <div class="score">
            <section class="player-info">
                <div class="score-unit">
                    <p>${applicationScope.match.playerOne.name}</p>
                </div>
                <div class="score-unit">
                    <p class="game-unit">Game</p>
                    <p class="point">${applicationScope.match.score.playerOnePoint}</p>
                </div>
                <div class="score-unit">
                    <p class="game-unit">Set</p>
                    <p class="point">${applicationScope.match.score.playerOneSet}</p>
                </div>

                <div class="point-button">
                    <button type="submit" value="1" name="player-number">Point</button>
                </div>
            </section>
            <section class="player-info">
                <div class="score-unit">
                    <p>${applicationScope.match.playerTwo.name}</p>
                </div>
                <div class="score-unit">
                    <p class="game-unit">Game</p>
                    <p class="point">${applicationScope.match.score.playerTwoPoint}</p>
                </div>
                <div class="score-unit">
                    <p class="game-unit">Set</p>
                    <p class="point">${applicationScope.match.score.playerTwoSet}</p>
                </div>

                <div class="point-button">
                    <button type="submit" value="2" name="player-number">Point</button>
                </div>
            </section>
        </div>
    </form>

    <a href="http://localhost:8080/new-match">back</a>
</div>
</body>
</html>