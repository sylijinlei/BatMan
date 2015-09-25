package com.seuic;



import com.batman.package1.R;
import com.batman.package1.WificarNew;
import com.seuic.function.AppGSenserFunction;
import com.seuic.function.AppListenAudioFunction;
import com.seuic.util.WificarNewLayoutParams;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AppHorizontalSeekBar extends SurfaceView implements SurfaceHolder.Callback{
	public static AppHorizontalSeekBar instance;
	public static final String TAG = "AppHorizontalSeekBar";
	public Context context;
	public AppVoiceManage appVoiceManage;
	public static boolean isAudioEnable = false;
	
	public AppHorizontalSeekBar(Context context) {
		super(context);
		instance = this;
		this.context = context;
		appVoiceManage = new AppVoiceManage(context);
		init();
	}
	
	public AppHorizontalSeekBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		instance = this;
		this.context = context;
		appVoiceManage = new AppVoiceManage(context);
		init();
	}
	
	public void init(){
		isAudioEnable  = false;
		holder = this.getHolder();
		holder.addCallback(this);
		getDisplayMetrics();
		paint = new Paint();
		paint.setAlpha(255);
		paint.setAntiAlias(true);//抗锯齿
		setFocusable(true);
        setFocusableInTouchMode(true);
        setZOrderOnTop(true);
        stickBar_height = WificarNewLayoutParams.Volume_Move_Width;
        stickBall_width = WificarNewLayoutParams.Volume_Move_Height;
        
        move_limit = (stickBar_height - stickBall_width) ; //移动限定
        half_stickBall_width = stickBall_width;//
		move_limit_1 = move_limit - half_stickBall_width;
		move_limit_2 = move_limit * 2 - half_stickBall_width;
		move_limit_3 = move_limit * 3 - half_stickBall_width;
		move_limit_4 = move_limit * 4 - half_stickBall_width;
		stickBallPointY = (stickBar_height - stickBall_width)/2*3;
		voice_limit_max = appVoiceManage.getVoiceLimits(AppVoiceManage.MUSIC_VOICE_MAX) / 4;
		voice_init_value = appVoiceManage.getVoiceLimits(AppVoiceManage.MUSIC_VOICE_CURRENT);
		holder.setFormat(PixelFormat.TRANSPARENT);//设置背景透明
		stickBar = BitmapFactory.decodeResource(getResources(), R.drawable.volume_bar);
		stickBall =  BitmapFactory.decodeResource(getResources(), R.drawable.volume_point);
		stickBar = Bitmap.createScaledBitmap(stickBar, stickBall_width, stickBar_height, true);
		stickBall = Bitmap.createScaledBitmap(stickBall, stickBall_width/3*2, stickBall_width/3*2, true);
		Drawable bkDrawable = new BitmapDrawable(stickBar);
		this.setBackgroundDrawable(bkDrawable);
	}
	
	//设置监听接口
	public interface HorizontalBarListener{
		 void onVoiceFlagChanged(int voiceFlag);
	}
	
	//设置回调接口
    public void setHorizontalBarListener(HorizontalBarListener horizontalBarListener) {
    	this.horizontalBarListener = horizontalBarListener;
    }
    
    @Override
	public boolean onTouchEvent(MotionEvent event) {
    	try {
//			if (!isHide) {
    		if (AppInforToCustom.isGSensorControl != true || AppGSenserFunction.isStop == true) {
				WificarNew.instance.TouchHorzontalTime = System.currentTimeMillis();
				x = event.getY();
				if (x < 0) {
					x = 0;
					progressBarValue = (int)x;
				}else if (x > (stickBar_height - stickBall_width)/2*3) {
					x = (stickBar_height - stickBall_width)/2*3;
					progressBarValue = (int)x;
				}else{
					progressBarValue = (int)x;
				}
				AppLog.e(TAG, "x:  "+x+"  "+stickBar_width+"  "+stickBall_width);
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if(VoiceFlag != 0 && progressBarValue > 0 && progressBarValue < move_limit_1){
						setVoiceCurrent(0);
						VoiceFlag = 0;
						AppLog.e("test", "current:"+progressBarValue+"  kedu"+VoiceFlag+"    max:   "+ move_limit_1);
					}else if(VoiceFlag != 1 && progressBarValue >= move_limit_1 && progressBarValue < move_limit_2){
						VoiceFlag = 1;
						setVoiceCurrent(1);
						AppLog.e("test", "current:"+progressBarValue+"  kedu"+VoiceFlag+"    3:   "+ move_limit_2);
					}else if(VoiceFlag != 2 && progressBarValue >= move_limit_2 && progressBarValue < move_limit_3){
						VoiceFlag = 2;
						setVoiceCurrent(2);
						AppLog.e("test", "current:"+progressBarValue+"  kedu"+VoiceFlag+"    3:   "+ move_limit_3);
					}else if(VoiceFlag != 3 && progressBarValue >= move_limit_3 && progressBarValue < move_limit_4){
						VoiceFlag = 3;
						setVoiceCurrent(3);
						AppLog.e("test", "current:"+progressBarValue+"  kedu"+VoiceFlag+"    3:   "+ move_limit_4);
					
					}else if(VoiceFlag != 4 && progressBarValue == 0){
						VoiceFlag = 4;
						setVoiceCurrent(4);
						AppLog.e("test", "current:"+progressBarValue+"  kedu"+VoiceFlag+"    3:   "+ voice_limit_max * 0);
					}else if(VoiceFlag != 5 && progressBarValue >= move_limit_4){
						VoiceFlag = 5;
						setVoiceCurrent(5);
						AppLog.e("test", "current:"+progressBarValue+"  kedu"+VoiceFlag+"    3:   "+ voice_limit_max * 5);
					}
					AppLog.e(TAG, "move_limit:  "+move_limit+"  "+stickBar_width+"  "+stickBall_width);
					AppLog.e(TAG, "half_stickBall_width:  "+half_stickBall_width+"  "+stickBar_width+"  "+stickBall_width);
					AppLog.e(TAG, "move_limit_1:  "+move_limit_1+"  "+stickBar_width+"  "+stickBall_width);
					AppLog.e(TAG, "move_limit_2:  "+move_limit_2+"  "+stickBar_width+"  "+stickBall_width);
					AppLog.e(TAG, "move_limit_3:  "+move_limit_3+"  "+stickBar_width+"  "+stickBall_width);
					AppLog.e(TAG, "move_limit_4:  "+move_limit_4+"  "+stickBar_width+"  "+stickBall_width);
				}
				stickBallPointY = x;//上下移动
				if(x< (stickBar_height - stickBall_width)/2*3){
					isAudioEnable = true;
					AppListenAudioFunction.getAppAudioFunctionInstance()
					.AudioEnable();
					if (WificarNew.instance.wificarNewLayoutParams.screenSize < 5.8) {
						WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_on_ipad);
					} else {
						WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_on_ipad);
					}
				
				}
				else{
					isAudioEnable = false;
					AppListenAudioFunction.getAppAudioFunctionInstance().AudioDisable();
					if (WificarNew.instance.wificarNewLayoutParams.screenSize < 5.8) {
						WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_on_ipad);
					} else {
						WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_off_ipad);
					}
				}
				redraw();
				Thread.sleep(70);
    		}
		} catch (Exception e) {
			
		}
    	
		return true;
	}

    //TODO
	/**
	 * 设置手机音量
	 * @param flag -- VoiceFlag
	 */
	public void setVoiceCurrent(int flag){
		horizontalBarListener.onVoiceFlagChanged(flag);
		switch (flag) {
		case 0:
			appVoiceManage.setVoiceValue(stream_type, voice_limit_max * 6, 0);
			break;
		case 1:
			appVoiceManage.setVoiceValue(stream_type, voice_limit_max * 5, 0);
			break;
		case 2:
			appVoiceManage.setVoiceValue(stream_type, voice_limit_max * 4, 0);
			break;
		case 3:
			appVoiceManage.setVoiceValue(stream_type, voice_limit_max * 3, 0);
			break;
		case 5:
			appVoiceManage.setVoiceValue(stream_type, voice_limit_max, 0);
			break;
		
		
		
		case 6: //恢复之前的声音
			appVoiceManage.setVoiceValue(stream_type, voice_init_value, 0);
			break;
		}
	}
	/**
	 * 屏蔽物理调节音量的按键
	 * 当按下去的时候调节音量并弹出Dialog
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_VOLUME_UP:
			//AppLog.e("atest", "down—— uup");
			break;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			//AppLog.e("atest", "down—— down");
			break;
		default:
			return super.onKeyDown(keyCode, event);
		}
		return true;
	}

	/**
	 * 屏蔽物理调节音量的按键
	 * 当松开手，按键弹起的时候，手机发出声音
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_VOLUME_UP:
			//AppLog.e("atest", "up—— uup");
			break;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			//AppLog.e("atest", "up—— down");
			break;
		default:
			return super.onKeyDown(keyCode, event);
		}
		return true;
	}
	/**
	 * 绘图
	 */
	public synchronized void redraw() {
		Canvas canvas = null;
		try {
//			if (!isHide && Talk_VoiceFlag == 0) {
				if (Talk_VoiceFlag == 0) {
				canvas = holder.lockCanvas();
				canvas.drawColor(Color.TRANSPARENT,Mode.CLEAR);//清除屏幕
//				canvas.drawBitmap(stickBar, stickBarPointX, stickBarPointY, paint);
				canvas.drawBitmap(stickBall, stickBallPointX+dip2px(7), stickBallPointY, paint);
			}else {
				 canvas = holder.lockCanvas();
	             canvas.drawColor(Color.TRANSPARENT,Mode.CLEAR);//清除屏幕
			}
		} catch (Exception e) {
		
		}finally{
			if (canvas != null) {
				holder.unlockCanvasAndPost(canvas);
			}
		}
	}
	/*
	 * 设置是否隐藏
	 */
	public void isHide(boolean opt){
	//	isHide = opt;
		if(opt && VoiceFlag == 4){//静音 + 隐藏 = 关闭监听
			//setVoiceCurrent(6);//屏蔽的原因是打开监听默认是静音，点击监听图标收起滑竿时会有一声响声【关闭监听时会出现一声残留声音】
			stickBallPointY = 0;
		}else if (opt && VoiceFlag != 4) { //隐藏 + 音量 = 隐藏滑竿
			//appVoiceManage.setVoiceValue(AppVoiceManage.MUSIC_VOICE_CURRENT, 0, 0);
		}else if (!opt && VoiceFlag == 4) { //显示滑竿  + 静音  = 初次打开监听
			setVoiceCurrent(4);
		}else if (!opt && VoiceFlag != 4) { //显示滑竿 + 音量 = 恢复之前打开监听的状态
			
		}
		redraw();
	}
	
	/**
	 * 返回当前的音量标志
	 * @return
	 */
	public int getVoiceFlageCurrent(){
		return VoiceFlag;
	}
	/**
	 * 当监听的时候，进行talk，设置Talk_VoiceFlag = 1，当talk结束后恢复监听后，设置Talk_VoiceFlag = 0；
	 * @param Talk_VoiceFlag
	 */
	public void setTalkVoiceFlageCurrent(int Talk_VoiceFlag){
		this.Talk_VoiceFlag = Talk_VoiceFlag;
	}
	public int getTalkVoiceFlageCurrent(){
		return Talk_VoiceFlag;
	}
	public void surfaceCreated(SurfaceHolder holder) {
		isHide(false);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		redraw();
	}
	
	public int dip2px(float dpValue) {  
        return (int)(dpValue * scale + 0.5f);
    }
	
	public int px2dip(float pxValue) {  
        return (int) (pxValue / scale + 0.5f);
    }  
	/*
	 * 获取屏幕的宽度，高度和密度以及dp / px
	 */
	 public void getDisplayMetrics() {
  		DisplayMetrics dm = new DisplayMetrics();
  		dm = context.getApplicationContext().getResources().getDisplayMetrics();
  		int width = dm.widthPixels;
  		int height = dm.heightPixels;
  		scale = context.getResources().getDisplayMetrics().density;
  		double bb = Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2));
  		dimension = bb / (160 * dm.density);
	 }	
	double dimension = 0.00;
	float scale;
	float x;
	int stickBar_height;
	int stickBar_width;
	int stickBall_width;
	int half_stickBall_width;
	public Paint paint;
	public SurfaceHolder holder;
	public HorizontalBarListener horizontalBarListener;
	Bitmap stickBar = null;
	Bitmap stickBall = null;
	private float stickBallPointX = 0;
	public float stickBallPointY =0;
	private float stickBarPointX = 0;
	private float stickBarPointY = 0;
	int move_limit = (100 - stickBall_width) / 4;
	int move_limit_1, move_limit_2, move_limit_3, move_limit_4;
	int voice_limit_max = 0;
	int voice_init_value = 0;
	int stream_type = 7;
	int progressBarValue = 0, VoiceFlag = 4;
	int Talk_VoiceFlag = 0;
}
