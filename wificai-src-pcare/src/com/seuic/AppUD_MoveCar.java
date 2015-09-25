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

import com.batman.package1.R;
import com.batman.package1.WificarNew;
import com.seuic.util.WificarNewLayoutParams;

public class AppUD_MoveCar extends View {

	public static AppUD_MoveCar appUD_MoveCar = null;
	public Context context;
	public int stickBar_height;
	public int stickBall_width;
	public int statkBall_halfWidth;
	public int stickBar_halfHeight;
	public int stickBar_maxHeight;
	public int stickBar_Local;
	public Bitmap stickBall;
	public Paint mPaint;
	int isHide = 0;
	int diff_x;
	int diff_y;
	int UD_Jet_diff_x;
	int UD_Jet_diff_y;
	int JetBall_width;
	public AppUD_MoveCarListener listener = null; // 事件回调接口
	public static final int ACTION_RUDDER = 1, ACTION_ATTACK_DEVICEMOVE = 2,
			ACTION_STOP = 3, ACTION_ATTACK_CAMERAMOVE = 4;

	// 回调接口
	public interface AppUD_MoveCarListener {
		void onSteeringWheelChanged(int action, int flag);
	}

	// 设置回调接口
	public void setAppUD_MoveCarListener(AppUD_MoveCarListener rockerListener) {
		listener = rockerListener;
	}

	public AppUD_MoveCar getAppUD_MoveCar() {
		return appUD_MoveCar;
	}

	public AppUD_MoveCar(Context context) {
		super(context);
		this.context = context;
		appUD_MoveCar = this;
		stickBar_height = WificarNewLayoutParams.Car_Move_Progress_Width;
		stickBall_width = WificarNewLayoutParams.Car_Move_Progress_Height;
		stickBar_Local = stickBar_halfHeight = (stickBar_height - stickBall_width) / 2;
		stickBar_maxHeight = stickBar_height - stickBall_width;
		statkBall_halfWidth = stickBall_width / 2;
		this.setBackgroundResource(R.drawable.updown_bar);
		mPaint = new Paint();
		mPaint.setFilterBitmap(true);
		mPaint.setAntiAlias(true);// 抗锯齿
		stickBall = BitmapFactory.decodeResource(getResources(), R.drawable.slider_round);
		stickBall = Bitmap.createScaledBitmap(stickBall, stickBall_width, stickBall_width, true);

		diff_x = WificarNewLayoutParams.UD_Diff_x;
		diff_y = WificarNewLayoutParams.UD_Diff_y;
		
		UD_Jet_diff_x = WificarNewLayoutParams.UD_Jet_Diff_x;
		UD_Jet_diff_y = WificarNewLayoutParams.UD_Jet_Diff_y;
		JetBall_width = WificarNewLayoutParams.Jet_Move_Height;
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
	int x0 = 0;
	int y0 = 0;
	public static boolean isJetDo;
	private boolean isValide;
	private String TAG = "AppUD_MoveCar";
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		try {
			if(WificarNew.isDoorOpen != true) {
			if (isHide == 0 && !WificarNew.isDrawSlider && WificarNew.isTop) {
				
				int pointerIndex = ((event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT);
				int pointerId = event.getPointerId(pointerIndex);//对于每个触控的点的细节，我们可以通过一个循环执行getPointerId方法获取索引
				int action = event.getActionMasked();//获取触控动作比如ACTION_DOWN
				int pointerCount = event.getPointerCount();//来获取当前触控点的个数
				Log.i("AppUD_MoveCar", "pointerCount:"+pointerCount+", pointerIndex:"+pointerIndex+
						", pointerId:"+pointerId+", action:"+action);
				if(pointerCount>=3){//触控点最多为2个
					Log.e(TAG, "触控点大于3");
				}else{
					for (int i = 0; i < pointerCount; i++) {
						int id = event.getPointerId(i);
						switch (action) {
						case MotionEvent.ACTION_DOWN:
							Log.e("onTouchEvent", "---ACTION_DOWN---");
							if (pointerCount == 3) {
								break;
							}else{
								OutLocalCircle = 0;
								isValide = true;
							}
							
						case MotionEvent.ACTION_MOVE:
							int mx = 0;
							int my = 0;
							if (event.getPointerCount() == 1) {
								mx = (int) event.getX();
								my = (int) event.getY();
							} else if (event.getPointerCount() == 2) {
								mx = (int) event.getX(id);
								my = (int) event.getY(id);
							}
							if (id == 0 && OutLocalCircle == 0) {
							Log.e("onTouchEvent", "UD---ACTION_MOVE--->id = "+id);
								stickBar_Local = my - statkBall_halfWidth;
								check();
								
								
							} else if (id == 1 && OutLeftCircle == 0) {
								Log.e("onTouchEvent", "UD===ACTION_MOVE===>id = "+id);
								x0 = mx; y0 = my;
								if (my > 0) {
									mx = mx - diff_x - statkBall_halfWidth;
									my = my - diff_y;
									if (mx < 0) {
										mx = 0;
									}
									if (mx > stickBar_height) {
										mx = stickBar_height;
									}
									AppLR_MoveCar.appLR_MoveCar.stickBall_Local = mx;
									AppLR_MoveCar.appLR_MoveCar.Check();
								}
								if(WificarNew.isLRHide!=true){
								if (y0 < 0) {
									if (isJetDo == false)
									{
										mx = x0 - UD_Jet_diff_x;
										my = y0 + UD_Jet_diff_y;
										if (my < 0) {
											my=0;
										}
										if (my > AppJet_MoveCar.stickBar_maxHeight) {
											my = AppJet_MoveCar.stickBar_maxHeight;
										}	
										AppJet_MoveCar.appJet_Movebar.stickBall_Local = my;
										AppJet_MoveCar.appJet_Movebar.Check();
									}
									
									if (my >= 0 && my <= 5) {
										isJetDo = true;
										AppJet_MoveCar.appJet_Movebar.isHightLight = true;
									}
									
								}
								}
							}
							break;
						case MotionEvent.ACTION_UP:
							Log.e("onTouchEvent", "UD---ACTION_UP---pointerCount="+ pointerCount);
							if (pointerCount == 1) {
								if(WificarNew.isJet!=true){
									
									if (isJetDo != true) {
										AppJet_MoveCar.appJet_Movebar.init();
									} else if(OutLocalCircle == 1) {
										AppJet_MoveCar.appJet_Movebar.do_Jet();
									}
								}
								
								OutLocalCircle = 0;
								init();
								OutLeftCircle = 1;
								AppLR_MoveCar.appLR_MoveCar.init();
							}
							break;
						case MotionEvent.ACTION_POINTER_1_DOWN:
							if (pointerId == id) {
								int x1 = (int)event.getX(id);
								int y1 = (int)event.getY(id);
								Log.e("onTouchEvent", "UD---ACTION_POINTER_1_DOWN--->x0 = "+x1+" y0="+y1);
								if (id == 0) {
									Log.e(TAG, "MotionEvent.ACTION_POINTER_1_DOWN id --------0");
									Log.e("onTouchEvent", "UD---ACTION_POINTER_1_DOWN--->id = "+id);
									Log.e("onTouchEvent", "id=0时 getX:"+x1+"  getY:"+y1);
									if (x1 >= 0 && x1 <= stickBall_width && y1 > 0 && y1 <= stickBar_height) {
										stickBar_Local = (int)event.getY(id) - statkBall_halfWidth;
										check();
										OutLocalCircle = 0;
									}else {
										OutLocalCircle = 1;
									}
									
//									if
								} else if (id == 1) {
									Log.e(TAG, "MotionEvent.ACTION_POINTER_1_DOWN id --------1");
									Log.e("onTouchEvent", "UD---ACTION_POINTER_1_DOWN=====>id = "+id);
									
									x0=x1;
									y0=y1;
									if (y1 > 0) {
										Log.e("onTouchEvent", "---------------   y1 > 0   ----------------------");
										Log.e("onTouchEvent", "UD_LR--->x1= "+x1+" y1="+y1);
										Log.e("onTouchEvent", "UD_LR--->stickBar_height= "+stickBar_height+" stickBall_width= "+stickBall_width);
										x1 = x1 - diff_x - statkBall_halfWidth;
										y1 = y1 - diff_y;
										Log.e("onTouchEvent", "UD_LR--->x1="+x1+"  y1:"+y1);
										if (x1 > 0 && x1 <= stickBar_height && y1 > 0 && y1 <= (stickBall_width+50)) {
											AppLR_MoveCar.appLR_MoveCar.stickBall_Local = x1;
											AppLR_MoveCar.appLR_MoveCar.Check();
											OutLeftCircle = 0;
										}else {
											OutLeftCircle = 1;
										}
									}
									
									if (y0 < 0) {
										Log.e(TAG , "WificarNew.isJet:"+WificarNew.isJet);
										Log.e(TAG , "WificarNew.isLRHide:"+WificarNew.isLRHide);
										if(WificarNew.isJet!=true&&WificarNew.isRocket!=true&&WificarNew.isLRHide!=true){
											if (isJetDo == false)
											{
												x1 = x0 - UD_Jet_diff_x;
												y1 = y0 + UD_Jet_diff_y;
												Log.e("onTouchEvent", "---------------   y0 < 0   ----------------------");
												Log.e("onTouchEvent", "UD_Jet_diff_x  " +UD_Jet_diff_x+ "UD_Jet_diff_y " + UD_Jet_diff_y);
												Log.e("onTouchEvent", "UD_Jet--->x1= "+x1+" y1="+y1);
												Log.e("onTouchEvent", "UD_Jet--->sticBar_height= "+AppJet_MoveCar.stickBar_height+" JetBall_width= "+JetBall_width);
												if (x1 > - 20 && x1 <= JetBall_width + 20  && y1 > - 20 && y1 <= AppJet_MoveCar.stickBar_height + 20) {
													
													AppJet_MoveCar.appJet_Movebar.stickBall_Local = y1;
													AppJet_MoveCar.appJet_Movebar.Check();
													OutLeftCircle = 0;
												}else {
													OutLeftCircle = 1;
												}
											}
											WificarNew.isJet = false;	
										}
										
										
									}
								}else if(id ==3){
									Log.e(TAG, "============33333333333333333===================");
								}
							}
							break;
						case MotionEvent.ACTION_POINTER_1_UP:
							if (pointerId == id)
							{
								Log.e("onTouchEvent", "UD---ACTION_POINTER_1_UP--->id = "+id);
								if (id == 0)  //为了兼容不同的手机的屏幕，有的手机当第一个点松开口，就会进入ACTION_UP，有的手机不会
								{
									Log.e("onTouchEvent", "ACTION_POINTER_1_UP---id=0");
									OutLocalCircle = 1;
									init();
								}else if (id == 1) {
									if(WificarNew.isJet!=true&&WificarNew.isRocket!=true&&WificarNew.isLRHide!=true){
										if(isJetDo == true) {
											Log.e("onTouchEvent", "ACTION_POINTER_1_UP---id=1");
											AppJet_MoveCar.appJet_Movebar.do_Jet();
												Log.e("onTouchEvent", "to start keepTimer..");
										} else {
											AppJet_MoveCar.appJet_Movebar.init();
										}
									}
									
									AppLR_MoveCar.appLR_MoveCar.init();								
									OutLeftCircle = 1;
								}
							}
							break;
						}
					}
				}
				
				Thread.sleep(30);
			} else {
				Thread.sleep(200);
				return false;
			}
			}
		} catch (Exception e) {
		}
		return true;
	}

	public void Canvas_OK() {
		invalidate();
	}
	
	public void init(){
		flag = 0;
		stickBar_Local = stickBar_halfHeight;
		requestLayout();
		Canvas_OK();
		listener.onSteeringWheelChanged(ACTION_STOP, flag);
	}
	public void check(){
		if (stickBar_Local > stickBar_maxHeight) {
			stickBar_Local = stickBar_maxHeight;
		} else if (stickBar_Local < 0) {
			stickBar_Local = 0;
		}
		Log.e("check","flag ---"+flag);
		if (flag != 1
				&& stickBar_Local <= stickBar_halfHeight) {
			flag = 1;
			listener.onSteeringWheelChanged(ACTION_RUDDER,
					flag);
		} else if (flag != 2
				&& stickBar_Local > stickBar_halfHeight) {
			flag = 2;
			listener.onSteeringWheelChanged(ACTION_RUDDER,
					flag);
		}
		Canvas_OK();
	}
	@Override
	protected void onDraw(Canvas canvas) {
		if (stickBall != null && isHide == 0) {
			canvas.drawBitmap(stickBall, 0, stickBar_Local, mPaint);
		}
		super.onDraw(canvas);
	}
}
