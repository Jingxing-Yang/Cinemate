function printResult(){
	
	var searchType = getParameter("searchType").toLowerCase();
	var searchInput = getParameter("searchInput");
	searchType = searchType.replace(/\+/g, " ");
	searchInput = searchInput.replace(/\+/g, " ");
	//alert(searchType);
	//alert(searchInput);
	var result;
	var mtype = "";
	var req = new XMLHttpRequest();
	if(searchType=="user"){
		var url = "SearchServlet";
		req.open('POST',url, false);
		req.onreadystatechange = function () {
			if(req.readyState == 4 && req.status == 200 ) {
				result = JSON.parse(req.responseText);
				console.log(req.responseText);
			}
		}
		req.setRequestHeader("Content-type","Application/json");
		var data=JSON.stringify({"searchType": searchType,"searchInput": searchInput});
		req.send(data);
	}
	else if(searchType=="movie"){
		
		mtype = searchInput.substring(0,searchInput.indexOf(':')).trim().toLowerCase();
		var mKey = searchInput.substring(searchInput.indexOf(':')+1).trim();
		if(mtype=="title"){
			console.log(mKey.replace(/ /g,"+"));
			var url = "http://www.omdbapi.com/?type=movie&s="+mKey.replace(/ /g,"+");
			req.open("Get",url,false);
			req.onreadystatechange = function () {
				if(req.readyState == 4 && req.status == 200 ) {
					result = JSON.parse(req.responseText).Search;
					console.log(req.responseText);
					//console.log(req.responseText);
				}
			}
			req.send(null);
		}
		else if(mtype="actor"){
			var tmdbKey = "6fcdd94b2c3de6dca333cce3a2789227";
			var url = "https://api.themoviedb.org/3/search/person?api_key="+tmdbKey
			+"&&language=en-US&page=1&include_adult=false&query="+mKey.replace(/ /g,"%20");
			req.open("Get",url,false);
			req.onreadystatechange = function () {
				if(req.readyState == 4 && req.status == 200 ) {
					result = JSON.parse(req.responseText).results[0].known_for;
					//console.log(req.responseText);
				}
			}
			req.send(null);
			
			for(var i = 0; i < result.length;i++){
				var idReq = new XMLHttpRequest();
				var url = "https://api.themoviedb.org/3/movie/"+result[i].id
				+"?language=en-US&api_key="+tmdbKey;
				idReq.open("Get",url,false);
				idReq.onreadystatechange = function () {
					if(idReq.readyState == 4 && idReq.status == 200 ) {
						result[i] = JSON.parse(idReq.responseText);
						//console.log(req.responseText);
					}
				}
				idReq.send(null);
			}
		}
	}
	
	var resultBox = document.getElementById("resultBox");
	for(var i = 0; i < result.length;i++){
		var listItem = document.createElement("a");	
		listItem.id = "resultItem";
		if(searchType=="movie"){
			if(mtype=="title"){
				listItem.innerHTML = result[i].Title;
				listItem.href="Movie.jsp?id="+result[i].imdbID;
			}
			else if(mtype="actor"){
				listItem.innerHTML = result[i].title;
				listItem.href="Movie.jsp?id="+result[i].imdb_id;
			}
		}
		else if(searchType=="user"){
			listItem.innerHTML = result[i];
			listItem.href="Profile.jsp?username="+result[i];
		}
		var nl = document.createElement("br");
		resultBox.appendChild(listItem);
		resultBox.appendChild(nl);
	}
}
/*
function printList(result){
	var resultBox = document.getElementById("resultBox");
	for(var i = 0; i < result.length();i++){
		var listItem = document.createElement("a");
		listItem.innerHTML = result[i];
		listItem.id = "resultItem";
		listItem.href="Movie.jsp?title="+result[i];
		resultBox.appendChild(listItem);
	}
}

*/
function getUrlVars() {
	var vars = {};
	var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
	vars[key] = value;
	});
	return vars;
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