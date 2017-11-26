<%@ page import="config.ConfigString" %><%--
  Created by IntelliJ IDEA.
  User: HZJ
  Date: 11/25/2017
  Time: 4:10 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (request.getSession().getAttribute("socketAddress") == null) {
        request.getSession().setAttribute("socketAddress", ConfigString.socketAddress);
    }
%>
<%--https://developer.mozilla.org/en-US/docs/Web/API/AudioBuffer--%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<audio id="player" controls></audio>
<%--<button>Play song</button>--%>
<script>
    //    let player = document.getElementById("player");
    let ws = new WebSocket("ws://${sessionScope.socketAddress}/" +
        "${not empty pageContext.request.contextPath ? "/" + pageContext.request.contextPath: ""}"
        + "/liveStreamAudio");
    ws.binaryType = 'arraybuffer';

    ws.onmessage = function (msg) {
//        console.log(new Float32Array(msg.data));
        playsound(msg.data);
    };

    let audioContext = new (window.AudioContext || window.webkitAudioContext)();

    function playsound(arrayBuffer) {
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
</body>
</html>
