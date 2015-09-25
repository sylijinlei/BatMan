package com.seuic.function;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.SharedPreferences;

import com.seuic.AppConnect;
import com.seuic.AppInforToCustom;
import com.seuic.AppLog;
import com.seuic.interfaces.CustomerInterface;

public class AppPlayPathFunction
{
	private static final String TAG = "AppPlayPathFunction";
	private Context context = null;
	private static AppPlayPathFunction playPathInstance = null;
	public AppPlayPathFunction(Context context){
		this.context = context;
	}
	public static AppPlayPathFunction getPlayPathInstance(Context context){
		if (playPathInstance == null)
		{
			playPathInstance = new AppPlayPathFunction(context);
		}
		return playPathInstance;
	}
	
	/*
	 * 判断路径是否是在录制，在UI层确定
	 * 播放路径
	 * @param opt true:循环播放路径   false:单次循环播放路径
	 */
	Socket socketRec = null;
	DataOutputStream dataOutputStreamRec = null;
	DataInputStream dataInputStreamRec = null;
	public void PlayPath_Start(boolean opt){
		AppInforToCustom.getAppInforToCustomInstance().setPlayModeStatus(true);
		/*
		 * 从手机上传输设备路径文件到设备上
		 */
		AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_TransPathToDevice();
		AppLog.e(TAG, "trancs_end");
		//传输完毕后，通知设备播放录制路径
		AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_PlayingPath(true);
		if (opt) {
			if(replay_start ==0){
				replay_time = 0;
			}else{
				replay_time = System.currentTimeMillis() - replay_start;
			}
			AppInforToCustom.getAppInforToCustomInstance().setIsCyclePlayMode(opt);
		}else {
			replay_time = 0;
			AppInforToCustom.getAppInforToCustomInstance().setIsCyclePlayMode(opt);
		}
		SharedPreferences share_time_play = context.getSharedPreferences(RECORD_TIME, 0);
		SharedPreferences share = context.getSharedPreferences(FILENSME, 0);
		SharedPreferences share_video = context.getSharedPreferences(FILENAME_V, 0);
		SharedPreferences share_s = context.getSharedPreferences(FILENAME_S, 0);

		recordTimeLength = share_time_play.getLong("record",0);
		pictrue_play = share.getInt("pictrue_play",0);
		video_play = share_video.getInt("video_play",0);
		video_play_stop = share_s.getInt("video_play_stop",0);
		take_pictrue_T = share.getInt("record_times",0);
		take_video_T = share_video.getInt("record_video",0);
		take_video_T_S = share_s.getInt("stop_video",0);
		if(AppInforToCustom.getAppInforToCustomInstance().getIsCyclePlayMode()){
			replayTimer = new Timer();
			replayTimer.schedule(new rePlayTask(),recordTimeLength);
		}else {
			playPathToStopTimer = new Timer();
			playPathToStopTimer.schedule(new PlayPathStopTimerTask(), recordTimeLength);
		}
	}
	/*
	 * 停止播放路径
	 */
	public void PlayPath_Stop(){
		AppInforToCustom.getAppInforToCustomInstance().setPlayModeStatus(false);
		AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_PlayingPath(false); // 停止播放路径
		j = 0;
		k = 0;
		s = 0;
		replay_time = 0;
		replay_start = 0;
		if (take_pictrue != null) {
			take_pictrue.cancel();
			take_pictrue = null;
		}

		if (take_video != null) {
			take_video.cancel();
			take_video = null;
		}

		if (stop_take_video != null) {
			stop_take_video.cancel();
			stop_take_video = null;
		}

		if (replayTimer != null) {
			replayTimer.cancel();
			replayTimer = null;
		}
		if (playPathToStopTimer != null)
		{
			playPathToStopTimer.cancel();
			playPathToStopTimer = null;
		}
	}
	/*
	 * 录像定时器
	 */
	class take_videoTask extends TimerTask {
		public void run() {
			if (AppInforToCustom.getAppInforToCustomInstance().getPlayModeStatus()) {
				AppConnect.getInstance().callBack(CustomerInterface.MESSAGE_SHOOTING_START);
				if (AppInforToCustom.getAppInforToCustomInstance().getSDCapacity() < AppInforToCustom.getAppInforToCustomInstance().SDLeastCapacity) {
					if (stop_take_video != null) {
						stop_take_video.cancel();
						stop_take_video = null;
					}
				}
			}
		}
	}
	/*
	 * 停止录像定时器
	 */
	class stop_take_videoTask extends TimerTask {
		public void run() {
			if (AppInforToCustom.getAppInforToCustomInstance().getPlayModeStatus()) {
				AppConnect.getInstance().callBack(CustomerInterface.MESSAGE_SHOOTING_COMPLETE);
			}
		}
	}
	/*
	 * 循环播放的定时器
	 */
	class rePlayTask extends TimerTask {
		@Override
		public void run() {
			replay_start = System.currentTimeMillis();
			if (replayTimer != null)
			{
				replayTimer.cancel();
				replayTimer = null;
				j = 0;
				k = 0;
				s = 0;
				if (take_pictrue != null) {
					take_pictrue.cancel();
					take_pictrue = null;
				}

				if (take_video != null) {
					take_video.cancel();
					take_video = null;
				}

				if (stop_take_video != null) {
					stop_take_video.cancel();
					stop_take_video = null;
				}
				if (playPathToStopTimer != null)
				{
					playPathToStopTimer.cancel();
					playPathToStopTimer = null;
				}
				if (AppInforToCustom.getAppInforToCustomInstance().getPlayModeStatus()) {
					PlayPath_Start(true);
				}
			}
		}
	}
	//播放路径定时器，检测如果到时间，关闭
	class PlayPathStopTimerTask extends TimerTask {
		public void run() {
			if (playPathToStopTimer != null)
			{
				playPathToStopTimer.cancel();
				playPathToStopTimer = null;
			}
			AppConnect.getInstance().callBack(CustomerInterface.MESSAGE_PLAYPATH_END);
		}
	}
	/*
	 * 声明变量
	 */
	public SharedPreferences recordPreferences;
	SharedPreferences.Editor record_edit;
	public String recordFile = "record_path";
	public String FILENSME = "time_record";
	public String FILENAME_V = "time_video";
	public String FILENAME_S = "stop_video";
	private String RECORD_TIME = "record_time";
	public String stop_take_v;
	public String take_p;
	public String take_v;
	private long recordTimeLength;
	public long replay_time = 0;
	public int take_video_T_S = 0;
	public int take_pictrue_T = 0;
	public int video_play_stop = 0;
	public int take_video_T = 0;
	public int video_play = 0;
	public int pictrue_play = 0;
	private long replay_start = 0;
	public int s = 0;
	public int k = 0;
	public int j = 0;
	public Timer take_pictrue;
	public Timer stop_take_video;
	public Timer take_video;
	public Timer replayTimer;
	public Timer playPathToStopTimer = null;
	public long[] stop_time;
	public long[] video_time;
	public long[] time;
}
