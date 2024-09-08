<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>New match</title>
</head>
<body>
<div>
    <form action="http://localhost:8080/new-match" method="post">
        <fieldset>
            <legend>Create new match</legend>

            <p><label for="player-one">Player one</label></p>
            <p><input placeholder="Enter player name" type="text" id="player-one"
                      name="player-one-name"></p>

            <p><label for="player-two">Player two</label></p>
            <p><input placeholder="Enter player name" type="text" id="player-two"
                      name="player-two-name"></p>

            <p>
                <button type="submit">Start</button>
            </p>
        </fieldset>

    </form>
</div>

</body>
</html>
