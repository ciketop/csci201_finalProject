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
  	function sendEmail() {
		var xhttp = new XMLHttpRequest();
		var email = document.sendEmailForm.email.value;
		var id = document.sendEmailForm.course.value;
     	xhttp.open("GET", "send.jsp?email="+email+"&courseID="+id, false);
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
		  		<form name="sendEmailForm" method="POST" action="queryClasses" onsubmit="return sendEmail()">
		  			<span id="err"></span>
		  			
		  			<label class="col s12" id = "createLabel"><b>Classes</b></label>
	    				<div class="row"></div>
	    				<select name="course">
	    				<%
	    					if(userCourses != null) {
			                  for(int i = 0; i < userCourses.size(); i++) {
			                     Course currCourse = userCourses.get(i);
			                     int ID = currCourse.getCourseID();
			                     String prefix = currCourse.getCoursePrefix();
			                     String number = currCourse.getCourseNumber();
			                     String name = currCourse.getCourseName();
			                     String courseName = prefix+" "+number+" - "+name;
			                     System.out.println("course " + courseName);
	    				%>
	    					<option value=<%= ID %>><%= courseName %></option>
	    				<%
			           		}
	    						System.out.println("in sendAccessCode");
	    					}
	    				%>
	    				</select>
	    				<div class="row"></div>
	    				
		  			
		  			<label class="col s12" id = "createLabel"><b>Email Address</b></label>
		  			<div class="row"></div>
	    				<input type="text" placeholder="Please enter the email address that you wish to send the access code" name="email" required>
					
	    				
	    				
					
					<div class="row"></div>
					
					
					<div class="col s12 push-s5">	
	    				<button class="btn" id = "createButton" type="submit"><b>Send</b></button>
	    				<input type="checkbox" checked="checked">
	  				</div>
	  			</form>
  			</div>
  			
  			
	  		
	  		
	  	</div>
  
	</div>
  </body>
</html>