<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.object.User" %>
<%@ page import="config.ConfigString" %>
<%
	if (request.getSession().getAttribute("socketAddress") == null) {
		request.getSession().setAttribute("socketAddress", ConfigString.socketAddress);
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		
		<link rel="stylesheet" type="text/css" href="css/Design.css" />
		<link rel="stylesheet" type="text/css" href="css/materialize.css" />
		
		<!-- Compiled and minified JavaScript -->
		<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
		 
		<meta charset="UTF-8">
		
		<title>Web Socket Chat Client</title>
		<%
			// Get some of the basic info that will be needed for this page
			// Set privilege to 0 for now, this will be changed soon
			int priv = 0;
			User currUser = (User) request.getSession().getAttribute("user");
			String courseName = request.getParameter("course");
			int ID = Integer.parseInt(request.getParameter("id"));
			
			System.out.println("chat.jsp for course: " + courseName);
			
			// If no user is logged in, this is probably a public course, set
			// privilege to 2 (viewer)
			if(currUser == null) {
				priv = 2;
			}
			// If user is logged in, call getPriv to see if it's is a student or instructor
			else {
				priv = currUser.getPriv(ID);
			}
			System.out.println("priv: " + priv);
			
		%>
		<!-- This is for the chat server -->
		<script>
			var socket;
			function connectToServer(){
				/* Create a new Websocket with the correct path, and parameter class */
				socket = new WebSocket("ws://${sessionScope.socketAddress}/" +
			            "${not empty pageContext.request.contextPath ? pageContext.request.contextPath: ""}" + "/chatroom?class=" + "<%= courseName %>");

				socket.onopen = function(event){
					document.getElementById("mychat").innerHTML +="Connected to chat server<br />";
				};
				
				socket.onmessage = function(event){
					document.getElementById("mychat").innerHTML += event.data + "<br />";
				};
				
				socket.onclose = function(event){
					document.getElementById("mychat").innerHTML += "disconnected!<br />";
				};
				
				
			}	
			/* sendMessage(name) */
			/* sends a message to the chat server */
			function sendMessage(name){
				var d = new Date();
				var n = d.toLocaleTimeString();
				n=n.substring(0,4);
				if(document.chatform.message.value !=""){
					//socket.send(n + " - " + name + ": " + document.chatform.message.value); //need to change User to login user
				<%
					if(priv == 2) {
				%>
					socket.send("<span id=\"blue\">" + name + ": " + "</span>" + document.chatform.message.value);
				<%
					}
					else if(priv == 1) {
				%>
					socket.send("<span id=\"gold\">" + name + ": " + "</span>" + document.chatform.message.value);
				<%
					}
				%>
					document.getElementById("myArea").value = "";
				}
				return false; 
		
			}
			/* return an error message in case user's not logged in */
			function errMsg() {
				document.getElementById("mychat").innerHTML += "<span id=\"red\">You must be logged in to send messages!</span><br />" ;
				document.getElementById("myArea").value = "";
				return false;
			}
			
		</script>
	</head>
	<body onload="connectToServer()" style="overflow:scroll;">
	
		<nav class="transparent z-depth-0">
	    		<div class="nav-wrapper">
	      		<font id = "titleChat">LiveClass</font>
	      		<ul class="right hide-on-med-and-down">
	      		<li><a href="index.jsp" id="navBtns">Home</a></li>
	      		<li><a href="queryClasses" id="navBtns">Lectures</a></li>
	      	<%
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
	      		</ul>
	    		</div>
	  	</nav> 
	  
	  	
	  	<%
	  		// Disaply stream if user has privilege of 2(student or guest)
	  		if(priv == 2) {
	  	%>
	  		<h4 id="h3Chat"><%= "Watching: " + courseName %></h4>
		  	<div id="container">
		    		<img id="target" style="display: inline;" src="${pageContext.request.contextPath}/asset/images/noLivestream.jpg"/>
			</div>
			<script type="text/javascript">
			    // to see live stream on another computer, change localhost:8080 to the ip address of that computer
			    "use strict";
			    let videoSocket = new WebSocket("ws://${sessionScope.socketAddress}/" +
			            "${not empty pageContext.request.contextPath ? pageContext.request.contextPath: ""}"
                    + "/liveStreamVideo?class=" + "<%= courseName %>");
			    /* videoSocket.send("course:" + courseName); */
			    let target = document.getElementById("target");
			
			    videoSocket.onmessage = function (msg) {
			    		console.log("video received");
			        let url = URL.createObjectURL(msg.data);
			        target.onload = function () {
			            window.URL.revokeObjectURL(url);
			        };
			        target.src = url;
			    };

			    // audio -- read
                let audioSocket = new WebSocket("ws://${sessionScope.socketAddress}/" +
                    "${not empty pageContext.request.contextPath ?  pageContext.request.contextPath: ""}"
                    + "/liveStreamAudio?class=" + "<%= courseName %>");
                audioSocket.binaryType = 'arraybuffer';

                audioSocket.onmessage = function (msg) {
                    playSound(msg.data);
                };

                let audioContext = new (window.AudioContext || window.webkitAudioContext)();

                function playSound(arrayBuffer) {
                    // convert arrayBuffer to float 32 array
                    let buffer = new Float32Array(arrayBuffer);
                    let src = audioContext.createBufferSource();
                    let audioBuffer = audioContext.createBuffer(1, buffer.length, audioContext.sampleRate);

                    audioBuffer.getChannelData(0).set(buffer);
                    src.buffer = audioBuffer;
                    src.connect(audioContext.destination);
                    src.start();
                }
			</script>
		<%
	  		}
	  		// If privilege is 1 (instructor), record stream instead
	  		else if(priv == 1) {
		%>
			<h4 id="h3Chat"><%= "Recording: " + courseName %></h4>
			<div id="videoContainer">
			    
			    <video id="live"  autoplay="autoplay"
			           style="display: inline;"></video>
			    <canvas width="320" id="canvas" height="240" style="display: none"></canvas>
			    <!-- <canvas width="640" id="canvas" height="480" style="display: none"></canvas> -->
			</div>
			
			<script type="text/javascript">
			    let video = document.getElementById("live");
			    let canvas = document.getElementById("canvas");
			    let ctx = canvas.getContext('2d');
			
			    console.log("ws://${sessionScope.socketAddress}/" +
			            "${not empty pageContext.request.contextPath ? pageContext.request.contextPath: ""}"
			            + "/liveStreamVideo?class=" + "<%= courseName %>");
			    let videoSocket = new WebSocket("ws://${sessionScope.socketAddress}/" +
			        "${not empty pageContext.request.contextPath ? pageContext.request.contextPath: ""}"
			        + "/liveStreamVideo?class=" + "<%= courseName %>");
			    /* let videoSocket = new WebSocket("ws://192.168.50.166:8080/csci201_finalProject/liveStreamVideo"); */
			    
			    videoSocket.onopen = function () {
			        console.log("Connection to websocket for video");
			    };

                let audioSocket = new WebSocket("ws://${sessionScope.socketAddress}/" +
                    "${not empty pageContext.request.contextPath ? pageContext.request.contextPath: ""}"
                    + "/liveStreamAudio?class=" + "<%= courseName %>");
                audioSocket.binaryType = 'arraybuffer';

                audioSocket.onopen = function () {
                    console.log("Connection to websocket for audio");
                };
			
			    // user media constraints
			    let constraints = {
			        video: true,
			        audio: true
			    };
			    navigator.mediaDevices.getUserMedia(constraints)
			        .then(handleSuccess)
			        .catch(function (error) {
			            console.error("Unable to get video/audio stream!");
			            console.error(error);
			        });
			
			    timer = setInterval(
			        function () {
			            ctx.drawImage(video, 0, 0, canvas.width, canvas.height);
			            let data = canvas.toDataURL('image/jpeg', 1.0);
			
			            videoSocket.send(convertToBinary(data));
			        }, 1000/30);
			
			    function convertToBinary(dataURI) {
			        // convert base64 to raw binary data held in a string
			        // doesn't handle URLEncoded DataURIs
			        let byteString = atob(dataURI.split(',')[1]);
			
			        // separate out the mime component
			        let mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];
			
			        // write the bytes of the string to an ArrayBuffer
			        let ab = new ArrayBuffer(byteString.length);
			        let ia = new Uint8Array(ab);
			        for (let i = 0; i < byteString.length; i++) {
			            ia[i] = byteString.charCodeAt(i);
			        }
			
			        // write the ArrayBuffer to a blob, and you're done
			        return new Blob([ab]);
			    }

                function handleSuccess(stream) {
			        handleVideoSuccess(stream);
			        handleAudioSuccess(stream);
                }

                function handleVideoSuccess(stream) {
                    video.src = URL.createObjectURL(stream);
                    video.muted = true;
                }

                function handleAudioSuccess(stream) {
                    let context = new AudioContext();
                    let source = context.createMediaStreamSource(stream);
                    let processor = context.createScriptProcessor(4096, 1, 1);

                    source.connect(processor);
                    processor.connect(context.destination);

                    processor.onaudioprocess = function(e) {
                        // Do something with the data, i.e Convert this to WAV
                        audioSocket.send(e.inputBuffer.getChannelData(0).buffer);
                    };
                }
			</script>
		<%
	  		}
		%>
	  
	  	<!-- chat interface -->
		<div id="chatbox"> 
			<div id="mychat"></div>
			
			<%
				// if there's an user logged in, send message normally
				if(currUser != null) {
					String name = currUser.getFirstName();
			%>
					<div id="chattextarea">
						<form name="chatform" onsubmit="return sendMessage('<%=name%>')"">
							<input id="myArea" type="text" name="message" placeholder="Type here..">
							<input class = "btn" id="button1" type="submit" name="submit" value="Send Message" />
						</form>
					</div>
			<%
				}
				// If user's not logged in, return error message instead
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