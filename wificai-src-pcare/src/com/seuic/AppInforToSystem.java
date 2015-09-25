package com.seuic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import com.seuic.protocol.STATUS_CONNECTION;

import android.os.Handler;
import android.os.HandlerThread;

public class AppInforToSystem
{
	public static final String BLOWFISH_KEY = "-hangzhou-shanghai";  //需要人工能够修改
	public static final String SP_FIRST = "first_app";
	public static boolean isFirstApp = true;
	public static boolean ConnectStatus = false; // Connection status
	public static int record_step = 0;
	public static boolean islistening = false;
	public static boolean isCameraShootingInitSuccess = false;
	public static boolean isFirstEOFerror = true;
	public static boolean isCameraChanging = true;
	public static int capture_paraSample = 0;
	public static int capture_paraIndex = 0;
	public static int iStatus = STATUS_CONNECTION.STATE_DISCONNECTED; // Status
	public static final int iHeaderLen = 23;
	public static final int SAMPLE_RATE = 16000;
	public static int looseHeartBeatTimes = 0;
	public static int iVideoLinkID = 0;
	public static int iAudioLinkID = 0;
	public static int currentSend = 0; //分辨是发送音乐还是spk
	public static int Camera_Move_Direct_flag = 8;
	public static int main_touch_flag = -1;
	public static int main_sound_flag = -1;
	public static int bottom_btn_scroll_range = 0;
	public static int bottom_btn_scroll_flag = -1;
	public static int bottom_btn_scroll_flagright = -1;
	public static int camera_reset = 0;//0 --- noreset 1 -- reset
	public static float Camera_lr_degree = 0;
	public static int[] ChNum = { 1, 2, 3, 4 };
	public static int[] AuNum = new int[4];
	public static int lr_speed = 0;
	/*
	 * 命令Socket和视频Socket
	 */
	public static Socket socket = null; // socket for command
	public static DataOutputStream dataOutputStream = null; // output data stream
	public static DataInputStream dataInputStream = null; // input data stream
	public static Socket socketAV = null; // socket for AV stream
	public static DataOutputStream dataOutputStreamAV = null; // output AV data stream
	public static DataInputStream dataInputStreamAV = null; // input AV data stream
	public static HandlerThread mThread_Receiver = null; // Thread to receive command
	public static Handler mHandler_Receiver = null; // Thread Handler for command thread
	public static HandlerThread mThread_AVReceiver = null; // Thread to receive AV
	public static Handler mHandler_AVReceiver = null; // Thread Handler for AV thread
}
