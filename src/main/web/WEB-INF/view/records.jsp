<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Records</title>
    <link rel="stylesheet" type="text/css" href="/static/css/records.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous"></head>
<body>
<nav class="navbar navbar-expand-lg fixed-top ">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-4">

            <li class="nav-item">
                <a class="nav-link" href="/">Main</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="user/game">Play game</a>
            </li>
            <li class="nav-item">
                <a class="nav-link " href="rules.jsp">Rules</a>
            </li>
            <li class="nav-item">
                <a class="nav-link " href="records">Records</a>
            </li>
        </ul>
        <c:if test="${auth}">
            <ul class="navbar-nav ml-auto nav-flex-icons">
                <li class="nav-item">
                    <a class="nav-link nav-item" href="login">Sign in</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link nav-item" href="sign_up">Sign up</a>
                </li>
            </ul>
        </c:if>
        <c:if test="${not auth}">
            <div class="navbar-nav ml-auto">
                <a class="nav-link nav-item" href="/user/home">Home</a>
            </div>
        </c:if>
    </div>
</nav>
<main>
    <div class="container overlay description text-center">
        <table>
            <tr>
                <th>num</th>
                <th>username</th>
                <th>value</th>
            </tr>
            <c:forEach items="${map}" var="entry" varStatus="loop">
                <tr>
                    <td>${loop.count}</td>
                    <td>${entry.key}</td>
                    <td>${entry.value}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</main>
</body>
</html>
