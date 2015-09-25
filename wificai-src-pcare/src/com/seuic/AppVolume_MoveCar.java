package com.seuic;

/**
 * public static int Car_Move_Progress_Width = 180;
 public static int Car_Move_Progress_Height = 50;

 public static int Car_Compont_UD_Marge_L = 20;
 public static int Car_Compont_LR_Marge_R = 20;
 public static int Car_Compont_UD_Marge_D = 80;
 public static int Car_Compont_LR_Marge_D = 80;

 x2_min = getX() - (Screen_width - Car_Compont_UD_Marge_L - Car_Move_Progress_Width - Car_Compont_LR_Marge_R);
 x2_max = getX() - (Screen_width - Car_Compont_UD_Marge_L - Car_Move_Progress_Width - Car_Compont_LR_Marge_R) + Car_Move_Progress_Width;
 y2_min = getY() - (Car_Move_Progress_Width + Car_Compont_UD_Marge_D) - (Car_Move_Progress_Height + Car_Compont_LR_Marge_D)
 y2_max = getY() - (Car_Move_Progress_Width + Car_Compont_UD_Marge_D) - (Car_Move_Progress_Height + Car_Compont_LR_Marge_D) + Car_Move_Progress_Height
 */
import com.batman.package1.R;
import com.batman.package1.WificarNew;
import com.seuic.function.AppGSenserFunction;
import com.seuic.function.AppListenAudioFunction;
import com.seuic.util.WificarNewLayoutParams;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

public class AppVolume_MoveCar extends View {
	public WificarNewLayoutParams wificarNewLayoutParams;
	public static AppVolume_MoveCar appVolume_MoveCar = null;
	public Context context;
	public int stickBar_height = 180;
	public int stickBar_width;
	public int stickBall_width = 50;
	public int statkBall_halfWidth;
	public int stickBar_halfHeight = 90;
	public int stickBar_maxHeight;
	public int stickBar_Local;
	public Bitmap stickBall;
	public Paint mPaint;
	int isHide = 0;
	public AppVolume_MoveCarListener listener = null; // 事件回调接口
	public static final int ACTION_RUDDER = 1, ACTION_ATTACK_DEVICEMOVE = 2,
			ACTION_STOP = 3, ACTION_ATTACK_CAMERAMOVE = 4;

	public AppVoiceManage appVoiceManage;
	int voice_limit_max = 0;
	int voice_init_value = 0;
	int move_limit = (100 - stickBall_width) / 4;
	int move_limit_1, move_limit_2, move_limit_3, move_limit_4;
	int stream_type = 7;
	int VoiceFlag = 4;
	int Talk_VoiceFlag = 0;
	int x = 0,y=0;
	public static boolean isAudioEnable = false;
	boolean isBtnOff = true;
	// 回调接口
	public interface AppVolume_MoveCarListener {
		void onVoiceFlagChanged(int voiceFlag);
	}

	// 设置回调接口
	public void setAppVolume_MoveCarListener(AppVolume_MoveCarListener rockerListener) {
		listener = rockerListener;
	}

	public AppVolume_MoveCar getAppVolume_MoveCar() {
		return appVolume_MoveCar;
	}

	public AppVolume_MoveCar(Context context) {
		super(context);
		this.context = context;
		appVolume_MoveCar = this;
		//里面滑轮的大小
		stickBar_height = WificarNewLayoutParams.Volume_Move_Width;
		stickBar_width = WificarNewLayoutParams.Volume_Move_Height;
		stickBall_width = stickBar_width*2/3;
		//位置
		stickBar_Local = stickBar_maxHeight = stickBar_height - stickBall_width;//上下的位置
		statkBall_halfWidth = stickBall_width/2;//滑动效果
		this.setBackgroundResource(R.drawable.volume_bar);
		mPaint = new Paint();
		mPaint.setFilterBitmap(true);
		mPaint.setAntiAlias(true);// 抗锯齿
		stickBall = BitmapFactory.decodeResource(getResources(), R.drawable.volume_point);
		stickBall = Bitmap.createScaledBitmap(stickBall, stickBall_width, stickBall_width, true);
		Log.e("AppVolume_MoveCar", "stickBar_height:"+stickBar_height);
		Log.e("AppVolume_MoveCar", "stickBar_width:"+stickBar_width);
		appVoiceManage = new AppVoiceManage(context);
		voice_limit_max = appVoiceManage.getVoiceLimits(AppVoiceManage.MUSIC_VOICE_MAX) / 4;  //  /4
		voice_init_value = appVoiceManage.getVoiceLimits(AppVoiceManage.MUSIC_VOICE_CURRENT);
		move_limit = stickBar_height -stickBall_width;
		move_limit_1 = move_limit/4;
		move_limit_2 = move_limit/4*2;
		move_limit_3 = move_limit/4*3;
		move_limit_4 = move_limit/4*4;
	}

	// 设置是否隐藏
	public void Hided(int opt) {
		isHide = opt;
		if (opt == 1) {
			getBackground().setAlpha(0);
		} else {
			getBackground().setAlpha(255);
		}
		Canvas_OK();
	}

	public void setHided(int opt) {
		isHide = opt;
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

	/**
	 * 返回圆盘是否隐藏
	 * 
	 * @return
	 */
	public int getIsHided() {
		return isHide;
	}

	public int flag = 0;// 1:up 2:down
	public int OutLeftCircle = 1; // 用来屏蔽点击外围
	public int OutLocalCircle = 1;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		try {
	    		if (AppInforToCustom.isGSensorControl != true || AppGSenserFunction.isStop == true) {
					if(WificarNew.isDooring != true){
					y = (int)event.getY();
					stickBar_Local = y;//上下移动
					check();
						if(VoiceFlag != 0 && y >= 0 && y < move_limit_1){
							setVoiceCurrent(0);
							VoiceFlag = 0;
						}else if(VoiceFlag != 1 && y >= move_limit_1 && y < move_limit_2){
							VoiceFlag = 1;
							setVoiceCurrent(1);
						}else if(VoiceFlag != 2 && y >= move_limit_2 && y < move_limit_3){
							VoiceFlag = 2;
							setVoiceCurrent(2);
						}else if(VoiceFlag != 3 && y >= move_limit_3 && y < move_limit_4){
							VoiceFlag = 3;
							setVoiceCurrent(3);					
						}else if(VoiceFlag != 4 && y >= stickBar_maxHeight){
							VoiceFlag = 4;
							setVoiceCurrent(4);
						}
//					}
					
					if(y < stickBar_maxHeight && isBtnOff == true){ //  -5
						isBtnOff = false;
						isAudioEnable = true;
						AppListenAudioFunction.getAppAudioFunctionInstance().AudioEnable();
						if (WificarNew.instance.wificarNewLayoutParams.screenSize < 5.8) {
							WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_on_phone);
						} else {
							WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_on_ipad);
						}
					}else if (y >= stickBar_maxHeight){
						isBtnOff = true;
						isAudioEnable = false;
						AppListenAudioFunction.getAppAudioFunctionInstance().AudioDisable();
						if (WificarNew.instance.wificarNewLayoutParams.screenSize < 5.8) {
							WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_off_phone);
						} else {
							WificarNew.instance.Mic_Btn.setBackgroundResource(R.drawable.volume_off_ipad);
						}
					}
					}
	    		}
		} catch (Exception e) {
			
		}
    	
		return true;
	}
	
	public void setVoiceCurrent(int flag){
		listener.onVoiceFlagChanged(flag);
		switch (flag) {
		case 0:
			Log.e("listenvoice", "*4");
			appVoiceManage.setVoiceValue(stream_type, voice_limit_max*8, 0); 
			break;
		case 1:
			Log.e("listenvoice", "*3");
			appVoiceManage.setVoiceValue(stream_type, voice_limit_max*6, 0);
			break;
		case 2:
			Log.e("listenvoice", "*2");
			appVoiceManage.setVoiceValue(stream_type, voice_limit_max*4, 0);
			break;
		case 3:
			Log.e("listenvoice", "*1");
			appVoiceManage.setVoiceValue(stream_type, voice_limit_max*2, 0);
			break;
		case 6: //恢复之前的声音
			appVoiceManage.setVoiceValue(stream_type, voice_init_value, 0);
			break;
		}
	}

	public void Canvas_OK() {
		invalidate();
	}
	
	public void init(){
		stickBar_Local = stickBar_maxHeight;
		requestLayout();
		Canvas_OK();
	}
	public void check(){
		if (stickBar_Local > stickBar_maxHeight) {
			stickBar_Local = stickBar_maxHeight;
		} else if (stickBar_Local < 0) {
			stickBar_Local = 0;
		}
		Canvas_OK();
	}
	@Override
	protected void onDraw(Canvas canvas) {
		if (stickBall != null && isHide == 0 && Talk_VoiceFlag == 0) {
			canvas.drawBitmap(stickBall, stickBall_width/6+wificarNewLayoutParams.dip2px(2), stickBar_Local, mPaint);
		}
		super.onDraw(canvas);
	}
}
