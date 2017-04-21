<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="client.*,java.util.*,menu.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Personal Profile</title>
		<link rel="stylesheet" type="text/css" href="css/feed.css"/>
		<link rel="stylesheet" type="text/css" href="css/menuBar.css"/>
		<script src="js/generateMenuBar.js" type="text/javascript"></script>
		<script src="js/feed.js" type="text/javascript"></script>
		<script>
			window.onload=function start(){
				addMenuBar();
				loadFeed();
			}			
		</script>
	</head>
	<body>
		<div id="top">
		</div>
		<div id="main">
			<div id="content-container">
				<table id="feed-table">
				</table>
			</div>				
		</div>
	</body>
</html>