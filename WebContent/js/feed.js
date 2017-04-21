/**
 * 
 */
function getTime(time){
	dayOption = {month:'long',day:'numeric'};
	timeOption = {hour:"2-digit",minute:"2-digit",hour12:true}
	var date = new Date(parseInt(time));
	var time1 = date.toLocaleDateString('en-US', dayOption);
	var time2 = date.toLocaleTimeString('en-US', timeOption);
	return time1+" at "+time2;
}


function loadFeed(){
	var result;
	var url = "FeedServlet";
	var req = new XMLHttpRequest();
	req.open("GET",url,false);
	req.onreadystatechange = function(){
		if(req.readyState == 4 && req.status == 200)
		{
			result = JSON.parse(req.responseText);
		}
	}
	req.send(null);
	
	//sort the events
	result.sort(function(a,b){
		return parseInt(b.time)-parseInt(a.time);
	});
	
	var feedTable = document.getElementById("feed-table")
	for(var i = 0; i < result.length; i++){
		var row = document.createElement("tr");
		
		//profile image
		var profileCell = document.createElement("td");
		var profileContainer = document.createElement("div");
		profileContainer.id="profileContainer";
		var profileImage = document.createElement("img");
		profileImage.src = result[i].profileImg;
		if(profileImage.src==""){
			profileImage.src="img/profile_image_default.png";
		}
		profileImage.id ="profile-image";
		var profileLink = document.createElement("a");
		profileLink.href = "Profile.jsp?username="+result[i].username;
		profileLink.appendChild(profileImage);
		profileContainer.appendChild(profileLink);
		createLabel(profileContainer,result[i].fullname);
		profileCell.appendChild(profileContainer);
	

		row.appendChild(profileCell);
		
		
		//action image
		var actionContainer = document.createElement("div");
		actionContainer.id = "actionContainer";
		var actionCell = document.createElement("td");
		var actionImage = document.createElement("img");
		var action = result[i].action;
		if(action!="Rated"){
			actionImage.src="img/"+action.toLowerCase()+".png";
		}
		else{
			var roundedRating = Math.round(result[i].rating/2);
			actionImage.src = "img/rating" + roundedRating + ".png";
		}
		actionImage.id="table-image";
		actionContainer.appendChild(actionImage);
		createLabel(actionContainer,action.toLowerCase());
		actionCell.appendChild(actionContainer);
		row.appendChild(actionCell)
		
		//movie image
		var movieContainer = document.createElement("div");
		movieContainer.id="movieContainer";
		var movieCell = document.createElement("td");
		var movieImage = document.createElement("img");
		var movieLink = document.createElement("a");
		movieLink.href = "Movie.jsp?id="+result[i].movieId;
		movieImage.src = getMovieImage(result[i].movieId);
		console.log(movieImage.src);
		movieImage.id="table-image";
		movieLink.appendChild(movieImage);
		movieContainer.appendChild(movieLink);
		createLabel(movieContainer, result[i].movieName);
		movieCell.appendChild(movieContainer);
		row.appendChild(movieCell);
		//still under test
		var timeCell = document.createElement("td");
		timeCell.textContent = getTime(result[i].time);
		//console.log(result[i].time);
		timeCell.style.paddingLeft = "10px";
		row.appendChild(timeCell);
		
		feedTable.appendChild(row);
	}
	
}

function getMovieImage(imdbID){
	var movie;
	var url = "http://www.omdbapi.com/?i=";
	var req = new XMLHttpRequest();
	req.open("GET",url+imdbID,false);
	req.onreadystatechange = function(){
		if(req.readyState == 4 && req.status == 200)
		{
			movie = JSON.parse(req.responseText);
		}
	}
	req.send(null);
	return movie.Poster;
}

function createLabel(cell, name) {
	// create label
	var label = document.createElement('span');
	label.className = 'feed-label';
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