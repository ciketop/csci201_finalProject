<!DOCTYPE html>
<html>
<head>
    <title>WebSocket Example</title>

    <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/socket.template.js"></script>
    <script>
        var socket;
        var hostname = "localhost";
        var port = "8080";
        var myname = "Zijian Hu";

        function connectToServer() {
            connectToServerHelper(socket, "ws://" + hostname + ":" + port + "${pageContext.request.contextPath}/chatroom");
        }

        function sendMessage() {
            sendMessageHelper(myname + ": " + document.chatform.message.value);
            return false;
        }
    </script>
</head>
<body onload="connectToServer();">
<form name="chatform" onsubmit="return sendMessage();">
    <input type="text" name="message" placeholder="TYPE HERE" value=""/><br/>
    <input type="submit" name="submit" value="SUBMIT"/><br/>
</form>
<br/>
<div id="mychat"></div>
</body>
</html>