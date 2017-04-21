<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="client.*,util.*,menu.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Search Movie Menu</title>
		<link rel="stylesheet" type="text/css" href="css/menu.css"/>
	</head>
	<body>
		<div id="top">
			<h1>Cinemate</h1>
			<p id="intro">Logged in! What would you like to do?</p>
		</div>
		<div id="main">
			<div id="contentBlock">
				<div id="menu">
					1.<a id="menuItem" href="Search.jsp?searchType=actor">Search by Actor</a><br />
					2.<a id="menuItem" href="Search.jsp?searchType=title">Search by Title</a><br />
					3.<a id="menuItem" href="Search.jsp?searchType=genre">Search by Genre</a><br />
					4.<a id="menuItem" href="LoginMenu.jsp">Back to Login Menu</a><br />
				</div>
			</div>
		</div>
		<div id="bottom">
		</div>
	</body>
</html>