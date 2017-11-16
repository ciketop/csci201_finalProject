<%--
  Created by IntelliJ IDEA.
  User: HZJ
  Date: 11/16/2017
  Time: 2:13 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>WebRTC Lib Test</title>
    <script src="https://cdn.WebRTC-Experiment.com/RecordRTC.js"></script>
    <script>
        // https://github.com/muaz-khan/WebRTC-Experiment/tree/master/RecordRTC
        var recordRTC;

        function successCallback(stream) {
            // RecordRTC usage goes here

            var options = {
                mimeType: 'video/webm', // or video/webm\;codecs=h264 or video/webm\;codecs=vp9
                audioBitsPerSecond: 128000,
                videoBitsPerSecond: 128000,
                bitsPerSecond: 128000 // if this line is provided, skip above two
            };
            recordRTC = RecordRTC(stream, options);
            recordRTC.startRecording();
            console.log(recordRTC);
        }

        function errorCallback(error) {
            // maybe another application is using the device
            console.log("errorCallback");
            console.log(error);
        }

        let mediaConstraints = {video: true, audio: true};

        function init() {
            console.log("init");
            navigator.mediaDevices.getUserMedia(mediaConstraints).then(successCallback).catch(errorCallback);
        }


        //        btnStopRecording.onclick = function () {
        function stopRecording() {
            let btnStopRecording = document.getElementById("btnStopRecording");
            let video = document.getElementById('video');

            recordRTC.stopRecording(function (audioVideoWebMURL) {
                video.src = audioVideoWebMURL;
//                window.open(audioVideoWebMURL);

                var recordedBlob = recordRTC.getBlob();
                recordRTC.getDataURL(function (dataURL) {
                    console.log("dataURL");
//                    console.log(recordedBlob);
//                    console.log(dataURL);
//                window.open(dataURL);
                    let link = document.createElement("downloadLink"); // Or maybe get it from the current document
                    link.href = dataURL;
                    link.download = "aDefaultFileName.mp4";
                    link.innerHTML = "Click here to download the file";
                    document.body.appendChild(link);
                });
            });
        }
    </script>
</head>
<body onload="init();">
<video id="video" width="640" height="360" autoplay></video>
<button id="flipCamera" onclick="flipCamera()">Flip Camera</button>
<button id="snap" onclick="takePhoto()">Snap Photo</button>
<button id="record" onclick="recordVideo()">Record</button>
<button id="btnStopRecording" onclick="stopRecording()">Stop Record</button>
<canvas id="canvas" width="640" height="480"></canvas>
</body>
</html>
