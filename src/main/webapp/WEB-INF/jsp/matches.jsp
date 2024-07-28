<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Matches</title>
</head>
<body>
<a href="http://localhost:8080/new-match">new match</a>
<table>
    <c:forEach var="m" items="${matches}">
        <tr>
            <td>${m.playerOne.name}</td>
            <td>${m.playerTwo.name}</td>
            <td>${m.winner.name}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
