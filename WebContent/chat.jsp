<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.object.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		
		<link rel="stylesheet" type="text/css" href="Design.css" />
		<link rel="stylesheet" type="text/css" href="css/materialize.css" />
		
		<!-- Compiled and minified JavaScript -->
		<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
		 
		<meta charset="UTF-8">
		
		<title>Web Socket Chat Client</title>
		
		<script>
			var socket;
			function connectToServer(){
				socket = new WebSocket("ws://localhost:8080/csci201_finalProject/chatroom");
				socket.onopen = function(event){
					document.getElementById("mychat").innerHTML +=" User connected!<br />";
				}
				
				socket.onmessage = function(event){
					document.getElementById("mychat").innerHTML += event.data + "<br />";
				}
				
				socket.onclose = function(event){
					document.getElementById("mychat").innerHTML += "disconnected!<br />";
				}
				
			}	
			
			function sendMessage(name){
				var d = new Date();
				var n = d.toLocaleTimeString();
				n=n.substring(0,4);
				if(document.chatform.message.value !=""){
					socket.send(n + " - " + name + ": " + document.chatform.message.value); //need to change User to login user
					document.getElementById("myArea").value = "";
				}
				return false; 
		
			}
			
			function errMsg() {
				document.getElementById("mychat").innerHTML += "<span>You must be logged in to send messages!</span><br />" ;
				return false;
			}
			
		</script>
	</head>
	<body onload="connectToServer()">
	
		<nav class="transparent z-depth-0">
	    		<div class="nav-wrapper">
	      		<font id = "titleChat">LiveClass</font>
	      		<ul class="right hide-on-med-and-down">
	      		<li><a href="index.html" id="navBtns">Home</a></li>
	        	 	<li><a href="login.html" id="navBtns">Login</a></li>
	      			<!-- <li><a href="index.html" id="navBtns" class="waves-effect waves-light btn">Home</a></li>
	        			<li><a href="login.html" id="navBtns" class="waves-effect waves-light btn">Login</a></li> -->
	      		</ul>
	    		</div>
	  	</nav> 
	  
		<div id="chatbox"> 
			<div id="mychat"></div>
			
			<%
				User currUser = (User) request.getSession().getAttribute("user");
				if(currUser != null) {
					String name = currUser.getFname();
			%>
					<div id="chattextarea">
						<form name="chatform" onsubmit="return sendMessage('<%=name%>')"">
							<input id="myArea" type="text" name="message" placeholder="Type here..">
							<input class = "btn" id="button1" type="submit" name="submit" value="Send Message" />
						</form>
					</div>
			<%
				}
				else {
					
			%>
					<div id="chattextarea">
						<form name="chatform" onsubmit="return errMsg();">
							<input id="myArea" type="text" name="message" placeholder="Type here..">
			
							<input class = "btn" id="button1" type="submit" name="submit" value="Send Message" />
						</form>
					</div>
			<%
				}
			%>
		</div>
		<br />
		
	</body>
</html>