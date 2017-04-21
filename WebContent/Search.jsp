<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Search</title>
		<link rel="stylesheet" type="text/css" href="css/searchPage.css"/>
		<link rel="stylesheet" type="text/css" href="css/menuBar.css"/>
		<script src="js/generateMenuBar.js" type="text/javascript"></script>
		<script src="js/search.js" type="text/javascript"></script>
		<script>
			window.onload = function(){
				addMenuBar();
				printResult();
			}
		</script>
	</head>
	<body>
		<div id="top">
		</div>
		<div id="main">
			<div id="contentBlock">
				<div id="resultBox">
					<p id="title">Search Results</p>
				</div>
			</div>
		</div>
	</body>
</html>