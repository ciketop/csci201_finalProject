
function sendMessageHelper(socket, message) {
    socket.send(message);
    return false;
}

function connectToServerHelper(socket, serverUrl) {
    socket = new WebSocket(serverUrl);

    socket.onopen = function (event) {
        document.getElementById("mychat").innerHTML += "CONNECTED!";
        document.getElementById("mychat").innerHTML += "<br />";
    };

    socket.onmessage = function (event) {
        document.getElementById("mychat").innerHTML += event.data;
        document.getElementById("mychat").innerHTML += "<br />";
    };

    socket.onclose = function (event) {
        document.getElementById("mychat").innerHTML += "DISCONNECTED!";
        document.getElementById("mychat").innerHTML += "<br />";
    }
}