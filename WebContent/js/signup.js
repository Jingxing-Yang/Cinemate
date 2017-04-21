function signup(){
	//alert("here");
	var errorMessage = "";
	var result;
	
	var fullname = document.signupForm.fullname.value;
	var username = document.signupForm.username.value;
	var password = document.signupForm.password.value;
	var image = document.signupForm.image.value;
	var url = "SignupServlet";
	var req = new XMLHttpRequest();
	req.open("POST",url,false);
	req.onreadystatechange = function(){
		if(req.readyState == 4 && req.status == 200)
		{
			result = JSON.parse(req.responseText);
			errorMessage = result.error;
		}
	}
	
	req.setRequestHeader("Content-type","Application/json");
	var data = JSON.stringify({"fullname": fullname, "username": username, "password": password, "image": image});
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
	return false;
}