<%@ page import="config.ConfigString" %><%--
  Created by IntelliJ IDEA.
  User: HZJ
  Date: 11/21/2017
  Time: 10:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (request.getSession().getAttribute("socketAddress") == null) {
        request.getSession().setAttribute("socketAddress", ConfigString.socketAddress);
    }
%>
<html>
<head>
    <title>Watching Live Stream</title>
</head>
<body>
<div>
    <img id="target" style="display: inline;"/>
</div>
<script type="text/javascript">
    // to see live stream on another computer, change localhost:8080 to the ip address of that computer
    
    let ws = new WebSocket("ws://${sessionScope.socketAddress}/" +
        "${not empty pageContext.request.contextPath ? pageContext.request.contextPath: ""}" + "/liveStreamVideo");
    let target = document.getElementById("target");

    ws.onmessage = function (msg) {
        let url = URL.createObjectURL(msg.data);
        target.onload = function () {
            window.URL.revokeObjectURL(url);
        };
        target.src = url;
    }
</script>
</body>
</html>
