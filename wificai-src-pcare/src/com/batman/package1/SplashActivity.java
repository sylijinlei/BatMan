package com.batman.package1;

import java.util.Timer;
import java.util.TimerTask;

import com.batman.package1.R;
import com.seuic.AppActivityClose;
import com.seuic.jni.AppDecodeH264;
import com.seuic.jni.AppGetCpuFeatures;
import com.seuic.util.WificarNewLayoutParams;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class SplashActivity extends Activity {
	@Override
	protected void onStop() {
		super.onStop();
		
		Log.e(TAG, "onStop()------");
		
	}
	
	public final static String TAG = "SplashActivity";
	public static int videoRes = 0;
	protected static final int MESSAGE_MAIN_PROCEDURE = 0;
	protected static final int MESSAGE_MAIN_START = 1;
	protected static final int MESSAGE_MAIN_START1 = 2;
	private  static SplashActivity instance = null;
	private Handler handler = null;
	WificarNewLayoutParams wificarParams;	
	RelativeLayout Parent;
	RelativeLayout.LayoutParams parentParams;
	private SurfaceView surfaceView = null ;
	private MediaPlayer mp = null;
    
    public static SplashActivity getInstance(){
		return instance;
	}
    
    public void finishExit(){
		instance.finish();//android.os.Process.killProcess(android.os.Process.myPid())
		instance = null;
	}
    
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppActivityClose.getInstance().addActivity(this);
		Parent = new RelativeLayout(getApplicationContext());
		DisplayMetrics dm = new DisplayMetrics();
		dm = getApplicationContext().getResources()
				.getDisplayMetrics();
		int Screen_width = dm.widthPixels;//屏幕绝对宽度 单位pixel
		int Screen_height = dm.heightPixels;
		Log.e("SplashActivity", "屏幕长宽： "+Screen_width+"  "+Screen_height);
		parentParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		if (Screen_width <= 480) {
			Parent.setBackgroundResource(R.drawable.bg_480_320);
			Log.e(TAG, "Screen_width < 480");
		}else if (Screen_width <= 960) {
			Parent.setBackgroundResource(R.drawable.bg_960_640);
			Log.e(TAG, "Screen_width < 960");
		}else if (Screen_width <= 1024) {
			Parent.setBackgroundResource(R.drawable.bg_1024_768);
			Log.e(TAG, "Screen_width < 1024");
		}
		else if (Screen_width <= 1920) {
			Parent.setBackgroundResource(R.drawable.bg_1920_1080);
			Log.e(TAG, "Screen_width < 1920");
		}
		else {
			Parent.setBackgroundResource(R.drawable.bg_2048_1536);
			Log.e(TAG, "Screen_width < else");
		}
		setContentView(Parent, parentParams);
		
		if ((double)Screen_width/Screen_height == (double)16/9)  //有些设备不支持高分辨率的视频播放  表现为在后面用MediaPlayer播放时提示出错且没有画面显示
		{
			if (Screen_width > 1500)
			{
				videoRes = 1;
			}else {
				videoRes = 0;
			}	
		} else 	{ //4:3 及其他
			if (Screen_width > 1000)
			{
				videoRes = 2;
			} else {
				videoRes = 3;
			}
		}
		Log.e("SplashActivity", "videoRes : "+videoRes);

	     
		instance = this;
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case MESSAGE_MAIN_PROCEDURE:
					Intent intent = new Intent(instance, WificarNew.class);
					startActivity(intent);
					break;
				case MESSAGE_MAIN_START:
					Parent.setBackgroundResource(R.drawable.huana_1920_1080);
					setContentView(Parent, parentParams);
					Timer delayTimer = new Timer();
					delayTimer.schedule(new delayTimert(), 2000);
					Log.e("SplashActivity", "开机画面运行中...");
					break;
				}
				super.handleMessage(msg);
			}
		};
		
		startTimer1 = new Timer();
		startTimer1.schedule(new startTimert(), 2000);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		AppGetCpuFeatures.loadCpuCheck();
		AppGetCpuFeatures.init();
	}
	
	@Override
	protected void onResume() {
		AppDecodeH264.Init(AppGetCpuFeatures.CpuCount);
		super.onResume();
	}
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.e(TAG, "onPause()-------");
	}

	@Override 
	public void onBackPressed() { 
	super.onBackPressed(); 
	System.exit(0);
	Log.e(TAG, "onBackPressed------");
	} 

	
	Timer startTimer1 = null;
	
	class startTimert extends TimerTask{
		@Override
		public void run() {
			Message msg = new Message();
			msg.what = MESSAGE_MAIN_START;
			handler.sendMessage(msg);
		}
		
	}
	
	class delayTimert extends TimerTask{
		@Override
		public void run() {
			Message msg = new Message();
			msg.what = MESSAGE_MAIN_PROCEDURE;
			handler.sendMessage(msg);
		}
		
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
	}

}
