<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="database.object.Course"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="Design.css" />
		<!-- Compiled and minified CSS -->
		<link rel="stylesheet" type="text/css" href="css/materialize.css" />

		<!-- Compiled and minified JavaScript -->
		<script
			src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
	</head>

	<body>
	<%
      List<Course> userCourses = (List)request.getSession().getAttribute("courses");
   	%>
	
		<div class="background1"></div>
		<div class="content">
			<nav class="transparent z-depth-0">
			<div class="nav-wrapper">
				<font id="title">LiveClass</font>
				<ul class="right hide-on-med-and-down">
					<li><a href="index.html" id="navBtns">Home</a></li>
					<li><a href="login.html" id="navBtns">Login</a></li>
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
		                  for(int i = 0; i < userCourses.size(); i++) {
		                     Course currCourse = userCourses.get(i);
		                     String prefix = currCourse.getCoursePrefix();
		                     String number = currCourse.getCourseNumber();
		                     String name = currCourse.getCourseName();
		                     
		               %>
		                  <tr>
		                     <td><%= prefix + " " + number + " - " + name %></td>
		   
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
							<tr>
								<td>CSCI 104 Data Structures and Software Development</td>
	
	
							</tr>
							<tr>
								<td>CSCI 103 Introduction to Programming</td>
	
							</tr>
						</tbody>
					</table>
	
				</div>
			</div>
		</div>
	</body>
</html>