<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="client.*,util.*,menu.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Login Page</title>
		<link rel="stylesheet" type="text/css" href="css/loginPage.css"/>
		<script src="js/loginPage.js" type="text/javascript"></script>

	</head>
	<body>
		<div id="top">
			<h1>Cinemate</h1>
		</div>
		<div id="main">
			<div id="contentBlock">
				<div id="form">
					<form name="loginForm" method="POST" action="Feed.jsp" onsubmit="return login();">
						<p id="logintitle">Username</p>
						<input id="loginInput" type="text" name="username" />
						<p id="logintitle">Password</p>
						<input id="loginInput"type="password" name="password" /><br />
						<input id="loginButton" type="submit" name="loginButton" value="Log In"><br />
					</form>
					<a href="Signup.jsp">
						<button id="signupButton">Sign up</button>
					</a>
					<p id="error"></p>
				</div>
			</div>
		</div>
		<div id="bottom">
		</div>	
	</body>
</html>