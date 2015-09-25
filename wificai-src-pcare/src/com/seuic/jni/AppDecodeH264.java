package com.seuic.jni;

public class AppDecodeH264 {
	public static native int Getcpu();
	public static native int Init(int count);
	public static native void Uninit();
	public static native void sessionDataCallBack(byte[] data, int size, int type);
	public static native void Adpcm2Pcm(byte[] data, int size, int sample,
			int index, byte[] output);
	public static native int GlCreate(byte[] data);
	public static native int GlInit(int width, int height);
	public static native void GlRender();
	public static native void GlZoomInit();
	public static native void GlZoomIn();
	public static native void GlZoomOut();
	public static native void GlSnapbyGpu(int width, int height, byte[] output);
	public static native void GlSnapFlag(String filename);
	public static native void GlMove(float diff_x, float diff_y);
	public static boolean isNeon = false;
}
