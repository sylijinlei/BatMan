package com.beelinker.media.mp4mux;

public class AppCameraShooting {
	public static final String version = "0.0.0.1"; 
	public static native boolean mp4init(String title, int type);
	public static native void mp4packVideo(byte[] data, int size, int keyframe, int frame_time);
	public static native void mp4packAudio(byte[] data, int size, int frame_time);
	public static native void mp4close();
	
	static {
		try {
			System.loadLibrary("CameraShooting");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
