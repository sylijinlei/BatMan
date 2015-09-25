package com.seuic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.HandlerThread;

import com.seuic.function.AppListenAudioFunction;
import com.seuic.function.AppVideoFunction;
import com.seuic.interfaces.CustomerInterface;
import com.seuic.protocol.CMD_OP_CODE;
import com.seuic.protocol.STATUS_CONNECTION;
import com.seuic.thread.AppThread;
import com.seuic.util.BlowFishLong;

public class AppConnect
{
	public Context context;
	public String WIFI_BSSID_Connect = null;
	private static AppConnect appConnectInstance = null;
	private CustomerInterface customerInterface = null;

	public AppConnect(){
		
	}
	public AppConnect(Context ctx,CustomerInterface customerInterface){
		context = ctx;
		setAppInstance(customerInterface);
	}
	public void setAppInstance(CustomerInterface customerInterface){
		this.customerInterface = customerInterface;
	}
	/*
	 * 回调函数
	 */
	public void callBack(int opt){
		customerInterface.callbackCall(opt);
	}
	/**
	 * 连接并通知服务器，此设备已运行当前程序
	 * @param AppConnect 实例
	 */
	public static AppConnect getInstance(){
		if (appConnectInstance == null)
		{
			appConnectInstance = new AppConnect();
		}
		return appConnectInstance;
	}
	/**
	 * 连接并通知服务器，此设备已运行当前程序
	 * @param context 上下文
	 * @param AppConnect 实例
	 */
	public static AppConnect getInstance(Context ctx, CustomerInterface customerInterface){
		if (appConnectInstance == null)
		{
			appConnectInstance = new AppConnect(ctx, customerInterface);
		}
		return appConnectInstance;
	}

	/*
	 * 建立命令和视频的Socket
	 */
	public boolean initSocket(){
		if (!AppInforToSystem.ConnectStatus) {
			try {
				BlowFishLong.getBlowFishLongInstance().BlowfishKeyInit(AppInforToSystem.BLOWFISH_KEY, AppInforToSystem.BLOWFISH_KEY.length());
				// Connect to target
				AppInforToSystem.socket = new Socket(AppInforToCustom.getAppInforToCustomInstance().getTargetIP(), AppInforToCustom.getAppInforToCustomInstance().getTargetPort());
				if (AppInforToSystem.socket != null) {
					AppLog.e("connect", "success");
				}else {
					AppLog.e("connect", "false");
				}
				AppInforToSystem.dataOutputStream = new DataOutputStream(AppInforToSystem.socket.getOutputStream());
				AppInforToSystem.dataInputStream = new DataInputStream(AppInforToSystem.socket.getInputStream());
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if ((AppInforToSystem.socket != null) && (AppInforToSystem.dataOutputStream != null)&& (AppInforToSystem.dataInputStream != null)) {
					AppInforToSystem.ConnectStatus = true;
				}
			}
			if (AppInforToSystem.ConnectStatus) {
				// Launch thread to receive and parse received content
				AppLog.e("connect", "mThread_Receiver");
				AppInforToSystem.mThread_Receiver = new HandlerThread("WifiCar_Receiver");
				AppInforToSystem.mThread_Receiver.start();
				AppInforToSystem.mHandler_Receiver = new Handler(AppInforToSystem.mThread_Receiver.getLooper());
				AppInforToSystem.mHandler_Receiver.post(AppThread.getAppThreadInstance().threadReceiver);
			}
			if (AppInforToSystem.ConnectStatus) {
				AppInforToSystem.ConnectStatus = AppCommand.getAppCommandInstace().sendCommand(CMD_OP_CODE.Login_Req);  //0登录请求
				AppLog.e("connect", "Login_Req");
				if (AppInforToSystem.ConnectStatus) {
					AppLog.e("connect", "STATE_REQ_SEND");
					AppInforToSystem.iStatus = STATUS_CONNECTION.STATE_REQ_SEND;// 1登录请求SEND
					int iTimeCount = 5000, iTimeStep = 50;
					while (AppInforToSystem.iStatus != STATUS_CONNECTION.STATE_RESP_RECEIVE) { //2恢复请求接收判断
						try {
							Thread.sleep(iTimeStep);
							if (iTimeCount == 2500)
							{
								AppCommand.getAppCommandInstace().sendCommand(CMD_OP_CODE.Login_Req); //再次发送一次请求
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if ((iTimeCount -= iTimeStep) < 0)
							break;
					}
					if (AppInforToSystem.iStatus == STATUS_CONNECTION.STATE_RESP_RECEIVE) { 
						AppLog.e("connect", "STATE_RESP_RECEIVE");
						AppInforToSystem.ConnectStatus = AppCommand.getAppCommandInstace().sendCommand(CMD_OP_CODE.Verify_Req); 
						if (AppInforToSystem.ConnectStatus) {
							AppLog.e("connect", "STATE_VERI_SEND");
							AppInforToSystem.iStatus = STATUS_CONNECTION.STATE_VERI_SEND; //3确认发送
							iTimeCount = 5000;
							iTimeStep = 50;
							while (AppInforToSystem.iStatus != STATUS_CONNECTION.STATE_VERI_RECEIVE) {  //4等待确认回复
								try {
									Thread.sleep(iTimeStep);
									if (iTimeCount == 2500)
									{
										AppCommand.getAppCommandInstace().sendCommand(CMD_OP_CODE.Verify_Req);//再次发送一次验证请求
									}
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								if ((iTimeCount -= iTimeStep) < 0)
									break;
							}
							
							/*
							 * 建立视频通信
							 */
							if (AppInforToSystem.iStatus == STATUS_CONNECTION.STATE_VERI_RECEIVE) {
								AppLog.e("connect", "STATE_VERI_RECEIVE");
								AppInforToSystem.iStatus = STATUS_CONNECTION.STATE_CONNECTED;
								AppInforToSystem.ConnectStatus = true;
								AppVideoFunction.getAppVideoFunctionInstance().VideoEnable();
							    WIFI_BSSID_Connect = getWIFI_BSSID();
							} else{
								AppInforToSystem.ConnectStatus = false;
							}
						}
					} else{
						AppInforToSystem.ConnectStatus = false;
					}
				}
			}
		}
		if (AppInforToSystem.ConnectStatus)
		{
			AppCheck.getAppCheck(context).SDCardSizeTest(); //SD卡检测定时器
		}
		return AppInforToSystem.ConnectStatus;
	}
	/*
	 * 应用程序退出是调用
	 */
	public void exitApp(){
		if (AppInforToSystem.ConnectStatus)
		{
			// If video is streaming, stop video first
			AppVideoFunction.getAppVideoFunctionInstance().VideoDisable();
			//如果没有关闭电池请求，则关闭
			AppInforToCustom.getAppInforToCustomInstance().cancleRequestBattery();
			//判断AudioStick是否关闭
			AppListenAudioFunction.getAppAudioFunctionInstance().destoryAudioTrack();
			//如果有音频请求的话
			AppListenAudioFunction.getAppAudioFunctionInstance().AudioDisable();
			//取消心跳包请求
			AppInforToCustom.getAppInforToCustomInstance().cancleRequestHeartBeat();
			//取消SD卡检测功能
			AppCheck.getAppCheck(context).CacleSDTest();
			AppInforToSystem.ConnectStatus = false;
			if (AppInforToSystem.socket != null) {
				try {
					AppInforToSystem.socket.close();
				} catch (IOException e) {
					AppLog.e("appExit", "socket");
					e.printStackTrace();
				}finally{
					AppLog.e("appExit", "socket1");
					AppInforToSystem.socket = null;
					System.gc();
				}
			}

			if (AppInforToSystem.dataOutputStream != null) {
				try {
					AppInforToSystem.dataOutputStream.close();
				} catch (IOException e) {
					AppLog.e("appExit", "dataOutputStream");
					e.printStackTrace();
				}finally{
					AppInforToSystem.dataOutputStream = null;
					System.gc();
				}
			}

			if (AppInforToSystem.dataInputStream != null) {
				try {
					AppInforToSystem.dataInputStream.close();
				} catch (IOException e) {
					AppLog.e("appExit", "dataInputStream");
					e.printStackTrace();
				}finally{
					AppInforToSystem.dataInputStream = null;
					System.gc();
				}
			}
			
			// Stop receiver thread
			if (AppInforToSystem.mHandler_Receiver != null) {
				AppInforToSystem.mHandler_Receiver.removeCallbacks(AppThread.getAppThreadInstance().threadReceiver);
				AppInforToSystem.mHandler_Receiver = null;
			}

			if (AppInforToSystem.mThread_Receiver != null) {
				AppInforToSystem.mThread_Receiver.quit();
				AppInforToSystem.mThread_Receiver = null;
			}
			if (AppThread.getAppThreadInstance().threadReceiver != null)
			{
				AppThread.getAppThreadInstance().threadReceiver = null;
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
		System.gc();
	}
	public String getWIFI_BSSID() {
		WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);// 获得WifiManager对象
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();// 获得连接信息对象
		return wifiInfo.getBSSID();
	}
	
}