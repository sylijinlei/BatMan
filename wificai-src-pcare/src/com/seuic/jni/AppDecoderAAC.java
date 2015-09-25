package com.seuic.jni;


public class AppDecoderAAC {
	public static native void init();
	public static native int decoder(byte[] data, int size, byte[] output);
	public static native void close();
	
	static{
		System.loadLibrary("faad");
	}
	
}
