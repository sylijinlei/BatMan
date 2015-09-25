package com.seuic;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class PanelBom extends LinearLayout implements GestureDetector.OnGestureListener{

	GestureDetector mGesture = null;
	private boolean isScrolling = false;
	private int MAX_HEIGHT = 80;//拖动的最大高度,当前布局位于父布局下面-80位置
	private double percent = 0.14872798;
	private float[] local = new float[2];
	private float mScrollX; // 滑块滑动距离
	public OnPanelListener onPanelListener;
	Context context;
	
	private enum State{
		OPENED,
		OPENGING,
		CLOSEED,
		CLOSING
	}
	private State state;
	/**
     * Callback invoked when the panel is opened/closed.
     */
    public interface OnPanelListener {
        /**
         * Invoked when the panel becomes fully closed.
         */
        public void onPanelClosed();
        /**
         * Invoked when the panel becomes fully opened.
         */
        public void onPanelOpened();
        public void onScrollStarted();
        public void onScrollEnd();
    }

	public void setOnPanelListener(OnPanelListener opListener){
		onPanelListener = opListener;
	}
	
	public PanelBom(Context context) {
		super(context);
		setOrientation(VERTICAL);
		this.context = context;
	}
	public PanelBom(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(VERTICAL);
		this.context = context;
	}
	//初始化一些参数
	public void init(int max_height, int width){
		mGesture = new GestureDetector(this);
		mGesture.setIsLongpressEnabled(false);
		MAX_HEIGHT = max_height;
		local[0] = (float)(width - width * percent) / 2;
		local[1] = (float)(width + width * percent) / 2;
	}
	
	public void addHandler(int height){
		LinearLayout.LayoutParams handlerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setBackgroundColor(12454545);
		this.addView(linearLayout, handlerParams);
	}
	public boolean lanjie = true;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (MotionEvent.ACTION_DOWN == event.getAction()) {
			float x = event.getX();
			if (x < local[0] || x > local[1]) {
				AppLog.e("out round");
				lanjie = false;
				return false;
			}else {
				lanjie = true;
			}
		}
		if (MotionEvent.ACTION_UP == event.getAction() && isScrolling) {
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)getLayoutParams();
			// 缩回去
			if (layoutParams.bottomMargin < -MAX_HEIGHT / 2) {
				new AsynMove().execute(-30);//负--往下
				if (state != State.CLOSING) {
					state = State.CLOSING; 
					AppLog.e("tag", "CLOSING");
				}
			} else {
				new AsynMove().execute(30);//正--往上
				if (state != State.OPENGING) {
					state = State.OPENGING; 
					AppLog.e("tag", "OPENGING");
				}
			}
		}
		return mGesture.onTouchEvent(event);
	}
	//Touch down时触发 
	@Override
	public boolean onDown(MotionEvent e) {
		AppLog.e("tag", "onDown");
		if (lanjie) {
			mScrollX = 0;
			isScrolling = false;
		}
		return true;
	}
	//Touch了还没有滑动时触发 
	@Override
	public void onShowPress(MotionEvent e) {
		
	}
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		AppLog.e("onSingleTapUp  "+AppInforToSystem.bottom_btn_scroll_flag);
		if (lanjie) {
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getLayoutParams();
			// 说明在上面，要往下
			onPanelListener.onScrollStarted();
			if (layoutParams.bottomMargin >= 0) {
				state = State.CLOSING;
				new AsynMove().execute(-30);//负--往下
			} else {
				state = State.OPENGING;
				new AsynMove().execute(30);//正--往上
			}
		}else if(AppInforToSystem.bottom_btn_scroll_flag == 3){
		}
		return true;
	}
	//Touch了滑动时触发
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		if (lanjie) {
			mScrollX += distanceY;
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)getLayoutParams();
			if (!isScrolling) {
				if (layoutParams.bottomMargin < 0 && mScrollX > 5) {
					isScrolling = true;
					onPanelListener.onScrollStarted();
					if (state == State.CLOSEED) {
						state = State.OPENGING;
					}
					AppLog.e("tag", "onScrollStarted_1");
				}else if(layoutParams.bottomMargin >= 0){
					isScrolling = true;
					onPanelListener.onScrollStarted();
					if (state == State.OPENED) {
						state = State.CLOSING;
					}
					AppLog.e("tag", "onScrollStarted");
				}
			}
			layoutParams.bottomMargin += mScrollX;
			if (layoutParams.bottomMargin >= 0) {
				isScrolling = false;// 拖过头了不需要再执行AsynMove了
				layoutParams.bottomMargin = 0;
				if (state != State.OPENED) {
					onPanelListener.onPanelOpened();
					state = State.OPENED;
					AppLog.e("tag", "onPanelOpened");
				}
			} else if (layoutParams.bottomMargin <= -MAX_HEIGHT) {
				// 拖过头了不需要再执行AsynMove了
				isScrolling = false;
				layoutParams.bottomMargin = -MAX_HEIGHT;
			}
			setLayoutParams(layoutParams);
		}
		return true;
	}
	//Touch了不移动一直Touch down时触发 
	@Override
	public void onLongPress(MotionEvent e) {
		
	}
	//Touch了滑动一点距离后，up时触发。
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return false;
	}
	//异步更新布局的位置
	class AsynMove extends AsyncTask<Integer, Integer, Void> {
		@Override
		protected Void doInBackground(Integer... params) {
			int times = 0;
			int divi = Math.abs(params[0]);
			if (MAX_HEIGHT % divi == 0)// 整除
				times = MAX_HEIGHT / Math.abs(params[0]);
			else
				times = MAX_HEIGHT / divi + 1;// 有余数

			for (int i = 0; i < times; i++) {
				publishProgress(params[0]);
				try {
					Thread.sleep(divi);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (state == State.CLOSING) {
				state = State.CLOSEED;
				onPanelListener.onPanelClosed();
				AppLog.e("tag", "CLOSING -- > onPanelClosed");
			}else if (state == State.OPENGING) {
				state = State.OPENGING;
				onPanelListener.onPanelOpened();
				AppLog.e("tag", "OPENGING -- > onPanelOpened");
			}
			return null;
		}
		@Override
		protected void onProgressUpdate(Integer... values) {
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getLayoutParams();
			if (values[0] < 0) {
				layoutParams.bottomMargin = Math.max(layoutParams.bottomMargin + values[0], -MAX_HEIGHT);
			} else {
				layoutParams.bottomMargin = Math.min(layoutParams.bottomMargin + values[0], 0);
			}
			setLayoutParams(layoutParams);
			super.onProgressUpdate(values);
		}
	}
}
