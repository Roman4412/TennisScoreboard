<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 23.06.2024
  Time: 3:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New match</title>
</head>
<body>
<div>
    <form action="http://localhost:8080/new-match" method="post" autocomplete="on" >
        <fieldset>
            <legend>Create new match</legend>

            <p><label for="player-one">Player one</label></p>
            <p><input placeholder="Enter player name" type="text" id="player-one" name="player-one-name"></p>

            <p><label for="player-two">Player two</label></p>
            <p><input placeholder="Enter player name" type="text" id="player-two" name="player-two-name"></p>

            <p><button type="submit">Start</button></p>
        </fieldset>

    </form>
</div>

</body>
</html>
