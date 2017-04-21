function getTime(time){
	dayOption = {month:'long',day:'numeric'};
	timeOption = {hour:"2-digit",minute:"2-digit",hour12:true}
	var date = new Date(parseInt(time));
	var time1 = date.toLocaleDateString('en-US', dayOption);
	var time2 = date.toLocaleTimeString('en-US', timeOption);
	return time1+" at "+time2;
}


function loadMovie(){
	
	//console.log(time1+" at "+time2);
	var movieId = getParameter("id");
	//alert(movieId);
	var url = "http://www.omdbapi.com/?i=";
	var message = "loadMovie";
	var movie;
	var req = new XMLHttpRequest();
	req.open('GET',url+movieId, false);
	req.onreadystatechange = function () {
		if(req.readyState == 4 && req.status == 200 ) {
			movie = JSON.parse(req.responseText);
		}
	}
	//req.setRequestHeader("Content-type","Application/json");
	//var data = JSON.stringify({"message": message,"title": title});
	req.send(null);
	var title = movie.Title;
	//set up action label
	var watched_container = document.getElementById("watched-container");
	createLabel(watched_container,"watched");
	var liked_container = document.getElementById("liked-container");
	createLabel(liked_container,"liked");
	var disliked_container = document.getElementById("disliked-container");
	createLabel(disliked_container,"disliked");
	
	var movieImage = document.getElementById("movie-image");
	movieImage.src = movie.Poster;
	var movieImageContainer = document.getElementById("movie-image-container");
	createLabel(movieImageContainer,movie.Title);
	var movieTitle = document.getElementById("movie-title");
	movieTitle.innerHTML = movie.Title+" ("+movie.Year+")";
	var movieGenre = document.getElementById("genre");
	movieGenre.innerHTML = movie.Genre;
	var movieDirector = document.getElementById("director");
	movieDirector.innerHTML = movie.Director;
	var movieActors = document.getElementById("actors");
	movieActors.innerHTML = movie.Actors;
	var movieWriters = document.getElementById("writers");
	movieWriters.innerHTML = movie.Writer;
	var movieDescription = document.getElementById("description");
	movieDescription.innerHTML = movie.Plot;
	
	var castContainer = document.getElementById("cast-container");
	var actors = movie.Actors.split(',');
	for(var i = 0; i < actors.length;i++){
		var castImage = document.createElement("img");
		castImage.src = getActorImage(actors[i]);
		castImage.id = "cast-image";
		var castName = document.createElement("p");
		castName.innerHTML = actors[i].trim();
		castName.id = "cast-name";
		var nl = document.createElement("br");
		castContainer.appendChild(castImage);
		castContainer.appendChild(castName);
		castContainer.appendChild(nl);
	}
	
	//set action buttons
	var watchButton = document.getElementById("watched");
	watchButton.onclick = function(){
		var action = "Watched";
		var actionReq = new XMLHttpRequest();
		var url = "MovieServlet";
		actionReq.open('POST',url, true);
		actionReq.onreadystatechange = function () {
			if(actionReq.readyState == 4 && actionReq.status == 200 ) {
				localStorage.setItem("currentUser",actionReq.responseText);
			}
		}
		actionReq.setRequestHeader("Content-type","Application/json");
		var data = JSON.stringify({"message": "non-rate-action","action": action,"movieId":movieId,"title": title,"time":Date.now()});
		actionReq.send(data);
	};
	var likeButton = document.getElementById("liked");
	likeButton.onclick = function(){
		var action = "Liked";
		var actionReq = new XMLHttpRequest();
		var url = "MovieServlet";
		actionReq.open('POST',url, true);
		actionReq.onreadystatechange = function () {
			if(actionReq.readyState == 4 && actionReq.status == 200 ) {
				localStorage.setItem("currentUser",actionReq.responseText);
			}
		}
		actionReq.setRequestHeader("Content-type","Application/json");
		var data = JSON.stringify({"message": "non-rate-action","action": action,"movieId":movieId,"title": title,"time":Date.now()});
		actionReq.send(data);
	};
	var dislikeButton = document.getElementById("disliked");
	dislikeButton.onclick =  function(){
		var action = "Disliked";
		var actionReq = new XMLHttpRequest();
		var url = "MovieServlet";
		actionReq.open('POST',url, true);
		actionReq.onreadystatechange = function () {
			if(actionReq.readyState == 4 && actionReq.status == 200 ) {
				localStorage.setItem("currentUser",actionReq.responseText);
			}
		}
		actionReq.setRequestHeader("Content-type","Application/json");
		var data = JSON.stringify({"message": "non-rate-action","action": action,"movieId":movieId,"title": title,"time":Date.now()});
		actionReq.send(data);
	}
	
	//imdb rating
	var imdbRating = Math.round(movie.imdbRating);
	for(var i = 1; i <=imdbRating; i++){
		var imdbRatingStar = document.getElementById("imdb-star"+i);
		imdbRatingStar.src = "img/imdb-star.png";
	}
	//cinemate rating
	var cMovie = getCinemateMovie(movie.imdbID,movie.title);
	var movieRating;
	if(cMovie.ratingCount==0){
		movieRating = 0;
	}
	else{
		movieRating = Math.round(cMovie.totalRating/cMovie.ratingCount);
	}
	displayRating(movieRating);


	
	
	//setup stars
	//star1
	var star1 = document.getElementById("star1");
	star1.addEventListener('mouseover',function(){
		var val = 1;
		for(var i = 1; i<= val;i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/star.png";
		}
		for(var i = val+1; i <= 10; i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/empty_star.png";
		}
	})
	star1.addEventListener('mouseout',function(){
		var val = movieRating;
		for(var i = 1; i<= val;i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/star.png";
		}
		for(var i = val+1; i <= 10; i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/empty_star.png";
		}
	})
	star1.addEventListener('click',function(){
		var newRating;
		var rating = "1";
		var rateReq = new XMLHttpRequest();
		var url = "MovieServlet";
		rateReq.open('POST',url, false);
		rateReq.onreadystatechange = function () {
			if(rateReq.readyState == 4 && rateReq.status == 200 ) {
				var reply = JSON.parse(rateReq.responseText);
				var updatedMovie = reply.movie;
				movieRating = Math.round(updatedMovie.totalRating/updatedMovie.ratingCount);
				var curUser = reply.curUser;
				var userString = JSON.stringify(curUser);
				localStorage.setItem("currentUser",userString);
			}
		}
		rateReq.setRequestHeader("Content-type","Application/json");
		var data = JSON.stringify({"message": "Rated","movieId":movieId,"title": title, "rating":rating,"time":Date.now()});
		rateReq.send(data);
	});
	
	
	//star2
	var star2 = document.getElementById("star2");
	star2.addEventListener('mouseover',function(){
		var val = 2;
		for(var i = 1; i<= val;i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/star.png";
		}
		for(var i = val+1; i <= 10; i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/empty_star.png";
		}
	})
	star2.addEventListener('mouseout',function(){
		var val = movieRating;
		for(var i = 1; i<= val;i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/star.png";
		}
		for(var i = val+1; i <= 10; i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/empty_star.png";
		}
	})
	star2.addEventListener('click',function(){
		var newRating;
		var rating = "2";
		var rateReq = new XMLHttpRequest();
		var url = "MovieServlet";
		rateReq.open('POST',url, false);
		rateReq.onreadystatechange = function () {
			if(rateReq.readyState == 4 && rateReq.status == 200 ) {
				var reply = JSON.parse(rateReq.responseText);
				var updatedMovie = reply.movie;
				movieRating = Math.round(updatedMovie.totalRating/updatedMovie.ratingCount);
				var curUser = reply.curUser;
				var userString = JSON.stringify(curUser);
				localStorage.setItem("currentUser",userString);
			}
		}
		rateReq.setRequestHeader("Content-type","Application/json");
		var data = JSON.stringify({"message": "Rated","movieId":movieId,"title": title, "rating":rating,"time":Date.now()});
		rateReq.send(data);
	});
	
	
	
	
	//star3
	var star3 = document.getElementById("star3");
	star3.addEventListener('mouseover',function(){
		var val = 3;
		for(var i = 1; i<= val;i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/star.png";
		}
		for(var i = val+1; i <= 10; i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/empty_star.png";
		}
	})
	star3.addEventListener('mouseout',function(){
		var val = movieRating;
		for(var i = 1; i<= val;i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/star.png";
		}
		for(var i = val+1; i <= 10; i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/empty_star.png";
		}
	})
	star3.addEventListener('click',function(){
		var newRating;
		var rating = "3";
		var rateReq = new XMLHttpRequest();
		var url = "MovieServlet";
		rateReq.open('POST',url, false);
		rateReq.onreadystatechange = function () {
			if(rateReq.readyState == 4 && rateReq.status == 200 ) {
				var reply = JSON.parse(rateReq.responseText);
				var updatedMovie = reply.movie;
				movieRating = Math.round(updatedMovie.totalRating/updatedMovie.ratingCount);
				var curUser = reply.curUser;
				var userString = JSON.stringify(curUser);
				localStorage.setItem("currentUser",userString);
			}
		}
		rateReq.setRequestHeader("Content-type","Application/json");
		var data = JSON.stringify({"message": "Rated","movieId":movieId,"title": title, "rating":rating,"time":Date.now()});
		rateReq.send(data);
	});
	
	
	
	//star4
	var star4 = document.getElementById("star4");
	star4.addEventListener('mouseover',function(){
		var val = 4;
		for(var i = 1; i<= val;i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/star.png";
		}
		for(var i = val+1; i <= 10; i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/empty_star.png";
		}
	})
	star4.addEventListener('mouseout',function(){
		var val = movieRating;
		for(var i = 1; i<= val;i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/star.png";
		}
		for(var i = val+1; i <= 10; i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/empty_star.png";
		}
	})
	star4.addEventListener('click',function(){
		var newRating;
		var rating = "4";
		var rateReq = new XMLHttpRequest();
		var url = "MovieServlet";
		rateReq.open('POST',url, false);
		rateReq.onreadystatechange = function () {
			if(rateReq.readyState == 4 && rateReq.status == 200 ) {
				var reply = JSON.parse(rateReq.responseText);
				var updatedMovie = reply.movie;
				movieRating = Math.round(updatedMovie.totalRating/updatedMovie.ratingCount);
				var curUser = reply.curUser;
				var userString = JSON.stringify(curUser);
				localStorage.setItem("currentUser",userString);
			}
		}
		rateReq.setRequestHeader("Content-type","Application/json");
		var data = JSON.stringify({"message": "Rated","movieId":movieId,"title": title, "rating":rating,"time":Date.now()});
		rateReq.send(data);
	});
	
	
	//star5
	var star5 = document.getElementById("star5");
	star5.addEventListener('mouseover',function(){
		var val = 5;
		for(var i = 1; i<= val;i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/star.png";
		}
		for(var i = val+1; i <= 10; i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/empty_star.png";
		}
	})
	star5.addEventListener('mouseout',function(){
		var val = movieRating;
		for(var i = 1; i<= val;i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/star.png";
		}
		for(var i = val+1; i <= 10; i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/empty_star.png";
		}
	})
	star5.addEventListener('click',function(){
		var newRating;
		var rating = "5";
		var rateReq = new XMLHttpRequest();
		var url = "MovieServlet";
		rateReq.open('POST',url, false);
		rateReq.onreadystatechange = function () {
			if(rateReq.readyState == 4 && rateReq.status == 200 ) {
				var reply = JSON.parse(rateReq.responseText);
				var updatedMovie = reply.movie;
				movieRating = Math.round(updatedMovie.totalRating/updatedMovie.ratingCount);
				var curUser = reply.curUser;
				var userString = JSON.stringify(curUser);
				localStorage.setItem("currentUser",userString);
			}
		}
		rateReq.setRequestHeader("Content-type","Application/json");
		var data = JSON.stringify({"message": "Rated","movieId":movieId,"title": title, "rating":rating,"time":Date.now()});
		rateReq.send(data);
	});
	
	
	
	//star6
	var star6 = document.getElementById("star6");
	star6.addEventListener('mouseover',function(){
		var val = 6;
		for(var i = 1; i<= val;i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/star.png";
		}
		for(var i = val+1; i <= 10; i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/empty_star.png";
		}
	})
	star6.addEventListener('mouseout',function(){
		var val = movieRating;
		for(var i = 1; i<= val;i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/star.png";
		}
		for(var i = val+1; i <= 10; i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/empty_star.png";
		}
	})
	star6.addEventListener('click',function(){
		var newRating;
		var rating = "6";
		var rateReq = new XMLHttpRequest();
		var url = "MovieServlet";
		rateReq.open('POST',url, false);
		rateReq.onreadystatechange = function () {
			if(rateReq.readyState == 4 && rateReq.status == 200 ) {
				var reply = JSON.parse(rateReq.responseText);
				var updatedMovie = reply.movie;
				movieRating = Math.round(updatedMovie.totalRating/updatedMovie.ratingCount);
				var curUser = reply.curUser;
				var userString = JSON.stringify(curUser);
				localStorage.setItem("currentUser",userString);
			}
		}
		rateReq.setRequestHeader("Content-type","Application/json");
		var data = JSON.stringify({"message": "Rated","movieId":movieId,"title": title, "rating":rating,"time":Date.now()});
		rateReq.send(data);
	});
	
	
	
	
	//star7
	var star7 = document.getElementById("star7");
	star7.addEventListener('mouseover',function(){
		var val = 7;
		for(var i = 1; i<= val;i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/star.png";
		}
		for(var i = val+1; i <= 10; i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/empty_star.png";
		}
	})
	star7.addEventListener('mouseout',function(){
		var val = movieRating;
		for(var i = 1; i<= val;i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/star.png";
		}
		for(var i = val+1; i <= 10; i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/empty_star.png";
		}
	})
	star7.addEventListener('click',function(){
		var newRating;
		var rating = "7";
		var rateReq = new XMLHttpRequest();
		var url = "MovieServlet";
		rateReq.open('POST',url, false);
		rateReq.onreadystatechange = function () {
			if(rateReq.readyState == 4 && rateReq.status == 200 ) {
				var reply = JSON.parse(rateReq.responseText);
				var updatedMovie = reply.movie;
				movieRating = Math.round(updatedMovie.totalRating/updatedMovie.ratingCount);
				var curUser = reply.curUser;
				var userString = JSON.stringify(curUser);
				localStorage.setItem("currentUser",userString);
			}
		}
		rateReq.setRequestHeader("Content-type","Application/json");
		var data = JSON.stringify({"message": "Rated","movieId":movieId,"title": title, "rating":rating,"time":Date.now()});
		rateReq.send(data);
	});
	
	
	//star8
	var star8 = document.getElementById("star8");
	star8.addEventListener('mouseover',function(){
		var val = 8;
		for(var i = 1; i<= val;i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/star.png";
		}
		for(var i = val+1; i <= 10; i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/empty_star.png";
		}
	})
	star8.addEventListener('mouseout',function(){
		var val = movieRating;
		for(var i = 1; i<= val;i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/star.png";
		}
		for(var i = val+1; i <= 10; i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/empty_star.png";
		}
	})
	star8.addEventListener('click',function(){
		var newRating;
		var rating = "8";
		var rateReq = new XMLHttpRequest();
		var url = "MovieServlet";
		rateReq.open('POST',url, false);
		rateReq.onreadystatechange = function () {
			if(rateReq.readyState == 4 && rateReq.status == 200 ) {
				var reply = JSON.parse(rateReq.responseText);
				var updatedMovie = reply.movie;
				movieRating = Math.round(updatedMovie.totalRating/updatedMovie.ratingCount);
				var curUser = reply.curUser;
				var userString = JSON.stringify(curUser);
				localStorage.setItem("currentUser",userString);
			}
		}
		rateReq.setRequestHeader("Content-type","Application/json");
		var data = JSON.stringify({"message": "Rated","movieId":movieId,"title": title, "rating":rating,"time":Date.now()});
		rateReq.send(data);
	});
	
	
	//star9
	var star9 = document.getElementById("star9");
	star9.addEventListener('mouseover',function(){
		var val = 9;
		for(var i = 1; i<= val;i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/star.png";
		}
		for(var i = val+1; i <= 10; i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/empty_star.png";
		}
	})
	star9.addEventListener('mouseout',function(){
		var val = movieRating;
		for(var i = 1; i<= val;i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/star.png";
		}
		for(var i = val+1; i <= 10; i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/empty_star.png";
		}
	})
	star9.addEventListener('click',function(){
		var newRating;
		var rating = "9";
		var rateReq = new XMLHttpRequest();
		var url = "MovieServlet";
		rateReq.open('POST',url, false);
		rateReq.onreadystatechange = function () {
			if(rateReq.readyState == 4 && rateReq.status == 200 ) {
				var reply = JSON.parse(rateReq.responseText);
				var updatedMovie = reply.movie;
				movieRating = Math.round(updatedMovie.totalRating/updatedMovie.ratingCount);
				var curUser = reply.curUser;
				var userString = JSON.stringify(curUser);
				localStorage.setItem("currentUser",userString);
			}
		}
		rateReq.setRequestHeader("Content-type","Application/json");
		var data = JSON.stringify({"message": "Rated","movieId":movieId,"title": title, "rating":rating,"time":Date.now()});
		rateReq.send(data);
	});
	
	//star10
	var star10 = document.getElementById("star10");
	star10.addEventListener('mouseover',function(){
		var val = 10;
		for(var i = 1; i<= val;i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/star.png";
		}
		for(var i = val+1; i <= 10; i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/empty_star.png";
		}
	})
	star10.addEventListener('mouseout',function(){
		var val = movieRating;
		for(var i = 1; i<= val;i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/star.png";
		}
		for(var i = val+1; i <= 10; i++){
			var ratingImage = document.getElementById("star"+i);
			ratingImage.src = "img/empty_star.png";
		}
	})
	star10.addEventListener('click',function(){
		var newRating;
		var rating = "10";
		var rateReq = new XMLHttpRequest();
		var url = "MovieServlet";
		rateReq.open('POST',url, false);
		rateReq.onreadystatechange = function () {
			if(rateReq.readyState == 4 && rateReq.status == 200 ) {
				var reply = JSON.parse(rateReq.responseText);
				var updatedMovie = reply.movie;
				movieRating = Math.round(updatedMovie.totalRating/updatedMovie.ratingCount);
				var curUser = reply.curUser;
				var userString = JSON.stringify(curUser);
				localStorage.setItem("currentUser",userString);
			}
		}
		rateReq.setRequestHeader("Content-type","Application/json");
		var data = JSON.stringify({"message": "Rated","movieId":movieId,"title": title, "rating":rating,"time":Date.now()});
		rateReq.send(data);
	});
	
	
}


function displayRating(rating){
	for(var i = 1; i<= rating;i++){
		var ratingImage = document.getElementById("star"+i);
		ratingImage.src = "img/star.png";
	}
	for(var i = rating+1; i <= 10; i++){
		var ratingImage = document.getElementById("star"+i);
		ratingImage.src = "img/empty_star.png";
	}
}

function createLabel(cell, name) {
	// create label
	var label = document.createElement('span');
	label.className = 'moviePage-label';
	label.innerHTML = name;
	label.style.display = 'none';
	label.style.width = name.length*6+'px';
	cell.appendChild(label);

	// event listeners
	cell.addEventListener('mouseover', function () {
		label.style.display = 'block';
	});
	cell.addEventListener('mouseout', function () {
		label.style.display = 'none';
	});
}

function getActorImage(actorName){
	console.log(actorName);
	var tmdbKey = "6fcdd94b2c3de6dca333cce3a2789227";
	var imageurl = "https://image.tmdb.org/t/p/w500";
	var url = "https://api.themoviedb.org/3/search/person?api_key="+tmdbKey
	+"&language=en-US&page=1&include_adult=false&query="+actorName;
	//console.log(url);
	var result;
	var req = new XMLHttpRequest();
	req.open('GET',url, false);
	req.onreadystatechange = function () {
		if(req.readyState == 4 && req.status == 200 ) {
			result = JSON.parse(req.responseText);
			//console.log(req.responseText);
		}
	}
	req.send(null);
	if(result.results[0].profile_path=='null'||result.results[0].profile_path==null){
		return "img/profile_image_default.png";
	}
	return imageurl+result.results[0].profile_path;
}

function getCinemateMovie(movieId,title){
	var movie;
	var url = "MovieServlet";
	var req = new XMLHttpRequest();
	req.open('POST',url,false);
	req.onreadystatechange = function(){
		if(req.readyState == 4 && req.status == 200 ) {
			movie = JSON.parse(req.responseText);
		}
	}
	var data = JSON.stringify({"message":"loadMovie","title":title,"movieId":movieId});
	req.send(data);
	return movie;
}

function getParameter(theParameter) { 
	var params = window.location.search.substr(1).split('&');
 
	for (var i = 0; i < params.length; i++) {
	    var p=params[i].split('=');
		if (p[0] == theParameter) {
		  return decodeURIComponent(p[1]);
		}
	}
  return false;
}

