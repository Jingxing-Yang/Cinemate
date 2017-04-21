function login(){
	var errorMessage = "";
	var result = null;
	var resultText = "";
	var username = document.loginForm.username.value;
	var password = document.loginForm.password.value;
	
	var url = "LoginServlet";
	var req = new XMLHttpRequest();
	req.open("POST",url,false);
	req.onreadystatechange = function(){
		if(req.readyState == 4 && req.status == 200)
		{
			result = JSON.parse(req.responseText);
			resultText = req.responseText;
			errorMessage = result.error;
		}
	}
	
	req.setRequestHeader("Content-type","Application/json");
	var data = JSON.stringify({"username": username, "password": password});
	req.send(data);
	
	if(errorMessage=="None"){
		var currentUser = result.currentUser;
		localStorage.setItem("currentUser", JSON.stringify(currentUser));
		return true;
	}
	else{
		var displayError = document.getElementById("error");
		displayError.innerHTML = errorMessage;
		return false;
	}
}
