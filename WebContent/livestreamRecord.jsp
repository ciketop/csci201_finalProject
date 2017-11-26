<%@ page import="config.ConfigString" %><%--
  Created by IntelliJ IDEA.
  User: HZJ
  Date: 11/21/2017
  Time: 5:13 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (request.getSession().getAttribute("socketAddress") == null) {
        request.getSession().setAttribute("socketAddress", ConfigString.socketAddress);
    }
%>
<%--https://github.com/sambaf/WScams/blob/master/src/main/webapp/js/main.js--%>
<%--http://javawebsocketsvideo.blogspot.com/--%>
<html>
<head>
    <title>Record Live Stream</title>
</head>
<body>

<div>
    <h1>Video from Your Camera</h1>
    <video id="live" width="320" height="240" autoplay="autoplay"
           style="display: inline;"></video>
    <canvas width="320" id="canvas" height="240" style="display: none"></canvas>
    <!-- <canvas width="640" id="canvas" height="480" style="display: none"></canvas> -->
</div>

<script type="text/javascript">
    let video = document.getElementById("live");
    let canvas = document.getElementById("canvas");
    let ctx = canvas.getContext('2d');

    console.log("ws://localhost:8080" +
            "${not empty pageContext.request.contextPath ? pageContext.request.contextPath: ""}"
            + "/liveStreamVideo");
    let ws = new WebSocket("ws://${sessionScope.socketAddress}/" +
        "${not empty pageContext.request.contextPath ? pageContext.request.contextPath: ""}"
        + "/liveStreamVideo");
    /* let ws = new WebSocket("ws://192.168.50.166:8080/csci201_finalProject/liveStreamVideo"); */
    
    ws.onopen = function () {
        console.log("Openened connection to websocket");
    };

    // user media constraints
    let constraints = {
        video: true,
        audio: true
    };
    navigator.mediaDevices.getUserMedia(constraints)
        .then(function (stream) {
            video.src = URL.createObjectURL(stream);
        })
        .catch(function (error) {
            console.log("Unable to get video stream!");
            console.error(error);
        });

    timer = setInterval(
        function () {
            ctx.drawImage(video, 0, 0, canvas.width, canvas.height);
            let data = canvas.toDataURL('image/jpeg', 1.0);

            ws.send(convertToBinary(data));
        }, 100);

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
</script>

<div>
    <h1>Live Stream from Server</h1>
    <img id="target" style="display: inline;" />
</div>
<script type="text/javascript">
    let target = document.getElementById("target");
    
    let clientWs = new WebSocket("ws://localhost:8080" +
            "${not empty pageContext.request.contextPath ? pageContext.request.contextPath: ""}"
            + "/liveStreamVideo");
    /* let clientWs = new WebSocket("ws://192.168.50.166:8080/csci201_finalProject/liveStreamVideo"); */

    clientWs.onmessage = function (msg) {
        let url = URL.createObjectURL(msg.data);
        target.onload = function () {
            window.URL.revokeObjectURL(url);
        };
        target.src = url;
    }
</script>
</body>
</html>
