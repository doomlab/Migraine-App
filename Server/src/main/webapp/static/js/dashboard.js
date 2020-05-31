
function dashboard() {
  'use strict'
  var logged = isLoggedIn()
  if (!logged) {
	  window.location.replace("signin.html")
  }
  else {
	  window.location.replace("dash.html")
  } 
}

function register() {
	
	
}

function isLoggedIn () {
  const token = localStorage.getItem('authToken')
  if (!token) return false
  // TODO: Check token?  Later...
  return true
}

function onSigninClick() {
	var emailValue = document.getElementById("inputEmail").value;
	var passwordValue = document.getElementById("inputPassword").value;
	servletlogin(emailValue, passwordValue)
}

function servletlogin (email, password) {
	var request = new XMLHttpRequest()
	var data = '{ "username": "' + email + '", "password": "' + password + '"}'
	request.open('POST', 'https://nua.insufficient-light.com/nuaserver/login', true)
	request.setRequestHeader("Content-Type", "application/json")

	request.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var result = JSON.parse(this.responseText)
			
			localStorage.setItem('userId', result.userId)
			localStorage.setItem('authToken', result.authToken)
			localStorage.setItem('loginSource', "NUA")
			window.location.replace("dash.html");
		}
		else if(!http.status == 200) {
	        alert(http.responseText)
	    }
	}
	
	request.send(data)
}
