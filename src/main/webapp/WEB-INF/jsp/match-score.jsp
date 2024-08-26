<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Match score</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/styles-match-score.css">
</head>
<body>
<div>
    <form action="http://localhost:8080/match-score?uuid=${param.uuid}" method="post"
          name="player-number">
        <div class="score">
            <section class="name">
                <div class="score-unit">
                    <p>${resp.playerOne.name}</p>
                </div>
                <div class="score-unit">
                    <p class="game-unit">GAME</p>
                    <p class="point">${resp.playerOneGamePts}</p>
                </div>
                <div class="score-unit">
                    <p class="game-unit">SET</p>
                    <p class="point">${resp.playerOneSetPts}</p>
                </div>
                <div class="score-unit">
                    <p class="game-unit">MATCH</p>
                    <p class="point">${resp.playerOneMatchPts}</p>
                </div>
                <div class="score-unit">
                    <p class="game-unit">TIEBREAK</p>
                    <p class="point">${resp.playerOneTiebreakPts}</p>
                </div>

                <div class="point-button">
                    <button type="submit" value=${resp.playerOne.id} name="player-id">Point
                    </button>
                </div>
            </section>
            <section class="name">
                <div class="score-unit">
                    <p>${resp.playerTwo.name}</p>
                </div>
                <div class="score-unit">
                    <p class="game-unit">GAME</p>
                    <p class="point">${resp.playerTwoGamePts}</p>
                </div>
                <div class="score-unit">
                    <p class="game-unit">SET</p>
                    <p class="point">${resp.playerTwoSetPts}</p>
                </div>
                <div class="score-unit">
                    <p class="game-unit">MATCH</p>
                    <p class="point">${resp.playerTwoMatchPts}</p>
                </div>
                <div class="score-unit">
                    <p class="game-unit">TIEBREAK</p>
                    <p class="point">${resp.playerTwoTiebreakPts}</p>
                </div>

                <div class="point-button">
                    <button type="submit" value=${resp.playerTwo.id} name="player-id">Point
                    </button>
                </div>
            </section>
        </div>
    </form>

    <a href="http://localhost:8080/new-match">back</a>
</div>
</body>
</html>