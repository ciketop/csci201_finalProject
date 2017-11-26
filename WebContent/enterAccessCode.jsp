<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="database.object.User" %>
<%@ page import="database.object.Course"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
   
   <link rel="stylesheet" type="text/css" href="css/Design.css" />
 <!-- Compiled and minified CSS -->
  <link rel="stylesheet" type="text/css" href="css/materialize.css" />

  <!-- Compiled and minified JavaScript -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
  <script>
  	function enroll() {
		var xhttp = new XMLHttpRequest();
		var accesscode = document.enterAccessCodeForm.accesscode.value;
     	xhttp.open("GET", "enroll.jsp?accesscode="+accesscode, false);
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
   <%
		User currUser = (User)request.getSession().getAttribute("user");
    		List<Course> userCourses = (List<Course>)request.getSession().getAttribute("courses");
	  	//List<Course> publicCourse = (List<Course>)request.getSession().getAttribute("publicCourse");
	%>
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
		  		<form name="enterAccessCodeForm" method="POST" action="queryClasses" onsubmit="return enroll()">
		  			<span id="err"></span>
	    				
		  			
		  			<label class="col s12" id = "createLabel"><b>Access Code</b></label>
		  			<div class="row"></div>
	    				<input type="text" placeholder="Please enter the access code of the class" name="accesscode" required>
					
	    				
	    				
					
					<div class="row"></div>
					
					
					<div class="col s12 push-s5">	
	    				<button class="btn" id = "createButton" type="submit"><b>Enroll</b></button>
	    				<input type="checkbox" checked="checked">
	  				</div>
	  			</form>
  			</div>
  			
  			
	  		
	  		
	  	</div>
  
	</div>
  </body>
</html>