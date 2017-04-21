<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Login Page</title>
		<link rel="stylesheet" type="text/css" href="css/signup.css"/>
		<script src="js/signup.js" type="text/javascript"></script>
	</head>
	<body>
		<div id="top">
			<h1>Cinemate</h1>
		</div>
		<div id="main">
			<div id="contentBlock">
				<div id="form">
					<form name="signupForm" method="POST" action="Feed.jsp" onsubmit="return signup();">
						<input id="inputBox" type="text" name="fullname" placeholder="Full Name"/><br/>
						<input id="inputBox" type="text" name="username" placeholder="Username"/><br/>
						<input id="inputBox" type="password" name="password" placeholder="Password"/><br/>
						<input id="inputBox" type="text" name="image" placeholder="ImageURL" /><br/>
						<input id="signupButton" type="submit" name="signupButton" value="Sign Up"><br />
						<p id="error"></p>
					</form>
				</div>
			</div>
		</div>
		<div id="bottom">
		</div>	
	</body>
</html>