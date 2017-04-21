function loadProfile(){
	var username = getParameter("username");
	var user;
	var message = "loadProfile";
	var url = "ProfileServlet";
	var req = new XMLHttpRequest();
	req.open('POST',url, false);
	req.onreadystatechange = function () {
		if(req.readyState == 4 && req.status == 200 ) {
			user = JSON.parse(req.responseText);
			//console.log(req.responseText);
		}
	}
	req.setRequestHeader("Content-type","Application/json");
	var data=JSON.stringify({"message": message,"username": username});
	req.send(data);
	
	var profileImage = document.getElementById("profileImage");
	profileImage.src = user.imageURL;
	var fullnameTitle = document.getElementById("fullname");
	fullnameTitle.innerHTML = user.fname+" "+user.lname;
	var usernameTitle = document.getElementById("username");
	usernameTitle.innerHTML = "@"+user.username;
	createFollowers(user);
	createFollowing(user);
	createEvents(user);
	
	//create followButton
	var currentUser = JSON.parse(localStorage.currentUser);
	if(user.username!=currentUser.username){
		var isCurrentUser = (currentUser.username==user.username);
		var isFollowing = checkIsFollowing(currentUser,user);
	
		var main = document.getElementById("main");
		var followButton = document.createElement("button");
		followButton.id = "follow-button";
		if(isFollowing){
			followButton.innerHTML = "Unfollow";
		}
		else{
			followButton.innerHTML = "Follow";
		}
	
		followButton.onclick = function(){
			var updatedUser;
			var updatedCurUser;
			if(isFollowing){
				message = "unfollow";
				this.innerHTML = "Follow";
			}
			else{
				message = "follow";
				this.innerHTML = "Unfollow";
			}
			isFollowing = !isFollowing;
			//send request to servlet
			var xhttp = new XMLHttpRequest();
			xhttp.open('POST',url, false);
			xhttp.onreadystatechange = function () {
				if(xhttp.readyState == 4 && xhttp.status == 200 ) {
					var wrapper = JSON.parse(xhttp.responseText);
					updatedUser = wrapper.user;
					updatedCurUser = wrapper.curUser;
	
				}
			}
			xhttp.setRequestHeader("Content-type","Application/json");
			var val =JSON.stringify({"message": message,"username": username});
			xhttp.send(val);
			
			//update local info
			createFollowers(updatedUser);
			localStorage.setItem("currentUser", JSON.stringify(updatedCurUser));
		}
		main.appendChild(followButton);
	}
}



//return true if currentUser is following the user
function checkIsFollowing(currentUser, user){
	for(var i = 0; i < user.followers.length;i++){
		if(currentUser.username==user.followers[i]){
			
			return true;
		}
	}
	return false;
}


function createFollowers(user){
	var follower_list = document.getElementById("follower-list");
	while (follower_list.firstChild) {
	    follower_list.removeChild(follower_list.firstChild);
	}
	for(var i = 0; i < user.followers.length;i++){
		var listItem = document.createElement('p');
		listItem.innerHTML = user.followers[i];
		listItem.id="listItem";
		
		var listLink = document.createElement('a');
		listLink.href="Profile.jsp?username="+user.followers[i];
		listLink.id="user-link";
		
		listLink.appendChild(listItem);
		follower_list.appendChild(listLink);
	}
}


function createEvents(user){
	var eventContainer = document.getElementById("event-container");
	//sort the feed by time
	
	user.feed.sort(function(a,b){
		return parseInt(b.time)-parseInt(a.time);
	});
	
	for(var i = 0; i < user.feed.length;i++){

		var actionImage = document.createElement("img");
		var action = user.feed[i].action;
		if(action!="Rated"){
			actionImage.src="img/"+action.toLowerCase()+".png";
		}
		else{
			var roundedRating = Math.round(user.feed[i].rating/2);
			actionImage.src = "img/rating" + roundedRating + ".png";
		}
		actionImage.id="action-image";
		eventContainer.appendChild(actionImage);
		
		var actionText = document.createElement("p");
		actionText.id="action-text";
		actionText.innerHTML=user.feed[i].action.toLowerCase();
		eventContainer.appendChild(actionText);
		
		var movieName = document.createElement("a");
		movieName.id="movie-name";
		movieName.innerHTML = user.feed[i].movieName;
		movieName.href = "Movie.jsp?id="+user.feed[i].movieId;
		eventContainer.appendChild(movieName);
		
		var eventTime = document.createElement('span');
		eventTime.className = "eventTime";
		eventTime.textContent = getTime(user.feed[i].time);
		eventContainer.appendChild(eventTime);
		var newline = document.createElement("br");
		eventContainer.appendChild(newline);
	}
	
}


function createFollowing(user){
	var following_list = document.getElementById("following-list");
	while (following_list.firstChild) {
	    following_list.removeChild(following_list.firstChild);
	}
	for(var i = 0; i < user.following.length;i++){
		var listItem = document.createElement('p');
		listItem.innerHTML = user.following[i];
		listItem.id="listItem";
		
		var listLink = document.createElement('a');
		listLink.href="Profile.jsp?username="+user.following[i];
		listLink.id="user-link";
		
		listLink.appendChild(listItem);
		following_list.appendChild(listLink);
	}
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

function getTime(time){
	dayOption = {month:'long',day:'numeric'};
	timeOption = {hour:"2-digit",minute:"2-digit",hour12:true}
	var date = new Date(parseInt(time));
	var time1 = date.toLocaleDateString('en-US', dayOption);
	var time2 = date.toLocaleTimeString('en-US', timeOption);
	return time1+" at "+time2;
}