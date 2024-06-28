<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 23.06.2024
  Time: 5:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Match score</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div>
    <form action="http://localhost:8080/matchscore" method="post">
        <div class="score">
            <section class="player-info">
                <div class="score-unit">
                    <p>Player one</p>
                </div>
                <div class="score-unit">
                    <p class="game-unit">Game</p>
                    <p class="point">0</p>
                </div>
                <div class="score-unit">
                    <p class="game-unit">Set</p>
                    <p class="point">0</p>
                </div>

                <div class="point-button">
                    <button type="submit" value="player-one">Point</button>
                </div>
            </section>
            <section class="player-info">
                <div class="score-unit">
                    <p>Player two</p>
                </div>
                <div class="score-unit">
                    <p class="game-unit">Game</p>
                    <p class="point">0</p>
                </div>
                <div class="score-unit">
                    <p class="game-unit">Set</p>
                    <p class="point">0</p>
                </div>

                <div class="point-button">
                    <button type="submit" value="player-one">Point</button>
                </div>
            </section>
        </div>
    </form>

    <a href="http://localhost:8080/new-match">back</a>
</div>
</body>
</html>