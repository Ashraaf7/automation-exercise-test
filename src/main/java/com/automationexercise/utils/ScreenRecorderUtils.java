package com.automationexercise.utils;

import com.automation.remarks.video.RecorderFactory;
import com.automation.remarks.video.recorder.IVideoRecorder;
import com.automation.remarks.video.recorder.VideoRecorder;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;

import java.io.File;

public class ScreenRecorderUtils {

    public final static String RECORDINGS_PATH = "test-outputs/recordings/";
    private static final ThreadLocal<IVideoRecorder> recorder = new ThreadLocal<>();

    /**
     * Starts screen recording.
     */
    public static void startRecording() {
        try {
            // Ensure the recordings directory exists
            File recordingsDir = new File(RECORDINGS_PATH);
            if (!recordingsDir.exists()) {
                recordingsDir.mkdirs();
            }
            if (ConfigUtils.getConfigValue("executionType").equalsIgnoreCase("local")) {
                // Configure the recorder to use the custom directory and file name
                recorder.set(RecorderFactory.getRecorder(VideoRecorder.conf().recorderType()));
                // Start recording
                recorder.get().start();
                LogUtils.info("Recording Started");
            }

        } catch (Exception e) {
            LogUtils.error("Failed to start recording: " + e.getMessage());
        }
    }

    /**
     * Stops screen recording and returns the video as an InputStream.
     */
    public static void stopRecording(String testMethodName) {
        try {
            if (recorder.get() != null) {
                // Stop the recorder and get the video file
                String videoFilePath = String.valueOf(recorder.get().stopAndSave(testMethodName));
                File videoFile = new File(videoFilePath);

                // Log the file path for debugging
                LogUtils.info("Video file saved at: " + videoFile.getAbsolutePath());


                // Convert the video to .mp4 format
                File mp4File = encodeRecording(videoFile);
                LogUtils.info("Recording Stopped and Converted to MP4: " + mp4File.getName());
            }
        } catch (Exception e) {
            LogUtils.error("Failed to stop recording: " + e.getMessage());
        } finally {
            recorder.remove();
        }
    }

    /**
     * Converts a video file to .mp4 format.
     *
     * @param sourceFile The input video file.
     * @return The converted .mp4 file.
     */
    private static File encodeRecording(File sourceFile) {
        File targetFile = new File(sourceFile.getParent(), sourceFile.getName().replace(".avi", ".mp4"));
        try {

            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("aac"); // AAC audio codec

            VideoAttributes video = new VideoAttributes();
            video.setCodec("libx264"); // H.264 video codec

            EncodingAttributes encodingAttributes = new EncodingAttributes();
            encodingAttributes.setOutputFormat("mp4"); // Output format
            encodingAttributes.setAudioAttributes(audio);
            encodingAttributes.setVideoAttributes(video);

            // Encode the video
            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(sourceFile), targetFile, encodingAttributes);

            // Delete the original .avi file after conversion
            if (targetFile.exists()) {
                sourceFile.delete();
                LogUtils.info("Deleted original AVI file: " + sourceFile.getName());
            }
        } catch (EncoderException e) {
            LogUtils.error("Failed to convert video to MP4: " + e.getMessage());
        }
        return targetFile;
    }


}