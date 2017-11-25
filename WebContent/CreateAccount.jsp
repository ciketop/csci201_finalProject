<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.object.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
   
   <link rel="stylesheet" type="text/css" href="css/Design.css" />
 <!-- Compiled and minified CSS -->
  <link rel="stylesheet" type="text/css" href="css/materialize.css" />

  <!-- Compiled and minified JavaScript -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
  <script>
  	function create() {
		var xhttp = new XMLHttpRequest();
		var uname = document.createForm.uname.value;
		var psw = document.createForm.psw.value;
		var fname = document.createForm.fname.value;
		var lname = document.createForm.lname.value;
		var email = document.createForm.email.value
     	xhttp.open("GET", "create.jsp?username="+uname+"&password="+psw+"&fname="+fname+"&lname="+lname+"&email="+email, false);
     	xhttp.send();
	  	if (xhttp.responseText.trim().length > 0) {
	  	  console.log("response: " + xhttp.responseText)
	       document.getElementById("err").innerHTML = xhttp.responseText;
	       console.log("Returning false");
	       return false;
	     }
	  	console.log("Returning true");
	     return true;
	}
  </script>
   </head>
   
   <body>
   
	  <div class="background"></div>
	  <div class="content">
	  
 	 <a href="index.jsp" id = "loginTitle">LiveClass</a>
	  	
	  	<div class = "row"></div>
	  	<div class = "row"></div>
	  	<div class = "row"></div>
	  	<div class = "row"></div>
	  	<div class = "row"></div>
	  	<div class = "row"></div>
	  	<div class="row">
	  	
	  		
	  		<div class="col s12" id = "createBox">
		  		<form name="createForm" method="POST" action="queryClasses" onsubmit="return create()">
		  			<span id="err"></span>
		  			<label class="col s12" id = "createLabel"><b>Username</b></label>
		  			<div class="row"></div>
	    				<input type="text" placeholder="Enter Username" name="uname" required>
					
	    				
	    				<label class="col s12" id = "createLabel"><b>Password</b></label>
	    				<div class="row"></div>
	    				<input type="password" placeholder="Enter Password" name="psw" required>
					
					
					<label class="col s12" id = "createLabel"><b>First Name</b></label>
		  			<div class="row"></div>
	    				<input type="text" placeholder="Enter First Name" name="fname" required>
	
					
					<label class="col s12" id = createLabel><b>Last Name</b></label>
		  			<div class="row"></div>
	    				<input type="text" placeholder="Enter Last Name" name="lname" required>
					
					
					<label class="col s12" id = "createLabel"><b>Email Address</b></label>
		  			<div class="row"></div>
	    				<input type="email" placeholder="Enter Email Address" name="email" required>
					
					<div class="row"></div>
					
					
					<div class="col s12 push-s5">	
	    				<button class="btn" id = "createButton" type="submit"><b>Create Account</b></button>
	    				<input type="checkbox" checked="checked">
	  				</div>
	  			</form>
  			</div>
  			
  			
	  		
	  		
	  	</div>
  
	</div>
  </body>
</html>