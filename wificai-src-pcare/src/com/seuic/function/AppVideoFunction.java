package com.seuic.function;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.seuic.AppCommand;
import com.seuic.AppInforToCustom;
import com.seuic.AppInforToSystem;
import com.seuic.AppLog;
import com.seuic.protocol.CMD_OP_CODE;
import com.seuic.thread.AppThread;

import android.os.Handler;
import android.os.HandlerThread;

public class AppVideoFunction
{
	private static AppVideoFunction appVideoFunctionInstance = null;
	public AppVideoFunction(){
		
	}
	
	public static AppVideoFunction getAppVideoFunctionInstance(){
		if (appVideoFunctionInstance == null)
		{
			appVideoFunctionInstance = new AppVideoFunction();
		}
		return appVideoFunctionInstance;
	}
	/*
	 * 视频连接功能
	 */
	public Boolean VideoEnable() {
		if (!AppInforToSystem.ConnectStatus){
			return false;
		}
		 Boolean bRet = AppCommand.getAppCommandInstace().sendCommand(CMD_OP_CODE.Video_Start_Req);  //发送视频请求4
		if (bRet) {
			// Wait 5 seconds for access code ready
			int iTimeCount = 5000, iTimeStep = 100;
			while (AppInforToSystem.iVideoLinkID == 0) {
				try {
					Thread.sleep(iTimeStep);
					if (iTimeCount == 2500)
					{
						AppCommand.getAppCommandInstace().sendCommand(CMD_OP_CODE.Video_Start_Req);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if ((iTimeCount -= iTimeStep) < 0)
					break;
			}

			if (AppInforToSystem.iVideoLinkID != 0){
				AppLog.e("connect", "AppInforToSystem.iVideoLinkID != 0");
				bRet = true;
			}
			else{
				bRet = false;
			}
		}

		if (bRet) {
			try {
				// Connect to target
				AppInforToSystem.socketAV = new Socket(AppInforToCustom.getAppInforToCustomInstance().getTargetIP(), AppInforToCustom.getAppInforToCustomInstance().getTargetPort());
				if (AppInforToSystem.socketAV != null) {
					AppLog.e("av", "avsocketg");
				}
				// create input and output stream
				AppInforToSystem.dataOutputStreamAV = new DataOutputStream(AppInforToSystem.socketAV.getOutputStream());
				AppInforToSystem.dataInputStreamAV = new DataInputStream(AppInforToSystem.socketAV.getInputStream());
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
				bRet = false;
			} catch (IOException e1) {
				e1.printStackTrace();
				bRet = false;
			} finally {
				if ((AppInforToSystem.socketAV != null) && (AppInforToSystem.dataOutputStreamAV != null) && (AppInforToSystem.dataInputStreamAV != null)) {
					bRet = true;
				} else {
					bRet = false;
					if (AppInforToSystem.socketAV != null) {
						try {
							AppInforToSystem.socketAV.close();
							AppInforToSystem.socketAV = null;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (AppInforToSystem.dataOutputStreamAV != null) {
						try {
							AppInforToSystem.dataOutputStreamAV.close();
							AppInforToSystem.dataOutputStreamAV = null;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

					if (AppInforToSystem.dataInputStreamAV != null) {
						try {
							AppInforToSystem.dataInputStreamAV.close();
							AppInforToSystem.dataInputStreamAV = null;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

		if (bRet) {
			// Start thread to get AV stream
			AppInforToSystem.mThread_AVReceiver = new HandlerThread("WifiCar_AVReceiver");
			AppInforToSystem.mThread_AVReceiver.start();
			AppInforToSystem.mThread_AVReceiver.setPriority(10);
			AppInforToSystem.mHandler_AVReceiver = new Handler(AppInforToSystem.mThread_AVReceiver.getLooper());
			AppInforToSystem.mHandler_AVReceiver.post(AppThread.getAppThreadInstance().threadAVReceiver);
		}

		return bRet;
	}
	
	/*
	 * 关闭视频功能
	 */
	public Boolean VideoDisable() {
		if (!AppInforToSystem.ConnectStatus){
			return false;
		}
		Boolean bRet = false;
		bRet = AppCommand.getAppCommandInstace().sendCommand(CMD_OP_CODE.Video_End);
		if (bRet) {
			AppInforToSystem.iVideoLinkID = 0;
			if (AppInforToSystem.socketAV != null) {
				try {
					AppInforToSystem.socketAV.close();
					AppInforToSystem.socketAV = null;
				} catch (IOException e) {
					e.printStackTrace();
					bRet = false;
				}
			}

			if (AppInforToSystem.dataOutputStreamAV != null) {
				try {
					AppInforToSystem.dataOutputStreamAV.close();
					AppInforToSystem.dataOutputStreamAV = null;
				} catch (IOException e) {
					e.printStackTrace();
					bRet = false;
				}
			}

			if (AppInforToSystem.dataInputStreamAV != null) {
				try {
					AppInforToSystem.dataInputStreamAV.close();
					AppInforToSystem.dataInputStreamAV = null;
				} catch (IOException e) {
					e.printStackTrace();
					bRet = false;
				}
			}

			// Stop AV receiver thread
			if (AppInforToSystem.mHandler_AVReceiver != null) {
				AppInforToSystem.mHandler_AVReceiver.removeCallbacks(AppThread.getAppThreadInstance().threadAVReceiver);
				AppInforToSystem.mHandler_AVReceiver = null;
			}

			if (AppInforToSystem.mThread_AVReceiver != null) {
				AppInforToSystem.mThread_AVReceiver.quit();
				AppInforToSystem.mThread_AVReceiver = null;
			}

		}

		return bRet;
	}
}
