<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>New match</title>
    <link rel="stylesheet" type="text/css" href="../../resources/css/new-match.css">
</head>
<body>
<form class="form" action="http://localhost:8080/new-match" method="post">
    <c:if test="${not empty errorMessage}">
        <p class="error">${errorMessage}</p>
    </c:if>
    <input placeholder="Player one" type="text" id="player-one"
           name="player-one-name">
    <input placeholder="Player two" type="text" id="player-two"
           name="player-two-name">
    <button class="start-button" type="submit">Start match</button>
</form>
</body>
</html>
