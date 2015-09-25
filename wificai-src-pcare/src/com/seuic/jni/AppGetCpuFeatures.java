package com.seuic.jni;

public class AppGetCpuFeatures {
	public static int CpuCount = 1;
	
	public static native int init();
	
	//加载neon --- armv7
	public static int loadNeon(){
		return 1;
	}
	//加载VFPv3  -armv7
	public static int  loadVFPv3(){
		return 1;
	}
	//加载VFPv2 --ARMv6
	public static int loadVFPv2(){
		return 1;
	}
	//加载普通版本  ---ARMv5,ARMv6,ARMv7
	public static int loadAll(){
		return 1;
	}
	//加载cpu检测库
	public static void loadCpuCheck(){
		System.loadLibrary("getCpuFeatures");
		System.loadLibrary("ffmpeg");
		System.loadLibrary("ffmpeg-test");
	}
	//设置cpu的个数
	public static int setCpuCount(int count){
		CpuCount = count;
		return 1;
	}
}
