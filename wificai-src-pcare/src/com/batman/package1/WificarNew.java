package com.batman.package1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.vending.expansion.zipfile.APKExpansionSupport;
import com.android.vending.expansion.zipfile.ZipResourceFile;
import com.batman.package1.R;
import com.seuic.AppActivityClose;
import com.seuic.AppCommand;
import com.seuic.AppConnect;
import com.seuic.AppHorizontalScroView;
import com.seuic.AppHorizontalScroViewRight;
import com.seuic.AppInforToCustom;
import com.seuic.AppInforToSystem;
import com.seuic.AppJet_MoveCar;
import com.seuic.AppJet_MoveCar.AppJet_MoveCarListener;
import com.seuic.AppLR_MoveCar;
import com.seuic.AppLR_MoveCar.AppLR_MoveCarListener;
import com.seuic.AppLog;
import com.seuic.AppUD_MoveCar;
import com.seuic.AppUD_MoveCar.AppUD_MoveCarListener;
import com.seuic.AppVolume_MoveCar;
import com.seuic.AppVolume_MoveCar.AppVolume_MoveCarListener;
import com.seuic.PanelBom;
import com.seuic.function.AppBrightLightFunction;
import com.seuic.function.AppCameraShootingFunction;
import com.seuic.function.AppCameraSurfaceFunction;
import com.seuic.function.AppDeviceMoveFunction;
import com.seuic.function.AppDeviceStateLedFunction;
import com.seuic.function.AppGSenserFunction;
import com.seuic.function.AppListenAudioFunction;
import com.seuic.function.AppNightLightFunction;
import com.seuic.function.AppPlayPathFunction;
import com.seuic.function.AppRecordPathFunction;
import com.seuic.function.AppSpkAudioFunction;
import com.seuic.protocol.CMD_OP_CODE;
import com.seuic.share.ShareActivity;
import com.seuic.thread.AppThread;
import com.seuic.util.WificarNewLayoutParams;
import com.wificar.dialog.Connect_Dialog;
import com.wificar.dialog.G_Dilaog;
import com.wificar.surface.CameraSurfaceView;
import com.wificar.util.MessageUtility;


public class WificarNew extends Activity implements OnClickListener,
		OnTouchListener, OnChronometerTickListener{
	public static final String TAG = "WificarNew";
	public static WificarNew instance;
	public WificarNewLayoutParams wificarNewLayoutParams;
	public Handler handler = null;
	public static boolean isDoorOpen;
	public static boolean isDooring;
	public static boolean isSpoiler;
	public static boolean isJet;
	public static boolean isCharging = false;
	public static boolean isShow =false;
	boolean isSingleTap = true;
	public static boolean isRocket;
	public int a = 1; 
	MediaPlayer mediaPlayer;
	MediaPlayer mediaPlayer1;
	MediaPlayer mpfunc;
	AlertDialog.Builder builder;
	private Drawable bgDrawable;
	AudioManager mAudioManager;
	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppActivityClose.getInstance().addActivity(this);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		instance = this;
		new MessageUtility(instance);
		wificarNewLayoutParams = WificarNewLayoutParams.getWificarNewLayoutParams(instance);
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE); 
		AppLog.enableLogging(true); // 打开log信息
		disG_Dilaog = new G_Dilaog(this, R.style.CustomDialog);
		mediaPlayer = MediaPlayer.create(this, R.raw.gun);
		 try
		{
			mediaPlayer.prepare();
		} catch (IllegalStateException e2)
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2)
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}  
	    
		mediaPlayer1 = MediaPlayer.create(this, R.raw.rocket);
		try
		{
			mediaPlayer1.prepare();
		} catch (IllegalStateException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
	    mediaPlayer1.setLooping(true);
		
		surfaceView = new SurfaceView(this);
		surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);	
		surfaceView.getHolder().addCallback(new SurfaceHolder.Callback()
		{
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder)
			{
				// TODO Auto-generated method stub
				Log.e("Callback", "in surfaceDestroyed...");
				if (mpfunc.isPlaying())
				{
					mpfunc.stop();
				}
				mpfunc.release();
				mpfunc = null;
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder holder)
			{
				// TODO Auto-generated method stub
				Log.e("Callback", "in surfaceCreated...");
				AssetFileDescriptor doorFd = null;
				ZipResourceFile expansionFile = null;
				switch (SplashActivity.videoRes)
				{
				case 0:
				case 1:
					try
					{
						expansionFile = APKExpansionSupport.getAPKExpansionZipFile(instance,
						        1, 0);
						if (expansionFile != null)
						{
							Log.e("getAPKExpansionZipFile", "getAPKExpansionZipFile ret not null ...");
							doorFd = expansionFile.getAssetFileDescriptor("door_1280_720.mp4");
							mpfunc = new MediaPlayer();
							mpfunc.setAudioStreamType(AudioManager.STREAM_MUSIC);
							mpfunc.setDataSource(doorFd.getFileDescriptor(), doorFd.getStartOffset(), doorFd.getLength());
							doorFd.close();
					        mpfunc.setDisplay(surfaceView.getHolder());
					        mpfunc.prepare();
					        mpfunc.start();	
						} else {
							Log.e("getAPKExpansionZipFile", "getAPKExpansionZipFile ret null ...");
						}
						
						
					} catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Log.e(TAG, "door_1280_720");
					break;
				case 2:
					try
					{
						expansionFile = APKExpansionSupport.getAPKExpansionZipFile(instance,
						        1, 0);
						if (expansionFile != null)
						{
							Log.e("getAPKExpansionZipFile", "getAPKExpansionZipFile ret not null ...");
							doorFd = expansionFile.getAssetFileDescriptor("door_1024_768.mp4");
							mpfunc = new MediaPlayer();
							mpfunc.setAudioStreamType(AudioManager.STREAM_MUSIC);
							mpfunc.setDataSource(doorFd.getFileDescriptor(), doorFd.getStartOffset(), doorFd.getLength());
							doorFd.close();
					        mpfunc.setDisplay(surfaceView.getHolder());
					        mpfunc.prepare();
					        mpfunc.start();	
						}else {
							Log.e("getAPKExpansionZipFile", "getAPKExpansionZipFile ret null ...");
						}
					} catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Log.e(TAG, "door_1024_768");
					break;
				case 3:
					try
					{
						expansionFile = APKExpansionSupport.getAPKExpansionZipFile(instance,
						        1, 0);
						if (expansionFile != null)
						{
							Log.e("getAPKExpansionZipFile", "getAPKExpansionZipFile ret not null ...");
							doorFd = expansionFile.getAssetFileDescriptor("door_640_480.mp4");
							mpfunc = new MediaPlayer();
							mpfunc.setAudioStreamType(AudioManager.STREAM_MUSIC);
							mpfunc.setDataSource(doorFd.getFileDescriptor(), doorFd.getStartOffset(), doorFd.getLength());
							doorFd.close();
					        mpfunc.setDisplay(surfaceView.getHolder());
					        mpfunc.prepare();
					        mpfunc.start();	
						}else {
							Log.e("getAPKExpansionZipFile", "getAPKExpansionZipFile ret null ...");
						}
					} catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Log.e(TAG, "door_640_480");
					break;
				default:
					try
					{
						expansionFile = APKExpansionSupport.getAPKExpansionZipFile(instance,
						        1, 0);
						if (expansionFile != null)
						{
							Log.e("getAPKExpansionZipFile", "getAPKExpansionZipFile ret not null ...");
							doorFd = expansionFile.getAssetFileDescriptor("door_1024_768.mp4");
							mpfunc = new MediaPlayer();
							mpfunc.setAudioStreamType(AudioManager.STREAM_MUSIC);
							mpfunc.setDataSource(doorFd.getFileDescriptor(), doorFd.getStartOffset(), doorFd.getLength());
							doorFd.close();
					        mpfunc.setDisplay(surfaceView.getHolder());
					        mpfunc.prepare();
					        mpfunc.start();	
						}else {
							Log.e("getAPKExpansionZipFile", "getAPKExpansionZipFile ret null ...");
						}
					} catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Log.e(TAG, "door_1024_768");
					break;
				}
				
				if (doorFd == null) {
					Log.e("doorFd", "doorFd == null");
				} else {
					Log.e("doorFd", "doorFd != null");
				}
				
		        mpfunc.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
				{
					
					@Override
					public void onCompletion(MediaPlayer mp)
					{
						// TODO Auto-generated method stub
						Log.e("onCompletion", "mpfunc play done ...");
						handler.sendEmptyMessage(MESSAGE_DEMO_END);
						if (AppInforToSystem.isFirstApp)
						{
							handler.sendEmptyMessage(MESSAGE_SHOW_DEMODIALOG);
						}
						AppCommand.getAppCommandInstace().sendCommand(CMD_OP_CODE.Demo_Animation_End);
					}
				});

			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		if (wificarNewLayoutParams.screenSize > 5.8) {
			setHLayoutparams();
		} else {
			setLayoutparams();
		}
		try {
			handler = new Handler() {
				
				@Override
				public void handleMessage(Message msg) {
					switch (msg.what) {
					case MESSAGE_SOUNDSLIDER_CANCEL:

						break;
					case MESSAGE_SHOWINFORMATION_END:
						ShowInfoTView.setText(" ");
						if (showInforTimer != null) {
							showInforTimer.cancel();
							showInforTimer = null;
						}
						break;
					case MESSAGE_SCALE_END:
						ScaleTView.setText(" ");
						if (scaleTimer != null) {
							scaleTimer.cancel();
							scaleTimer = null;
						}
						break;
					case MESSAGE_CONNECT_TO_CAR_FAIL:
						Connect_Dialog.createconnectDialog(instance).show();
						break;
					case MESSAGE_CONNECT_TO_CAR_SUCCESS:
						CheckFirstApp();
						if(AppInforToSystem.isFirstApp){
							Log.e(TAG, "AppInforToSystem.isFirstApp1:"+AppInforToSystem.isFirstApp);
							AppCommand.getAppCommandInstace().Func(false);
							Log.e(TAG, "Func0");
						}else{
							Log.e(TAG, "AppInforToSystem.isFirstApp2:"+AppInforToSystem.isFirstApp);
							AppCommand.getAppCommandInstace().Func(true);
							Log.e(TAG, "Func1");
						}
						
						break;
					case MESSAGE_CONNECT_TO_CAR:
						break;
					case MESSAGE_NO_SHOOTING:
						if (wificarNewLayoutParams.screenSize < 5.8) {
							Video_Btn
									.setBackgroundResource(R.drawable.video_off_phone);
							Video1_Btn
							.setBackgroundResource(R.drawable.video_off_phone);
						} else {
							Video_Btn
									.setBackgroundResource(R.drawable.video_off);
							Video1_Btn
							.setBackgroundResource(R.drawable.video_off);
						}
						break;
					case MESSAGE_NO_RECORD:
						RecordPathStop();
						break;
					case MESSAGE_PING_FAIL:
						if (isFirstExit) {
							isFirstExit = false;
							if (AppInforToCustom.getAppInforToCustomInstance()
									.getIsGSensorControl()) {
								sendMessages(MESSAGE_GSENSOR_END);
							}
							// 如果是隐身模式，则开启状态灯
							if (AppInforToCustom.getAppInforToCustomInstance()
									.getIsStealthControl()) {
								AppDeviceStateLedFunction
										.getAppDeviceStateLedFunctionInstance()
										.DeviceStateLed_OandC(true);
							}
							if (AppInforToCustom.getAppInforToCustomInstance()
									.getIsCameraShooting()) {
								chronometer.stop();
								Video_Red.setVisibility(View.INVISIBLE);
								chronometer.setVisibility(View.INVISIBLE);
								AppCameraShootingFunction
										.getAppCameraShootingFunctionInstance()
										.ShootingClose();
								if (wificarNewLayoutParams.screenSize < 5.8) {
									Talk_Btn.setBackgroundResource(R.drawable.talk_off_ipad);
								} else {
									Talk_Btn.setBackgroundResource(R.drawable.talk_off_ipad);
								}
								if (playPathFirstFlag) {
									PlayPath_Btn.getBackground().setAlpha(255);
									if (wificarNewLayoutParams.screenSize < 5.8) {
										PlayPath_Btn
												.setBackgroundResource(R.drawable.play_path_off_phone);
									} else {
										PlayPath_Btn
												.setBackgroundResource(R.drawable.play_path_off);
									}
								}
								if (wificarNewLayoutParams.screenSize < 5.8) {
									Video_Btn
											.setBackgroundResource(R.drawable.video_off_phone);
									Video1_Btn
									.setBackgroundResource(R.drawable.video_off_phone);
								} else {
									Video_Btn
											.setBackgroundResource(R.drawable.video_off);
									Video1_Btn
									.setBackgroundResource(R.drawable.video_off);
								}
							}
							if (AppInforToCustom.getAppInforToCustomInstance()
									.getRecordPath_flag() == 1) {
								RecordPathStop();
							}
							AlertDialog.Builder ping1 = new AlertDialog.Builder(
									instance);
							ping1.setTitle(R.string.net_connect_error_status)
									.setMessage(R.string.net_disconnect_dialog)
									.setPositiveButton(
											R.string.net_disconnect_exit,
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int which) {
													isHomeExit = false;
													unregisterReceiver(spReceiver);
													AppConnect.getInstance()
															.exitApp();
													if (!isShared) {
														AppActivityClose
																.getInstance()
																.exitAll();
													}
												}
											})
									.setNegativeButton(
											R.string.net_connect_error_share,
											new DialogInterface.OnClickListener() {
												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													if (WificarNew.instance != null) {
														WificarNew.instance
																.share();
													}
												}
											}).setCancelable(false).show();
						}
						break;
					case MESSAGE_PHONE_CAPACITY_LOW:
						if (AppInforToCustom.getAppInforToCustomInstance()
								.getIsCameraShooting()) {// 如果正在录像的话，关闭录像
							ShootingComplte();
							Toast.makeText(instance,
									R.string.wificar_no_enough,
									Toast.LENGTH_SHORT).show();
						}
						break;
					case MESSAGE_PHOTOGRAPH_END:
						endTakePicture();
						break;
					case MESSAGE_PLAYPATH_END:
						playpath_end();
						break;
					case MESSAGE_SHOOTING_START:
						ShootingStart();
						break;
					case MESSAGE_SHOOTING_COMPLETE:
						ShootingComplte();
						break;
					case MESSAGE_CAMERACHANGE_END:
						ShowInfoTView.setText(" ");
						ShowInfoTView.setBackgroundColor(getResources()
								.getColor(R.color.transparent));
						Photo_Btn.setEnabled(true);
						Photo1_Btn.setEnabled(true);
						Video_Btn.setEnabled(true);
						Video1_Btn.setEnabled(true);
						ZoomIn.setEnabled(true);
						ZoomOut.setEnabled(true);
						AppInforToSystem.isCameraChanging = false;
						break;
					case MESSAGE_GSENSOR_END:
						GMove_end();
						System.gc();
						break;
					case MESSAGE_NIGHT_LIGNTH_END: // 关闭隐身模式需要打开状态灯
						AppNightLightFunction
								.getAppNightLightFunctionInstance()
								.NightLight_OandC(false);
						if (wificarNewLayoutParams.screenSize < 5.8) {
							Ir_Btn
									.setBackgroundResource(R.drawable.ir_off_phone);
							Ir1_Btn
							.setBackgroundResource(R.drawable.ir_off_phone);
						} else {
							Ir_Btn
									.setBackgroundResource(R.drawable.ir_off1);
							Ir1_Btn
							.setBackgroundResource(R.drawable.ir_off);
						}
						Ir_Btn.setEnabled(true);
						Ir1_Btn.setEnabled(true);
						Lights_Btn.setEnabled(true);
						Lights_Btn.getBackground().setAlpha(255);
						if (AppInforToCustom.getAppInforToCustomInstance()
								.getIsBrightControl()) {
							if (wificarNewLayoutParams.screenSize < 5.8) {
								Lights_Btn
										.setBackgroundResource(R.drawable.light_on_phone);
							} else {
								Lights_Btn
										.setBackgroundResource(R.drawable.lights_on);
							}
						}
						break;
					case MESSAGE_LISTENING_END:
						if (listenLimitClickTimes == 0) {
							if (horzontalTimer != null) {
								horzontalTimer.cancel();
								horzontalTimer = null;
							}
							listenLimitClickTimes = 1;
							Mic_Btn.setEnabled(false);
							if (!AppInforToCustom.getAppInforToCustomInstance()
									.getIsCameraShooting()) {
								Talk_Btn.setEnabled(false);
							}
							if (wificarNewLayoutParams.screenSize < 5.8) {
								Mic_Btn
										.setBackgroundResource(R.drawable.volume_off_phone);
							} else {
								Mic_Btn
										.setBackgroundResource(R.drawable.volume_off_ipad);
							}
							listenCloseTimer = new Timer();
							listenCloseTimer.schedule(new listenTimerTask(),
									1000);
						} else {
							listenCloseTimer.cancel();
							listenCloseTimer = null;
							listenLimitClickTimes = 0;
							if (!AppInforToCustom.getAppInforToCustomInstance()
									.getIsCameraShooting()) {
								Talk_Btn.setEnabled(true);
							}
						}
						break;
					case MESSAGE_RECORDPATH_COMPLETE:
						RecordPathStop();
						break;
					case MESSAGE_SPK_END_SUCCESS:
						if(AppVolume_MoveCar.appVolume_MoveCar.stickBar_Local<(AppVolume_MoveCar.appVolume_MoveCar.stickBar_maxHeight-5))
						{
							try {
								AppListenAudioFunction.getAppAudioFunctionInstance()
								.AudioEnable();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (WificarNew.instance.wificarNewLayoutParams.screenSize < 5.8) {
								WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_on_phone);
							} else {
								WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_on_ipad);
							}
						}else if(AppVolume_MoveCar.appVolume_MoveCar.stickBar_Local>=(AppVolume_MoveCar.appVolume_MoveCar.stickBar_maxHeight-5))
						{
							AppListenAudioFunction.getAppAudioFunctionInstance().AudioDisable();
							if (WificarNew.instance.wificarNewLayoutParams.screenSize < 5.8) {
								WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_off_phone);
							} else {
								WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_off_ipad);
							}
						}
						
						
						appVolume_MoveCar.setTalkVoiceFlageCurrent(0);
						
						
						Talk_Btn.setEnabled(true);
						break;
					case MESSAGE_GSENSER_ENABLE:
						if (wificarNewLayoutParams.screenSize <= 5.8) {
						G_Btn1.setEnabled(true);
						G_Btn.setEnabled(true);
						}else{
							G_Btn.setEnabled(true);
						}
						break;
					case MESSAGE_VIDEO_ENABLE:
						if (wificarNewLayoutParams.screenSize <= 5.8) {
							Video_Btn.setEnabled(true);
							Video1_Btn.setEnabled(true);
							}else{
								Video_Btn.setEnabled(true);
								Video1_Btn.setEnabled(true);
							}
							break;
					case MESSGE_FUNC:
						isCharging = false;
						Log.e(TAG, "MESSGE_FUNC");
						//第一次运行
						if(a==1){
							AppBrightLightFunction.getAppBrightLightFunctionInstance().BrightLight_OandC(true);
							if (WificarNew.instance.wificarNewLayoutParams.screenSize < 5.8) {
								Lights_Btn
								.setBackgroundResource(R.drawable.light_on_phone);
							}else{
								Lights_Btn
								.setBackgroundResource(R.drawable.lights_on);
							}
							a=2;							
						}
						if(func==true){
							ShowInfo(R.string.nofunshowing, Toast.LENGTH_LONG);						
						} else {	
							Parent1.addView(Parent0,wificarNewLayoutParams.parentParams);
							Parent1.addView(Parent,wificarNewLayoutParams.parentParams);
							Parent1.removeView(connectLayout);
							if(AppInforToSystem.isFirstApp)
							{
								surfParent.addView(surfaceView,wificarNewLayoutParams.contentParams);
								setContentView(surfParent, wificarNewLayoutParams.parentParams0);
							}
						}
						Bat_Btn.setEnabled(true);
						if (wificarNewLayoutParams.screenSize < 5.8) {
							setLeftScrollToRight();
						}
						break;
					case MESSGE_FUNC_START:
						isCharging = true;
						isShow = true;
						if (func != true)
						{
							Parent1.addView(Parent0,wificarNewLayoutParams.parentParams);
							Parent1.addView(Parent,wificarNewLayoutParams.parentParams);
							surfParent.addView(surfaceView,wificarNewLayoutParams.contentParams);
							Parent1.removeView(connectLayout);
							setContentView(surfParent, wificarNewLayoutParams.parentParams0);
						} else {
							if (AppInforToCustom.getAppInforToCustomInstance().getIsBrightControl())
							{
								if (wificarNewLayoutParams.screenSize <= 5.8) {
									Lights_Btn.setBackgroundResource(R.drawable.light_off_phone);
								} else {
									Lights_Btn.setBackgroundResource(R.drawable.lights_off);
								}
							}
							if (AppInforToCustom.getAppInforToCustomInstance().getIsDoor())
							{
								if (wificarNewLayoutParams.screenSize <= 5.8) {
									Door_Btn.setBackgroundResource(R.drawable.door_off_phone);
								} else {
									Door_Btn.setBackgroundResource(R.drawable.door_off);	
								}
							}
							if (spoiler)
							{
								spoiler=false;
								isSpoiler=false;
							   Log.e(TAG, "spoiler false:"+spoiler);
							   AppCommand.getAppCommandInstace().stopSpoiler(true);
							   if (wificarNewLayoutParams.screenSize <= 5.8) {
								   Spoiler_Btn.setBackgroundResource(R.drawable.spoiler_off_phone);
							   } else {
								   Spoiler_Btn.setBackgroundResource(R.drawable.spoiler_off);
							   }
							}
							
							funcProgressDialog.show();
							AppCommand.getAppCommandInstace().sendCommand(CMD_OP_CODE.Demo_Animation_End);
						}
						
						Bat_Btn.setEnabled(true);
						Log.e(TAG, "MESSGE_FUNC_START");
						break;
					case MESSGE_FUNC_STOP:
						isShow = false;
						funcProgressDialog.cancel();
						AppBrightLightFunction.getAppBrightLightFunctionInstance().BrightLight_OandC(false);
						if (wificarNewLayoutParams.screenSize <= 5.8) {
							Lights_Btn.setBackgroundResource(R.drawable.light_off_phone);
						} else {
							Lights_Btn.setBackgroundResource(R.drawable.lights_off);
						}
							Log.e("MESSAGE_DOOR_DONE", "setIsDoor(false)");
							AppInforToCustom.getAppInforToCustomInstance().setIsDoor(false);
							isDoorOpen = false;
							isDooring = false;
							doorCloseProgressDialog.cancel();
							if(wificarNewLayoutParams.screenSize <= 5.8){
								Path_Btn.setEnabled(true);
								Path_Btn.getBackground().setAlpha(255);
								PlayPath_Btn.setEnabled(true);
								PlayPath_Btn.getBackground().setAlpha(255);
								G_Btn.setEnabled(true);
								G_Btn.getBackground().setAlpha(255);
								G_Btn1.setEnabled(true);
								G_Btn1.getBackground().setAlpha(255);
								Talk_Btn.setEnabled(true);
								Talk_Btn.getBackground().setAlpha(255);
								appUD_MoveCar.setEnabled(true);
								appUD_MoveCar.getBackground().setAlpha(255);
								appLR_MoveCar.setEnabled(true);
								appLR_MoveCar.getBackground().setAlpha(255);
								appJet_Movebar.setEnabled(true);
								appJet_Movebar.getBackground().setAlpha(255);
								
								appVolume_MoveCar.setEnabled(true);
								appVolume_MoveCar.getBackground().setAlpha(255);
							}else{
								Talk_Btn.setEnabled(true);
								Talk_Btn.getBackground().setAlpha(255);
								Path_Btn.setEnabled(true);
								Path_Btn.getBackground().setAlpha(255);
								PlayPath_Btn.setEnabled(true);
								PlayPath_Btn.getBackground().setAlpha(255);
								G_Btn.setEnabled(true);
								G_Btn.getBackground().setAlpha(255);
								
								appUD_MoveCar.setEnabled(true);
								appUD_MoveCar.getBackground().setAlpha(255);
								appLR_MoveCar.setEnabled(true);
								appLR_MoveCar.getBackground().setAlpha(255);
								appJet_Movebar.setEnabled(true);
								appJet_Movebar.getBackground().setAlpha(255);
								appVolume_MoveCar.setEnabled(true);
								appVolume_MoveCar.getBackground().setAlpha(255);
							}
							if(AppVolume_MoveCar.appVolume_MoveCar.stickBar_Local<(AppVolume_MoveCar.appVolume_MoveCar.stickBar_maxHeight-5))
							{
								try {
									AppListenAudioFunction.getAppAudioFunctionInstance()
									.AudioEnable();
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if (WificarNew.instance.wificarNewLayoutParams.screenSize < 5.8) {
									WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_on_phone);
								} else {
									WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_on_ipad);
								}
							}else if(AppVolume_MoveCar.appVolume_MoveCar.stickBar_Local>=(AppVolume_MoveCar.appVolume_MoveCar.stickBar_maxHeight-5))
							{
								AppListenAudioFunction.getAppAudioFunctionInstance().AudioDisable();
								if (WificarNew.instance.wificarNewLayoutParams.screenSize < 5.8) {
									WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_off_phone);
								} else {
									WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_off_ipad);
								}
							}
						Log.e(TAG, "MESSGE_FUNC_STOP");
						if (wificarNewLayoutParams.screenSize < 5.8) {
							setLeftScrollToRight();
						}
						break;
					case MESSAGE_GSENSER_START:
						if (!AppGSenserFunction
								.getAppCameraSurfaceFunction(instance).isFirst) {
							if (AppInforToCustom.getAppInforToCustomInstance()
									.getPlayModeStatus()) {
								playpath_end();
							}
							if (AppGSenserFunction.Sensor_Type != 2) {
								// 要隐藏虚拟摇杆
								//TODO
								appUD_MoveCar.Hided(1);
								appLR_MoveCar.Hided(1);
								Ir_Btn.setVisibility(View.INVISIBLE);
								Path_Btn.setVisibility(View.INVISIBLE);
								PlayPath_Btn.setVisibility(View.INVISIBLE);
								Share_Btn.setVisibility(View.INVISIBLE);
								Door_Btn.setVisibility(View.INVISIBLE);
								Photo_Btn.setVisibility(View.INVISIBLE);
								Photo1_Btn.setVisibility(View.VISIBLE);
								Mgun_Btn.setVisibility(View.VISIBLE);
								Rocket_Btn.setVisibility(View.VISIBLE);
								Lights_Btn.setVisibility(View.INVISIBLE);
								Spoiler_Btn.setVisibility(View.INVISIBLE);
								Video_Btn.setVisibility(View.INVISIBLE);
								Ir1_Btn.setVisibility(View.VISIBLE);
								Video1_Btn.setVisibility(View.VISIBLE);
								if (wificarNewLayoutParams.screenSize <= 5.8) {
									appHorizontalScroView.setVisibility(View.INVISIBLE);
									scrol_left.setVisibility(View.INVISIBLE);
									scrol_right.setVisibility(View.INVISIBLE);
									scrol_left1.setVisibility(View.INVISIBLE);
									scrol_right1.setVisibility(View.INVISIBLE);
									appHorizontalScroView1.setVisibility(View.INVISIBLE);
									G_Btn1.setVisibility(View.VISIBLE);
									G_Btn1.setBackgroundResource(R.drawable.g_on_phone);
									if ((float)wificarNewLayoutParams.Screen_width / wificarNewLayoutParams.Screen_height == (float)960 / 540)
									{
										Log.e("Background", "比例是960 : 540");
										Parent.setBackgroundResource(R.drawable.phonebackground1);
									}
									else if((float)wificarNewLayoutParams.Screen_width / wificarNewLayoutParams.Screen_height == (float)854 / 480)
									{
										Log.e("Background", "比例是854 : 480");
										Parent.setBackgroundResource(R.drawable.dbackground854_480);
									}
									else 
									{
										Log.e("Background", "比例是854 : 480");
										Parent.setBackgroundResource(R.drawable.dbackground854_480);
									}
								} else {
									G_Btn.setBackgroundResource(R.drawable.g_on);
									Ir_Btn.setVisibility(View.INVISIBLE);
									Path_Btn.setVisibility(View.INVISIBLE);
									PlayPath_Btn.setVisibility(View.INVISIBLE);
									Share_Btn.setVisibility(View.INVISIBLE);
									Door_Btn.setVisibility(View.INVISIBLE);
									Photo_Btn.setVisibility(View.INVISIBLE);
									Photo1_Btn.setVisibility(View.VISIBLE);
									Mgun_Btn.setVisibility(View.VISIBLE);
									Rocket_Btn.setVisibility(View.VISIBLE);
									Lights_Btn.setVisibility(View.INVISIBLE);
									Spoiler_Btn.setVisibility(View.INVISIBLE);
									Video_Btn.setVisibility(View.INVISIBLE);
									Ir1_Btn.setVisibility(View.VISIBLE);
									Video1_Btn.setVisibility(View.VISIBLE);
									
									if ((float)wificarNewLayoutParams.Screen_width / wificarNewLayoutParams.Screen_height == (float)1280 / 800)
									{
										Log.e("Background", "比例是1280:800.。。");	
										Parent.setBackgroundResource(R.drawable.db1);
									} else if ((float)wificarNewLayoutParams.Screen_width / wificarNewLayoutParams.Screen_height == (float)4 / 3)
									{
										Parent.setBackgroundResource(R.drawable.db1024_768);
									}
									else if(wificarNewLayoutParams.Screen_height>800){
										Log.e("Background", "height>800.。。....");
										Parent.setBackgroundResource(R.drawable.dbackground1);
									}else {
										if(wificarNewLayoutParams.Screen_height < 744) {
											Log.e("Background", "比例选择是1280:736.。。");
											System.gc();
											bgDrawable = Parent.getBackground();
											WeakReference wrb = new WeakReference(bgDrawable);
											bgDrawable = null;
											System.gc();
											bgDrawable = getApplication().getResources().getDrawable(R.drawable.dbackgrounds1);
											Parent.setBackgroundDrawable(bgDrawable);
										} else {
											System.gc();
											bgDrawable = Parent.getBackground();
											WeakReference wrb = new WeakReference(bgDrawable);
											bgDrawable = null;
											System.gc();
											bgDrawable = getApplication().getResources().getDrawable(R.drawable.dbackground1);
											Parent.setBackgroundDrawable(bgDrawable);
											Log.e("Background", "比例选择是1280:752.。。");
										}	
									}
									
									
								}
							} else {
								new AlertDialog.Builder(instance)
										.setMessage(R.string.Gsensor_none)
										.create().show();
							}
						} else {
							showDialog();
							AppGSenserFunction.getAppCameraSurfaceFunction(
									instance).DisableGSensorControl();
							if (wificarNewLayoutParams.screenSize < 5.8) {
								G_Btn.setBackgroundResource(R.drawable.g_off_phone);
								G_Btn1.setBackgroundResource(R.drawable.g_off_phone);
							} else {
								G_Btn.setBackgroundResource(R.drawable.g_off);
							}
						}
						break;
					case MESSAGE_TALK_PRESS:
						if (talkPressTimer != null) {
							talkPressTimer.cancel();
							talkPressTimer = null;
						}
						Mic_Btn.setEnabled(false);
						Video_Btn.setEnabled(false);
						Video1_Btn.setEnabled(false);
						if (AppInforToSystem.islistening) {
							if (horzontalTimer != null) {
								horzontalTimer.cancel();
								horzontalTimer = null;
							}
							appVolume_MoveCar.setTalkVoiceFlageCurrent(1);
							AppListenAudioFunction.getAppAudioFunctionInstance().AudioDisable();
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							} // 给小车那边有足够的时间关闭监听
						}
						AppSpkAudioFunction.getAppSpkAudioFunctionInstance()
								.SpkAudioEnable();
						break;

					case MESSAGE_HOME_PRESS:
						if (!isShared) {
							sendBroadcast(new Intent(
									Intent.ACTION_MEDIA_MOUNTED,
									Uri.parse("file://"
											+ Environment
													.getExternalStorageDirectory()
													.getAbsolutePath())));
							AppActivityClose.getInstance().exitAll();
						}
						break;
					case MESSAGE_SCROLL_LR_FLAG:
						break;
					case MESSGE_SCROLL_RIGHT:
						break;

					case MESSAGE_JET_END:
						appUD_MoveCar.appUD_MoveCar.isJetDo = false;
						AppJet_MoveCar.appJet_Movebar.isJetDo = false;
						AppJet_MoveCar.appJet_Movebar.isHightLight=false;
						AppJet_MoveCar.appJet_Movebar.init();
						ShowInfo(R.string.Jetend, Toast.LENGTH_LONG);
						break;
					case MESSGE_SPOILER:
						Log.e("MESSGE_SPOILER", "MESSGE_SPOILER...");
						if(isSpoiler==true){
							spoilerOpenProgressDialog.cancel();
							Spoiler_Btn.setEnabled(true);
							Spoiler_Btn.getBackground().setAlpha(255);
						}else{
							spoilerCloseProgressDialog.cancel();
							Spoiler_Btn.setEnabled(true);
							Spoiler_Btn.getBackground().setAlpha(255);
						}
						
//TODO
					case MESSAGE_DOOR_DONE:
						Log.e("MESSAGE_DOOR_DONE", "MESSAGE_DOOR_DONE...");
						if (AppInforToCustom.getAppInforToCustomInstance().getIsDoor() == true) {
							Log.e("MESSAGE_DOOR_DONE", "setIsStopDoor(false)");
							AppInforToCustom.getAppInforToCustomInstance().setIsStopDoor(false);
							Talk_Btn.setEnabled(true);
							Talk_Btn.getBackground().setAlpha(255);
							appVolume_MoveCar.setEnabled(true);
							appVolume_MoveCar.getBackground().setAlpha(255);
							isDoorOpen = true;
							isDooring =false;
							doorOpenProgressDialog.cancel();
							Log.e("MESSAGE_DOOR_DONE", "isDoorOpen:"+isDoorOpen);
							if(AppVolume_MoveCar.appVolume_MoveCar.stickBar_Local<(AppVolume_MoveCar.appVolume_MoveCar.stickBar_maxHeight-5))
							{
								try {
									AppListenAudioFunction.getAppAudioFunctionInstance()
									.AudioEnable();
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if (WificarNew.instance.wificarNewLayoutParams.screenSize < 5.8) {
									WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_on_phone);
								} else {
									WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_on_ipad);
								}
							}else if(AppVolume_MoveCar.appVolume_MoveCar.stickBar_Local>=(AppVolume_MoveCar.appVolume_MoveCar.stickBar_maxHeight-5))
							{
								AppListenAudioFunction.getAppAudioFunctionInstance().AudioDisable();
								if (WificarNew.instance.wificarNewLayoutParams.screenSize < 5.8) {
									WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_off_phone);
								} else {
									WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_off_ipad);
								}
							}
						}
						if (AppInforToCustom.getAppInforToCustomInstance().getIsStopDoor() == true)
						{
							Log.e("MESSAGE_DOOR_DONE", "setIsDoor(false)");
							AppInforToCustom.getAppInforToCustomInstance().setIsDoor(false);
							isDoorOpen = false;
							isDooring = false;
							doorCloseProgressDialog.cancel();
							if(wificarNewLayoutParams.screenSize <= 5.8){
								Path_Btn.setEnabled(true);
								Path_Btn.getBackground().setAlpha(255);
								PlayPath_Btn.setEnabled(true);
								PlayPath_Btn.getBackground().setAlpha(255);
								G_Btn.setEnabled(true);
								G_Btn.getBackground().setAlpha(255);
								G_Btn1.setEnabled(true);
								G_Btn1.getBackground().setAlpha(255);
								Talk_Btn.setEnabled(true);
								Talk_Btn.getBackground().setAlpha(255);
								appUD_MoveCar.setEnabled(true);
								appUD_MoveCar.getBackground().setAlpha(255);
								appLR_MoveCar.setEnabled(true);
								appLR_MoveCar.getBackground().setAlpha(255);
								appJet_Movebar.setEnabled(true);
								appJet_Movebar.getBackground().setAlpha(255);
							}else{
								Talk_Btn.setEnabled(true);
								Talk_Btn.getBackground().setAlpha(255);
								Path_Btn.setEnabled(true);
								Path_Btn.getBackground().setAlpha(255);
								PlayPath_Btn.setEnabled(true);
								PlayPath_Btn.getBackground().setAlpha(255);
								G_Btn.setEnabled(true);
								G_Btn.getBackground().setAlpha(255);
								
								appUD_MoveCar.setEnabled(true);
								appUD_MoveCar.getBackground().setAlpha(255);
								appLR_MoveCar.setEnabled(true);
								appLR_MoveCar.getBackground().setAlpha(255);
								appJet_Movebar.setEnabled(true);
								appJet_Movebar.getBackground().setAlpha(255);
							}
							if(AppVolume_MoveCar.appVolume_MoveCar.stickBar_Local<(AppVolume_MoveCar.appVolume_MoveCar.stickBar_maxHeight-5))
							{
								try {
									AppListenAudioFunction.getAppAudioFunctionInstance()
									.AudioEnable();
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if (WificarNew.instance.wificarNewLayoutParams.screenSize < 5.8) {
									WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_on_phone);
								} else {
									WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_on_ipad);
								}
							}else if(AppVolume_MoveCar.appVolume_MoveCar.stickBar_Local>=(AppVolume_MoveCar.appVolume_MoveCar.stickBar_maxHeight-5))
							{
								AppListenAudioFunction.getAppAudioFunctionInstance().AudioDisable();
								if (WificarNew.instance.wificarNewLayoutParams.screenSize < 5.8) {
									WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_off_phone);
								} else {
									WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_off_ipad);
								}
							}
						}
						Door_Btn.setEnabled(true);
						break;
					case MESSAGE_G_VOLUME_ENABLE:
						appVolume_MoveCar.setEnabled(true);
						appVolume_MoveCar.getBackground().setAlpha(255);
						Mic_Btn.setEnabled(true);
						Mic_Btn.getBackground().setAlpha(255);
						break;
					case MESSAGE_G_VOLUME_DISABLE:
						if (appVolume_MoveCar.isAudioEnable)
						{	
							Log.e(TAG, "MESSAGE_G_VOLUME_DISABLE!!!!!!!!!!!!!!!!!!!!!");
						}
						
						appVolume_MoveCar.setEnabled(false);
						appVolume_MoveCar.getBackground().setAlpha(60);
						Mic_Btn.setEnabled(false);
						Mic_Btn.getBackground().setAlpha(60);
						break;
					case MESSAGE_ROCKET_CLICKED:
						AppCommand.getAppCommandInstace().stopCommand1(false);
						Rocket_Btn.setEnabled(true);
						Rocket_Btn.getBackground().setAlpha(255);
						isRocket=false;
						if(isJet){
							Talk_Btn.setEnabled(false);
							Talk_Btn.getBackground().setAlpha(60);
						}else{
							Talk_Btn.setEnabled(true);
							Talk_Btn.getBackground().setAlpha(255);
						}
						
						mediaPlayer1.pause();
						if (WificarNew.instance.wificarNewLayoutParams.screenSize < 5.8) {
							Rocket_Btn.setBackgroundResource(R.drawable.rocket_off_phone);
						}else{
							Rocket_Btn.setBackgroundResource(R.drawable.rocket_off);
						}
						if (rocket_timer != null) {
							rocket_timer.cancel();
							rocket_timer = null;
						}
						Mgun_Btn.setEnabled(true);
						Mgun_Btn.getBackground().setAlpha(255);
						break;
					case MESSAGE_UPDATE_PLAYPOS:
						mediaPlayer.seekTo(0);
						break;
					case MESSAGE_SHOW_DEMODIALOG:
							new AlertDialog.Builder(instance)
									.setMessage(
											"When the RC Tumbler is charging, please press the Bat Icon to initiate the DEMO MODE.")
									.setPositiveButton("OK",
											new DialogInterface.OnClickListener() {
												@Override
												public void onClick(DialogInterface dialog,
														int which) {
												}
											}).setCancelable(false).create().show();
						break;
					case MESSAGE_DEMO_END:
						setContentView(Parent1, wificarNewLayoutParams.parentParams);
						if (isCharging&&isShow) {
							funcProgressDialog.show();
							Log.e(TAG, "isCharging:"+isCharging);
						}
						break;
					case MESSGE_CHARGING_STATUS:
						 sendMessages(MESSAGE_PLAYPATH_START);
						break;
					case MESSAGE_PLAYPATH_START:
						PlayPath_Btn.setEnabled(true);
						if (isSingleTap == true)
						{
							if (!isCharging) {
									if (phone_battery < 20) {
										playDialog();
										break;
									}
									if (flag == 0) {
										PlayPath_Btn.setEnabled(false);
										Path_Btn.setEnabled(false);
										if (AppInforToCustom.getAppInforToCustomInstance()
												.getRecordPath_flag() == 1) // Recording then close
																			// and save
										{
											RecordPathStop();
										}
										if (AppInforToCustom.getAppInforToCustomInstance()
												.getIsGSensorControl()) {
											GMove_end();
										}
										Path_Btn.getBackground().setAlpha(100);
										isBatteryLowStopFunciton = false;
										AppPlayPathFunction.getPlayPathInstance(instance)
												.PlayPath_Start(false);
										flag = 1;
										PlayPath_Btn.setEnabled(true);
										Path_Btn.getBackground().setAlpha(255);
										Path_Btn.setEnabled(true);
										Door_Btn.setEnabled(false);
										Door_Btn.getBackground().setAlpha(60);
										if (wificarNewLayoutParams.screenSize < 5.8) {
											PlayPath_Btn
													.setBackgroundResource(R.drawable.play_path_on_phone);
										} else {
											PlayPath_Btn
													.setBackgroundResource(R.drawable.play_path_on);
										}
										PlayPath_Btn.getBackground().setAlpha(255);
									} else if (flag != 0) {
										sendMessages(MESSAGE_PLAYPATH_END);
									}
								} else {
									ShowInfo(R.string.no_replay_here, Toast.LENGTH_LONG);
								}
						} else {
							if (!isCharging) {
									if (phone_battery < 20) {
										playDialog();
										break;
									}
									// 双击的第二下Touch down时触发
									if (flag == 1) {
										break;
									}
									if (flag != 2) {
										PlayPath_Btn.setEnabled(false);
										Path_Btn.setEnabled(false);
										if (AppInforToCustom.getAppInforToCustomInstance()
												.getRecordPath_flag() == 1) {
											RecordPathStop();
										}
										Path_Btn.getBackground().setAlpha(100);
										if (AppInforToCustom.getAppInforToCustomInstance()
												.getIsGSensorControl()) {
											sendMessages(MESSAGE_GSENSOR_END);
										}
										isBatteryLowStopFunciton = false;
										AppPlayPathFunction.getPlayPathInstance(instance)
												.PlayPath_Start(true);
										Path_Btn.getBackground().setAlpha(255);
										Path_Btn.setEnabled(true);
										if (wificarNewLayoutParams.screenSize < 5.8) {
											PlayPath_Btn
													.setBackgroundResource(R.drawable.path_phone);
										} else {
											PlayPath_Btn
													.setBackgroundResource(R.drawable.ipad_replay);
										}
										flag = 2;
										PlayPath_Btn.setEnabled(true);
									}
								} else {
									ShowInfo(R.string.no_replay_here, Toast.LENGTH_LONG);
								}
						}
						break;
					}
					super.handleMessage(msg);
				}
			};
		} catch (Exception e) {
	}
	
		playPathFirstFlag = TestCarPlayFileIsExit(instance, AppInforToCustom
				.getAppInforToCustomInstance().getConfigFileName());
		if (!playPathFirstFlag) {
			PlayPath_Btn.getBackground().setAlpha(100);
			PlayPath_Btn.setEnabled(false);
			playPathFirstFlag = false;
		}
		
		funcProgressDialog = new ProgressDialog(this);
		funcProgressDialog.setCancelable(false);
		funcProgressDialog.setMessage(instance.getText(R.string.funcshowing));
		
		nofunProgressDialog = new ProgressDialog(this);
		nofunProgressDialog.setCancelable(false);
		nofunProgressDialog.setMessage(instance.getText(R.string.funcshowing));
		
		spoilerOpenProgressDialog= new ProgressDialog(this);
		spoilerOpenProgressDialog.setCancelable(false);
		spoilerOpenProgressDialog.setMessage(instance.getText(R.string.spoileropen));

		spoilerCloseProgressDialog = new ProgressDialog(this);
		spoilerCloseProgressDialog.setCancelable(false);
		spoilerCloseProgressDialog.setMessage(instance.getText(R.string.spoilerclose));
		
		doorOpenProgressDialog = new ProgressDialog(this);
		doorOpenProgressDialog.setCancelable(false);
		doorOpenProgressDialog.setMessage(instance.getText(R.string.dooropen));
		
		doorCloseProgressDialog = new ProgressDialog(this);
		doorCloseProgressDialog.setCancelable(false);
		doorCloseProgressDialog.setMessage(instance.getText(R.string.doorclose));
	}

	
	public void showDialog() {
		Window window = disG_Dilaog.getWindow();
		WindowManager.LayoutParams lParams = window.getAttributes();
		lParams.width = (int) (wificarNewLayoutParams.Screen_width * 0.8);
		lParams.height = (int) (wificarNewLayoutParams.Screen_height * 0.6);
		window.setGravity(Gravity.CENTER);
		window.setBackgroundDrawableResource(R.color.transparent);
		window.setAttributes(lParams);
		disG_Dilaog.show();
	}

	/**
	 * 测试小车是否进行过路径录制
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean TestCarPlayFileIsExit(Context con, String fileName) {
		boolean isExit = false;
		String[] fileNameArray = con.fileList();
		for (int i = 0; i < fileNameArray.length; i++) {
			if (fileNameArray[i].equals(fileName)) {
				isExit = true;
			}
		}
		return isExit;
	}
	
	public static Bitmap readBitMap(Context context, int resId){
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		//获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is,null,opt);
		}
//TODO
	@Override
	protected void onStart() {
		bigeyeCallBack = new BigeyeCallBack();
		bigeyeCallBack.setCameraSurfaceView(cameraSurfaceView);
		IntentFilter intentFilter = new IntentFilter();

		intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		intentFilter.addAction("android.intent.action.SCREEN_ON");
		intentFilter.addAction("android.intent.action.SCREEN_OFF");
		intentFilter.addAction("android.intent.action.BATTERY_LOW");
		intentFilter.addAction("android.intent.action.BATTERY_OKAY");
		intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
		intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
		intentFilter
				.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
		intentFilter.addAction("WifiManager.WIFI_STATE_CHANGED_ACTION");
		intentFilter.addAction("BluetoothAdapter.STATE_TURNING_OFF");
		intentFilter.addAction("BluetoothAdapter.STATE_ON");

		registerReceiver(spReceiver, intentFilter);
		Runnable init = new Runnable() {
			public void run() {
				boolean result = true;
				ConnnectOut_timer = new Timer();
				ConnnectOut_timer.schedule(new ConnectOut(), 6000);
				result = AppConnect.getInstance(instance, bigeyeCallBack)
						.initSocket();
				if (result && isConnectOut == 0) {
					isConnectOut = 1;
					Message messageConnectSuccess = new Message();
					messageConnectSuccess.what = MESSAGE_CONNECT_TO_CAR_SUCCESS;
					handler.sendMessage(messageConnectSuccess);
					
					AppLog.e("connect_status", "connect:" + isConnectOut);
				} else if (!result && isConnectOut == 0) {
					isConnectOut = 2;
					Message messageConnectFail = new Message();
					messageConnectFail.what = MESSAGE_CONNECT_TO_CAR_FAIL;
					handler.sendMessage(messageConnectFail);
					Log.e("connect_status", "connect:" + isConnectOut);
				}
			}
		};
		if (!AppInforToSystem.ConnectStatus) {
			Message messageConnect = new Message();
			messageConnect.what = MESSAGE_CONNECT_TO_CAR;
			handler.sendMessage(messageConnect);
			Thread initThread = new Thread(init);
			initThread.start();
		}
		super.onStart();
	}

	@Override
	protected void onResume() {
		if (SplashActivity.getInstance() != null) {
			SplashActivity.getInstance().finishExit();
		}
		super.onResume();
		isTop = true;
	}
	
	void setLeftScrollToRight()
	{
		new Handler().postDelayed(
				(new Runnable() {
					@Override
					public void run() {
						Log.e("Handler().postDelayed","Handler().postDelayed");
						appHorizontalScroView.scrollTo(
								G_Btn.getLeft(),
								0);
						}
					}),500);
	}

	@Override
	protected void onPause() {
		AppLog.e("onPause");
		isTop = false;

		if (UD_Move_flag != 3) {
			appUD_MoveCar.init();
		}
		if (LR_Move_flag != 3) {
			appLR_MoveCar.init();
		}
		if(Jet_flag != 3){
			appJet_Movebar.init();
		}
		if (AppInforToCustom.getAppInforToCustomInstance()
				.getIsGSensorControl()) {
			GMove_end();
		}
		if (AppInforToCustom.getAppInforToCustomInstance().getPlayModeStatus()) {
			playpath_end();
		}
		super.onPause();
	}

	@Override
	protected void onStop() {
		mediaPlayer.stop();
		mediaPlayer.release();
		mediaPlayer = null;
		
		mediaPlayer1.stop();
		mediaPlayer1.release();
		mediaPlayer1 = null;
		
		ExitPcare();
		super.onStop();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (!isBack) {
				isBack = true;
				ShowInfo(R.string.click_again_to_exit_the_program,
						Toast.LENGTH_SHORT);
				BackTimer = new Timer();
				BackTimer.schedule(new backTimerTask(), 1000);
			} else {
				isHomeExit = false;
				ExitPcare();
			}
		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			 mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,    
                     AudioManager.FX_FOCUS_NAVIGATION_UP); 
		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
			mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,    
                    AudioManager.FX_FOCUS_NAVIGATION_UP);    
		}
		
		
		
		return true;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return true;
	}

	public void setLayoutparams() {
		initLayout();
		Parent0.addView(cameraSurfaceView, wificarNewLayoutParams.surfaceParams);
		
		Parent.addView(ZoomIn, wificarNewLayoutParams.zoomInParams);
		Parent.addView(ZoomOut, wificarNewLayoutParams.zoomOutParams);
		Parent.addView(appUD_MoveCar, wificarNewLayoutParams.car_UD_Move_Params);
		Parent.addView(appLR_MoveCar, wificarNewLayoutParams.car_LR_Move_Params);
		Parent.addView(appVolume_MoveCar,wificarNewLayoutParams.volume_bar);
		Parent.addView(Mgun_Btn,
				wificarNewLayoutParams.mgunParams);
		Parent.addView(Photo1_Btn,
				wificarNewLayoutParams.photoParams1);
		Parent.addView(Mic_Btn,wificarNewLayoutParams.micParams);
		Parent.addView(chronometer, wificarNewLayoutParams.chronParams);
		Parent.addView(Video_Red, wificarNewLayoutParams.videoRedParams);
		Parent.addView(G_Btn1, wificarNewLayoutParams.gsensorParams1);
		Parent.addView(Bat_Btn,wificarNewLayoutParams.batParams);
		
		Parent.addView(Talk_Btn,wificarNewLayoutParams.talkParams);
		Parent.addView(Rocket_Btn,
				wificarNewLayoutParams.rocketParams);
		Parent.addView(Video1_Btn,
				wificarNewLayoutParams.videoParams1);
	
		
		Scrolinear.addView(Share_Btn, wificarNewLayoutParams.bottom_Btn_Params);
		Scrolinear.addView(Path_Btn, wificarNewLayoutParams.bottom_Btn_Params);
		Scrolinear.addView(PlayPath_Btn,
				wificarNewLayoutParams.bottom_Btn_Params);
		Scrolinear.addView(Door_Btn,
				wificarNewLayoutParams.bottom_Btn_Params);
		Scrolinear.addView(G_Btn, wificarNewLayoutParams.last_Bottom_Btn_Params);//重力
		
		
		Scrolinear1.addView(Lights_Btn,
				wificarNewLayoutParams.bottom_Btn_Params);
		Scrolinear1.addView(Spoiler_Btn,
				wificarNewLayoutParams.bottom_Btn_Params);
		Scrolinear1.addView(Photo_Btn, wificarNewLayoutParams.bottom_Btn_Params);
		Scrolinear1.addView(Video_Btn, wificarNewLayoutParams.bottom_Btn_Params);
		Scrolinear1.addView(Ir_Btn, wificarNewLayoutParams.last_Bottom_Btn_Params);
		Parent.addView(Ir1_Btn,
				wificarNewLayoutParams.irParams1);
		Parent.addView(scrol_left,wificarNewLayoutParams.scrol_leftParams);
		Parent.addView(scrol_right,wificarNewLayoutParams.scrol_rightParams);
		
		Parent.addView(scrol_left1,wificarNewLayoutParams.scrol_leftParams1);
		Parent.addView(scrol_right1,wificarNewLayoutParams.scrol_rightParams1);
		Parent.addView(G_End_Show, wificarNewLayoutParams.showInfoTViewParams);
		Parent.addView(ShowInfoTView,
				wificarNewLayoutParams.showInfoTViewParams);
		Parent.addView(ScaleTView, wificarNewLayoutParams.scaleTViewParams);
		Parent.addView(appJet_Movebar,wificarNewLayoutParams.jet_bar);
		Parent.addView(Jetpower_Btn,wificarNewLayoutParams.jetpower);
		
		LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		connectLayout.setOrientation(LinearLayout.HORIZONTAL);
		connectLayout.addView(loading, llParams);
		connectLayout.addView(tv_Loading, llParams);
		Parent1.addView(connectLayout, wificarNewLayoutParams.connectParams);
		setContentView(Parent1,wificarNewLayoutParams.parentParams);
		refreshUIListener();
	}

	//TODO>5.8
	public void setHLayoutparams() {
		initHLayout();
		Parent0.addView(cameraSurfaceView, wificarNewLayoutParams.surfaceParams);
		Parent.addView(Mgun_Btn,
				wificarNewLayoutParams.mgunParams);
		Parent.addView(Path_Btn,
				wificarNewLayoutParams.pathParams);
		Parent.addView(PlayPath_Btn,
				wificarNewLayoutParams.pathParams1);
		Parent.addView(Door_Btn,
				wificarNewLayoutParams.doorParams);
		Parent.addView(Photo1_Btn,
				wificarNewLayoutParams.photoParams1);
		Parent.addView(G_Btn,
				wificarNewLayoutParams.gsensorParams);
		
		Parent.addView(Lights_Btn,
				wificarNewLayoutParams.lightParams);
		Parent.addView(Ir1_Btn,
				wificarNewLayoutParams.irParams1);
		
		Parent.addView(Spoiler_Btn,
				wificarNewLayoutParams.spoilerParams);
		
		Parent.addView(Video1_Btn,
				wificarNewLayoutParams.videoParams1);
		
		Parent.addView(Photo_Btn,
				wificarNewLayoutParams.photoParams);
		Parent.addView(Video_Btn,
				wificarNewLayoutParams.videoParams);
		Parent.addView(Rocket_Btn,
				wificarNewLayoutParams.rocketParams);
		Parent.addView(
				appVolume_MoveCar,
				wificarNewLayoutParams.volume_bar);
		
		Parent.addView(Bat_Btn,wificarNewLayoutParams.batParams);

		Parent.addView(ZoomIn, wificarNewLayoutParams.zoomInParams);
		Parent.addView(ZoomOut, wificarNewLayoutParams.zoomOutParams);

		Parent.addView(Share_Btn, wificarNewLayoutParams.shareParams);
		Parent.addView(Mic_Btn,wificarNewLayoutParams.micParams);
		Parent.addView(Ir_Btn,wificarNewLayoutParams.irParams);
		Parent.addView(Talk_Btn, wificarNewLayoutParams.talkParams);
		
		Parent.addView(appUD_MoveCar, wificarNewLayoutParams.car_UD_Move_Params);
		
		Parent.addView(appJet_Movebar,wificarNewLayoutParams.jet_bar);
		Parent.addView(Jetpower_Btn,wificarNewLayoutParams.jetpower);
		Parent.addView(appLR_MoveCar, wificarNewLayoutParams.car_LR_Move_Params);
		Parent.addView(ScaleTView, wificarNewLayoutParams.scaleTViewParams);
		Parent.addView(G_End_Show, wificarNewLayoutParams.showInfoTViewParams);
		Parent.addView(ShowInfoTView,
				wificarNewLayoutParams.showInfoTViewParams);
		Parent.addView(chronometer, wificarNewLayoutParams.chronParams);
		Parent.addView(Video_Red, wificarNewLayoutParams.videoRedParams);
		Parent.addView(null_Btn,wificarNewLayoutParams.nullParams);
		LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		connectLayout.setOrientation(LinearLayout.HORIZONTAL);
		connectLayout.addView(loading, llParams);
		connectLayout.addView(tv_Loading, llParams);
		Parent1.addView(connectLayout, wificarNewLayoutParams.connectParams);
		setContentView(Parent1, wificarNewLayoutParams.parentParams);
		refreshUIListener();
	}

	public static boolean isSingleClick = false;
	public static boolean isUDHide=false,isLRHide=false;
	private Timer rocket_timer;
	//TODO
	// 注册监听事件
	public void refreshUIListener() {
		if (wificarNewLayoutParams.screenSize <5.8) {
			G_Btn.setOnClickListener(instance);
			G_Btn1.setOnClickListener(instance);
			ZoomIn.setOnTouchListener(instance);
			ZoomOut.setOnTouchListener(instance);
			Ir_Btn.setOnClickListener(instance);
			Ir1_Btn.setOnClickListener(instance);
			Share_Btn.setOnClickListener(instance);
			Photo_Btn.setOnTouchListener(instance);
			Photo1_Btn.setOnTouchListener(instance);
			Video_Btn.setOnClickListener(instance);
			Video1_Btn.setOnClickListener(instance);
			Door_Btn.setOnClickListener(instance);
			Spoiler_Btn.setOnClickListener(instance);
			Path_Btn.setOnClickListener(instance);
			PlayPath_Btn.setOnTouchListener(instance);
			Talk_Btn.setOnTouchListener(instance);
			Lights_Btn.setOnClickListener(instance);
			Rocket_Btn.setOnClickListener(instance);
			Mgun_Btn.setOnTouchListener(instance);
			Bat_Btn.setOnClickListener(instance);
			Scrolinear.setOnTouchListener(instance);
			chronometer.setOnChronometerTickListener(instance);
			
		}else{
			G_Btn.setOnClickListener(instance);
			ZoomIn.setOnTouchListener(instance);
			ZoomOut.setOnTouchListener(instance);
			Ir_Btn.setOnClickListener(instance);
			Ir1_Btn.setOnClickListener(instance);
			Share_Btn.setOnClickListener(instance);
			Photo_Btn.setOnTouchListener(instance);
			Photo1_Btn.setOnTouchListener(instance);
			Video_Btn.setOnClickListener(instance);
			Video1_Btn.setOnClickListener(instance);
			Door_Btn.setOnClickListener(instance);
			Spoiler_Btn.setOnClickListener(instance);
			Path_Btn.setOnClickListener(instance);
			PlayPath_Btn.setOnTouchListener(instance);
			Talk_Btn.setOnTouchListener(instance);
			Lights_Btn.setOnClickListener(instance);
			Rocket_Btn.setOnClickListener(instance);
			Mgun_Btn.setOnTouchListener(instance);
			Bat_Btn.setOnClickListener(instance);
			chronometer.setOnChronometerTickListener(instance);
			
		}
		appUD_MoveCar.setAppUD_MoveCarListener(new AppUD_MoveCarListener() {
			@Override
			public void onSteeringWheelChanged(int action, int flag) {
				Log.i(TAG, "appUD_MoveCar - onSteeringWheelChanged:"+action);
				if (action == AppUD_MoveCar.ACTION_RUDDER) {
					CarMove_hide(1);
					isUDHide = true;
					if (AppInforToCustom.getAppInforToCustomInstance()
							.getPlayModeStatus()) {
						playpath_end();
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					if (flag == 1) {// 前
						AppDeviceMoveFunction
								.getAppDeviceMoveFunctionInstance()
								.DeviceMove_Forward(true);
						
					} else if (flag == 2) {// 后
						AppDeviceMoveFunction
								.getAppDeviceMoveFunctionInstance()
								.DeviceMove_Back(true);
					}
				} else if (action == AppUD_MoveCar.ACTION_STOP) {
					isUDHide = false;
					if (isLRHide != true) {
						CarMove_hide(2);
					}
					AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance()
							.DeviceMove_Forward(false);
				}
				UD_Move_flag = action;
			}
		});
		appLR_MoveCar.setAppLR_MoveCarListener(new AppLR_MoveCarListener() {
			@Override
			public void onSteeringWheelChanged(int action, int flag) {
				Log.i(TAG, "appLR_MoveCar - onSteeringWheelChanged:"+action);
				if (action == AppLR_MoveCar.ACTION_RUDDER) {
					CarMove_hide(1);
					isLRHide = true;
					if (AppInforToCustom.getAppInforToCustomInstance()
							.getPlayModeStatus()) {
						playpath_end();
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					if (flag == 1) {// 左   快
						AppCommand.getAppCommandInstace()
								.setDevice_MoveLRSpeedFlag(0);
						AppDeviceMoveFunction
								.getAppDeviceMoveFunctionInstance()
								.DeviceMove_Left(true);
					} else if (flag == 2) { // 左 慢
						AppCommand.getAppCommandInstace()
								.setDevice_MoveLRSpeedFlag(1);
						AppDeviceMoveFunction
								.getAppDeviceMoveFunctionInstance()
								.DeviceMove_Left(true);
					} else if (flag == 3) {//右 快
						AppCommand.getAppCommandInstace()
								.setDevice_MoveLRSpeedFlag(0);
						AppDeviceMoveFunction
								.getAppDeviceMoveFunctionInstance()
								.DeviceMove_Right(true);
					} else if (flag == 4) {// 右 慢
						AppCommand.getAppCommandInstace()
								.setDevice_MoveLRSpeedFlag(1);
						AppDeviceMoveFunction
								.getAppDeviceMoveFunctionInstance()
								.DeviceMove_Right(true);
					}
				} else if (action == AppLR_MoveCar.ACTION_STOP) {
					isLRHide = false;
					if (isUDHide != true)
					{
						CarMove_hide(2);
					}
					AppCommand.getAppCommandInstace()
							.setDevice_MoveLRSpeedFlag(0);
					AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance()
							.DeviceMove_Left(false);
				}
				LR_Move_flag = action;
			}
		});
	
//TODO	
		appJet_Movebar.setAppJet_MoveCarListener(new AppJet_MoveCarListener(){

			@Override
			public void onSteeringWheelChanged(int action, int flag) {
				Log.i(TAG, "onSteeringWheelChanged:"+action);
				if (action == appJet_Movebar.ACTION_RUDDER) {
					if (AppInforToCustom.getAppInforToCustomInstance()
							.getPlayModeStatus()) {
						playpath_end();
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					if (flag == 1) {// 不是Gsensor控制
						Log.e("appJet_Movebar", "flag == 1");
						CarMove_hide(3);
						AppCommand.getAppCommandInstace().Speed(true);
						isJet = true;
						ShowInfo(R.string.isJeting, Toast.LENGTH_LONG);
					}else if (flag == 2) {
						Log.e("appJet_Movebar", "flag == 2");
						CarMove_hide(5);
						AppCommand.getAppCommandInstace().Speed(true);
						isJet = true;
						ShowInfo(R.string.isJeting, Toast.LENGTH_LONG);
					}
					
				}else if (action == appJet_Movebar.ACTION_STOP) {
					if (!AppInforToCustom.isGSensorControl)
					{
						if(isUDHide != true && isLRHide != true) {
							CarMove_hide(4);
						}
					}else {
						CarMove_hide(6);
					}
					isJet= false;
					appJet_Movebar.getBackground().setAlpha(255);		
				}
				
				Jet_flag = action;
			}
			
		});

		appVolume_MoveCar.setAppVolume_MoveCarListener(new AppVolume_MoveCarListener() {
			public void onVoiceFlagChanged(int voiceFlag) {
				switch (voiceFlag) {
				case 4: // 静音
					if (listenViocePress) {
						listenViocePress = false;
					}
					break;
				case 0: // 这样编写的原因为防止用户快速滑动
				case 1:
				case 2:
				case 3:
				case 5:
					if (!listenViocePress) {
						listenViocePress = true;
					}
					break;
				}
			}
		});
		
		detector = new GestureDetector(new OnGestureListener() {
			public boolean onDown(MotionEvent e) {
				return false;
			}

			public void onShowPress(MotionEvent e) {
			}

			public boolean onSingleTapUp(MotionEvent e) {
				return false;
			}

			public boolean onScroll(MotionEvent e1, MotionEvent e2,
					float distanceX, float distanceY) {
				return false;
			}

			public void onLongPress(MotionEvent e) {
			}

			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				return false;
			}
		});
		detector.setOnDoubleTapListener(new OnDoubleTapListener() {
			public boolean onSingleTapConfirmed(MotionEvent e) {
				 AppCommand.getAppCommandInstace().sendCommand(CMD_OP_CODE.Query_Charging_Status);
				 isSingleTap = true;
				 PlayPath_Btn.setEnabled(false);
				return true;
			}

			public boolean onDoubleTapEvent(MotionEvent e) {
				// 双击的第二下Touch down和up都会触发，可用e.getAction()区分
				return false;
			}

			public boolean onDoubleTap(MotionEvent e) {
				 AppCommand.getAppCommandInstace().sendCommand(CMD_OP_CODE.Query_Charging_Status);
				 isSingleTap = false;
				 PlayPath_Btn.setEnabled(false);
				return true;
			}
		});
	}

	public void onClick(View v) {
		try {
			if (v == Ir_Btn) {
				if (!AppInforToCustom.getAppInforToCustomInstance().getIsStealthControl()) {
					if (wificarNewLayoutParams.screenSize <= 5.8) {
						Ir_Btn
								.setBackgroundResource(R.drawable.ir_on_phone);
						Ir1_Btn
						.setBackgroundResource(R.drawable.ir_on_phone);
					} else {
						Ir_Btn
								.setBackgroundResource(R.drawable.ir_on1);
						Ir1_Btn
						.setBackgroundResource(R.drawable.ir_on);
					}
					AppNightLightFunction.getAppNightLightFunctionInstance().NightLight_OandC(true);
				}else {
					AppNightLightFunction.getAppNightLightFunctionInstance().NightLight_OandC(false);
					if (wificarNewLayoutParams.screenSize <= 5.8) {
						Ir_Btn
								.setBackgroundResource(R.drawable.ir_off_phone);
						Ir1_Btn
						.setBackgroundResource(R.drawable.ir_off_phone);
					} else {
						Ir_Btn
								.setBackgroundResource(R.drawable.ir_off1);
						Ir1_Btn
						.setBackgroundResource(R.drawable.ir_off);
					}
					if (AppInforToCustom.getAppInforToCustomInstance().getIsBrightControl()) {
						if (wificarNewLayoutParams.screenSize <= 5.8) {
							Lights_Btn
								.setBackgroundResource(R.drawable.light_on_phone);
						} else {
							Lights_Btn
								.setBackgroundResource(R.drawable.lights_on);
						}
					}
				}
			}else if (v == Share_Btn) {
				if (wificarNewLayoutParams.screenSize < 5.8) {
					Share_Btn.setBackgroundResource(R.drawable.share_on_phone);
				} else {
					Share_Btn.setBackgroundResource(R.drawable.share_on1);
				}
				Share_Btn.setEnabled(false);
				new AlertDialog.Builder(instance)
					.setCancelable(false)
					.setMessage(R.string.share_btn_click)
					.setPositiveButton(
							R.string.share_btn_click_ok,
							new android.content.DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,int which) {
									Share_Btn.setEnabled(true);
									if (wificarNewLayoutParams.screenSize < 5.8) {
										Share_Btn.setBackgroundResource(R.drawable.share_off_phone);
									} else {
										Share_Btn.setBackgroundResource(R.drawable.share_off1);
									}
								}
							}).create().show();
			}else if (v == Lights_Btn) {
					if (!AppInforToCustom.getAppInforToCustomInstance().getIsBrightControl()) {
						AppBrightLightFunction.getAppBrightLightFunctionInstance().BrightLight_OandC(true);
						if (wificarNewLayoutParams.screenSize <= 5.8) {
							Lights_Btn.setBackgroundResource(R.drawable.light_on_phone);
						} else {
							Lights_Btn.setBackgroundResource(R.drawable.lights_on);
						}
//						AppNightLightFunction.getAppNightLightFunctionInstance().NightLight_OandC(false);
//						if (wificarNewLayoutParams.screenSize <= 5.8) {
//							Ir_Btn
//									.setBackgroundResource(R.drawable.ir_off_phone);
//							Ir1_Btn
//							.setBackgroundResource(R.drawable.ir_off_phone);
//						} else {
//							Ir_Btn
//									.setBackgroundResource(R.drawable.ir_off1);
//							Ir1_Btn
//							.setBackgroundResource(R.drawable.ir_off);
//						}
					} else {
						AppBrightLightFunction.getAppBrightLightFunctionInstance().BrightLight_OandC(false);
						if (wificarNewLayoutParams.screenSize <= 5.8) {
							Lights_Btn.setBackgroundResource(R.drawable.light_off_phone);
						} else {
							Lights_Btn.setBackgroundResource(R.drawable.lights_off);
						}
					}
				}
		else if (v == Path_Btn) {// 录制路径
				int id = 1;
				if (wificarNewLayoutParams.screenSize <= 5.8) {
					id = appHorizontalScroView.getLeftId();
				}
				if (id <= 2 && AppInforToSystem.bottom_btn_scroll_flag != 1) {
					if (phone_battery < 20) {
						recordDialog();
						return;
					}
					if (AppInforToCustom.getAppInforToCustomInstance()
							.getRecordPath_flag() == 0) {
						Path_Btn.setEnabled(false);
						Path_Btn.getBackground().setAlpha(100);
						PlayPath_Btn.setEnabled(false);
						isBatteryLowStopFunciton = false;
						if (AppInforToCustom.getAppInforToCustomInstance()
								.getPlayModeStatus()) {
							playpath_end();
						}
						AppRecordPathFunction.getRecordPathInstance(instance)
								.AppRecordPath();
						if (AppInforToCustom.getAppInforToCustomInstance()
								.getIsGSensorControl()) {
							AppDeviceMoveFunction
									.getAppDeviceMoveFunctionInstance()
									.G_Move_Record(
											AppDeviceMoveFunction.direction);
						}
						PlayPath_Btn.getBackground().setAlpha(255);
						playPathFirstFlag = true;
						if (wificarNewLayoutParams.screenSize < 5.8) {
							PlayPath_Btn
									.setBackgroundResource(R.drawable.play_path_off_phone);
						} else {
							PlayPath_Btn
									.setBackgroundResource(R.drawable.play_path_off);
						}
						Path_Btn.getBackground().setAlpha(255);
						if (wificarNewLayoutParams.screenSize < 5.8) {
							Path_Btn.setBackgroundResource(R.drawable.path_on_phone);
						} else {
							Path_Btn.setBackgroundResource(R.drawable.path_on);
						}
						Path_Btn.setEnabled(true);
						PlayPath_Btn.setEnabled(true);
					} else {
						RecordPathStop();
					}
				}
			} 
				if (v == Video_Btn) {
					if (!AppInforToCustom.getAppInforToCustomInstance()
							.getIsCameraShooting()) {
						ShootingStart();
						Log.e(TAG, "videoTimer");
						videoTimer = new Timer();
						videoTimer.schedule(new videoTimerTask(), 1000);
						isVideoing = true;
					} else {
						ShootingComplte();
						isVideoing = false;
					}
				} 
				else if(v == Video1_Btn){
					if (!AppInforToCustom.getAppInforToCustomInstance()
							.getIsCameraShooting()) {
						ShootingStart();
						videoTimer = new Timer();
						videoTimer.schedule(new videoTimerTask(), 1000);
						isVideoing = true;
					} else {
						Log.e(TAG, "videoTimer1");
						isVideoing = false;
						ShootingComplte();
					}
				
				}
				else if (v == G_Btn) {
					G_Btn.setEnabled(false);
					if (!AppInforToCustom.getAppInforToCustomInstance()
							.getIsGSensorControl()) {
						AppGSenserFunction
								.getAppCameraSurfaceFunction(instance)
								.EnableGSensorControl();
					} else {
						sendMessages(MESSAGE_GSENSOR_END);
					}
					GsensorTimer = new Timer();
					GsensorTimer.schedule(new gsensorTimerTask(), 100);
				}
				else if (v == G_Btn1) {
					G_Btn1.setEnabled(false);
					if (!AppInforToCustom.getAppInforToCustomInstance()
							.getIsGSensorControl()) {
						AppGSenserFunction
								.getAppCameraSurfaceFunction(instance)
								.EnableGSensorControl();
					} else {
						sendMessages(MESSAGE_GSENSOR_END);
					}
					GsensorTimer = new Timer();
					GsensorTimer.schedule(new gsensorTimerTask(), 100);
				}
				else if(v == Door_Btn){
					Door_Btn.setEnabled(false);
					Talk_Btn.setEnabled(false);
					Talk_Btn.getBackground().setAlpha(60);
					isDoorOpen = true;
					isDooring = true;
					AppListenAudioFunction.getAppAudioFunctionInstance().AudioDisable();
					if (wificarNewLayoutParams.screenSize < 5.8) {
						WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_off_phone);
					} else {
						WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_off_ipad);
					}
					if(!AppInforToCustom.getAppInforToCustomInstance().getIsDoor()){
						Log.e("Door_Btn", "enable door..");
						AppCommand.getAppCommandInstace().Door(true);
						isDoorOpen = true;
						isDooring = true;
						doorOpenProgressDialog.show();
						Log.e("MESSAGE_DOOR_DONE", "isDoorOpen:"+isDoorOpen);
						if (wificarNewLayoutParams.screenSize <= 5.8) {
							Door_Btn.setBackgroundResource(R.drawable.door_on_phone);
							Path_Btn.setEnabled(false);
							Path_Btn.getBackground().setAlpha(60);
							PlayPath_Btn.setEnabled(false);
							PlayPath_Btn.getBackground().setAlpha(60);
							G_Btn.setEnabled(false);
							G_Btn.getBackground().setAlpha(60);
							G_Btn1.setEnabled(false);
							G_Btn1.getBackground().setAlpha(60);
							appUD_MoveCar.setEnabled(false);
							appUD_MoveCar.getBackground().setAlpha(60);
							appLR_MoveCar.setEnabled(false);
							appLR_MoveCar.getBackground().setAlpha(60);
							appJet_Movebar.setEnabled(false);
							appJet_Movebar.getBackground().setAlpha(60);
							appVolume_MoveCar.setEnabled(false);
							appVolume_MoveCar.getBackground().setAlpha(60);
						} else {
							Door_Btn.setBackgroundResource(R.drawable.door_on);		
							Path_Btn.setEnabled(false);
							Path_Btn.getBackground().setAlpha(60);
							PlayPath_Btn.setEnabled(false);
							PlayPath_Btn.getBackground().setAlpha(60);
							G_Btn.setEnabled(false);
							G_Btn.getBackground().setAlpha(60);
							appUD_MoveCar.setEnabled(false);
							appUD_MoveCar.getBackground().setAlpha(60);
							appLR_MoveCar.setEnabled(false);
							appLR_MoveCar.getBackground().setAlpha(60);
							appJet_Movebar.setEnabled(false);
							appJet_Movebar.getBackground().setAlpha(60);
							appVolume_MoveCar.setEnabled(false);
							appVolume_MoveCar.getBackground().setAlpha(60);
						}
						
						AppListenAudioFunction.getAppAudioFunctionInstance().AudioDisable();
						if (wificarNewLayoutParams.screenSize < 5.8) {
							WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_off_phone);
						} else {
							WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_off_ipad);
						}
					}else{
						Log.e("Door_Btn", "disable door..");
						AppCommand.getAppCommandInstace().stopDoor(true);
						doorCloseProgressDialog.show();
						if (wificarNewLayoutParams.screenSize <= 5.8) {
							Door_Btn.setBackgroundResource(R.drawable.door_off_phone);
						} else {
							Door_Btn.setBackgroundResource(R.drawable.door_off);	
						}
					}
				}
				else if(v ==Bat_Btn ){
					Bat_Btn.getBackground().setAlpha(0);
					func = true;
					if(isJet!=true){
						AppCommand.getAppCommandInstace().Func(true);
					}else{
						ShowInfo(R.string.nofunshowing1, Toast.LENGTH_LONG);
					}
				} 
				
				
				else if(v == Rocket_Btn){
					isRocket=true;
					Rocket_Btn.setBackgroundResource(R.drawable.rocket_on_phone);
					Rocket_Btn.setEnabled(false);
					Rocket_Btn.getBackground().setAlpha(60);
					Talk_Btn.setEnabled(false);
					Talk_Btn.getBackground().setAlpha(60);
					Mgun_Btn.setEnabled(false);
					Mgun_Btn.getBackground().setAlpha(60);
					AppCommand.getAppCommandInstace().soundCommand1(true);
					  try {       
						     mediaPlayer1.start();   
						    } catch (Exception e) {  
						     e.printStackTrace();  
						    }      
					rocket_timer = new Timer();
					rocket_timer.schedule(new rocketTask(),4400);
				} 
//尾翼
				else if(v == Spoiler_Btn){
					Log.e(TAG, "Spoiler_Btn");
					if(spoiler){
							spoiler=false;
							isSpoiler=false;
							spoilerCloseProgressDialog.show();
						   Log.e(TAG, "spoiler false:"+spoiler);
						   AppCommand.getAppCommandInstace().stopSpoiler(true);
						   if (wificarNewLayoutParams.screenSize <= 5.8) {
							   Spoiler_Btn.setBackgroundResource(R.drawable.spoiler_off_phone);
						   } else {
							   Spoiler_Btn.setBackgroundResource(R.drawable.spoiler_off);
						   }
					}else{
						spoiler=true;
						isSpoiler = true;
						spoilerOpenProgressDialog.show();
						Log.e(TAG, "spoiler true:"+spoiler);
						 AppCommand.getAppCommandInstace().Spoiler(true);
						   if (wificarNewLayoutParams.screenSize <= 5.8) {
							   Spoiler_Btn.setBackgroundResource(R.drawable.spoiler_on_phone);
							} else {
								Spoiler_Btn.setBackgroundResource(R.drawable.spoiler_on);
							}
						 
						   Spoiler_Btn.setEnabled(false);
						   Spoiler_Btn.getBackground().setAlpha(60);
					}
					
				}else if (v == Ir1_Btn) {
					Log.e("Ir1_Btn", "Ir1_Btn onclicked...");
					if (!AppInforToCustom.getAppInforToCustomInstance().getIsStealthControl()) {
						Log.e("Ir1_Btn", "getIsStealthControl() is  false");
						if (wificarNewLayoutParams.screenSize < 5.8) {
							Ir_Btn
									.setBackgroundResource(R.drawable.ir_on_phone);
							Ir1_Btn
							.setBackgroundResource(R.drawable.ir_on_phone);
						} else {
							Ir1_Btn
									.setBackgroundResource(R.drawable.ir_on);
							Ir_Btn
							.setBackgroundResource(R.drawable.ir_on1);
						}
						AppNightLightFunction.getAppNightLightFunctionInstance().NightLight_OandC(true);
					}else {
						Log.e("Ir1_Btn", "getIsStealthControl() is  true");
						AppNightLightFunction.getAppNightLightFunctionInstance().NightLight_OandC(false);
						if (wificarNewLayoutParams.screenSize <= 5.8) {
							Ir1_Btn
									.setBackgroundResource(R.drawable.ir_off_phone);
							Ir_Btn
							.setBackgroundResource(R.drawable.ir_off_phone);
						} else {
							Ir1_Btn
									.setBackgroundResource(R.drawable.ir_off);
							Ir_Btn
							.setBackgroundResource(R.drawable.ir_off1);
						}
						if (AppInforToCustom.getAppInforToCustomInstance().getIsBrightControl()) {
							if (wificarNewLayoutParams.screenSize <= 5.8) {
								Lights_Btn
									.setBackgroundResource(R.drawable.light_on_phone);
							} else {
								Lights_Btn
									.setBackgroundResource(R.drawable.lights_on);
							}
						}
					}
				}
//			}
		} catch (Exception e) {
		}
	}
	
	
	

	public boolean onTouch(View v, MotionEvent event) {
		try {
			if (v == Talk_Btn) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					istalking = true;
					if (wificarNewLayoutParams.screenSize <= 5.8) {
						Talk_Btn.setBackgroundResource(R.drawable.talk_on_ipad);
						Mic_Btn
								.setBackgroundResource(R.drawable.volume_off_phone);
					} else {
						Talk_Btn.setBackgroundResource(R.drawable.talk_on_ipad);
						Mic_Btn.setBackgroundResource(R.drawable.volume_off_ipad);
					}
					talkPressTimer = new Timer();
					talkPressTimer.schedule(new talkPressTimerTask(), talk_delay_time);
					break;
				case MotionEvent.ACTION_MOVE:
					int x = (int) event.getX();
					int y = (int) event.getY();
					//滑动超过图片范围则停止
					if(x<0||x>wificarNewLayoutParams.Screen_width*40/854||y<0||y>wificarNewLayoutParams.Screen_width*40/854){
						AppInforToSystem.main_touch_flag = -1;
						if (talkPressTimer != null) {
							talkPressTimer.cancel();
							talkPressTimer = null;
						} else if (istalking) {
							Talk_Btn.setEnabled(false);
							AppSpkAudioFunction.getAppSpkAudioFunctionInstance()
									.SpkAudioDisable();
						}
						if (wificarNewLayoutParams.screenSize <= 5.8) {
							Talk_Btn.setBackgroundResource(R.drawable.talk_off_ipad);
							if (AppInforToSystem.islistening)
								Mic_Btn
										.setBackgroundResource(R.drawable.volume_on_phone);
						} else {
							Talk_Btn.setBackgroundResource(R.drawable.talk_off_ipad);
							if (AppInforToSystem.islistening)
								Mic_Btn
										.setBackgroundResource(R.drawable.volume_on_ipad);
						}
						Video_Btn.setEnabled(true);
						Video1_Btn.setEnabled(true);
						istalking = false;
					}
					break;
				case MotionEvent.ACTION_UP:
					AppInforToSystem.main_touch_flag = -1;
					if (talkPressTimer != null) {
						talkPressTimer.cancel();
						talkPressTimer = null;
					} else if (istalking) {
						Talk_Btn.setEnabled(false);
						AppSpkAudioFunction.getAppSpkAudioFunctionInstance()
								.SpkAudioDisable();
					}
					if (wificarNewLayoutParams.screenSize <= 5.8) {
						Talk_Btn.setBackgroundResource(R.drawable.talk_off_ipad);
						if (AppInforToSystem.islistening)
							Mic_Btn
									.setBackgroundResource(R.drawable.volume_on_phone);
					} else {
						Talk_Btn.setBackgroundResource(R.drawable.talk_off_ipad);
						if (AppInforToSystem.islistening)
							Mic_Btn
									.setBackgroundResource(R.drawable.volume_on_ipad);
					}
					Video_Btn.setEnabled(true);
					Video1_Btn.setEnabled(true);
					istalking = false;
					break;
				}
			}
			
//机枪
else if(v == Mgun_Btn){
				
				switch (event.getAction()) {
				
				case MotionEvent.ACTION_DOWN:
			   { 
				   try {      
					     mediaPlayer.start(); 
					     isInUse = true;
					     PlayThread playThread = new PlayThread();
					     playThread.start();
					    } catch (Exception e) {  
					     e.printStackTrace();  
					    }      
				   AppCommand.getAppCommandInstace().soundCommand(true);
				   
				   if (wificarNewLayoutParams.screenSize <= 5.8) {
					   Mgun_Btn.setBackgroundResource(R.drawable.mgun_on_phone);
					} else {
						Mgun_Btn.setBackgroundResource(R.drawable.mgun_on);
					}
			    break;
			   }
			   case MotionEvent.ACTION_MOVE:
			   {	
			   int x = (int) event.getX();
			   int y = (int) event.getY();
			   int rawX = (int) event.getRawX();
			   int rawY = (int) event.getRawY();
			   Log.e(TAG, "x = " + x + "; y = " + y + "; rawX = " + rawX + "; rawY = " + rawY);
			   if(wificarNewLayoutParams.screenSize <= 5.8){
				   if(x<0 || x>(wificarNewLayoutParams.Screen_height*98/480)||
						   y<0||y>(wificarNewLayoutParams.Screen_height*98/480))
				   {
					   			   if(mediaPlayer!=null)
						  {
					   		mediaPlayer.pause();
							Log.e(TAG, "stop");
						}
						   AppCommand.getAppCommandInstace().stopCommand(true);
						   if (wificarNewLayoutParams.screenSize <= 5.8) {
							   Mgun_Btn.setBackgroundResource(R.drawable.mgun_off_phone);
						} else {
							Mgun_Btn.setBackgroundResource(R.drawable.mgun_off);
						}
				   } 
			   }else{
				   if(x<0 || x>(wificarNewLayoutParams.Screen_width*294/2560)||
						   y<0||y>(wificarNewLayoutParams.Screen_height*294/1600))
				   {
					   			   if(mediaPlayer!=null)
						  {
					   				mediaPlayer.pause();
							Log.e(TAG, "stop");
						}
						   AppCommand.getAppCommandInstace().stopCommand(true);
						   if (wificarNewLayoutParams.screenSize <= 5.8) {
							   Mgun_Btn.setBackgroundResource(R.drawable.mgun_off_phone);
						} else {
							Mgun_Btn.setBackgroundResource(R.drawable.mgun_off);
						}
				   } 
			   }
			    break;
			   }
			   case MotionEvent.ACTION_UP:
			   {	
			   if(mediaPlayer!=null)
				  { 
				   isInUse = false;
				   mediaPlayer.pause();
					Log.e(TAG, "stop");
				}
			   AppCommand.getAppCommandInstace().stopCommand(true);
			   if (wificarNewLayoutParams.screenSize <= 5.8) {
				   Mgun_Btn.setBackgroundResource(R.drawable.mgun_off_phone);
			} else {
				Mgun_Btn.setBackgroundResource(R.drawable.mgun_off);
			}
			    break;
			   }
			   	default:
			    break;
			   }
		}		
			else if (v == PlayPath_Btn) {
				return detector.onTouchEvent(event);
			} 
				if (v == Photo_Btn) {
					if (wificarNewLayoutParams.screenSize <= 5.8) {
						Log.e("Photo_Btn", "<= 5.8");
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						if (!AppInforToCustom.getAppInforToCustomInstance()
								.getIsAvaiableSDCard()) {
							Toast.makeText(instance, R.string.sdcard_nomount,
									Toast.LENGTH_SHORT).show();
							return false;
						}
						if (AppInforToCustom.getAppInforToCustomInstance()
								.getSDCapacity() < AppInforToCustom
								.getAppInforToCustomInstance().SDLeastCapacity) {
							Toast.makeText(instance,
									R.string.wificar_no_enough,
									Toast.LENGTH_SHORT).show();
							return false;
						}
						if (wificarNewLayoutParams.screenSize <= 5.8) {
							Photo_Btn
									.setBackgroundResource(R.drawable.photo_on_phone);
						} else {
							Photo_Btn
									.setBackgroundResource(R.drawable.photo_on);
						}
						photodown = true;
						AppCameraSurfaceFunction
								.getAppCameraSurfaceFunctionInstance()
								.CameraTakePicture();
						break;
					case MotionEvent.ACTION_CANCEL:
						if (photodown) {
							photodown = false;
							if (wificarNewLayoutParams.screenSize <= 5.8) {
								Photo_Btn
										.setBackgroundResource(R.drawable.photo_off_phone);
							} else {
								Photo_Btn
										.setBackgroundResource(R.drawable.photo_off);
							}
						}
						
						break;
					case MotionEvent.ACTION_UP:
						if (photodown) {
							endTakePicture();
							photodown = false;
							if (wificarNewLayoutParams.screenSize <= 5.8) {
								Photo_Btn
										.setBackgroundResource(R.drawable.photo_off_phone);
							} else {
								Photo_Btn
										.setBackgroundResource(R.drawable.photo_off);
							}
						}
						break;
					}
				}else{
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						if (!AppInforToCustom.getAppInforToCustomInstance()
								.getIsAvaiableSDCard()) {
							Toast.makeText(instance, R.string.sdcard_nomount,
									Toast.LENGTH_SHORT).show();
							return false;
						}
						if (AppInforToCustom.getAppInforToCustomInstance()
								.getSDCapacity() < AppInforToCustom
								.getAppInforToCustomInstance().SDLeastCapacity) {
							Toast.makeText(instance,
									R.string.wificar_no_enough,
									Toast.LENGTH_SHORT).show();
							return false;
						}
						if (wificarNewLayoutParams.screenSize <= 5.8) {
							Photo_Btn
									.setBackgroundResource(R.drawable.photo_on_phone);
						} else {
							Photo_Btn
									.setBackgroundResource(R.drawable.photo_on);
						}
						photodown = true;
						AppCameraSurfaceFunction
								.getAppCameraSurfaceFunctionInstance()
								.CameraTakePicture();
						break;
					case MotionEvent.ACTION_MOVE:
							 if (photodown) {
								 int x = (int) event.getX();
								  int y = (int) event.getY();
								 if(x<0 || x>(wificarNewLayoutParams.Screen_height*65/480)||
										   y<0||y>(wificarNewLayoutParams.Screen_height*65/480))
										   {
									endTakePicture();
									photodown = false;
									if (wificarNewLayoutParams.screenSize <= 5.8) {
										Photo_Btn
												.setBackgroundResource(R.drawable.photo_off_phone);
									} else {
										Photo_Btn
												.setBackgroundResource(R.drawable.photo_off);
									}
								}
								   }
						
						
						break;
					case MotionEvent.ACTION_UP:
						if (photodown) {
							endTakePicture();
							photodown = false;
							if (wificarNewLayoutParams.screenSize <= 5.8) {
								Photo_Btn
										.setBackgroundResource(R.drawable.photo_off_phone);
							} else {
								Photo_Btn
										.setBackgroundResource(R.drawable.photo_off);
							}
						}
						break;
					}
				
				}
				}else if(v == Photo1_Btn){

					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						if (!AppInforToCustom.getAppInforToCustomInstance()
								.getIsAvaiableSDCard()) {
							Toast.makeText(instance, R.string.sdcard_nomount,
									Toast.LENGTH_SHORT).show();
							return false;
						}
						if (AppInforToCustom.getAppInforToCustomInstance()
								.getSDCapacity() < AppInforToCustom
								.getAppInforToCustomInstance().SDLeastCapacity) {
							Toast.makeText(instance,
									R.string.wificar_no_enough,
									Toast.LENGTH_SHORT).show();
							return false;
						}
						if (wificarNewLayoutParams.screenSize <= 5.8) {
							Photo1_Btn
									.setBackgroundResource(R.drawable.photo_on_phone);
						} else {
							Photo1_Btn
									.setBackgroundResource(R.drawable.photo_on);
						}
						photodown = true;
						AppCameraSurfaceFunction
								.getAppCameraSurfaceFunctionInstance()
								.CameraTakePicture();
						break;
					case MotionEvent.ACTION_MOVE:
						 int x = (int) event.getX();
						  int y = (int) event.getY();
						 
						if (photodown) {
							if(x<0 || x>(wificarNewLayoutParams.Screen_height*65/480)||
									   y<0||y>(wificarNewLayoutParams.Screen_height*65/480))
									   {
								endTakePicture();
								photodown = false;
								if (wificarNewLayoutParams.screenSize <= 5.8) {
									Photo1_Btn
											.setBackgroundResource(R.drawable.photo_off_phone);
								} else {
									Photo1_Btn
											.setBackgroundResource(R.drawable.photo_off);
								}
							}
							   }
					break;
					case MotionEvent.ACTION_UP:
						if (photodown) {
							endTakePicture();
							photodown = false;
							if (wificarNewLayoutParams.screenSize <= 5.8) {
								Photo1_Btn
										.setBackgroundResource(R.drawable.photo_off_phone);
							} else {
								Photo1_Btn
										.setBackgroundResource(R.drawable.photo_off);
							}
						}
						break;
					}
				
				} 
				else if (v == ZoomIn) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						if (wificarNewLayoutParams.screenSize > 5.8) {
							v.setBackgroundResource(R.drawable.padzoominon);
						} else {
							v.setBackgroundResource(R.drawable.zoon_in_on);
						}
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						cameraSurfaceView.zoomIn();
						int value = (int) cameraSurfaceView
								.getTargetZoomValue();
						String valuestr = String.valueOf(value) + "%";
						ScaleTView.setText(valuestr);
						if (wificarNewLayoutParams.screenSize > 5.8) {
							v.setBackgroundResource(R.drawable.zoon_in_off);
						} else {
							v.setBackgroundResource(R.drawable.zoon_in_off);
						}
						if (scaleTimer != null)
							scaleTimer.cancel();
						scaleTimer = new Timer();
						scaleTimer.schedule(new scaleTimerTask(), 5000);
					}
				} else if (v == ZoomOut) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						if (wificarNewLayoutParams.screenSize > 5.8) {
							v.setBackgroundResource(R.drawable.padzoomouton);
						} else {
							v.setBackgroundResource(R.drawable.zoon_out_on);
						}
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						cameraSurfaceView.zoomOut();
						int value = (int) cameraSurfaceView
								.getTargetZoomValue();
						String valuestr1 = String.valueOf(value) + "%";
						ScaleTView.setText(valuestr1);
						if (wificarNewLayoutParams.screenSize > 5.8) {
							v.setBackgroundResource(R.drawable.padzoomoutoff);
						} else {
							v.setBackgroundResource(R.drawable.zoon_out_off);
						}
						if (scaleTimer != null)
							scaleTimer.cancel();
						scaleTimer = new Timer();
						scaleTimer.schedule(new scaleTimerTask(), 5000);
					}
				}
		} catch (Exception e) {
		}
		return true;
	}

	public void onChronometerTick(Chronometer chronometer) {
		if (AppInforToCustom.getAppInforToCustomInstance().getShootingTimes() < 3) {
			AppInforToCustom.getAppInforToCustomInstance().addShootingTimes();
		} else if (AppInforToCustom.getAppInforToCustomInstance()
				.getShootingTimes() == 3) {
			AppInforToCustom.getAppInforToCustomInstance().addShootingTimes();
			Video_Btn.getBackground().setAlpha(255);
			Video_Btn.setEnabled(true);
			Video1_Btn.getBackground().setAlpha(255);
			Video1_Btn.setEnabled(true);
		}
		if (Video_Red.isChecked() && Video_Red != null) {
			Video_Red.setBackgroundResource(R.drawable.video_off_led);
			Video_Red.setChecked(false);
		} else if (!Video_Red.isChecked() && Video_Red != null) {
			Video_Red.setBackgroundResource(R.drawable.video_on_led);
			Video_Red.setChecked(true);
		}
	}

	/**
	 * 广播接收器
	 */
	private BroadcastReceiver spReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String str = intent.getAction();
			if (str.equals("android.intent.action.BATTERY_CHANGED")) {
				phone_battery = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,
						-1);
				if (phone_battery < 20 && !isBatteryLowStopFunciton) {
					if (AppInforToCustom.getAppInforToCustomInstance()
							.getPlayModeStatus()) {
						isBatteryLowStopFunciton = true;
						sendMessages(MESSAGE_PLAYPATH_END);
					} else if (AppInforToCustom.getAppInforToCustomInstance()
							.getRecordPath_flag() == 1) {
						isBatteryLowStopFunciton = true;
						sendMessages(MESSAGE_RECORDPATH_COMPLETE);
						recordDialog();
					} else if (AppInforToCustom.getAppInforToCustomInstance()
							.getIsCameraShooting()) {
						isBatteryLowStopFunciton = true;
						shootDialog();
						sendMessages(MESSAGE_SHOOTING_COMPLETE);
					}
				}
			} else if (str
					.equals("android.intent.action.ACTION_POWER_CONNECTED")) {

			} else if (str
					.equals("android.intent.action.ACTION_POWER_DISCONNECTED")) {
			} else if (str.equals("WifiManager.WIFI_STATE_CHANGED_ACTION")) {
			} else if (str.equals("BluetoothAdapter.STATE_ON")) {
			} else if (str.equals("BluetoothAdapter.STATE_TURNING_OFF")) {
			} else if (str.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
				if (AppInforToSystem.ConnectStatus
						&& AppConnect.getInstance().WIFI_BSSID_Connect != null) {
					if (!AppConnect.getInstance().WIFI_BSSID_Connect
							.equals(AppConnect.getInstance().getWIFI_BSSID())) {
						AppConnect.getInstance().WIFI_BSSID_Connect = null;
						sendMessages(MESSAGE_PING_FAIL);
					}
				}
			} else if (str.equals("android.intent.action.SCREEN_OFF")) {
				ExitPcare();
			} else if (str.equals("android.intent.action.SCREEN_ON")) {
			}

		}
	};

	/**
	 * 结束拍照
	 */
	public void endTakePicture() {
		if (photoLimitClickTimes == 0) {
			photoLimitClickTimes = 1;
			Photo_Btn.setEnabled(false);
			Photo1_Btn.setEnabled(false);
			if (wificarNewLayoutParams.screenSize <= 5.8) {
				Photo_Btn.setBackgroundResource(R.drawable.photo_off_phone);
				Photo1_Btn.setBackgroundResource(R.drawable.photo_off_phone);
			} else {
				Photo_Btn.setBackgroundResource(R.drawable.photo_off);
				Photo1_Btn.setBackgroundResource(R.drawable.photo_off);
			}
			Photo_Btn.getBackground().setAlpha(100);
			Photo1_Btn.getBackground().setAlpha(100);
			ShowInfo(R.string.take_photo_successfully, Toast.LENGTH_LONG);
			photoTimer = new Timer();
			photoTimer.schedule(new photoTimerTask(), 2000);
		} else {
			Photo_Btn.getBackground().setAlpha(255);
			Photo_Btn.setEnabled(true);
			Photo1_Btn.getBackground().setAlpha(255);
			Photo1_Btn.setEnabled(true);
			photoTimer.cancel();
			photoTimer = null;
			photoLimitClickTimes = 0;
		}
	}

	/**
	 * 开始拍照函数
	 */
	public void startTakePictue() {
		if (!AppInforToCustom.getAppInforToCustomInstance()
				.getIsAvaiableSDCard()) {
			Toast.makeText(instance, R.string.sdcard_nomount,
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (AppInforToCustom.getAppInforToCustomInstance().getSDCapacity() < AppInforToCustom
				.getAppInforToCustomInstance().SDLeastCapacity) {
			Toast.makeText(instance, R.string.wificar_no_enough,
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (wificarNewLayoutParams.screenSize <= 5.8) {
			Photo_Btn.setBackgroundResource(R.drawable.photo_on_phone);
			Photo1_Btn.setBackgroundResource(R.drawable.photo_on_phone);
		} else {
			Photo_Btn.setBackgroundResource(R.drawable.photo_on);
			Photo1_Btn.setBackgroundResource(R.drawable.photo_on);
		}
		AppCameraSurfaceFunction.getAppCameraSurfaceFunctionInstance()
				.CameraTakePicture();
	}

	// 录制路径结束
	public void RecordPathStop() {
		AppLog.e("停止录制::  按钮关闭");
		ShowInfo(R.string.path_complete, Toast.LENGTH_LONG);
		Path_Btn.setEnabled(false);
		PlayPath_Btn.setEnabled(false);
		AppRecordPathFunction.getRecordPathInstance(instance).AppStopRecordPath();
		if (wificarNewLayoutParams.screenSize <= 5.8) {
			Path_Btn.setBackgroundResource(R.drawable.path_off_phone);
		} else {
			Path_Btn.setBackgroundResource(R.drawable.path_off);
		}
		Path_Btn.setEnabled(true);
		PlayPath_Btn.setEnabled(true);
		AppLog.e("停止录制::  按钮开启");
	}
    
	public void CarMove_hide(int hide) {
		if (hide == 1) {
			Log.e(TAG, "CarMove_hide");
			appVolume_MoveCar.setEnabled(false);
     		appVolume_MoveCar.getBackground().setAlpha(60);
     		Mic_Btn.setEnabled(false);
     		Mic_Btn.getBackground().setAlpha(60);
			Ir_Btn.setEnabled(false);
			Ir_Btn.getBackground().setAlpha(60);
			Share_Btn.setEnabled(false);
			Share_Btn.getBackground().setAlpha(60);
			Path_Btn.setEnabled(false);
			Path_Btn.getBackground().setAlpha(60);
			PlayPath_Btn.setEnabled(false);
			PlayPath_Btn.getBackground().setAlpha(60);
			Door_Btn.setEnabled(false);
			Door_Btn.getBackground().setAlpha(60);
			G_Btn.setEnabled(false);
			G_Btn.getBackground().setAlpha(60);
			Photo_Btn.setEnabled(false);
			Photo_Btn.getBackground().setAlpha(60);
			Lights_Btn.setEnabled(false);
			Lights_Btn.getBackground().setAlpha(60);
			Spoiler_Btn.setEnabled(false);
			Spoiler_Btn.getBackground().setAlpha(60);
			
			Video_Btn.setEnabled(false);
			Video_Btn.getBackground().setAlpha(60);
			Talk_Btn.setEnabled(false);
			Talk_Btn.getBackground().setAlpha(60);
		}else if (hide == 2){
			appVolume_MoveCar.setEnabled(true);
			appVolume_MoveCar.getBackground().setAlpha(255);
			Mic_Btn.setEnabled(true);
     		Mic_Btn.getBackground().setAlpha(255);
			Ir_Btn.setEnabled(true);
			Ir_Btn.getBackground().setAlpha(255);
			Share_Btn.setEnabled(true);
			Share_Btn.getBackground().setAlpha(255);
			Path_Btn.setEnabled(true);
			Path_Btn.getBackground().setAlpha(255);
			PlayPath_Btn.setEnabled(true);
			PlayPath_Btn.getBackground().setAlpha(255);
			G_Btn.setEnabled(true);
			G_Btn.getBackground().setAlpha(255);
			Photo_Btn.setEnabled(true);
			Photo_Btn.getBackground().setAlpha(255);
			Lights_Btn.setEnabled(true);
			Lights_Btn.getBackground().setAlpha(255);
			Spoiler_Btn.setEnabled(true);
			Spoiler_Btn.getBackground().setAlpha(255);
			Video_Btn.setEnabled(true);
			Video_Btn.getBackground().setAlpha(255);
			
			if (AppJet_MoveCar.appJet_Movebar.isJetDo != true && AppUD_MoveCar.appUD_MoveCar.isJetDo != true) {
				if(isVideoing){
					Talk_Btn.setEnabled(false);
					Talk_Btn.getBackground().setAlpha(60);
				}else{
					Talk_Btn.setEnabled(true);
					Talk_Btn.getBackground().setAlpha(255);
				}
				Door_Btn.setEnabled(true);
				Door_Btn.getBackground().setAlpha(255);
			}
		}else if (hide == 3)
		{
			Talk_Btn.setEnabled(false);
			Talk_Btn.getBackground().setAlpha(60);
			if(isJet){
				Door_Btn.setEnabled(true);
				Door_Btn.getBackground().setAlpha(255);
				Log.e(TAG, "hide == 3");
			}else{
			
				Door_Btn.setEnabled(false);
				Door_Btn.getBackground().setAlpha(60);
			}
			
			appJet_Movebar.getBackground().setAlpha(60);
		}else if (hide == 4)
		{
			if (isUDHide != true)
			{
				appJet_Movebar.getBackground().setAlpha(255);				
				Mic_Btn.setEnabled(true);
	     		Mic_Btn.getBackground().setAlpha(255);
				appVolume_MoveCar.setEnabled(true);
				appVolume_MoveCar.getBackground().setAlpha(255);
				Door_Btn.setEnabled(true);
				Door_Btn.getBackground().setAlpha(255);
				if(isVideoing){
						Talk_Btn.setEnabled(false);
						Talk_Btn.getBackground().setAlpha(60);
					}else{
						Talk_Btn.setEnabled(true);
						Talk_Btn.getBackground().setAlpha(255);
					}
			}
		}else if (hide == 5)
		{
			Talk_Btn.setEnabled(false);
			Talk_Btn.getBackground().setAlpha(60);
			Door_Btn.setEnabled(false);
			Door_Btn.getBackground().setAlpha(60);
			Mic_Btn.setEnabled(false);
     		Mic_Btn.getBackground().setAlpha(60);
			appVolume_MoveCar.setEnabled(false);
			appVolume_MoveCar.getBackground().setAlpha(60);
			appJet_Movebar.getBackground().setAlpha(60);
		}else if (hide == 6)
		{
			Door_Btn.setEnabled(true);
			Door_Btn.getBackground().setAlpha(255);
			Mic_Btn.setEnabled(true);
     		Mic_Btn.getBackground().setAlpha(255);
			appVolume_MoveCar.setEnabled(true);
			appVolume_MoveCar.getBackground().setAlpha(255);
			appJet_Movebar.getBackground().setAlpha(255);
			if(isJet){
				
				Door_Btn.setEnabled(true);
				Door_Btn.getBackground().setAlpha(255);
			}else{
				Door_Btn.setEnabled(false);
				Door_Btn.getBackground().setAlpha(60);
				
			}
			if(isVideoing){
					Talk_Btn.setEnabled(false);
					Talk_Btn.getBackground().setAlpha(60);
				}else{
					Talk_Btn.setEnabled(true);
					Talk_Btn.getBackground().setAlpha(255);
				}
		}
		
	}
	
	public void GMove_end() {
		AppGSenserFunction.getAppCameraSurfaceFunction(instance)
				.DisableGSensorControl();
 		// 要显示虚拟摇杆
		//TODO
		appUD_MoveCar.Hided(0);
		appLR_MoveCar.Hided(0);
		G_End_Show.setText(" ");
		Ir_Btn.setVisibility(View.VISIBLE);
		Share_Btn.setVisibility(View.VISIBLE);
		Path_Btn.setVisibility(View.VISIBLE);
		PlayPath_Btn.setVisibility(View.VISIBLE);
		Door_Btn.setVisibility(View.VISIBLE);
		Photo1_Btn.setVisibility(View.INVISIBLE);
		Photo_Btn.setVisibility(View.VISIBLE);
		Mgun_Btn.setVisibility(View.INVISIBLE);
		Rocket_Btn.setVisibility(View.INVISIBLE);
		Lights_Btn.setVisibility(View.VISIBLE);
		Spoiler_Btn.setVisibility(View.VISIBLE);
		Video_Btn.setVisibility(View.VISIBLE);
		Ir1_Btn.setVisibility(View.INVISIBLE);
		Video1_Btn.setVisibility(View.INVISIBLE);
		appVolume_MoveCar.getBackground().setAlpha(255);
		Mic_Btn.getBackground().setAlpha(255);
		if(AppVolume_MoveCar.appVolume_MoveCar.stickBar_Local<(AppVolume_MoveCar.appVolume_MoveCar.stickBar_maxHeight-5))
		{
			try {
				AppListenAudioFunction.getAppAudioFunctionInstance()
				.AudioEnable();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (WificarNew.instance.wificarNewLayoutParams.screenSize < 5.8) {
				WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_on_phone);
			} else {
				WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_on_ipad);
			}
		}else if(AppVolume_MoveCar.appVolume_MoveCar.stickBar_Local>=(AppVolume_MoveCar.appVolume_MoveCar.stickBar_maxHeight-5))
		{
			AppListenAudioFunction.getAppAudioFunctionInstance().AudioDisable();
			if (WificarNew.instance.wificarNewLayoutParams.screenSize < 5.8) {
				WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_off_phone);
			} else {
				WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_off_ipad);
			}
		}
		if (wificarNewLayoutParams.screenSize <= 5.8) {
			if ((float)wificarNewLayoutParams.Screen_width / wificarNewLayoutParams.Screen_height == (float)960 / 540)
			{
				Log.e("Background", "比例是960 : 540");
				Parent.setBackgroundResource(R.drawable.phonebackground);
			}
			else if((float)wificarNewLayoutParams.Screen_width / wificarNewLayoutParams.Screen_height == (float)854 / 480)
			{
				Log.e("Background", "比例是854 : 480");
				Parent.setBackgroundResource(R.drawable.bd854_480);
			}
			else
			{
				Log.e("Background", "比例是854 : 480");
				Parent.setBackgroundResource(R.drawable.bd854_480);
			}
			G_Btn.setBackgroundResource(R.drawable.g_off_phone);
			G_End_Show.setText(" ");
			Ir_Btn.setVisibility(View.VISIBLE);
			Share_Btn.setVisibility(View.VISIBLE);
			Path_Btn.setVisibility(View.VISIBLE);
			PlayPath_Btn.setVisibility(View.VISIBLE);
			Door_Btn.setVisibility(View.VISIBLE);
			Photo1_Btn.setVisibility(View.INVISIBLE);
			Photo_Btn.setVisibility(View.VISIBLE);
			Mgun_Btn.setVisibility(View.INVISIBLE);
			Rocket_Btn.setVisibility(View.INVISIBLE);
			Lights_Btn.setVisibility(View.VISIBLE);
			Spoiler_Btn.setVisibility(View.VISIBLE);
			Video_Btn.setVisibility(View.VISIBLE);
			Ir1_Btn.setVisibility(View.INVISIBLE);
			Video1_Btn.setVisibility(View.INVISIBLE);
			G_Btn1.setVisibility(View.INVISIBLE);
			appHorizontalScroView.setVisibility(View.VISIBLE);
			scrol_left.setVisibility(View.VISIBLE);
			scrol_right.setVisibility(View.VISIBLE);
			scrol_left1.setVisibility(View.VISIBLE);
			scrol_right1.setVisibility(View.VISIBLE);
			appHorizontalScroView1.setVisibility(View.VISIBLE);
		} else {
			G_Btn.setBackgroundResource(R.drawable.g_off);
			if ((float)wificarNewLayoutParams.Screen_width / wificarNewLayoutParams.Screen_height == (float)1280 / 800)
			{
				Log.e("Background", "比例选择是1280:800.。。");
				Parent.setBackgroundResource(R.drawable.db);
			}else if ((float)wificarNewLayoutParams.Screen_width / wificarNewLayoutParams.Screen_height == (float)4 / 3)
			{
				Parent.setBackgroundResource(R.drawable.dbackgroud1024_768);
			}
			else if(wificarNewLayoutParams.Screen_height>800){
				Log.e("Background", "height>800.。。");
				Parent.setBackgroundResource(R.drawable.dbackground);
			}else {
				if(wificarNewLayoutParams.Screen_height < 744) {
					Log.e("Background", "比例选择是1280:736.。。");
					Parent.setBackgroundResource(R.drawable.dbackgrounds);
				} else {
					Log.e("Background", "比例选择是1280:752.。。");
					Parent.setBackgroundResource(R.drawable.dbackground);
				}	
			}
//TODO
			Ir_Btn.setVisibility(View.VISIBLE);
			Share_Btn.setVisibility(View.VISIBLE);
			Path_Btn.setVisibility(View.VISIBLE);
			PlayPath_Btn.setVisibility(View.VISIBLE);
			Door_Btn.setVisibility(View.VISIBLE);
			Photo1_Btn.setVisibility(View.INVISIBLE);
			Photo_Btn.setVisibility(View.VISIBLE);
			Mgun_Btn.setVisibility(View.INVISIBLE);
			Rocket_Btn.setVisibility(View.INVISIBLE);
			Lights_Btn.setVisibility(View.VISIBLE);
			Spoiler_Btn.setVisibility(View.VISIBLE);
			Video_Btn.setVisibility(View.VISIBLE);
			Ir1_Btn.setVisibility(View.INVISIBLE);
			Video1_Btn.setVisibility(View.INVISIBLE);
		}
		G_End_Show.setText(" ");// 目的让j_left显示在视频画面上面，如果不加，就会显示在视频画面下面，另一实现在视
	}

	/**
	 * 结束播放路径
	 */
	public void playpath_end() {
		PlayPath_Btn.setEnabled(false);
		Path_Btn.setEnabled(false);
		if (wificarNewLayoutParams.screenSize <= 5.8) {
			PlayPath_Btn.setBackgroundResource(R.drawable.play_path_off_phone);
		} else {
			PlayPath_Btn.setBackgroundResource(R.drawable.play_path_off);
		}
		PlayPath_Btn.getBackground().setAlpha(100);
		Path_Btn.getBackground().setAlpha(100);
		ShowInfo(R.string.play_path_complete, Toast.LENGTH_LONG);
		AppPlayPathFunction.getPlayPathInstance(instance).PlayPath_Stop();
		flag = 0;
		Path_Btn.getBackground().setAlpha(255);
		Path_Btn.setEnabled(true);
		PlayPath_Btn.getBackground().setAlpha(255);
		PlayPath_Btn.setEnabled(true);
		if(isJet){
				Door_Btn.setEnabled(false);
				Door_Btn.getBackground().setAlpha(60);
			}else{
				Door_Btn.setEnabled(true);
				Door_Btn.getBackground().setAlpha(255);
			}
	}

	/**
	 * 开始录像函数
	 */
//TODO
	public void ShootingStart() {
		if (!AppInforToCustom.getAppInforToCustomInstance()
				.getIsAvaiableSDCard()) {
			Toast.makeText(instance, R.string.sdcard_nomount,
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (AppInforToCustom.getAppInforToCustomInstance().getSDCapacity() < AppInforToCustom
				.getAppInforToCustomInstance().SDLeastCapacity) {
			Toast.makeText(instance, R.string.wificar_no_enough,
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (phone_battery < 20) {
			shootDialog();
			Video_Btn.setEnabled(true);
			Video1_Btn.setEnabled(true);
			Talk_Btn.setEnabled(true);
			return;
		}
	
		Video_Btn.setEnabled(false); // 屏蔽录像按钮
		Video1_Btn.setEnabled(false);
		isBatteryLowStopFunciton = false;
		Talk_Btn.setEnabled(false);
		Talk_Btn.getBackground().setAlpha(60);
		if (!AppInforToSystem.islistening) {
			AppCommand.getAppCommandInstace().sendCommand(
					CMD_OP_CODE.Audio_Start_Req);
		}
		AppCameraShootingFunction
				.getAppCameraShootingFunctionInstance()
				.ShootingInit(AppThread.getAppThreadInstance().CurrentVideoType);
		Video_Red.setVisibility(0);
		chronometer.setBase(SystemClock.elapsedRealtime());
		chronometer.start();
		chronometer.setVisibility(0);
		if (wificarNewLayoutParams.screenSize <= 5.8) {
			Video_Btn.setBackgroundResource(R.drawable.video_on_phone);
			Video1_Btn.setBackgroundResource(R.drawable.video_on_phone);
		} else {
			Video_Btn.setBackgroundResource(R.drawable.video_on);
			Video1_Btn.setBackgroundResource(R.drawable.video_on);
		}
		
		Video_Btn.setEnabled(false); // 屏蔽录像按钮
		Video1_Btn.setEnabled(false);
	}

	/**
	 * 结束录像函数
	 */
	public void ShootingComplte() {
		if (shootingInforShowTimes == 0) {
			if (AppInforToCustom.getAppInforToCustomInstance()
					.getPlayModeStatus()) {
				// 添加的目的是当录像过程中，空间不足，则要关闭播放路径的停止录像的定时器
				if (AppInforToCustom.getAppInforToCustomInstance()
						.getSDCapacity() < AppInforToCustom
						.getAppInforToCustomInstance().SDLeastCapacity) {
					if (AppPlayPathFunction.getPlayPathInstance(instance).stop_take_video != null) {
						AppPlayPathFunction.getPlayPathInstance(instance).stop_take_video
								.cancel();
						AppPlayPathFunction.getPlayPathInstance(instance).stop_take_video = null;
					}
				}
			}
			Video_Btn.setEnabled(false);
			Video1_Btn.setEnabled(false);
			if (wificarNewLayoutParams.screenSize <= 5.8) {
				Video_Btn.setBackgroundResource(R.drawable.video_off_phone);
				Video1_Btn.setBackgroundResource(R.drawable.video_off_phone);
			} else {
				Video_Btn.setBackgroundResource(R.drawable.video_off);
				Video1_Btn.setBackgroundResource(R.drawable.video_off);
			}
			Video_Btn.getBackground().setAlpha(255);
			Video1_Btn.getBackground().setAlpha(255);
			shootingInforShowTimes = 1;
			AppCameraShootingFunction.getAppCameraShootingFunctionInstance()
					.ShootingClose();
			if (AppInforToSystem.islistening == false) {
				AppCommand.getAppCommandInstace().sendCommand(
						CMD_OP_CODE.Audio_End);
			}
			System.gc();
			chronometer.stop();
			Video_Red.setVisibility(Button.INVISIBLE);
			chronometer.setVisibility(Button.INVISIBLE);
			ShootingCompleteTimer = new Timer();
			ShootingCompleteTimer.schedule(new shootingCompleteTimerTask(),
					500, 500);
		} else if (shootingInforShowTimes == 1) {
			ShowInfo(R.string.video_shooting_finish, Toast.LENGTH_LONG);
			shootingInforShowTimes = 2;
		} else if (shootingInforShowTimes == 2) {
			shootingInforShowTimes = 0;
			if (ShootingCompleteTimer != null) {
				ShootingCompleteTimer.cancel();
				ShootingCompleteTimer = null;
			}
			Video_Btn.getBackground().setAlpha(255);
			Video_Btn.setEnabled(true);
			Video1_Btn.getBackground().setAlpha(255);
			Video1_Btn.setEnabled(true);
//TODO
			if(isJet){
				Talk_Btn.setEnabled(false);
				Talk_Btn.getBackground().setAlpha(60);
			}else{
				Talk_Btn.setEnabled(true);
				Talk_Btn.getBackground().setAlpha(255);
			}
		}
	}

	public void playDialog() {
		if (builder_battery == null) {
			builder_battery = new AlertDialog.Builder(instance);
			builder_battery.setMessage(R.string.playing_powerlow);
			builder_battery.setCancelable(false);
			builder_battery
					.setPositiveButton(R.string.system_query,
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									builder_battery = null;
								}
							}).create().show();
		}
	}

	public void recordDialog() {
		if (builder_battery == null) {
			builder_battery = new AlertDialog.Builder(instance);
			builder_battery.setMessage(R.string.recording_powerlow);
			builder_battery.setCancelable(false);
			builder_battery
					.setPositiveButton(R.string.system_query,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									builder_battery = null;
									Path_Btn.setChecked(false);
								}
							}).create().show();
		}

	}

	public void shootDialog() {
		if (builder_battery == null) {
			builder_battery = new AlertDialog.Builder(instance);
			builder_battery.setMessage(R.string.video_shooting_powerlow);
			builder_battery.setCancelable(false);
			builder_battery
					.setPositiveButton(R.string.system_query,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									builder_battery = null;
								}
							}).create().show();
		}
	}

	// 设置按钮显示
	public void setDialog() {
		if (aboutFirst) {
			aboutDialogLayout = new Dialog(this, R.style.my_dialog);
			if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				aboutDialogLayout.setContentView(R.layout.information);
			}
			aboutDialogLayout.setOnKeyListener(new OnKeyListener() {
				public boolean onKey(DialogInterface dialog, int keyCode,
						KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_BACK) {
						aboutDialogLayout.dismiss();
					}
					return false;
				}
			});
		}

		aboutFirst = false;
	
		EditText tIP = (EditText) aboutDialogLayout
				.findViewById(R.id.EditText_IP);
		EditText tPort = (EditText) aboutDialogLayout
				.findViewById(R.id.EditText_PORT);
		TextView tdevice = (TextView) aboutDialogLayout
				.findViewById(R.id.TextView_D);
		TextView tfirmware = (TextView) aboutDialogLayout
				.findViewById(R.id.TextView_F);
		TextView tsoftware = (TextView) aboutDialogLayout
				.findViewById(R.id.TextView_S);
		ImageView imageView =(ImageView)aboutDialogLayout
				.findViewById(R.id.imageView1);
		imageView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				aboutDialogLayout.cancel();
				aboutDialogLayout.dismiss();
			}
		});
		String version = AppInforToCustom.getAppInforToCustomInstance()
				.getFWVersion();
		String devId = AppInforToCustom.getAppInforToCustomInstance()
				.getTargetDevID();
		String IP = AppInforToCustom.getAppInforToCustomInstance()
				.getTargetIP();
		int Port = AppInforToCustom.getAppInforToCustomInstance()
				.getTargetPort();
		String soft_v = getString(R.string.soft_version).toString();

		tIP.setText(IP);
		tIP.setClickable(false);
		tPort.setText(Port + "");
		tPort.setClickable(false);
		tdevice.setText(devId);
		tfirmware.setText(version);
		tsoftware.setText(soft_v);
		aboutDialogLayout.show();
		if (inforTimer != null) {
			inforTimer.cancel();
			inforTimer = null;
		}
		inforTimer = new Timer();
		inforTimer.schedule(new infoTimeTask(), 100);
	}

	/**
	 * 判断是否是程序第一次开启
	 */
	public void CheckFirstApp() {
		SharedPreferences sharedPreferences = getSharedPreferences(
				AppInforToSystem.SP_FIRST, Activity.MODE_PRIVATE);
		AppInforToSystem.isFirstApp = sharedPreferences.getBoolean("first",
				true);
		if (AppInforToSystem.isFirstApp) {
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putBoolean("first", false);
			editor.commit();
		} 
	}

	/**
	 * 判断Home按键
	 */
	public void ExitIsHome() {
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.wificar_icon,
				"Power off the RC Tumbler when not in use",
				System.currentTimeMillis());
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				getIntent(), 0);
		notification
				.setLatestEventInfo(this, "wificar",
						"Power off the RC Tumbler when not in use",
						contentIntent);
		notificationManager.notify(R.drawable.wificar_icon, notification);
	}

	// 退出程序时调用
	public void ExitPcare() {
		try {
			if (isFirstExit) {
				isFirstExit = false;
				if (AppInforToCustom.getAppInforToCustomInstance()
						.getIsGSensorControl()) {
					AppGSenserFunction.getAppCameraSurfaceFunction(instance)
							.DisableGSensorControl();
				} // 如果是隐身模式，则开启状态灯
				if (AppInforToCustom.getAppInforToCustomInstance()
						.getIsStealthControl()) {
					AppDeviceStateLedFunction
							.getAppDeviceStateLedFunctionInstance()
							.DeviceStateLed_OandC(true);
				}
				if (AppInforToCustom.getAppInforToCustomInstance()
						.getIsCameraShooting()) {
					chronometer.stop();
					Video_Red.setVisibility(View.INVISIBLE);
					chronometer.setVisibility(View.INVISIBLE);
					AppCameraShootingFunction
							.getAppCameraShootingFunctionInstance()
							.ShootingClose();
					if (wificarNewLayoutParams.screenSize <= 5.8) {
						Talk_Btn.setBackgroundResource(R.drawable.talk_off_ipad);
					} else {
						Talk_Btn.setBackgroundResource(R.drawable.talk_off_ipad);
					}
					if (playPathFirstFlag) {
						PlayPath_Btn.getBackground().setAlpha(255);
						if (wificarNewLayoutParams.screenSize <= 5.8) {
							PlayPath_Btn
									.setBackgroundResource(R.drawable.play_path_off_phone);
						} else {
							PlayPath_Btn
									.setBackgroundResource(R.drawable.play_path_off);
						}
					}
					if (wificarNewLayoutParams.screenSize <= 5.8) {
						Video_Btn
								.setBackgroundResource(R.drawable.video_off_phone);
						Video1_Btn.setBackgroundResource(R.drawable.video_off_phone);
					} else {
						Video_Btn.setBackgroundResource(R.drawable.video_off);
						Video1_Btn.setBackgroundResource(R.drawable.video_off);
					}
				}
				if (AppInforToCustom.getAppInforToCustomInstance()
						.getRecordPath_flag() == 1) {
					RecordPathStop();
				}
				unregisterReceiver(spReceiver);
				AppConnect.getInstance().exitApp();
				if (!isShared) {
					AppActivityClose.getInstance().exitAll();
				}
			}
		} catch (Exception e) {

		}
	}

	// 进入分享界面
	public void share() {
		isShared = true;
		Intent intent = new Intent();
		intent.setClass(WificarNew.this, ShareActivity.class);
		startActivity(intent);
		if (SplashActivity.getInstance() != null) {
			SplashActivity.getInstance().finishExit();
		}
		instance.finish();
		instance = null;
	}

	
	/*
	 * 定时器区域
	 */
	/*
	 * 防止socket建立的时候时间太长，界面一直处于连接状态
	 */
	class ConnectOut extends TimerTask {
		@Override
		public void run() {
			ConnnectOut_timer.cancel();
			ConnnectOut_timer = null;
			if (isConnectOut == 0) {
				isConnectOut = 3;
				Message messageConnectFail = new Message();
				messageConnectFail.what = MESSAGE_CONNECT_TO_CAR_FAIL;
				handler.sendMessage(messageConnectFail);
			}
		}
	}

	// 显示基本的信息的定时器
	class showInforTimerTask extends TimerTask {
		@Override
		public void run() {
			if (showInforTimer != null) {
				Message msg = Message.obtain();
				msg.what = MESSAGE_SHOWINFORMATION_END;
				handler.sendMessage(msg);
				if (showInforTimer != null) {
					showInforTimer.cancel();
					showInforTimer = null;
				}
			}
		}
	}

	// 缩放定时器
	class scaleTimerTask extends TimerTask {
		@Override
		public void run() {
			if (scaleTimer != null) {
				Message msg = new Message();
				msg.what = MESSAGE_SCALE_END;
				handler.sendMessage(msg);
				msg = null;
			}
		}
	}

	class rocketTask extends TimerTask {
		@Override
		public void run() {
			Message msg = new Message();
			msg.what = MESSAGE_ROCKET_CLICKED;
			handler.sendMessage(msg);
			msg = null;
		}
	}
	// 显示录像结束后的提示信息
	class shootingCompleteTimerTask extends TimerTask {
		@Override
		public void run() {
			Message msg = new Message();
			msg.what = MESSAGE_SHOOTING_COMPLETE;
			handler.sendMessage(msg);
			msg = null;
		}
	}

	// talk按钮定时按下
	class talkPressTimerTask extends TimerTask {
		@Override
		public void run() {
			AppInforToSystem.main_touch_flag = 0;
			sendMessages(MESSAGE_TALK_PRESS);
		}
	}
	
	// 用于限制拍照的定时器
	class photoTimerTask extends TimerTask {
		@Override
		public void run() {
			sendMessages(MESSAGE_PHOTOGRAPH_END);
		}
	}

	// 用于限制监听的定时器
	class listenTimerTask extends TimerTask {
		@Override
		public void run() {
			sendMessages(MESSAGE_LISTENING_END);
		}
	}

	// 限制G-sensor
	class gsensorTimerTask extends TimerTask {
		@Override
		public void run() {
			sendMessages(MESSAGE_GSENSER_ENABLE);
		}
	}

	class videoTimerTask extends TimerTask{
		@Override
		public void run() {
			sendMessages(MESSAGE_VIDEO_ENABLE);
			
		}
		
	}
	
	// 定时双击back
	class backTimerTask extends TimerTask {
		@Override
		public void run() {
			isBack = false;
		}
	}

	// 按home键，2秒后退出应用程序
	class homeTimerTask extends TimerTask {
		@Override
		public void run() {
			sendMessages(MESSAGE_HOME_PRESS);
		}
	}

	// 点击info，关闭G功能
	class infoTimeTask extends TimerTask {
		@Override
		public void run() {
			if (AppInforToCustom.getAppInforToCustomInstance()
					.getIsGSensorControl()) {
				sendMessages(MESSAGE_GSENSOR_END);
			}
			if (inforTimer != null) {
				inforTimer.cancel();
				inforTimer = null;
			}
		}
	}
	
	public static boolean isInUse = false;
	class PlayThread extends Thread {
		public void run() {
			while (isInUse)
			{
				try
				{
					sleep(5250);
				} catch (Exception e)
				{
					// TODO: handle exception
				}
				handler.sendEmptyMessage(MESSAGE_UPDATE_PLAYPOS);
			}
		}
	}

	/**
	 * 显示提示信息
	 */
	public void ShowInfo(int id, int mode) {
		if (infoToast == null) {
			infoToast = Toast.makeText(instance, id, Toast.LENGTH_SHORT);
			infoToast.setGravity(Gravity.CENTER, 0, 0);
		} else {
			infoToast.setText(id);
		}
		infoToast.show();
	}

	public void sendMessages(int cmd) {
		Message msg = new Message();
		msg.what = cmd;
		handler.sendMessage(msg);
	}

	//初始布局
	//TODO
	public void initLayout() {
		LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
		View view = inflater.inflate(R.layout.display_image, null);
	
		Parent = (RelativeLayout) view.findViewById(R.id.bottom_id);
		Parent0 = new RelativeLayout(getApplicationContext());
		Parent1= new RelativeLayout(getApplicationContext());
		surfParent = new RelativeLayout(getApplicationContext());
		surfParent.setBackgroundColor(Color.BLACK);
		cameraSurfaceView = new CameraSurfaceView(getApplicationContext());
		cameraSurfaceView.setId(_R.id.Camera_id);
		connectLayout = new LinearLayout(getApplicationContext());
		loading = new ProgressBar(this);
		loading.setContentDescription("Connecting ...");
		tv_Loading = new TextView(getApplicationContext());
		tv_Loading.setText("Connecting ...");
		tv_Loading.setTextSize(23);
		Scrolinear = (LinearLayout) view.findViewById(R.id.scrolinear);
		Scrolinear1 = (LinearLayout)view.findViewById(R.id.scrolinear1);
		scrol_left = new ImageButton(getApplicationContext());
		scrol_left.setBackgroundResource(R.drawable.scrol_left);
		
		scrol_right = new ImageButton(getApplicationContext());
		scrol_right.setBackgroundResource(R.drawable.scrol_right);
		
		scrol_left1 = new ImageButton(getApplicationContext());
		scrol_left1.setBackgroundResource(R.drawable.scrol_left);
		
		scrol_right1 = new ImageButton(getApplicationContext());
		scrol_right1.setBackgroundResource(R.drawable.scrol_right);
		
		Ir_Btn = new ToggleButton(getApplicationContext());
		Ir_Btn.setBackgroundResource(R.drawable.ir_off_phone);
		Ir_Btn.setTextOn(" ");
		Ir_Btn.setTextOff(" ");
		Ir_Btn.setText(" ");
		
		Ir1_Btn = new ImageButton(getApplicationContext());
		Ir1_Btn.setBackgroundResource(R.drawable.ir_off_phone);
		Ir1_Btn.setVisibility(View.INVISIBLE);
		
		Share_Btn = new ImageButton(getApplicationContext());
		Share_Btn.setId(_R.id.Share_Btn_id);
		Share_Btn.setBackgroundResource(R.drawable.share_off_phone);
		P_V_Parent = new LinearLayout(getApplicationContext());
		Photo_Btn = new ToggleButton(getApplicationContext());
		Photo_Btn.setBackgroundResource(R.drawable.photo_off_phone);
		Photo_Btn.setTextOn(" ");
		Photo_Btn.setTextOff(" ");
		Photo_Btn.setText(" ");
		Video_Btn = new ToggleButton(getApplicationContext());
		Video_Btn.setBackgroundResource(R.drawable.video_off_phone);
		Video_Btn.setTextOn(" ");
		Video_Btn.setTextOff(" ");
		Video_Btn.setText(" ");
		Path_Btn = new ToggleButton(getApplicationContext());
		Path_Btn.setBackgroundResource(R.drawable.path_off_phone);
		Path_Btn.setTextOn(" ");
		Path_Btn.setTextOff(" ");
		Path_Btn.setText(" ");
		PlayPath_Btn = new ToggleButton(getApplicationContext());
		PlayPath_Btn.setBackgroundResource(R.drawable.play_path_off_phone);
		PlayPath_Btn.setTextOn(" ");
		PlayPath_Btn.setTextOff(" ");
		PlayPath_Btn.setText(" ");
		Talk_Btn = new ToggleButton(getApplicationContext());
		Talk_Btn.setBackgroundResource(R.drawable.talk_off_ipad);
		Talk_Btn.setTextOn(" ");
		Talk_Btn.setTextOff(" ");
		Talk_Btn.setText(" ");
		
		Bat_Btn = new ImageButton(getApplicationContext());
		Bat_Btn.getBackground().setAlpha(0);
		
		Mic_Btn = new ToggleButton(getApplicationContext());
		Mic_Btn.setBackgroundResource(R.drawable.volume_off_phone);
		Mic_Btn.setTextOn(" ");
		Mic_Btn.setTextOff(" ");
		Mic_Btn.setText(" ");
		Lights_Btn = new ToggleButton(getApplicationContext());
		Lights_Btn.setBackgroundResource(R.drawable.light_off_phone);
		AppInforToCustom.getAppInforToCustomInstance().setIsBrightControl(true);
		Lights_Btn.setTextOn(" ");
		Lights_Btn.setTextOff(" ");
		Lights_Btn.setText(" ");
		Parent.removeView(panelBom);
		
			Parent.setBackgroundResource(R.drawable.bd854_480);
			appHorizontalScroView = (AppHorizontalScroView) view
					.findViewById(R.id.apphorizontal);
			appHorizontalScroView.setOverScrollMode(View.OVER_SCROLL_NEVER);//去底纹
			//重新布局
			 FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
					 LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			 			params.leftMargin = wificarNewLayoutParams.Screen_width*83/854;
			 			params.bottomMargin = wificarNewLayoutParams.Screen_height*25/480;
			 			params.width = wificarNewLayoutParams.Screen_width/4;
			 			appHorizontalScroView.setLayoutParams(params);
			appHorizontalScroView1 = (AppHorizontalScroViewRight)view.findViewById(R.id.apphorizontal1);
			appHorizontalScroView1.setOverScrollMode(View.OVER_SCROLL_NEVER);//去底纹
			 FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(
					 LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			 			params1.leftMargin= wificarNewLayoutParams.Screen_width/2+(wificarNewLayoutParams.Screen_width*129/854);
			 			params1.bottomMargin = wificarNewLayoutParams.Screen_height*25/480;
			 			params1.width = wificarNewLayoutParams.Screen_width/4;
			 			appHorizontalScroView1.setLayoutParams(params1);
			 			
		Photo1_Btn = new ToggleButton(getApplicationContext());
		Photo1_Btn.setBackgroundResource(R.drawable.photo_off_phone);
		Photo1_Btn.setTextOn(" ");
		Photo1_Btn.setTextOff(" ");
		Photo1_Btn.setText(" ");
		Photo1_Btn.setVisibility(View.INVISIBLE);
		
		Video1_Btn = new ToggleButton(getApplicationContext());
		Video1_Btn.setBackgroundResource(R.drawable.video_off_phone);
		Video1_Btn.setTextOn(" ");
		Video1_Btn.setTextOff(" ");
		Video1_Btn.setText(" ");
		Video1_Btn.setVisibility(View.INVISIBLE);
		Door_Btn = new ToggleButton(getApplicationContext());
		Door_Btn.setBackgroundResource(R.drawable.door_off_phone);
		Door_Btn.setTextOn(" ");
		Door_Btn.setTextOff(" ");
		Door_Btn.setText(" ");
	
		G_Btn = new ImageButton(getApplicationContext());
		G_Btn.setId(_R.id.G_Btn_Id);
		G_Btn.setBackgroundResource(R.drawable.g_off_phone);
		
		G_Btn1= new ImageButton(getApplicationContext());
		G_Btn1.setId(_R.id.G_Btn_Id);
		G_Btn1.setBackgroundResource(R.drawable.g_off_phone);
		G_Btn1.setVisibility(View.INVISIBLE);
		
		Spoiler_Btn = new ToggleButton(getApplicationContext());
		Spoiler_Btn.setBackgroundResource(R.drawable.spoiler_off_phone);
		Spoiler_Btn.setTextOn(" ");
		Spoiler_Btn.setTextOff(" ");
		Spoiler_Btn.setText(" ");
		
		Rocket_Btn=new ToggleButton(getApplicationContext());
		Rocket_Btn.setBackgroundResource(R.drawable.rocket_off_phone);
		Rocket_Btn.setTextOn(" ");
		Rocket_Btn.setTextOff(" ");
		Rocket_Btn.setText(" ");
		Rocket_Btn.setVisibility(View.INVISIBLE);
		
		Jetpower_Btn = new ImageButton(getApplicationContext());
		Jetpower_Btn.setId(_R.id.jetpower_id);
		Jetpower_Btn.setBackgroundResource(R.drawable.jetpower_off_phone);
		
		Mgun_Btn = new ToggleButton(getApplicationContext());
		Mgun_Btn.setBackgroundResource(R.drawable.mgun_off_phone);
		Mgun_Btn.setTextOn(" ");
		Mgun_Btn.setTextOff(" ");
		Mgun_Btn.setText(" ");
		Mgun_Btn.setVisibility(View.INVISIBLE);
		cameraSurfaceView = new CameraSurfaceView(getApplicationContext());
		cameraSurfaceView.setId(_R.id.Camera_Surface_id);
		ZoomIn = new Button(getApplicationContext());
		ZoomIn.setBackgroundResource(R.drawable.zoon_in_off);
		ZoomOut = new Button(getApplicationContext());
		ZoomOut.setId(_R.id.ZoomOut_id);
		ZoomOut.setBackgroundResource(R.drawable.zoon_out_off);

		ShowInfoTView = new TextView(getApplicationContext());
		ShowInfoTView.setTextSize(20);
		ShowInfoTView.setGravity(Gravity.CENTER);
		G_End_Show = new TextView(getApplicationContext());
		G_End_Show.setTextSize(20);
		G_End_Show.setGravity(Gravity.CENTER);

		ScaleTView = new TextView(getApplicationContext());
		ScaleTView.setTextSize(20);
		ScaleTView.setTextColor(Color.WHITE);
		ScaleTView.setText(" ");

		chronometer = new Chronometer(getApplicationContext());
		chronometer.setFormat("%s");
		chronometer.setId(_R.id.Chronometer_id);
		chronometer.setVisibility(View.INVISIBLE);
		Video_Red = new ToggleButton(getApplicationContext());
		Video_Red.setBackgroundResource(R.drawable.video_on_led);
		Video_Red.setVisibility(View.INVISIBLE);

		appVolume_MoveCar = new AppVolume_MoveCar(getApplication());
		appVolume_MoveCar.setId(_R.id.Volume_id);
		
		appUD_MoveCar = new AppUD_MoveCar(this);
		appUD_MoveCar.setId(_R.id.Car_UD_id);
		appLR_MoveCar = new AppLR_MoveCar(this);
		appLR_MoveCar.setId(_R.id.Car_LR_id);
		appJet_Movebar = new AppJet_MoveCar(this);
		appJet_Movebar.setId(_R.id.Jet_id);
	}

	
	
	//TODO
	//>5.8
	public void initHLayout() {
		Parent0 = new RelativeLayout(getApplicationContext());
		Parent1 = new RelativeLayout(getApplicationContext());	
		surfParent = new RelativeLayout(getApplicationContext());
		surfParent.setBackgroundColor(Color.BLACK);
		connectLayout = new LinearLayout(getApplicationContext());
		loading = new ProgressBar(this);
		loading.setContentDescription("Connecting ...");
		tv_Loading = new TextView(getApplicationContext());
		tv_Loading.setText("Connecting ...");
		tv_Loading.setTextSize(27);
		cameraSurfaceView = new CameraSurfaceView(getApplicationContext());
		cameraSurfaceView.setId(_R.id.Camera_id);
		Parent = new RelativeLayout(getApplicationContext());

		if ((float)wificarNewLayoutParams.Screen_width / wificarNewLayoutParams.Screen_height == (float)1280 / 800)
		{
			Log.e("Background", "比例选择是1280:800.。。");
			Parent.setBackgroundResource(R.drawable.db);
		}else if ((float)wificarNewLayoutParams.Screen_width / wificarNewLayoutParams.Screen_height == (float)4 / 3)
		{
			Parent.setBackgroundResource(R.drawable.dbackgroud1024_768);
		}
		else if(wificarNewLayoutParams.Screen_height>800){
			Log.e("Background", "height>800.。。");
			Parent.setBackgroundResource(R.drawable.dbackground);
		}
		else {
			if(wificarNewLayoutParams.Screen_height < 744) {
				Log.e("Background", "比例选择是1280:736.。。");
				bgDrawable = getApplication().getResources().getDrawable(R.drawable.dbackgrounds);
				Parent.setBackgroundDrawable(bgDrawable);
			} else {
				Log.e("Background", "比例选择是1280:752.。。");
				bgDrawable = getApplication().getResources().getDrawable(R.drawable.dbackground);
				Parent.setBackgroundDrawable(bgDrawable);
			}	
		}
		BottomInParent = new LinearLayout(getApplicationContext());
		BottomInParent.setId(_R.id.BottomInParent_id);
		Ir_Btn = new ToggleButton(getApplicationContext());
		Ir_Btn.setBackgroundResource(R.drawable.ir_off1);
		Ir_Btn.setTextOn(" ");
		Ir_Btn.setTextOff(" ");
		Ir_Btn.setText(" ");
		Ir_Btn.setId(_R.id.Ir_Btn_id);
		G_Btn = new ImageButton(getApplicationContext());
		G_Btn.setBackgroundResource(R.drawable.g_off);
		
		Ir1_Btn = new ImageButton(getApplicationContext());
		Ir1_Btn.setBackgroundResource(R.drawable.ir_off);
		Ir1_Btn.setVisibility(View.INVISIBLE);
		
		Photo1_Btn = new ToggleButton(getApplicationContext());
		Photo1_Btn.setBackgroundResource(R.drawable.photo_off);
		Photo1_Btn.setTextOn(" ");
		Photo1_Btn.setTextOff(" ");
		Photo1_Btn.setText(" ");
		Photo1_Btn.setVisibility(View.INVISIBLE);
		Rocket_Btn=new ToggleButton(getApplicationContext());
		Rocket_Btn.setBackgroundResource(R.drawable.rocket_off);
		Rocket_Btn.setTextOn(" ");
		Rocket_Btn.setTextOff(" ");
		Rocket_Btn.setText(" ");
		Rocket_Btn.setVisibility(View.INVISIBLE);
		Video1_Btn = new ToggleButton(getApplicationContext());
		Video1_Btn.setBackgroundResource(R.drawable.video_off);
		Video1_Btn.setTextOn(" ");
		Video1_Btn.setTextOff(" ");
		Video1_Btn.setText(" ");
		Video1_Btn.setVisibility(View.INVISIBLE);
		Mgun_Btn = new ToggleButton(getApplicationContext());
		Mgun_Btn.setBackgroundResource(R.drawable.mgun_off);
		Mgun_Btn.setTextOn(" ");
		Mgun_Btn.setTextOff(" ");
		Mgun_Btn.setText(" ");
		Mgun_Btn.setVisibility(View.INVISIBLE);
		Photo_Btn = new ToggleButton(getApplicationContext());
		Photo_Btn.setBackgroundResource(R.drawable.photo_off);
		Photo_Btn.setTextOn(" ");
		Photo_Btn.setTextOff(" ");
		Photo_Btn.setText(" ");
		Video_Btn = new ToggleButton(getApplicationContext());
		Video_Btn.setBackgroundResource(R.drawable.video_off);
		Video_Btn.setTextOn(" ");
		Video_Btn.setTextOff(" ");
		Video_Btn.setText(" ");
		Video_Btn.setId(_R.id.video_id);
		
		Door_Btn = new ToggleButton(getApplicationContext());
		Door_Btn.setBackgroundResource(R.drawable.door_off);
		Door_Btn.setTextOn(" ");
		Door_Btn.setTextOff(" ");
		Door_Btn.setText(" ");
		
		Spoiler_Btn = new ToggleButton(getApplicationContext());
		Spoiler_Btn.setBackgroundResource(R.drawable.spoiler_off);
		Spoiler_Btn.setTextOn(" ");
		Spoiler_Btn.setTextOff(" ");
		Spoiler_Btn.setText(" ");
		Path_Btn = new ToggleButton(getApplicationContext());
		Path_Btn.setBackgroundResource(R.drawable.path_off);
		Path_Btn.setTextOn(" ");
		Path_Btn.setTextOff(" ");
		Path_Btn.setText(" ");
		
		Bat_Btn = new ImageButton(getApplicationContext());
		Bat_Btn.getBackground().setAlpha(0); 
		
		null_Btn = new ImageButton(getApplicationContext());
		null_Btn.getBackground().setAlpha(0); 
		
		PlayPath_Btn = new ToggleButton(getApplicationContext());
		PlayPath_Btn.setBackgroundResource(R.drawable.play_path_off);
		PlayPath_Btn.setTextOn(" ");
		PlayPath_Btn.setTextOff(" ");
		PlayPath_Btn.setText(" ");
		Talk_Btn = new ToggleButton(getApplicationContext());
		Talk_Btn.setBackgroundResource(R.drawable.talk_off_ipad);
		Talk_Btn.setTextOn(" ");
		Talk_Btn.setTextOff(" ");
		Talk_Btn.setText(" ");
		Mic_Btn = new ToggleButton(getApplicationContext());
		Mic_Btn.setBackgroundResource(R.drawable.volume_off_ipad);
		Mic_Btn.setId(_R.id.mic_id);
		Mic_Btn.setTextOn(" ");
		Mic_Btn.setTextOff(" ");
		Mic_Btn.setText(" ");
		Lights_Btn = new ToggleButton(getApplicationContext());
		Lights_Btn.setBackgroundResource(R.drawable.lights_on);
		AppInforToCustom.getAppInforToCustomInstance().setIsBrightControl(true);
		Lights_Btn.setTextOn(" ");
		Lights_Btn.setTextOff(" ");
		Lights_Btn.setText(" ");

		ZoomIn = new Button(getApplicationContext());
		ZoomIn.setBackgroundResource(R.drawable.zoon_in_off);
		ZoomOut = new Button(getApplicationContext());
		ZoomOut.setBackgroundResource(R.drawable.padzoomoutoff);
		ZoomOut.setId(_R.id.ZoomOut_id);

		Share_Btn = new ImageButton(getApplicationContext());
		Share_Btn.setId(_R.id.Share_Btn_id);
		Share_Btn.setBackgroundResource(R.drawable.share_off1);
		
		Jetpower_Btn = new ImageButton(getApplicationContext());
		Jetpower_Btn.setId(_R.id.jetpower_id);
		Jetpower_Btn.setBackgroundResource(R.drawable.jetpower_off);
		
		ShowInfoTView = new TextView(getApplicationContext());
		ShowInfoTView.setTextSize(25);
		ShowInfoTView.setGravity(Gravity.CENTER);
		G_End_Show = new TextView(getApplicationContext());
		G_End_Show.setTextSize(25);
		G_End_Show.setGravity(Gravity.CENTER);

		ScaleTView = new TextView(getApplicationContext());
		ScaleTView.setTextSize(30);
		ScaleTView.setText(" ");
		ScaleTView.setTextColor(Color.WHITE);

		chronometer = new Chronometer(getApplicationContext());
		chronometer.setFormat("%s");
		chronometer.setId(_R.id.Chronometer_id);
		chronometer.setVisibility(View.INVISIBLE);

		Video_Red = new ToggleButton(getApplicationContext());
		Video_Red.setBackgroundResource(R.drawable.video_on_led);
		Video_Red.setVisibility(View.INVISIBLE);

		appVolume_MoveCar = new AppVolume_MoveCar(getApplication());
		appVolume_MoveCar.setId(_R.id.Volume_id);
		
		appUD_MoveCar = new AppUD_MoveCar(this);
		appUD_MoveCar.setId(_R.id.Car_UD_id);
		appLR_MoveCar = new AppLR_MoveCar(this);
		appLR_MoveCar.setId(_R.id.Car_LR_id);
		appJet_Movebar = new AppJet_MoveCar(this);
		appJet_Movebar.setId(_R.id.Jet_id);
	}

	// 声明布局
	public ProgressBar loading;
	public RelativeLayout Parent,Parent0,Parent1,surfParent;
	public LinearLayout BottomInParent, P_V_Parent,connectLayout;
	public ImageButton G_Btn,G_Btn1, Speed_Btn,Share_Btn, CamMove_Reset,Speed,Speed1,Bat_Btn,
	scrol_left,scrol_right,scrol_left1,scrol_right1,Ir1_Btn,null_Btn;
	public static ImageButton Jetpower_Btn;
	public ToggleButton Path_Btn, PlayPath_Btn,
			Video_Btn, Photo_Btn,  Mic_Btn,Ir_Btn, Lights_Btn,Door_Btn,Spoiler_Btn,Video1_Btn,
			Photo1_Btn,Mgun_Btn,Rocket_Btn,Talk_Btn;//下拉布局
	public Chronometer chronometer;
	public ToggleButton Video_Red;
	public Button ZoomIn, ZoomOut;// , Info_Btn;
	public TextView ShowInfoTView, ScaleTView, G_End_Show,tv_Loading;
	public PanelBom panelBom;
	public LinearLayout Scrolinear,Scrolinear1;

	public AppUD_MoveCar appUD_MoveCar;
	public AppLR_MoveCar appLR_MoveCar;
	public AppJet_MoveCar appJet_Movebar;
	public AppVolume_MoveCar appVolume_MoveCar;
	public CameraSurfaceView cameraSurfaceView;
	public AppHorizontalScroView appHorizontalScroView;
	public AppHorizontalScroViewRight appHorizontalScroView1;
	public ProgressBar connectionProgressDialog = null;
	public ProgressDialog connectionProgressDialog1 = null;
	public ProgressDialog funcProgressDialog = null;
	public ProgressDialog nofunProgressDialog = null;
	public ProgressDialog spoilerOpenProgressDialog = null;
	public ProgressDialog spoilerCloseProgressDialog = null;
	public ProgressDialog doorOpenProgressDialog = null;
	public ProgressDialog doorCloseProgressDialog = null;
	public AlertDialog.Builder builder_battery = null;
	public BigeyeCallBack bigeyeCallBack;
	public GestureDetector detector = null;
	public GestureDetector cameraResetDesture = null;
	public G_Dilaog disG_Dilaog;
	private Dialog aboutDialogLayout;
	
	
	

	// 声明变量
	public boolean CamMove_UD_Btn_Click = false;
	public boolean CamMove_LR_Btn_Click = false;
	public boolean isShared = false;
	public boolean isFirstExit = true;
	public boolean playPathFirstFlag = false;
	public boolean isBatteryLowStopFunciton = false;
	public boolean listenViocePress = false;
	private boolean photodown = false;
	public boolean istalking = false;
	public boolean issounding = false;
	public boolean iscommanding = false;
	private boolean aboutFirst = true; // 是否是第一次点击设置按钮
	public static boolean isDrawSlider = false; // 是否是拉动抽屉界面
	public boolean isHomeExit = true;
	public boolean isBack = false;
	public boolean isStealth_Down = false;
	public boolean isShare_Down = false;
	public boolean isLights_Down = false;
	public int talk_delay_time = 300;
	public int isConnectOut = 0;
	public int phone_battery = -1;
	public int pre_battery = 100;
	public int shootingInforShowTimes = 0;
	public int flag = 0;
	public int UD_Move_flag = 3;
	public int LR_Move_flag = 3;
	public int Volume_flag = 3;
	public int Jet_flag = 3;
	public static boolean isTop = true;
	public int photoLimitClickTimes = 0;
	public int listenLimitClickTimes = 0;
	public int Camera_Move_flag = 0;
	public static boolean shooting = false;
	public boolean func = false;
	public boolean spoiler=false;
	public boolean isVideoing = false;
    
	public long TouchHorzontalTime = 0;
	public long TouchHorzontalNewTime = 0;
	public static Toast infoToast;
	private SurfaceView surfaceView = null ;
	
	Timer scaleTimer, showInforTimer, soundSliderTimer;
	Timer ConnnectOut_timer = null;
	Timer ShootingCompleteTimer = null;
	Timer listenCloseTimer;
	Timer photoTimer = null;
	Timer GsensorTimer = null;
	Timer horzontalTimer = null;
	Timer videoTimer = null;
	Timer BackTimer = null;
	Timer photoPressTimer;
	Timer talkPressTimer;
	Timer homeTimer;
	Timer inforTimer;
	Timer resetSingleClickTimer;
	Timer soundTimer;

	// 消息声明
	public static final int MESSAGE_SOUNDSLIDER_CANCEL = 999;
	public static final int MESSAGE_SHOWINFORMATION_END = 1001;
	public final static int MESSAGE_CONNECT_TO_CAR_FAIL = 1002;
	public final static int MESSAGE_CONNECT_TO_CAR_SUCCESS = 1003;
	public final static int MESSAGE_CONNECT_TO_CAR = 1004;
	public static final int MESSAGE_BATTERY_100 = 1005;
	public static final int MESSAGE_BATTERY_75 = 1006;
	public static final int MESSAGE_BATTERY_20 = 1007;
	public static final int MESSAGE_BATTERY_50 = 1008;
	public static final int MESSAGE_BATTERY_25 = 1009;
	public static final int MESSAGE_BATTERY_0 = 1010;
	public static final int MESSAGE_BATTERY_UNKNOWN = 1011;
	public static final int MESSAGE_SCALE_END = 1012;
	public static final int MESSAGE_PHONE_CAPACITY_LOW = 1013;
	public static final int MESSAGE_SHOOTING_START = 2000;
	public static final int MESSAGE_SHOOTING_COMPLETE = 2001;
	public static final int MESSAGE_STOP_PLAYPATH = 2002;
	public static final int MESSAGE_RECORDPATH_COMPLETE = 2003;
	public static final int MESSAGE_PLAYPATH_START = 2004;
	public static final int MESSAGE_PHOTOGRAPH_END = 2005;
	public static final int MESSAGE_PLAYPATH_END = 2006;
	public static final int MESSAGE_CAMERACHANGE_END = 2007;
	public static final int MESSAGE_GSENSOR_END = 2008;
	public static final int MESSAGE_NIGHT_LIGNTH_END = 2009;
	public static final int MESSAGE_LISTENING_END = 2010;
	public static final int MESSAGE_GSENSER_ENABLE = 2012;
	public static final int MESSAGE_VIDEO_ENABLE = 2013;
	public final static int MESSAGE_PING_FAIL = 3001;
	public final static int MESSAGE_SPK_END_SUCCESS = 3002;
	public static final int MESSAGE_NO_SHOOTING = 5001;
	public static final int MESSAGE_NO_RECORD = 5002;
	public static final int MESSAGE_HEARTBEAT_WARNING = 5003;
	public static final int MESSAGE_HORZONTAL_HIDE = 5004;
	public static final int MESSAGE_GSENSER_START = 5005;
	public static final int MESSAGE_TALK_PRESS = 5007;
	public static final int MESSAGE_HOME_PRESS = 5008;
	public static final int MESSAGE_SCROLL_LR_FLAG = 5009;
	public static final int MESSAGE_CAMERA_DEGREE = 5010;
	public static final int MESSAGE_CAMERA_RESET_END = 5011;
	public static final int MESSAGE_SINGLRESETCLICK = 5012;
	public static final int MESSAGE_SOUND_PRESS = 5013;
	public static final int MESSGE_SCROLL_RIGHT = 5014;
	 
	
	public static final int MESSAGE_JET_END = 7001;
	public static final int MESSAGE_DOOR_DONE = 7002;
	public static final int MESSAGE_G_VOLUME_ENABLE = 7003;
	public static final int MESSAGE_G_VOLUME_DISABLE = 7004;
	public static final int MESSAGE_ROCKET_CLICKED = 7005;
	public static final int MESSGE_FUNC = 8001;
	public static final int MESSGE_FUNC_START = 8002;
	public static final int MESSGE_FUNC_STOP = 8003;
	public static final int MESSGE_SPOILER = 8004;
	public final int MESSAGE_UPDATE_PLAYPOS = 8005;
	public final int MESSAGE_SHOW_DEMODIALOG =8006;
	public final int MESSAGE_DEMO_END = 8007;
	public static final int MESSGE_CHARGING_STATUS = 8008;
}