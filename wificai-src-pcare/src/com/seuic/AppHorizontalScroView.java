package com.seuic;

import java.util.Timer;
import java.util.TimerTask;

import com.seuic.interfaces.CustomerInterface;
import com.seuic.util.WificarNewLayoutParams;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

@SuppressLint("HandlerLeak")
public class AppHorizontalScroView extends HorizontalScrollView{

	public static AppHorizontalScroView instance;
	public int diff_scroll = 0;//滚动条可滚动的宽度
	public int pre_x = -100;
	public static final int SCROLL_END = 10000;
	public static final int SCROLL_END_SHIXIAO = 8888;
	public AppHorizontalScroView(Context context) {
		super(context);
		instance = this;
	}

	public AppHorizontalScroView(Context context, AttributeSet attrs) {
		super(context, attrs);
		instance = this;
	}
	
	public Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SCROLL_END:
				//判断滚动的值是否满足滚动矩形，如果不满足则要调整
				if (pre_x == -100) {
					pre_x = getScrollX();
					AppLog.e("test", "pre_x = -100");
				}
				checkRange(pre_x);
				AppConnect.getInstance().callBack(CustomerInterface.MESSAGE_SCROLL_LR_FLAG);
				break;
			case SCROLL_END_SHIXIAO:
				AppLog.e("test", "chu li cuo wu");
				pre_x = getScrollX();
				checkRange(pre_x);
				AppConnect.getInstance().callBack(CustomerInterface.MESSAGE_SCROLL_LR_FLAG);
				break;
			}
			super.handleMessage(msg);
		}
		
	};
	public void init(){
		try {
			View view = (View) this.getChildAt(this.getChildCount() - 1);
			diff_scroll = view.getRight() - getWidth();//初始状态为0，没有滚动，如果滚动到右边，则等于subViewWidth - width
			int value = 102;
			int val = value / AppInforToSystem.bottom_btn_scroll_range;
			smoothScrollTo(AppInforToSystem.bottom_btn_scroll_range * (val+1), 0);
			Log.e("AppHorizontalScroView", "init");
			} catch (Exception e) {
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		try {
			switch (event.getAction()) {
			case MotionEvent.ACTION_MOVE:
				if(AppInforToSystem.bottom_btn_scroll_flag != 3){
					AppInforToSystem.bottom_btn_scroll_flag = 3;
					AppConnect.getInstance().callBack(CustomerInterface.MESSAGE_SCROLL_LR_FLAG);
					Log.e("AppHorizontalScroView", "ACTION_MOVE");
				}
				break;
			case MotionEvent.ACTION_UP:
				/*
				 * 当up的时候开启定时器，每隔10ms来测试一下，滚动的数值是否相同，
				 * 如果相同则认为滚动结束，取消定时器。
				 * 
				 */
				Log.e("AppHorizontalScroView", "ACTION_UP");
				
					pre_x = getScrollX();
						init();	
			
				if (showTimer != null) {
					showTimer.cancel();
					showTimer = null;
				}
				
				showTimer = new Timer();
				showTimer.schedule(new ShowTimerTask(), diff_t, diff_t);
				
				break;
			}
		} catch (Exception e) {
		}
		return super.onTouchEvent(event);
	}

	//拦截事件
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (AppInforToSystem.main_touch_flag == 0) {
			return false;
		}else {
			return super.onInterceptTouchEvent(ev);
		}
	}
	
	class ShowTimerTask extends TimerTask{
		@Override
		public void run() {
			if (pre_x != getScrollX()) {
				pre_x = getScrollX();
			}else{
				if (showTimer != null) {
					showTimer.cancel();
					showTimer = null;
				}
				handler.sendEmptyMessage(SCROLL_END);
			}
		}
	}
	
	public void checkRange(int value){
		try {
			if (value <= AppInforToSystem.bottom_btn_scroll_range / 2) {
				value = 0;
				smoothScrollTo(0, 0);
				AppInforToSystem.bottom_btn_scroll_flag = 2;
			}else if (value >= (diff_scroll - AppInforToSystem.bottom_btn_scroll_range / 2)) {
				smoothScrollTo(diff_scroll, 0);
				AppInforToSystem.bottom_btn_scroll_flag = 1;
			}else {
				int val = value / AppInforToSystem.bottom_btn_scroll_range;
				int diff = value % AppInforToSystem.bottom_btn_scroll_range;
				if (diff < AppInforToSystem.bottom_btn_scroll_range /2) {
					smoothScrollTo(AppInforToSystem.bottom_btn_scroll_range * val, 0);
				}else {
					smoothScrollTo(AppInforToSystem.bottom_btn_scroll_range * (val+1), 0);
				}
				AppInforToSystem.bottom_btn_scroll_flag = 0;
			}
			this.computeScroll();
		} catch (Exception e) {
			AppInforToSystem.bottom_btn_scroll_flag = 0;
		}
	}
	
	/**
	 * 获取最左边按钮处于的索引值
	 */
	public int getLeftId(){
		int val = getScrollX() / AppInforToSystem.bottom_btn_scroll_range;
		return val;
	}
	public Timer showTimer;
	public int diff_t = 10;//ms
	public boolean isScrool = true;
}
