package com.seuic.function;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.seuic.AppConnect;
import com.seuic.AppInforToCustom;
import com.seuic.AppLog;
import com.seuic.interfaces.CustomerInterface;

public class AppRecordPathFunction
{
	private Context context = null;
	private static AppRecordPathFunction recordPathInstance = null;
	public AppRecordPathFunction(Context context){
		this.context = context;
	}
	public static AppRecordPathFunction getRecordPathInstance(Context context){
		if (recordPathInstance == null)
		{
			recordPathInstance = new AppRecordPathFunction(context);
		}
		return recordPathInstance;
	}
	
	/*
	 * 录制路径功能
	 */
	public void AppRecordPath(){
		overtime = 0;
		AppInforToCustom.getAppInforToCustomInstance().setRecordPath_flag(1);
		pictrue_play = 0;
		video_play = 0;
		video_play_stop = 0;
		
		recordPreferences = context.getSharedPreferences(recordFile,Activity.MODE_PRIVATE);
		SharedPreferences share_time = context.getSharedPreferences(RECORD_TIME, Activity.MODE_PRIVATE);
		SharedPreferences share = context.getSharedPreferences(FILENSME, Activity.MODE_PRIVATE);
		SharedPreferences share_v = context.getSharedPreferences(FILENAME_V, Activity.MODE_PRIVATE);
		SharedPreferences share_s0 = context.getSharedPreferences(FILENAME_S, Activity.MODE_PRIVATE);
		
		record_edit = recordPreferences.edit();
		SharedPreferences.Editor edit_time = share_time.edit();
		SharedPreferences.Editor edit = share.edit();
		SharedPreferences.Editor edit_v = share_v.edit();
		SharedPreferences.Editor edit_s0 = share_s0.edit();
		
		edit.putInt("pictrue_play", pictrue_play);
		edit_v.putInt("video_play", video_play);
		edit_v.putInt("video_play_stop", video_play_stop);
		edit_s0.putInt("video_play_stop", video_play_stop);

		edit_time.commit();// direction
		edit.commit(); // Picture
		edit_v.commit(); // video 重置之前的数据
		edit_s0.commit();
        AppLog.e("开始录制路径");
		// 发送开始录制轨迹请求
		AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_ShootingPath(true);
		
		startRecordTimeStamp = System.currentTimeMillis();
		recTimer = new Timer(true);
		recTimer.schedule(new RecordTask(), 60000); // 60秒后关闭，最长60秒
		AppConnect.getInstance().callBack(CustomerInterface.MESSAGE_RECODEPATH_START);
	}

	/*
	 * 停止录制路径
	 */
	public void AppStopRecordPath(){
		try {
			// 发送停止录制请求
			AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_ShootingPath(false);
			if (recTimer != null) // 把录制路径的限时定时器取消掉
			{
				recTimer.cancel();
				recTimer = null;
				if (video_pressed == 1) {
					video_play_stop = 1;
					stop_take_v = Integer.toString(take_video_T_S);
					take_video_T_S++;
					stop_video_times = new long[60];
					if (overtime == 1)
					{
						 stop_video_times[take_video_T_S % 60]= 58000;
					}else {
						 stop_video_times[take_video_T_S % 60] = System.currentTimeMillis() - startRecordTimeStamp;
					}
		           
		            SharedPreferences share_s0 = context.getSharedPreferences(FILENAME_S, Activity.MODE_PRIVATE);
					SharedPreferences.Editor edit_s0 = share_s0.edit();
		
					edit_s0.putLong(stop_take_v,stop_video_times[take_video_T_S]);
					edit_s0.putInt("stop_video", take_video_T_S);
					edit_s0.putInt("video_play_stop", video_play_stop);
					edit_s0.commit();
					video_pressed = 0;
				}
				recordTimeLength = System.currentTimeMillis() - startRecordTimeStamp;
				SharedPreferences share_time = context.getSharedPreferences(RECORD_TIME, 0);
				SharedPreferences.Editor edit_time = share_time.edit();
		
				edit_time.putLong("record", recordTimeLength);
				edit_time.commit();
			}
				take_video_T_S = 0;
				take_pictrue_T = 0;
				take_video_T = 0;
				AppLog.e("test", "停止录制路径");
				AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_TransPathFromDevie();
				AppInforToCustom.getAppInforToCustomInstance().setRecordPath_flag(0);
				Thread.sleep(100);//防止命令重合
				AppLog.e("test", "停止录制路径end");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	/*
	 * 录像部分开始录制
	 */
	public void AppRecodPath_VideoStart_1(){
		try
		{
			if (AppInforToCustom.getAppInforToCustomInstance().getRecordPath_flag() == 1)
			{
				video_pressed = 1;
				take_v = Integer.toString(take_video_T);
				video_play = 1;
				take_video_T++;
				if (record_video_times == null)
				{
					record_video_times = new long[200];
				}
				record_video_times[take_video_T % 60] = System.currentTimeMillis() - startRecordTimeStamp;

				SharedPreferences share_v0 = context.getSharedPreferences(FILENAME_V, 0);
				SharedPreferences.Editor edit_v0 = share_v0.edit();

				edit_v0.putLong(take_v,record_video_times[take_video_T % 200]);
				edit_v0.putInt("record_video", take_video_T);
				edit_v0.putInt("video_play", video_play);
				edit_v0.commit();
			}
		} catch (Exception e)
		{
		}
	}
	/*
	 * 录像部分结束录制
	 */
	public void AppRecordPath_VideoEnd_1(){
		try {
			if (AppInforToCustom.getAppInforToCustomInstance().getRecordPath_flag() == 1)
			{
			if (video_pressed == 1) {
				video_play_stop = 1;
				stop_take_v = Integer.toString(take_video_T_S);
				take_video_T_S++;
				if (stop_video_times == null)
				{
					stop_video_times = new long[60];
				}
				stop_video_times[take_video_T_S % 60] = System.currentTimeMillis() - startRecordTimeStamp;
				SharedPreferences share_s0 = context.getSharedPreferences(FILENAME_S, Activity.MODE_PRIVATE);
				SharedPreferences.Editor edit_s0 = share_s0.edit();

				edit_s0.putLong(stop_take_v,stop_video_times[take_video_T_S]);
				edit_s0.putInt("stop_video", take_video_T_S);
				edit_s0.putInt("video_play_stop", video_play_stop);
				edit_s0.commit();
				video_pressed = 0;
			}
			}	
		} catch (Exception e) {
		}
	
	}
	/*
	 * 照相部分开始录制
	 */
	public void AppRecordPath_Photo_1(){
		if (AppInforToCustom.getAppInforToCustomInstance().getRecordPath_flag() == 1) { //标志开始录制路径
			pictrue_play = 1;
			take_p = Integer.toString(take_pictrue_T);
			take_pictrue_T++;
			if (record_times == null)
			{
				record_times = new long[300];
			}
			record_times[take_pictrue_T] = System.currentTimeMillis() - startRecordTimeStamp;
			SharedPreferences share = context.getSharedPreferences(FILENSME, Activity.MODE_PRIVATE);
			SharedPreferences.Editor edit = share.edit();

			edit.putLong(take_p, record_times[take_pictrue_T]);
			edit.putInt("record_times", take_pictrue_T);
			edit.putInt("pictrue_play", pictrue_play);
			edit.commit();
		}
	}
	/*
	 * 录制路径超时函数
	 */
	class RecordTask extends TimerTask {
		public void run() {
			recordTimeLength =System.currentTimeMillis()- startRecordTimeStamp;
			overtime = 1;
			AppConnect.getInstance().callBack(CustomerInterface.MESSAGE_NO_RECORD);
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
	public String take_v; //临时存放录像开始录制的次数的字符串
	public String stop_take_v; //临时存放录像结束录像的次数的字符串
	public String take_p;//临时存放照相的次数的字符串
	private long recordTimeLength = 0;
	private long startRecordTimeStamp = 0;
	public int pictrue_play = 0;
	public int take_pictrue_T = 0;
	public int overtime = 0; //超时标志
	public int video_play = 0;
	public int video_play_stop = 0;
	private int video_pressed = 0;
	public int take_video_T = 0;
	public int take_video_T_S = 0;
	public long[] record_video_times;
	public long[] stop_video_times;
	public long[] record_times;
	/*
	 * 定时器初始化
	 */
	private Timer recTimer;
}
