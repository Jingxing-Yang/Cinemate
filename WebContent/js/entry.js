function checkfile(){
	var errorMessage = "";
	var filePath = document.fileSubmitForm.filePath.value;
	
	var url = "EntryServlet";
	var req = new XMLHttpRequest();
	req.open('POST',url, false);
	req.onreadystatechange = function () {
		if(req.readyState == 4 && req.status == 200 ) {
			errorMessage = req.responseText;
		}
	}
	
	
	req.setRequestHeader("Content-type","Application/json");
	var data=JSON.stringify({"filePath": filePath});
	req.send(data);
	
	if(errorMessage!='None')
	{
		displayError(errorMessage);
		return false;
	}
	else
	{
		console.log('Valid file');
		return true;
	}
}		

function displayError(error){
	var displayMessage = document.getElementById('error');
	displayMessage.innerHTML = error;
}

