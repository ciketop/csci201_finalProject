<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="database.object.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

    <link rel="stylesheet" type="text/css" href="css/Design.css"/>
    <!-- Compiled and minified CSS -->
    <link rel="stylesheet" type="text/css" href="css/materialize.css"/>

    <!-- Compiled and minified JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
</head>

<body>
<%
    User currUser = (User) request.getSession().getAttribute("user");
%>
<nav class="transparent z-depth-0">
    <div class="nav-wrapper">
        <font data-shadow='LiveClass' id="title">LiveClass</font>
        <ul class="right hide-on-med-and-down">
            <li><a href="" id="navBtns">Home</a></li>
            <li><a href="queryClasses" id="navBtns">Lectures</a>
                    <%
	      		if(currUser == null) {
	      	%>
            <li><a href="login.html" id="navBtns">Login</a></li>
            <%
            } else {
            %>
            <li><a href="logout.jsp" id="navBtns">Logout</a></li>
            <%
                }
            %>
        </ul>
    </div>
</nav>


<div class="row"></div>
<div class="row"></div>
<div class="row">
    <div class="images">
        <img id="picture"
             src="http://az616578.vo.msecnd.net/files/2016/04/26/635972672371801121644731629_singapore_lecture_bishop.jpg">
        <img id="picture"
             src="https://assets.pcmag.com/media/images/549411-the-best-laptops-for-college-students.jpg?thumb=y&width=740&height=417">
        <h2 class="col s12" id="welcome">Welcome To LiveClass!</h2>

        <h3 class="cols12" id="description">LiveClass is an interactive website created to better connect students,
            teachers, and any individual
            interested in learning. With access to lecture livestreams as well as live chats with course staff and other
            students, LiveClass provides a space
            where students can easily stay up to date on their classes and teachers can effectively communicate
            information through a sole online medium.</h3>


    </div>
</div>

<div class="row"></div>
<div class="row"></div>
<div class="row"></div>
<div class="row"></div>
<div class="row"></div>
<div class="row"></div>
<div class="row"></div>
<div class="row"></div>
<div class="row"></div>
<div class="row"></div>
<div class="row"></div>
<div class="row"></div>


<!-- <div class= "row">
    <div class="col s12"><a href="queryClasses" id = "viewLecturesButton" class="waves-effect waves-light btn-large">View All Lectures</a></div>
</div>  -->

<div class="row"></div>
<div class="row"></div>
<div class="row"></div>
<div class="row"></div>


</body>
</html>