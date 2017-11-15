package camera;


import org.bytedeco.javacpp.avutil;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.*;

import static org.bytedeco.javacpp.opencv_core.cvFlip;

public class CameraTest {
    public static final String FILENAME = "output.mp4";

    public static void main(String[] args) throws Exception {
        OpenCVFrameConverter.ToIplImage toIplImageConverter = new OpenCVFrameConverter.ToIplImage();
        OpenCVFrameConverter.ToMat toMatConverter = new OpenCVFrameConverter.ToMat();

        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        grabber.start();
        opencv_core.IplImage grabbedImage = toIplImageConverter.convert(grabber.grab());

        CanvasFrame canvasFrame = new CanvasFrame("Cam");
        canvasFrame.setCanvasSize(grabbedImage.width(), grabbedImage.height());

        System.out.println("framerate = " + grabber.getFrameRate());
        grabber.setFrameRate(grabber.getFrameRate());
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(FILENAME, grabber.getImageWidth(), grabber.getImageHeight());

        recorder.setVideoCodec(13);
        recorder.setFormat("mp4");
        recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
        recorder.setFrameRate(30);
        recorder.setVideoBitrate(10 * 1024 * 1024);

        recorder.start();
        while (canvasFrame.isVisible()
                && (grabbedImage = toIplImageConverter.convert(grabber.grab())) != null
                ) {
            canvasFrame.showImage(toMatConverter.convert(grabbedImage));
            recorder.record(toMatConverter.convert(grabbedImage));
        }
        recorder.stop();
        grabber.stop();
        canvasFrame.dispose();
    }
}