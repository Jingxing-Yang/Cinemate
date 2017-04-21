<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="client.*,java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Personal Profile</title>
		<link rel="stylesheet" type="text/css" href="css/profile.css"/>
		<link rel="stylesheet" type="text/css" href="css/menuBar.css"/>
		<script src="js/generateMenuBar.js" type="text/javascript"></script>
		<script src="js/profile.js" type="text/javascript"></script>
		<script>
			window.onload = function start(){
				addMenuBar();
				loadProfile();
			}
		</script>

	</head>
	<body>
		<div id="top">
		</div>
		<div id="main">
			<img id="profileImage" />
			<h2 id="fullname"></h2>
			<h5 id="username"></h5>
		</div>
		<div id="bottom">
			<div id="followers-container">
				<div id="contentTitle">
					<p id="title">Followers</p>
				</div>
				<div id="follower-list">
				</div>
			</div>
			<div id="event-container">

			</div>
			<div id="following-container">
				<div id="contentTitle">
					<p id="title">Following</p>
				</div>
				<div id="following-list">
				</div>
			</div>
		</div>	
	</body>
</html>