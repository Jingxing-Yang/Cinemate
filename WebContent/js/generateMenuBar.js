
function addMenuBar(){
	var searchByMovie = true;
	var topPart = document.getElementById("top");
	var currentUser = JSON.parse(localStorage.currentUser);
	var menuBar = document.createElement("div");
	menuBar.id = "menu-bar";
	
	//create feed link button
	var feedCell = document.createElement('div');
	feedCell.id = "feedCell";
	var feedLink = document.createElement('a');
	feedLink.href = "Feed.jsp"
	var feedicon = document.createElement("img");
	feedicon.src = "img/feed_icon.png";
	feedicon.id = "feed_icon";

	feedLink.appendChild(feedicon);
	feedCell.appendChild(feedLink);
	createLabel(feedCell,"View Feed")
	menuBar.appendChild(feedCell);	
	//menuBar.appendChild(feedLink);
	
	
	
	
	//create profile link button
	var profileCell = document.createElement("div");
	profileCell.id = "profileCell";
	var profileLink = document.createElement("a");
	profileLink.href = "Profile.jsp?username="+currentUser.username;
	var profileImage = document.createElement("img");
	profileImage.src = currentUser.imageURL;
	if(profileImage.src==""){
		profileImage.src = "img/profile_image_default.png";
	}
	profileImage.id = "profile_image";
	profileLink.appendChild(profileImage);
	profileCell.appendChild(profileLink);
	createLabel(profileCell,"View profile");
	menuBar.appendChild(profileCell);
	
	//create search form
	var searchForm = document.createElement("form");
	searchForm.action = "Search.jsp";
	searchForm.method = "GET";

	
	var searchInput = document.createElement("input");
	searchInput.id = "searchInput";
	searchInput.type = "text";
	searchInput.name = "searchInput";
	searchInput.placeholder = "Search movies";
	searchForm.appendChild(searchInput);
	

	var searchTypeCell = document.createElement("div");
	searchTypeCell.id="searchTypeCell";
	var searchTypeIcon = document.createElement("img");
	searchTypeIcon.id = "searchTypeIcon";
	searchTypeIcon.src = "img/clapperboard_icon.png";
	searchTypeIcon.onclick = function(){
		searchByMovie = !searchByMovie;
		if(searchByMovie){
			this.src="img/clapperboard_icon.png";
			searchInput.placeholder = "Search movies";
			searchType.value = "movie";
		}
		else{
			this.src="img/user_icon.png";
			searchInput.placeholder = "Search users";
			searchType.value = "user";
		}
	}
	
	searchTypeCell.appendChild(searchTypeIcon);
	createLabel(searchTypeCell,"Toggle search type");
	searchForm.appendChild(searchTypeCell);
	
	
	//create search button
	var searchButtonCell = document.createElement("div");
	searchButtonCell.id="searchButtonCell";
	var searchButton = document.createElement("button");
	searchButton.id = "searchButton";
	searchButton.type = "submit";
	var buttonImg = document.createElement("img");
	buttonImg.id = "buttonImg";
	buttonImg.src = "img/search_icon.png";
	buttonImg.alt = "submit";
	searchButton.appendChild(buttonImg);
	searchButtonCell.appendChild(searchButton);
	createLabel(searchButtonCell,"Search");
	searchForm.appendChild(searchButtonCell);
	
	var searchType = document.createElement("input");
	searchType.id = "searchType";
	searchType.type = "text";
	searchType.name = "searchType";
	searchType.value = "movie";
	searchType.readOnly = true;
	searchForm.appendChild(searchType);
	
	
	menuBar.appendChild(searchForm);
	
	var barTitle = document.createElement("p");
	barTitle.id="barTitle";
	barTitle.innerHTML = "Cinemate";
	menuBar.appendChild(barTitle);
	
	//create logout link button
	var logoutCell = document.createElement("div");
	logoutCell.id = "logoutCell";
	var logoutLink = document.createElement("a");
	logoutLink.href = "index.jsp";
	var logoutImage = document.createElement("img");
	logoutImage.src = "img/logout_icon.png";
	logoutImage.id = "logout";
	logoutLink.appendChild(logoutImage);
	logoutCell.appendChild(logoutLink);
	createLabel(logoutCell,"Log out");
	menuBar.appendChild(logoutCell);
	
	//create exit link button
	/*
	var exitCell = document.createElement("div");
	exitCell.id="exitCell";
	var exitLink = document.createElement("a");
	exitLink.href = "entry_jingxiny.jsp";
	var exitImage = document.createElement("img");
	exitImage.src = "img/exit_icon.jpg";
	exitImage.id = "exit";
	exitLink.appendChild(exitImage);
	exitCell.appendChild(exitLink);
	createLabel(exitCell, "Exit");
	menuBar.appendChild(exitCell);
	*/
	topPart.appendChild(menuBar);
}

function createLabel(cell, name) {
	// create label
	var label = document.createElement('span');
	label.className = 'label';
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
