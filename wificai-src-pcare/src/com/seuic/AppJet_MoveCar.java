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
import java.util.Timer;
import java.util.TimerTask;

import com.batman.package1.R;
import com.batman.package1.WificarNew;
import com.seuic.function.AppGSenserFunction;
import com.seuic.util.WificarNewLayoutParams;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class AppJet_MoveCar extends View {

	public static AppJet_MoveCar appJet_Movebar = null;
	public Context context;
	public static int stickBar_height = 180;
	public int stickBall_width = 50;
	public static int statkBall_halfWidth;
	public int stickBar_halfHeight = 90;
	public static int stickBar_maxHeight;
	public int stickBar_Local;
	public int stickBall_Local;
	public Paint mPaint;
	int isHide = 0;
	int UD_Jet_diff_x;
	int UD_Jet_diff_y;
	public AppJet_MoveCarListener listener = null; // 事件回调接口
	private Bitmap stickBar;
	public Bitmap stickBall;
	private Bitmap stickBar0;
	private Bitmap stickBall0;
	public static final int ACTION_RUDDER = 1, ACTION_ATTACK_DEVICEMOVE = 2,
			ACTION_STOP = 3, ACTION_ATTACK_CAMERAMOVE = 4;

	// 回调接口
	public interface AppJet_MoveCarListener {
		void onSteeringWheelChanged(int action, int flag);
	}

	// 设置回调接口
	public void setAppJet_MoveCarListener(AppJet_MoveCarListener rockerListener) {
		listener = rockerListener;
	}

	public AppJet_MoveCar getAppJet_MoveCar() {
		return appJet_Movebar;
	}

	public AppJet_MoveCar(Context context) {
		super(context);
		this.context = context;
		appJet_Movebar = this;
		//里面滑轮的大小
		stickBar_height = WificarNewLayoutParams.Jet_Move_Width;
		stickBall_width = WificarNewLayoutParams.Jet_Move_Height;
		//位置
		stickBall_Local = (stickBar_height - stickBall_width);
		stickBar_maxHeight = stickBar_height - stickBall_width;
		statkBall_halfWidth = stickBall_width / 2;
		
		mPaint = new Paint();
		mPaint.setFilterBitmap(true);
		mPaint.setAntiAlias(true);// 抗锯齿
			this.setBackgroundResource(R.drawable.jet_bar_phone);
		
		stickBall = BitmapFactory.decodeResource(getResources(), R.drawable.jet_point);
		stickBall = Bitmap.createScaledBitmap(stickBall, stickBall_width, stickBall_width, true);
		
		stickBall0 = BitmapFactory.decodeResource(getResources(), R.drawable.jet_point_off);
		stickBall0 = Bitmap.createScaledBitmap(stickBall, stickBall_width, stickBall_width, true);
		UD_Jet_diff_x = WificarNewLayoutParams.UD_Jet_Diff_x;
		UD_Jet_diff_y = WificarNewLayoutParams.UD_Jet_Diff_y;
		
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
	public static boolean isJetDo;
	public static boolean isHightLight = false;
	int mx = 0;
	int my = 0;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		try{	
			Log.e("Jet", "WificarNew.isJet-------:"+WificarNew.isJet);
			Log.e("Jet", "WificarNew.isRocket:"+WificarNew.isRocket);
			if(WificarNew.isJet!=true&&WificarNew.isRocket!=true&&WificarNew.isLRHide!=true){
				Log.e("AppJet_MoveCar", "WificarNew.isRocket:"+WificarNew.isRocket);
				switch (event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					break;
				case MotionEvent.ACTION_MOVE:
					Log.e("Jet", "ACTION_MOVE");
					mx = (int)event.getX();
					my = (int)event.getY();
					Log.e("onTouchEvent", "Jet---ACTION_MOVE");
					if (AppGSenserFunction.jet_G_Enable == true && AppInforToCustom.isGSensorControl == true) 
					{
						
						Log.e("Jet", "mx = "+mx+" my = "+my);
						OutLocalCircle = 0;
						
						if (isJetDo == false)
						{
							stickBall_Local = my;
							if (my >= -10 && my < statkBall_halfWidth && mx >= -stickBar_halfHeight && mx <= stickBall_width + statkBall_halfWidth)
							{
								isJetDo = true;
								isHightLight = true;
								stickBall_Local = 0;
							}	
							Check();
						}
						
					}else {
						OutLocalCircle = 1;
					}
					
					break;
				case MotionEvent.ACTION_UP:
					Log.e("Jet", "ACTION_UP");
					if (OutLocalCircle == 0) {
						if (isJetDo != true) {
							init();
						}else{
							do_Jet();
						}
					}else if(OutLocalCircle == 1){
						if (isJetDo != true) {
							init();
						}else{
							do_Jet();
						}
					}
					
					break;
				default:
					break;
				}
			}
		
		} catch (Exception e) {
		}
		return true;
	}

	public  void Canvas_OK() {
		invalidate();
	}
	
	public void init(){
		flag = 0;
		stickBall_Local = stickBar_maxHeight;
		requestLayout();
		listener.onSteeringWheelChanged(ACTION_STOP, flag);
		Canvas_OK();
	}
	public void Check(){
		if (stickBall_Local > stickBar_maxHeight) {
			stickBall_Local = stickBar_maxHeight;//如果大于就等于最大高度
		} else if (stickBall_Local < 0) {
			stickBall_Local = 0;
		}	
		Canvas_OK();
	}
	
	public void do_Jet() {
		if(flag !=1 && !AppInforToCustom.isGSensorControl){
			flag = 1;
			listener.onSteeringWheelChanged(ACTION_RUDDER,
					flag);
		}
		
		if (flag != 2 && AppInforToCustom.isGSensorControl)
		{
			flag = 2;
			listener.onSteeringWheelChanged(ACTION_RUDDER,
					flag);
		}
	}
	@Override
	protected void onDraw(Canvas canvas) {
		if (stickBall != null && isHide == 0) {
			if (isHightLight == true) {
				canvas.drawBitmap(stickBall, 0, stickBall_Local, mPaint);
				if (WificarNew.instance.wificarNewLayoutParams.screenSize < 5.8) {
					WificarNew.Jetpower_Btn.setBackgroundResource(R.drawable.jetpower_on_phone);
				}else{
					WificarNew.Jetpower_Btn.setBackgroundResource(R.drawable.jetpower_on);
				}
			}else if(isHightLight == false) {
				canvas.drawBitmap(stickBall0, 0, stickBall_Local, mPaint);
				if (WificarNew.instance.wificarNewLayoutParams.screenSize < 5.8) {
					WificarNew.Jetpower_Btn.setBackgroundResource(R.drawable.jetpower_off_phone);
				}else{
					WificarNew.Jetpower_Btn.setBackgroundResource(R.drawable.jetpower_off);
				}
				
			}
		}
		super.onDraw(canvas);
	}
}
