<%@ page import="config.ConfigString" %><%--
  Created by IntelliJ IDEA.
  User: HZJ
  Date: 11/25/2017
  Time: 3:14 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (request.getSession().getAttribute("socketAddress") == null) {
        request.getSession().setAttribute("socketAddress", ConfigString.socketAddress);
    }
%>
<%--https://developers.google.com/web/fundamentals/media/recording-audio/--%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<audio id="player" controls></audio>
<script>
    let player = document.getElementById("player");
    let ws = new WebSocket("ws://${sessionScope.socketAddress}/" +
        "${not empty pageContext.request.contextPath ? "/" + pageContext.request.contextPath: ""}"
        + "/liveStreamAudio");
    ws.binaryType = 'arraybuffer';

    ws.onopen = function () {
        console.log("Openened connection to websocket");
    };

    navigator.mediaDevices.enumerateDevices().then((devices) => {
        devices = devices.filter((d) => d.kind === 'audioinput');
        console.log(devices);
    });

    let constraints = {
        audio: true,
//        audio: {
//            deviceId: devices[0].deviceId
//        },
        video: false
    };

    function handleSuccess(stream) {
        let context = new AudioContext();
        let source = context.createMediaStreamSource(stream);
        let processor = context.createScriptProcessor(4096, 1, 1);

        source.connect(processor);
        processor.connect(context.destination);

        processor.onaudioprocess = function(e) {
            // Do something with the data, i.e Convert this to WAV
            ws.send(e.inputBuffer.getChannelData(0).buffer);
        };
    }

    navigator.mediaDevices.getUserMedia(constraints)
        .then(handleSuccess)
        .catch(function (error) {
            console.error(error);
        });
</script>
</body>
</html>
