<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="client.*,java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Movie</title>
		<link rel="stylesheet" type="text/css" href="css/movie.css"/>
		<link rel="stylesheet" type="text/css" href="css/menuBar.css"/>
		<script src="js/movie.js" type="text/javascript"></script>
		<script src="js/generateMenuBar.js" type="text/javascript"></script>
		<script>
			window.onload = function(){
				loadMovie();
				addMenuBar();
			}
		</script>
	</head>
	<body>
		<div id="top">
		</div>
		<div id="main">
			<div id="movie-image-container">
				<img id="movie-image"/>
			</div>
			<div id="action-container">
				<div id="watched-container">
					<img class="action-image" id="watched" src="img/watched.png">
				</div>
				<div id="liked-container">
					<img class="action-image" id="liked" src="img/liked.png">
				</div>
				<div id="disliked-container">
					<img class="action-image" id="disliked" src="img/disliked.png">
				</div>
			</div>
			<div id="staff-container">
				<h1 id="movie-title"></h1>
				<p class="staff-info">Genre: </p>
				<p class="staff-info" id="genre"></p><br/>
				<p class="staff-info">Director: </p>
				<p class="staff-info" id="director"></p><br/>
				<p class="staff-info">Actors: </p>
				<p class="staff-info" id="actors"></p><br/>
				<p class="staff-info">Writers: </p>
				<p class="staff-info" id="writers"></p>
			</div>
			<div id="imdb-rating-container">
				<span id = "imdbRating">
					<span style="text-align:left;display:block; float:left; width: 80px">IMDB:</span>
					<img class = "rating-star" src="img/empty_star.png"id="imdb-star1">
					<img class = "rating-star" src="img/empty_star.png"id="imdb-star2">
					<img class = "rating-star" src="img/empty_star.png"id="imdb-star3">
					<img class = "rating-star" src="img/empty_star.png"id="imdb-star4">
					<img class = "rating-star" src="img/empty_star.png"id="imdb-star5">
					<img class = "rating-star" src="img/empty_star.png"id="imdb-star6">
					<img class = "rating-star" src="img/empty_star.png"id="imdb-star7">
					<img class = "rating-star" src="img/empty_star.png"id="imdb-star8">
					<img class = "rating-star" src="img/empty_star.png"id="imdb-star9">
					<img class = "rating-star" src="img/empty_star.png"id="imdb-star10">
				</span>
			</div>
			
			<div id="rating-container">
				<span class="starRating"> 	
					<span style="text-align:left;display:block; float:left; width: 80px">Cinemate:</span>
				 	<img class="rating-star cStar" id="star1"/>
				 	<img class="rating-star cStar" id="star2"/>
				 	<img class="rating-star cStar" id="star3"/>
				 	<img class="rating-star cStar" id="star4"/>	
				 	<img class="rating-star cStar" id="star5"/>
				 	<img class="rating-star cStar" id="star6"/>
				 	<img class="rating-star cStar" id="star7"/>
				 	<img class="rating-star cStar" id="star8"/>
				 	<img class="rating-star cStar" id="star9"/>
				 	<img class="rating-star cStar" id="star10"/>
				</span>
			</div>
			<div id="description-container">
				<p id="description"></p>	
			</div>
		</div>
		<div id="bottom">
			<p id="cast-title">Cast</p>
			<div id="cast-container">
			</div>

		</div>	
	</body>
</html>