package com.seuic.function;

import java.util.Timer;
import java.util.TimerTask;

import com.seuic.AppCommand;
import com.seuic.AppConnect;
import com.seuic.AppInforToSystem;
import com.seuic.AppLog;
import com.seuic.interfaces.CustomerInterface;
import com.seuic.protocol.CMD_OP_CODE;

public class AppSpkAudioFunction
{
	Timer talkEndCloseTimer;
	private static AppSpkAudioFunction appAppSpkAudioFunctionInstance = null;
	public AppSpkAudioFunction(){
		
	}
	public static AppSpkAudioFunction getAppSpkAudioFunctionInstance(){
		if (appAppSpkAudioFunctionInstance == null)
		{
			appAppSpkAudioFunctionInstance = new AppSpkAudioFunction();
		}
		return appAppSpkAudioFunctionInstance;
	}
	/*
	 * 开启spk功能
	 */
	public Boolean SpkAudioEnable() {
		if (!AppInforToSystem.ConnectStatus){
			return false;
		}
		AppInforToSystem.currentSend = 1;
		Boolean bRet = AppCommand.getAppCommandInstace().sendCommand(CMD_OP_CODE.Talk_Start_Req);
		AppInforToSystem.record_step = 1;
		AppLog.d("spk ", "SpkAudioEnable"+AppInforToSystem.record_step);
		return bRet;
	}
	/*
	 * 关闭spk功能
	 */
	public Boolean SpkAudioDisable() {
		if (!AppInforToSystem.ConnectStatus){
			return false;
		}
		Boolean bRet = true;
		//如果没有收到请求回应就关闭talk，则先不关闭，等待回应后在关闭
		if (AppInforToSystem.record_step <= 4 && AppInforToSystem.record_step >=2) {
			closeSpk();
		}
		AppInforToSystem.record_step = 5;
		return bRet;
	}
	/*
	 * 定时器 每隔100ms来检测AppInforToSystem.record_step是否为5，如果为5则说明可以再次请求
	 * 如果不是5还是4则再次发送请求关闭，3秒时间，则认为超时，可以重新发送请求
	 */
	int time = 0;
	class timerOutTask extends TimerTask {
		@Override
		public void run() {
			AppLog.d("spk ", "record_step :"+AppInforToSystem.record_step +"    time" +time);
			if (AppInforToSystem.record_step != 6 && time < 10) {
				AppCommand.getAppCommandInstace().sendCommand(CMD_OP_CODE.Talk_End);
			}else if(AppInforToSystem.record_step == 6){
				//发送可以再次发送音频请求
				AppConnect.getInstance().callBack(CustomerInterface.MESSAGE_SPK_END_SUCCESS);
				cancleTimer();
			}else if (AppInforToSystem.record_step != 6 && time == 10) {
				AppConnect.getInstance().callBack(CustomerInterface.MESSAGE_SPK_END_SUCCESS);
				cancleTimer();
			}
			if (talkEndCloseTimer == null) {
				this.cancel();
				AppLog.d("spk ", "timer == null");
			}else {
				AppLog.d("spk ", "timer != null");
			}
			time++;
		}
	};
	public void cancleTimer(){
		time = 0;
		if (talkEndCloseTimer != null) {
			talkEndCloseTimer.cancel();
			talkEndCloseTimer = null;
		}
	}
	public void closeSpk(){
		AppInforToSystem.record_step = 5;
		AppCommand.getAppCommandInstace().sendCommand(CMD_OP_CODE.Talk_End);
		if (talkEndCloseTimer != null) {
			talkEndCloseTimer.cancel();
			talkEndCloseTimer = null;
			AppLog.d("spk ", "timer  error");
		}
		time = 0;
		talkEndCloseTimer = new Timer();
		talkEndCloseTimer.schedule(new timerOutTask(), 300,300);
		AppLog.d("spk ", "SpkAudioDisable"+AppInforToSystem.record_step);
	}
}
