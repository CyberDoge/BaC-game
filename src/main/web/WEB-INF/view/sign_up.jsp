<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>sign up</title>
</head>
<body>
<link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<div style="margin-top:50px"
     class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
    <div class="panel panel-info">
        <div class="panel-heading">
            <div class="panel-title">Sign Up</div>
        </div>
        <div class="panel-body">
            <form id="signupform" class="form-horizontal" role="form" method="post" action="sign_up">

                <c:if test="${not empty messages}">
                    <div id="signupalert" class="alert alert-danger">
                        <p>Error:</p>
                        <c:forEach var="m" items="${messages}">
                        <c:out value="${m}"/><p>
                        </c:forEach>
                    </div>
                </c:if>
                <div class="form-group">
                    <label for="username" class="col-md-3 control-label">username</label>
                    <div class="col-md-9">
                        <input type="text" class="form-control" id="username" name="username" placeholder="Username">
                    </div>
                </div>
                <div class="form-group">
                    <label for="password" class="col-md-3 control-label">Password</label>
                    <div class="col-md-9">
                        <input type="password" id="password" class="form-control" name="password"
                               placeholder="Password">
                    </div>
                </div>
                <div class="form-group">
                    <label for="confirm_password" class="col-md-3 control-label">Confirm password</label>
                    <div class="col-md-9">
                        <input type="password" id="confirm_password" class="form-control"
                               name="confirm_password"
                               placeholder="Confirm password">
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-md-offset-3 col-md-9">
                        <input type="submit" id="btn-signup" type="button" value="Sign Up" class="btn btn-info"><i
                            class="icon-hand-right"></i>
                        </input>
                        <span style="margin:0 8px;">or</span> <a href="login">
                        Sign In Here
                    </a>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
