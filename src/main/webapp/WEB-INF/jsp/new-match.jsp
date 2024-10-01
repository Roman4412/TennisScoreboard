<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>New match</title>
    <link rel="stylesheet" type="text/css" href="../../resources/css/new-match.css">
</head>
<body>
<form class="form" action="http://localhost:8080/new-match" method="post">
    <input class="${ requestScope.containsKey("errorMessage") ? "error" : ""}"
           placeholder="Player one" type="text" id="player-one"
           name="player-one-name">
    <input placeholder="Player two" type="text" id="player-two"
           name="player-two-name">
    <button class="start-button" type="submit">Start match</button>
</form>
</body>
</html>
