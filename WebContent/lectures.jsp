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
		<script
			src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
	</head>

	<body>
	<%
		User currUser = (User)request.getSession().getAttribute("user");
    		List<Course> userCourses = (List<Course>)request.getSession().getAttribute("courses");
	  	List<Course> publicCourse = (List<Course>)request.getSession().getAttribute("publicCourse");
	%>
	
		<div class="background1"></div>
		<div class="lecContent">
			<nav class="transparent z-depth-0">
			<div class="nav-wrapper">
				<font id="title">LiveClass</font>
				<ul class="right hide-on-med-and-down">
				<li><a href="index.jsp" id="navBtns">Home</a></li>
				<%
					if(currUser != null) {
						if(userCourses.size() == 0) {
				%>			
						<li><a href="enterAccessCode.jsp" id="navBtns">Enroll</a></li>	
				<%			
						}
						for(Course course : userCourses) {
							if(currUser.getPriv(course.getCourseID()) == 1) { 
				%>
						<li><a href="sendAccessCode.jsp" id="navBtns">Access Code</a></li>
				<%
								break;
							}
						}
						for(Course course : userCourses) {
							if(currUser.getPriv(course.getCourseID()) == 2) {
				%>
						<li><a href="enterAccessCode.jsp" id="navBtns">Enroll</a></li>
				<%
								break;
							}
						}
					}
					if(currUser == null) {
				%>
						<li><a href="login.html" id="navBtns">Login</a></li>
				<%
					}
					else {
				%>
						<li><a href="logout.jsp" id="navBtns">Logout</a></li>
				<%
					}
				%>
				
					<!-- <li><a href="index.html" id="navBtns" class="waves-effect waves-light btn">Home</a></li>
		        			<li><a href="login.html" id="navBtns" class="waves-effect waves-light btn">Login</a></li> -->
				</ul>
			</div>
			</nav>
	
			<div class="container">
	
				<div class="row"></div>
				<div class="row"></div>
				<div class="row"></div>
				<div class="row"></div>
				<div class="row"></div>
				<div class="row"></div>
				<div class="row">
	
					<h4>Your Courses:</h4>
		            <table class="bordered" id="courseTable">
		               <thead>
		                  <tr>
		   
		                  </tr>
		               </thead>
		   
		               <tbody>
		               <% 
		               if(userCourses != null) {
		                  for(int i = 0; i < userCourses.size(); i++) {
		                     Course currCourse = userCourses.get(i);
		                     int ID = currCourse.getCourseID();
		                     String prefix = currCourse.getCoursePrefix();
		                     String number = currCourse.getCourseNumber();
		                     String name = currCourse.getCourseName();
		                     String courseName = prefix + "-" + number;
		                     int priv = currUser.getPriv(ID);
		               %>
		                  <tr>
		                     <td><a href="chat.jsp?course=<%=courseName%>&id=<%=ID%>"><span <%= priv == 1 ? "class=\"red\"" : "" %>><%= prefix+" "+number+" - "+name %></span></a></td>
		                  </tr>
		               <%
		                  }
		               }
		               else {
		               %>
		                  <tr>
		                     <td><span id="red">Please log in to see your personal courses!</span></td>
		                  </tr>
		               <%
		               }
		               %>
		   
		               </tbody>
		            </table>
				</div>
	
	
				<div class="row"></div>
				<div class="row"></div>
				<div class="row"></div>
	
				<div class="row">
	
					<h4>All Public Lectures:</h4>
					<table class="bordered" id="courseTable">
						<thead>
							<tr>
	
							</tr>
						</thead>
	
						<tbody>
		               <% 
		               //blah blah
		                  for(int i = 0; i < publicCourse.size(); i++) {
		                     Course currCourse = publicCourse.get(i);
		                     int ID = currCourse.getCourseID();
		                     String prefix = currCourse.getCoursePrefix();
		                     String number = currCourse.getCourseNumber();
		                     String name = currCourse.getCourseName();
		                     String courseName = prefix + "-" +  number;
		                     
		               %>
		                  <tr>
		                     <td><a href="chat.jsp?course=<%=courseName%>&id=<%=ID%>"><%= prefix + " " + number + " - " + name %></a></td>
		   
		                  </tr>
		               <%
		                  }
		               %>
		   
		               </tbody>
					</table>
	
				</div>
			</div>
		</div>
	</body>
</html>