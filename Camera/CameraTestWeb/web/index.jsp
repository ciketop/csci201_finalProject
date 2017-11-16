<%--
  Created by IntelliJ IDEA.
  User: HZJ
  Date: 11/15/2017
  Time: 9:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Camera Test</title>

    <script src="${pageContext.request.contextPath}/lib/whammy.js"></script>
    <script>
        // example from https://davidwalsh.name/browser-camera
        // tutorial: https://developer.mozilla.org/en-US/docs/Web/API/MediaDevices/getUserMedia
        // https://www.html5rocks.com/en/tutorials/getusermedia/intro/
        let currentVideoDeviceIdx = 0;
        let videoDevices = [];
        let audioDevices = [];

        function init() {
            navigator.mediaDevices.enumerateDevices()
                .then(function (MediaDeviceInfo) {
                    MediaDeviceInfo.forEach((element) => {
                        if (element.kind.includes("videoinput")) {
//                            console.log(element);
                            videoDevices.push(element);
                        }
                        else if (element.kind.includes("audio")) {
//                            console.log(element);
                            audioDevices.push(element);
                        }
                    });

                    setUpCamera();
                });
        }

        function setUpCamera() {
            let video = document.getElementById('video');
            let constraints = {
                audio: true,
                video: videoDevices.length > 0
                    ? {
                        deviceId: videoDevices[currentVideoDeviceIdx].deviceId,
                        width: 1280,
                        height: 720
                    } : false,
            };

            // Get access to the camera!
            if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
                navigator.mediaDevices.getUserMedia(constraints).then(function (stream) {
//                    video.src = window.URL.createObjectURL(stream);
//                    video.play();

//                    let video = document.querySelector('video');
                    video.srcObject = stream;
                    video.onloadedmetadata = function (e) {
                        console.log(e);

                        video.play();
                    };
                }).catch(function (err) {
                    console.log(err.name + ": " + err.message);
                });
            }
        }

        function flipCamera() {
            ++currentVideoDeviceIdx;
            if (currentVideoDeviceIdx >= videoDevices.length) {
                currentVideoDeviceIdx = 0;
            }

            setUpCamera();
        }

        function takePhoto() {
            // Elements for taking the snapshot
            let canvas = document.getElementById('canvas');
            let context = canvas.getContext('2d');
            let video = document.getElementById('video');

            // Trigger photo take
            document.getElementById("snap").addEventListener("click", function () {
                context.drawImage(video, 0, 0, 640, 480);
            });
        }

        let rafId;
        let frames = [];

        function drawVideoFrame(time) {
            let canvas = document.getElementById('canvas');
            let context = canvas.getContext('2d');
            let video = document.getElementById('video');

            rafId = requestAnimationFrame(drawVideoFrame);
            context.drawImage(video, 0, 0, 1280, 720);
            frames.push(canvas.toDataURL('image/webp', 1));
        }

        function recordVideo() {
//            rafId = requestAnimationFrame(drawVideoFrame);
            drawVideoFrame(undefined);
        }

        function stopRecording() {
            cancelAnimationFrame(rafId);  // Note: not using vendor prefixes!

            // 2nd param: framerate for the video file.
            let webmBlob = Whammy.fromImageArray(frames, 1000 / 60);

            let video = document.createElement('video');
            video.src = window.URL.createObjectURL(webmBlob);

            document.body.appendChild(video);
        }
    </script>
</head>
<body onload="init();">
<%--Ideally these elements aren't created until it's confirmed that the --%>
<%--client supports video/camera, but for the sake of illustrating the --%>
<%--elements involved, they are created with markup (not JavaScript)--%>
<div>
    <video id="video" width="1280" height="720" autoplay></video>
</div>
<button id="flipCamera" onclick="flipCamera()">Flip Camera</button>
<button id="snap" onclick="takePhoto()">Snap Photo</button>
<button id="snap" onclick="recordVideo()">Record</button>
<button id="snap" onclick="stopRecording()">Stop Record</button>
<canvas id="canvas" width="640" height="480"></canvas>
</body>
</html>
