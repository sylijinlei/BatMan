package com.seuic;

import com.batman.package1.R;
import com.batman.package1.WificarNew;
import com.seuic.util.WificarNewLayoutParams;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class AppLR_MoveCar extends View {

	public static AppLR_MoveCar appLR_MoveCar;
	public Context context;
	public int stickBar_width = 180;
	public int stickBall_width = 50;
	public int statkBall_halfWidth;
	public int stickBar_halfWidth = 90;
	public int stickBar_maxWidth;
	public int stickBall_Local;
	public Bitmap stickBar;
	public Bitmap stickBall;
	public double local_1, local_2, local_3, local_4, local_5;
	public Paint mPaint;
	int isHide = 0;
	int diff_x;
	int diff_y;
	public AppLR_MoveCarListener listener = null; // 事件回调接口
	public static final int ACTION_RUDDER = 1, ACTION_ATTACK_DEVICEMOVE = 2,
			ACTION_STOP = 3, ACTION_ATTACK_CAMERAMOVE = 4;

	public AppLR_MoveCar getAppLR_Move() {
		return appLR_MoveCar;
	}

	// 回调接口
	public interface AppLR_MoveCarListener {
		void onSteeringWheelChanged(int action, int angle);
	}

	// 设置回调接口
	public void setAppLR_MoveCarListener(AppLR_MoveCarListener rockerListener) {
		listener = rockerListener;
	}

	public AppLR_MoveCar(Context context) {
		super(context);
		this.context = context;
		appLR_MoveCar = this;
		stickBar_width = WificarNewLayoutParams.Car_Move_Progress_Width;
		stickBall_width = WificarNewLayoutParams.Car_Move_Progress_Height;
		stickBall_Local = stickBar_halfWidth = (stickBar_width - stickBall_width) / 2;
		stickBar_maxWidth = stickBar_width - stickBall_width;
		statkBall_halfWidth = stickBall_width / 2;
		local_1 = stickBar_width - stickBall_width - 20;
		local_2 = stickBar_width*0.11;//0923 add 向左边滑动时，滑到的范围小于此则速度为H
		local_3 = stickBar_halfWidth - statkBall_halfWidth+15;//0923 add 向左边滑动时，当在 local_2和local_3范围内，则速度为L
		local_4 = stickBar_halfWidth + statkBall_halfWidth-5;//0923 add 向右边滑动时，当在 local_4和local_5范围内，则速度为L 
		local_5 = stickBar_maxWidth - local_2; //0923 add 向右边滑动时，滑到的范围大于此则速度为H
		mPaint = new Paint();
		mPaint.setFilterBitmap(true);
		mPaint.setAntiAlias(true);// 抗锯齿
		stickBar = BitmapFactory.decodeResource(getResources(),
				R.drawable.lr_bar);
		stickBar = Bitmap.createScaledBitmap(stickBar, stickBar_width,
				stickBall_width, true);
		Drawable bkDrawable = new BitmapDrawable(stickBar);
		this.setBackgroundDrawable(bkDrawable);
		stickBall = BitmapFactory.decodeResource(getResources(),
				R.drawable.slider_round);
		stickBall = Bitmap.createScaledBitmap(stickBall, stickBall_width,
				stickBall_width, true);
		diff_x = WificarNewLayoutParams.UD_Diff_x;
		diff_y = WificarNewLayoutParams.UD_Diff_y - statkBall_halfWidth;
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

	public int flag = 0;
	public int OutLeftCircle = 1;
	public int OutLocalCircle = 1;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		try {
			
			if (isHide == 0 && !WificarNew.isDrawSlider && WificarNew.isTop) {
				if (WificarNew.isDoorOpen != true) {
				int pointerIndex = ((event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT);
				int pointerId = event.getPointerId(pointerIndex);
				int action = event.getActionMasked();
				int pointerCount = event.getPointerCount();
				Log.i("AppLR_MoveCar", "pointerCount:"+pointerCount+", pointerIndex:"+pointerIndex+
						", pointerId:"+pointerId+", action:"+action);
				for (int i = 0; i < pointerCount; i++) {
					int id = event.getPointerId(i);
					switch (action) {
					case MotionEvent.ACTION_DOWN:
						Log.e("onTouchEvent", "---ACTION_DOWN---");
						OutLocalCircle = 0;
					case MotionEvent.ACTION_MOVE:
						Log.e("onTouchEvent", "LR---ACTION_MOVE---");
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
							Log.e("onTouchEvent", "---ACTION_MOVE--->id = "+id);
							stickBall_Local = mx - statkBall_halfWidth;
							Check();
						} else if (id == 1 && OutLeftCircle == 0) {
							Log.e("onTouchEvent", "---ACTION_MOVE--->id = "+id);
							mx = mx + diff_x;
							my = my + diff_y;
							if (my < 0) {
								my = 0;
							}
							if (my > stickBar_width) {
								my = stickBar_width;
							}
							AppUD_MoveCar.appUD_MoveCar.stickBar_Local = my;
							AppUD_MoveCar.appUD_MoveCar.check();
						}
						break;
					case MotionEvent.ACTION_UP:
						Log.e("onTouchEvent", "LR---ACTION_UP---");
						if (pointerCount == 1) {
							OutLocalCircle = 1;
							init();
							OutLeftCircle = 1;
							AppUD_MoveCar.appUD_MoveCar.init();
						}
						break;
					case MotionEvent.ACTION_POINTER_1_DOWN:
						if (pointerId == id) {
							Log.e("onTouchEvent", "LR---ACTION_POINTER_1_DOWN---");
							int x1 = (int) event.getX(id);
							int y1 = (int) event.getY(id);
							if (id == 0) {
								Log.e("onTouchEvent", "---ACTION_POINTER_1_DOWN---id = "+id);
								Log.e("onTouchEvent", "id=0时  getX:"+x1+"  getY:"+y1);
								if (x1 > 0 && x1 < stickBar_width && y1 > 0
										&& y1 < stickBall_width+50) {
									stickBall_Local = x1 - statkBall_halfWidth;//
									Check();
									OutLocalCircle = 0;
								} else {
									OutLocalCircle = 1;
								}

							} else if (id == 1) {
								Log.e("onTouchEvent", "LR---ACTION_POINTER_1_DOWN---id = "+id);
								Log.e("onTouchEvent", "id=1时 getX:"+x1+"  getY:"+y1);
								x1 = x1 + diff_x + 5;
								y1 = y1 + diff_y;
								if (x1 > 0 && x1 <= stickBall_width && y1 > 0
										&& y1 <= stickBar_width) {
									AppUD_MoveCar.appUD_MoveCar.stickBar_Local = y1;
									AppUD_MoveCar.appUD_MoveCar.check();
									OutLeftCircle = 0;
								} else {
									OutLeftCircle = 1;
								}
							}
						}
						break;
					case MotionEvent.ACTION_POINTER_1_UP:
						if (pointerId == id) {
							Log.e("onTouchEvent", "LR---ACTION_POINTER_1_UP---");
							if (id == 0) // 为了兼容不同的手机的屏幕，有的手机当第一个点松开口，就会进入ACTION_UP，有的手机不会
							{
								Log.e("onTouchEvent", "LR---ACTION_POINTER_1_UP---id = "+id);
								OutLocalCircle = 1;
								init();
							} else if (id == 1) {
								Log.e("onTouchEvent", "LR---ACTION_POINTER_1_UP---id = "+id);
								OutLeftCircle = 1;
								AppUD_MoveCar.appUD_MoveCar.init();
							}
						}
						break;
					}
				}
				Thread.sleep(30);
			} else {
				Thread.sleep(200);
				return false;
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public void Check() {
		if (stickBall_Local > stickBar_maxWidth) {
			stickBall_Local = stickBar_maxWidth;
		} else if (stickBall_Local < 0) {
			stickBall_Local = 0;
		}
		if (stickBall_Local <= local_3) {
			if (flag != 1 && stickBall_Local <= local_2) {// 最左边
				flag = 1;
				listener.onSteeringWheelChanged(ACTION_RUDDER, flag);
			} else if (flag != 2 && stickBall_Local > local_2) {
				flag = 2;
				listener.onSteeringWheelChanged(ACTION_RUDDER, flag);
			}
		} else if (stickBall_Local >= local_4) {
			if (flag != 3 && stickBall_Local >= local_5) {
				flag = 3;
				listener.onSteeringWheelChanged(ACTION_RUDDER, flag);
			} else if (flag != 4 && stickBall_Local < local_5) {// modified local_4--->local_5
				flag = 4;
				listener.onSteeringWheelChanged(ACTION_RUDDER, flag);
			}
		} else {
			flag = 0;
			listener.onSteeringWheelChanged(ACTION_STOP, flag);
		}
		Canvas_OK();
	}

	public void Canvas_OK() {
		invalidate();
	}

	public void init() {
		flag = 0;
		stickBall_Local = stickBar_halfWidth;
		listener.onSteeringWheelChanged(ACTION_STOP, flag);
		requestLayout();
		Canvas_OK();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (stickBall != null && isHide == 0) {
//			canvas.drawBitmap(stickBar, 0, 0, mPaint);
			canvas.drawBitmap(stickBall, stickBall_Local, 0, mPaint);
		}
		super.onDraw(canvas);
	}

}
