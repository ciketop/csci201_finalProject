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
    <title>$Title$</title>
    <script src="https://cdn.WebRTC-Experiment.com/RecordRTC.js"></script>
    <script>
        // https://github.com/muaz-khan/WebRTC-Experiment/tree/master/RecordRTC
        var recordRTC;
        let btnStopRecording = document.getElementById("btnStopRecording");
        let video = document.getElementById('video');

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
        }

        function errorCallback(error) {
            // maybe another application is using the device
        }

        var mediaConstraints = {video: true, audio: true};

        navigator.mediaDevices.getUserMedia(mediaConstraints).then(successCallback).catch(errorCallback);

        //        btnStopRecording.onclick = function () {
        function stopRecording() {
            recordRTC.stopRecording(function (audioVideoWebMURL) {
                video.src = audioVideoWebMURL;

                var recordedBlob = recordRTC.getBlob();
                recordRTC.getDataURL(function (dataURL) {
                });
            });
        }
    </script>
</head>
<body>
<video id="video" width="640" height="360" autoplay></video>
<button id="flipCamera" onclick="flipCamera()">Flip Camera</button>
<button id="snap" onclick="takePhoto()">Snap Photo</button>
<button id="record" onclick="recordVideo()">Record</button>
<button id="btnStopRecording" onclick="stopRecording()">Stop Record</button>
<canvas id="canvas" width="640" height="480"></canvas>
</body>
</html>
