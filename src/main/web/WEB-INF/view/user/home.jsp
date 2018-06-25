<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
    <link rel="stylesheet" type="text/css" href="/static/css/style.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="/static/js/home.js"></script>
    <style>
        .start{
            margin-top: 120px;
        }
        .info {
            margin-left:  auto;
            margin-right:  auto;
            max-width: 30%;
            text-align: center;
        }

        html{
            background: #f6f6f6;
        }

        li{
            list-style-type: none;
        }

    </style>
</head>
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
                <a class="nav-link" href="game">Play game</a>
            </li>
            <li class="nav-item">
                <a class="nav-link " href="rules.jsp">Rules</a>
            </li>
            <li class="nav-item">
                <a class="nav-link " href="../records">Records</a>
            </li>
        </ul>
        <div class="navbar-nav ml-auto">
            <a class="nav-link nav-item" href="/login?logout">Logout</a>
        </div>
    </div>
</nav>
<main>
        <div class="start info">
            <h3>
                Welcome to your profile
                <p>
                    I can not come up with what to insert here (-_-;)・・・
                    <br/>
                </p>
            </h3>
        </div>

        <div class="info">
            <label for="username">This is your username <i style="font-size: 1px">ty, capt obvious</i></label>
            <h5 id="username">${username}</h5>
        </div>

        <div class="info">
            <label for="btn">WOW some button</label>
            <button id="btn" type="submit">┬┴┬┴┤･ω･)ﾉ press me</button>
            <br/>
            <div id="result">
            </div>
        </div>

    </main>
</body>
</html>
