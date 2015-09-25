package com.seuic.util;

import java.math.BigDecimal;

import com.batman.package1._R;
import com.seuic.AppInforToSystem;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class WificarNewLayoutParams {
	public static WificarNewLayoutParams instance;
	public Activity activity;

	public WificarNewLayoutParams(Activity activity) {
		this.activity = activity;
		getDisplayMetrics();
		initVar();
	}

	public static WificarNewLayoutParams getWificarNewLayoutParams() {
		return instance;
	}

	public static WificarNewLayoutParams getWificarNewLayoutParams(
			Activity activity) {
		if (instance == null) {
			instance = new WificarNewLayoutParams(activity);
		}
		return instance;
	}

	/**
	 * 初始化变量的值 根据手机不同的配置，设置组件的大小
	 */
	int x;
/*
 * 小数点后保留3位
	BigDecimal a = new BigDecimal((float)Screen_width /Screen_height).setScale(3, BigDecimal.ROUND_HALF_UP);
	BigDecimal b = new BigDecimal((float)1920 / 1080).setScale(3, BigDecimal.ROUND_HALF_UP);
	BigDecimal c = new BigDecimal((float)800 / 480).setScale(3, BigDecimal.ROUND_HALF_UP);
	*/
	public void initVar() {
		if (screenSize < 5.8) {
		
				Log.e("initVar", "是1920 / 1080---16/9");
				scrol_left_width = Screen_width*39/854;
				scrol_left_height = Screen_height*26/480;
				scrol_right_width = Screen_width/3+Screen_width*15/854;
				scrol_right_height = Screen_height*26/480;
				
				scrol_left_width1 = Screen_width/2+Screen_width*83/854;
				
				camera_change_btn_width = dip2px(50);
				camera_change_btn_height = dip2px(50);
				up_edge_marge_lr_width = Screen_height*30/480;
				up_edge_marge_lr_height = Screen_width*4/854;
				up_edge_ctn_btn_width = dip2px(100);
				up_edge_ctn_btn_height = dip2px(100);
				
				Volume_Move_Width = Screen_height*83/480;
				Volume_Move_Height =Screen_width*39/854;
				up_jet_height = Screen_width*3/854;
				btn_big_width = Screen_height*98/480;
				btn_big_height = Screen_height*98/480;
				bat_height = Screen_height*45/480;
				height_bottom = -(Screen_height*3/480);
						
				Jet_Move_Width = Screen_height*150/480;
				Jet_Move_Height = Screen_width*38/854;
				
				btn_bottom_width = Screen_width*40/854;
				btn_bottom_height =Screen_width*40/854;
				
				talk_width = Screen_width*50/854;
				
				bottom_heigth = Screen_height*20/480;
				btn_width = Screen_width*120/854;
				
				last_Bottom_height = -(Screen_height*3/480);
				btn_in_bottom_width = Screen_height*65/480;
				btn_in_bottom_height = Screen_height*65/480;
				up_edge_ctn_btn_height_center = up_edge_ctn_btn_height / 2;
				up_edge_botm_joystick_width = Screen_width*180/854;
				up_edge_botm_joystick_width_center = up_edge_botm_joystick_width / 2;
				up_center_zoom_width = Screen_width*45/854;
				up_center_zoom_top_marge = Screen_height*30/480;// 奇幻修改_0826
				bottom_left = Screen_width*20/854;
				bottom_btn_width = Screen_height*65/480;
				bottom_btn_height = Screen_height*65/480;
				up_edge_video_red_width = Screen_height*38/480;
				Car_Move_Progress_Width = Screen_height*149/480;
				Car_Move_Progress_Height = Screen_width*48/854;
				Car_Compont_UD_Marge_L = Screen_width*12/854;
				Car_Compont_LR_Marge_R =Screen_width*14/854;// 奇幻修改_0822
				Car_Compont_UD_Marge_D =Screen_height*98/480 ;
				talk_up_bottom = Screen_height*185/480;
				btn_right_width = Screen_width*15/854;
				Car_Compont_LR_Marge_D = Screen_height*94/480;
				surfaceParams_width = (Screen_width-Jet_Move_Height*2-dip2px(10));
				surfaceParams_heigth = ((Screen_width-Jet_Move_Height*2-dip2px(10))*3/4);
				
				UD_Diff_x = Screen_width - Car_Compont_UD_Marge_L
						- Car_Move_Progress_Width - Car_Compont_LR_Marge_R;
				UD_Diff_y = (Car_Move_Progress_Width + Car_Compont_UD_Marge_D)
						- (Car_Move_Progress_Height + Car_Compont_LR_Marge_D);

				UD_Jet_Diff_x = Screen_width - Car_Compont_UD_Marge_L
						- Jet_Move_Height - up_edge_marge_lr_height;
				UD_Jet_Diff_y = Screen_height - (up_center_zoom_top_marge + btn_bottom_height + up_jet_height)
						- Car_Move_Progress_Width - Car_Compont_UD_Marge_D;

				photo_bottom_magin = dip2px(70);// 奇幻修改_0826
				initParmas();
		} else { // >5.8
				Log.e("initVar", "是1280 ：800.。。");
			bottom_in_parent_height = dip2px(140);//下面一列的图标
			up_edge_marge_lr_width = Screen_height*73/1600;
			up_edge_marge_lr_height = Screen_width*16/2560;
			up_ir_height = Screen_height*12/1600;
			x = Screen_width / (btn_in_bottom_num * 5 + btn_in_bottom_num);
			Log.e("x", "x:"+x);
			btn_in_bottom_width = Screen_width*210/2560;
			btn_bottom_width = Screen_width*105/2560;
			btn_big_width = Screen_width*294/2560;
			btn_big_height =Screen_height*294/1600;
			btn_width = Screen_width*240/2560;
			up_edge_top_btn_height1 = Screen_height*50/1600;
			bottom_heigth = Screen_height*70/1600;
			btn_in_bottom_width_center = btn_in_bottom_width / 2;
			btn_in_bottom_height = Screen_height*205/1600;
			btn_bottom_height =Screen_height*105/1600;
			up_volume_height = Screen_height*14/1600;
			up_jet_height = Screen_width*20/2560;
			up_center_zoom_width = Screen_width*120/2560;
			up_center_zoom_height = Screen_width*120/2560;;// 奇幻修改_0821
			up_edge_botm_joystick_width = Screen_width*400/2560;
			up_edge_botm_right_joystick_width = 100;
			bat_height = Screen_height*200/1600;
			up_edge_botm_joystick_width_center = up_edge_botm_joystick_width / 2;
			up_center_zoom_top_marge = Screen_height*90/1600;
			Car_Move_Progress_Width = Screen_height*400/1600;
			Car_Move_Progress_Height = Screen_width*150/2560;
			Volume_Move_Width = Screen_height*200/1600;
			Volume_Move_Height = Screen_width*100/2560;
			Jet_Move_Width = Screen_height*420/1600;
			Jet_Move_Height = Screen_width*100/2560;
			up_share_height = Screen_height*130/1600;
			btn_right_width = Screen_width*60/2560;
			talk_up_bottom = Screen_height*618/1600;
			Car_Compont_UD_Marge_L = Screen_width*28/2560;// 奇幻修改—_0822
			Car_Compont_UD_Marge_D = Screen_height*300/1600;// 奇幻修改—_0822
			Car_Compont_LR_Marge_R = Screen_width*70/2560;// 奇幻修改—_0822
			Car_Compont_LR_Marge_D = Screen_height*294/1600;// 奇幻修改—_0822
//TODO
			UD_Diff_x = Screen_width - Car_Compont_UD_Marge_L
					- Car_Move_Progress_Width - Car_Compont_LR_Marge_R;
			UD_Diff_y = (Car_Move_Progress_Width + Car_Compont_UD_Marge_D)
					- (Car_Move_Progress_Height + Car_Compont_LR_Marge_D);
			
			UD_Jet_Diff_x = Screen_width - Car_Compont_UD_Marge_L
					- Jet_Move_Height - up_edge_marge_lr_height;
			UD_Jet_Diff_y = Screen_height - (up_center_zoom_top_marge + btn_bottom_height + dip2px(10))
					- Car_Move_Progress_Width - Car_Compont_UD_Marge_D;
			
			surfaceParams_heigth = ((Screen_width-Jet_Move_Height*2-dip2px(10))*3/4);
			surfaceParams_width = (Screen_width-Jet_Move_Height*2-dip2px(10));
			initHParams();
		}
	}
	
	/** 
     * 密度转换像素 
     * */
	public static int dip2px(float dpValue) {
		return (int) (dpValue * scale + 0.5f);
	}

	// 获取屏幕的宽度，高度和密度以及dp / px
	public void getDisplayMetrics() {
		DisplayMetrics dm = new DisplayMetrics();
		dm = activity.getApplicationContext().getResources()
				.getDisplayMetrics();
		Screen_width = dm.widthPixels;//屏幕绝对宽度 单位pixel
		Screen_height = dm.heightPixels;
		a = new BigDecimal((float)Screen_width /Screen_height).setScale(2, BigDecimal.ROUND_HALF_UP);
		b = new BigDecimal((float)1920 / 1080).setScale(2, BigDecimal.ROUND_HALF_UP);
		c = new BigDecimal((float)800 / 480).setScale(2, BigDecimal.ROUND_HALF_UP);
		
		Log.e("getDisplayMetrics", "Screen_width = "+Screen_width+"  Screen_height="+Screen_height);
		scale = activity.getResources().getDisplayMetrics().density;
		Log.e("getDisplayMetrics", "getResources()'s scale(density) = "+scale);
		density = dm.density;
		Log.e("getDisplayMetrics", "getApplicationContext()'s density = "+density);
		double bb = Math.sqrt(Math.pow(Screen_width, 2)
				+ Math.pow(Screen_height, 2));
		screenSize = bb / (160 * dm.density);
	}

	/**
	 * 初始化布局和组件的布局参数 < 5.8
	 */
	public void initParmas() {
		Log.e("initParmas", "屏幕尺寸小于等于5.8.。。");

		btn_in_bottom_width = btn_in_bottom_height;
		btn_big_height = btn_big_width;
		btn_bottom_height = btn_bottom_width;
		parentParams0 = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		parentParams = new LayoutParams(Screen_width,Screen_height);
		if ((double)Screen_width/Screen_height == (double)16/9 || (double)Screen_width/Screen_height == (double)4/3) {
			contentParams = new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.MATCH_PARENT);
		} else {
			contentParams = new LayoutParams(Screen_height*4/3,
					Screen_height);
		}
		contentParams.addRule(RelativeLayout.CENTER_IN_PARENT);

		surfaceParams = new LayoutParams(surfaceParams_width,
				surfaceParams_heigth);
		surfaceParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		surfaceParams.bottomMargin = btn_bottom_width+height_bottom;
		surfaceParams.topMargin = dip2px(5);

		zoomInParams = new LayoutParams(up_center_zoom_width,
				up_center_zoom_width);
		zoomInParams.leftMargin = up_edge_botm_joystick_width_center;
		zoomInParams.topMargin = up_center_zoom_top_marge;

		zoomOutParams = new LayoutParams(up_center_zoom_width,
				up_center_zoom_width);
		zoomOutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		zoomOutParams.rightMargin = up_edge_botm_joystick_width_center;// up_edge_marge_lr_width + up_edge_marge_lr_width +
							// up_edge_top_btn_width;
		zoomOutParams.topMargin = up_center_zoom_top_marge;

		showInfoTViewParams = new LayoutParams(dip2px(180), dip2px(80));
		showInfoTViewParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		showInfoTViewParams.topMargin = Screen_height / 3;
		int batWidth = Screen_width/7;
		int batHeight = Screen_height/15;
		batParams = new LayoutParams(batWidth,batHeight);
		batParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		batParams.leftMargin = (Screen_width -batWidth) / 2;
		batParams.bottomMargin = Screen_height*2/15;
		

		scaleTViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		scaleTViewParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		scaleTViewParams.topMargin = up_center_zoom_top_marge;

		chronParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		chronParams.addRule(RelativeLayout.LEFT_OF, _R.id.ZoomOut_id);
		chronParams.rightMargin = up_edge_marge_lr_width;
		chronParams.topMargin = up_center_zoom_top_marge;

		videoRedParams = new LayoutParams(up_edge_video_red_width,
				up_edge_video_red_width);
		videoRedParams.addRule(RelativeLayout.LEFT_OF, _R.id.Chronometer_id);
		videoRedParams.rightMargin = up_edge_marge_lr_width;
		videoRedParams.topMargin = up_center_zoom_top_marge;
		bottom_Btn_Params = new LinearLayout.LayoutParams(bottom_btn_width,
				bottom_btn_height);
		bottom_btn_marge = (Screen_width/4 - bottom_btn_width * 3) /4;
		bottom_Btn_Params.leftMargin = bottom_btn_marge;
		bottom_Btn_Params.bottomMargin = height_bottom;
		bottom_Btn_Params1 = new LinearLayout.LayoutParams(bottom_btn_width,
				bottom_btn_height);
		bottom_btn_marge = (Screen_width/4 - bottom_btn_width * 3) /4;
		bottom_Btn_Params1.rightMargin = bottom_btn_marge;
		bottom_Btn_Params1.bottomMargin = height_bottom;
		
		last_Bottom_Btn_Params = new LinearLayout.LayoutParams(
				bottom_btn_width, bottom_btn_height);
		last_Bottom_Btn_Params.leftMargin = bottom_btn_marge;
		last_Bottom_Btn_Params.rightMargin = bottom_btn_marge;
		last_Bottom_Btn_Params.bottomMargin =last_Bottom_height;
		
		scrol_leftParams = new LayoutParams(dip2px(30),
				dip2px(40));
		scrol_leftParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		scrol_leftParams.leftMargin = scrol_left_width;
		scrol_leftParams.bottomMargin = scrol_left_height;
		
		scrol_rightParams = new LayoutParams(dip2px(30),
				dip2px(40));
		scrol_rightParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		scrol_rightParams.leftMargin = scrol_right_width;
		scrol_rightParams.bottomMargin = scrol_right_height;
		
		scrol_leftParams1 = new LayoutParams(dip2px(30),
				dip2px(40));
		scrol_leftParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		scrol_leftParams1.leftMargin = scrol_left_width1;
		scrol_leftParams1.bottomMargin = scrol_left_height;
		
		scrol_rightParams1 = new LayoutParams(dip2px(30),
				dip2px(40));
		scrol_rightParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		scrol_rightParams1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		scrol_rightParams1.rightMargin = scrol_left_width;
		scrol_rightParams1.bottomMargin = scrol_right_height;
		
		
		
		scroViewParams = new LayoutParams(Screen_width/2-dip2px(100),bottom_btn_height);
		scroViewParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		scroViewParams.leftMargin = dip2px(28);
		scroViewParams.bottomMargin = dip2px(5);		
		volume_bar = new LayoutParams(Volume_Move_Height,
				Volume_Move_Width);
		volume_bar.topMargin = up_edge_marge_lr_width;
		volume_bar.leftMargin = up_edge_marge_lr_height;
		
		jet_bar = new LayoutParams(Jet_Move_Height,
				Jet_Move_Width);
		jet_bar.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		jet_bar.topMargin = up_center_zoom_top_marge + btn_bottom_height + up_jet_height;
		jet_bar.rightMargin = up_edge_marge_lr_height;
		
		micParams = new LayoutParams(btn_bottom_width,
				btn_bottom_height);
		micParams.addRule(RelativeLayout.BELOW, _R.id.Volume_id);
		micParams.leftMargin = up_jet_height;
		micParams.topMargin = up_volume_height;
		
		mgunParams = new LayoutParams(
				btn_big_width, btn_big_height);
		mgunParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		mgunParams.bottomMargin = up_center_zoom_top_marge;
		mgunParams.leftMargin =  bottom_left;
		
		rocketParams= new LayoutParams(
				btn_big_width, btn_big_height);
		rocketParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		rocketParams.rightMargin = bottom_left;
		rocketParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		rocketParams.bottomMargin = up_center_zoom_top_marge;
		
		videoParams1  = new LayoutParams(
				btn_in_bottom_width, btn_in_bottom_height);
		videoParams1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		videoParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		videoParams1.bottomMargin = bottom_heigth;
		videoParams1.rightMargin = up_center_zoom_top_marge+btn_width;
		
		photoParams1= new LayoutParams(
				btn_in_bottom_width, btn_in_bottom_height);
		photoParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		photoParams1.bottomMargin = bottom_heigth;
		photoParams1.leftMargin =  up_center_zoom_top_marge+btn_width;
		
		gsensorParams1 =  new LayoutParams(
				btn_in_bottom_width, btn_in_bottom_height);
		gsensorParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		gsensorParams1.bottomMargin = bottom_heigth;
		gsensorParams1.leftMargin =  up_center_zoom_top_marge+btn_width*2;
		irParams1 =  new LayoutParams(
				btn_in_bottom_width, btn_in_bottom_height);
		irParams1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		irParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		irParams1.bottomMargin = bottom_heigth;
		irParams1.rightMargin = up_center_zoom_top_marge+btn_width*2;
		
		talkParams = new LayoutParams(talk_width,
				talk_width);
		talkParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		talkParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		talkParams.rightMargin = btn_right_width;
		talkParams.bottomMargin = talk_up_bottom;//调整上下距离
		
		jetpower = new LayoutParams(btn_bottom_width,
				btn_bottom_height);
		jetpower.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		jetpower.topMargin = up_center_zoom_top_marge;
		jetpower.rightMargin = up_edge_marge_lr_height;

		AppInforToSystem.bottom_btn_scroll_range = bottom_btn_width
				+ bottom_btn_marge;// <5.8的滚动条的滚动矩形
		connectParams = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		connectParams.addRule(RelativeLayout.CENTER_IN_PARENT);

		car_UD_Move_Params = new LayoutParams(Car_Move_Progress_Height,
				Car_Move_Progress_Width);
		car_UD_Move_Params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		car_UD_Move_Params.leftMargin = Car_Compont_UD_Marge_L;
		car_UD_Move_Params.bottomMargin = Car_Compont_UD_Marge_D;

		
		//TODO
		car_LR_Move_Params = new LayoutParams(Car_Move_Progress_Width,
				Car_Move_Progress_Height);
		car_LR_Move_Params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		car_LR_Move_Params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		car_LR_Move_Params.rightMargin = Car_Compont_LR_Marge_R;
		car_LR_Move_Params.bottomMargin = Car_Compont_LR_Marge_D;

		camera_Move_Params = new LayoutParams(Car_Move_Progress_Width,
				Car_Move_Progress_Width);
		camera_Move_Params.addRule(RelativeLayout.ABOVE, _R.id.Car_LR_id);
		camera_Move_Params.addRule(RelativeLayout.ALIGN_RIGHT, _R.id.Car_LR_id);
		camera_Move_Params.bottomMargin = dip2px(15);
	}

	/**
	 * 初始化布局和组件的布局参数 > 5.8
	 */
	//TODO
	//TODO
	public void initHParams() {
		if((double)Screen_width/Screen_height == (double)4/3) {
			up_edge_marge_lr_height = up_edge_marge_lr_height +dip2px(2);
			btn_bottom_width = btn_bottom_width + dip2px(2);
			Volume_Move_Width = Volume_Move_Width +dip2px(10);
			up_edge_marge_lr_width = up_edge_marge_lr_width +dip2px(2);
//			up_edge_marge_lr_height = up_edge_marge_lr_height + dip2px(dpValue);
		}
		btn_in_bottom_width = btn_in_bottom_height;
		btn_big_height = btn_big_width;
		btn_bottom_height = btn_bottom_width;	
		Log.e("initHParams", "屏幕尺寸大于5.8.。。");
		parentParams0 = new LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		parentParams = new LayoutParams(Screen_width,Screen_height);
		if ((double)Screen_width/Screen_height == (double)16/9 || (double)Screen_width/Screen_height == (double)4/3) {
			contentParams = new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.MATCH_PARENT);
		} else {
			contentParams = new LayoutParams(Screen_height*4/3,
					Screen_height);
		}
		
		contentParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		btn_in_bottomParams = new LinearLayout.LayoutParams(
				btn_in_bottom_width, btn_in_bottom_height);
		btn_in_bottomParams.leftMargin = x + 50;
		btn_in_bottomParams.gravity = Gravity.CENTER_VERTICAL;
		btn_in_bottomParams.bottomMargin = up_edge_top_btn_height1;
		btn_in_bottomParams1 = new LinearLayout.LayoutParams(
				btn_in_bottom_width, btn_in_bottom_height);
		btn_in_bottomParams1.leftMargin = x;
		btn_in_bottomParams1.gravity = Gravity.CENTER_VERTICAL;
		btn_in_bottomParams1.bottomMargin = bottom_heigth;
		
		btn_in_bottomParamsr =  new LinearLayout.LayoutParams(
				btn_in_bottom_width, btn_in_bottom_height);		
		btn_in_bottomParamsr1 = new LinearLayout.LayoutParams(
				btn_in_bottom_width, btn_in_bottom_height);
		btn_in_bottomParamsr1.leftMargin = x;
		btn_in_bottomParamsr1.gravity = Gravity.CENTER_VERTICAL;
		btn_in_bottomParamsr1.bottomMargin = bottom_heigth;
		
		pathParams =  new LayoutParams(
				btn_in_bottom_width, btn_in_bottom_height);
		pathParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		pathParams.bottomMargin = up_center_zoom_top_marge;
		pathParams.leftMargin =  up_center_zoom_top_marge;
		
		mgunParams = new LayoutParams(
				btn_big_width, btn_big_height);
		mgunParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		mgunParams.bottomMargin = up_center_zoom_top_marge;
		mgunParams.leftMargin =  up_center_zoom_top_marge;
		
		
		pathParams1 = new LayoutParams(
				btn_in_bottom_width, btn_in_bottom_height);
		pathParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		pathParams1.bottomMargin = bottom_heigth;
		pathParams1.leftMargin =  up_center_zoom_top_marge+btn_width;
		
		doorParams= new LayoutParams(
				btn_in_bottom_width, btn_in_bottom_height);
		doorParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		doorParams.bottomMargin = bottom_heigth;
		doorParams.leftMargin =  up_center_zoom_top_marge+btn_width*2;
		
		photoParams1= new LayoutParams(
				btn_in_bottom_width, btn_in_bottom_height);
		photoParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		photoParams1.bottomMargin = bottom_heigth;
		photoParams1.leftMargin =  up_center_zoom_top_marge+btn_width*2;
		
		gsensorParams =  new LayoutParams(
				btn_in_bottom_width, btn_in_bottom_height);
		gsensorParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		gsensorParams.bottomMargin = bottom_heigth;
		gsensorParams.leftMargin =  up_center_zoom_top_marge+btn_width*3;
		
		videoParams = new LayoutParams(
				btn_in_bottom_width, btn_in_bottom_height);
		videoParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		videoParams.rightMargin = up_center_zoom_top_marge;
		
		videoParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		videoParams.bottomMargin = up_center_zoom_top_marge;
		
		rocketParams= new LayoutParams(
				btn_big_width, btn_big_height);
		rocketParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		rocketParams.rightMargin = up_center_zoom_top_marge;
		
		rocketParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		rocketParams.bottomMargin = up_center_zoom_top_marge;
		
		photoParams = new LayoutParams(
				btn_in_bottom_width, btn_in_bottom_height);
		photoParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		photoParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		photoParams.bottomMargin = bottom_heigth;
		photoParams.rightMargin = up_center_zoom_top_marge+btn_width;
		int batWidth = Screen_width/7;
		int batHeight = Screen_height/15;
		Log.e("WificarNewLayoutParams", "batWidth = "+batWidth+" batHeight = "+batHeight);
		batParams = new LayoutParams(batWidth,batHeight);
		batParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		batParams.leftMargin = (Screen_width -batWidth) / 2;
		batParams.bottomMargin = Screen_height*2/15;
		
		nullParams = new LayoutParams(dip2px(10),dip2px(10));
		nullParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		nullParams.bottomMargin = 0;
		
		spoilerParams = new LayoutParams(
				btn_in_bottom_width, btn_in_bottom_height);
		spoilerParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		spoilerParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		spoilerParams.bottomMargin = bottom_heigth;
		spoilerParams.rightMargin = up_center_zoom_top_marge+btn_width*2;
		
		videoParams1  = new LayoutParams(
				btn_in_bottom_width, btn_in_bottom_height);
		videoParams1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		videoParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		videoParams1.bottomMargin = bottom_heigth;
		videoParams1.rightMargin = up_center_zoom_top_marge+btn_width*2;
		
		lightParams = new LayoutParams(
				btn_in_bottom_width, btn_in_bottom_height);
		lightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		lightParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		lightParams.bottomMargin = bottom_heigth;
		lightParams.rightMargin = up_center_zoom_top_marge+btn_width*3;
		
		irParams1 =  new LayoutParams(
				btn_in_bottom_width, btn_in_bottom_height);
		irParams1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		irParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		irParams1.bottomMargin = bottom_heigth;
		irParams1.rightMargin = up_center_zoom_top_marge+btn_width*3;
		
		surfaceParams = new LayoutParams(surfaceParams_width,
				surfaceParams_heigth);
		surfaceParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		surfaceParams.topMargin = dip2px(5);

		zoomInParams = new LayoutParams(up_center_zoom_height,
				up_center_zoom_width);// 奇幻修改_0821
		zoomInParams.topMargin = up_center_zoom_top_marge;
		zoomInParams.leftMargin = btn_in_bottom_width ;// 奇幻修改_0821
		zoomOutParams = new LayoutParams(up_center_zoom_height,
				up_center_zoom_width);// 奇幻修改_0821
		zoomOutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		zoomOutParams.rightMargin = btn_in_bottom_width ;// 奇幻修改_0821
		zoomOutParams.topMargin = up_center_zoom_top_marge;

		speedParams = new LayoutParams(btn_in_bottom_width,
				btn_in_bottom_height);
		speedParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		speedParams.bottomMargin = dip2px(20);
		speedParams.leftMargin = btn_in_bottom_width / 4;

		irParams = new LayoutParams(btn_bottom_width,
				btn_bottom_height);
		irParams.addRule(RelativeLayout.BELOW, _R.id.mic_id);
		irParams.leftMargin = up_edge_marge_lr_height;
		irParams.topMargin = up_share_height;
		
		shareParams = new LayoutParams(btn_bottom_width,
				btn_bottom_height);
		shareParams.addRule(RelativeLayout.BELOW, _R.id.Ir_Btn_id);
		shareParams.leftMargin = up_edge_marge_lr_height;
		shareParams.topMargin = up_ir_height;
		
		
		talkParams = new LayoutParams(btn_bottom_width,
				btn_bottom_height);
		talkParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		talkParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		talkParams.rightMargin = btn_right_width;
		talkParams.bottomMargin = talk_up_bottom;//调整上下距离
		
		micParams = new LayoutParams(btn_bottom_width,
				btn_bottom_height);
		micParams.addRule(RelativeLayout.BELOW, _R.id.Volume_id);
		micParams.leftMargin = up_edge_marge_lr_height;
		micParams.topMargin = up_volume_height;

		scaleTViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		scaleTViewParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		scaleTViewParams.topMargin = up_center_zoom_top_marge;

		showInfoTViewParams = new LayoutParams(dip2px(200), dip2px(100));
		showInfoTViewParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		showInfoTViewParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		showInfoTViewParams.bottomMargin = Screen_height / 2;

		chronParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		chronParams.addRule(RelativeLayout.LEFT_OF, _R.id.ZoomOut_id);
		chronParams.rightMargin = dip2px(15);
		chronParams.topMargin = dip2px(45);


		videoRedParams = new LayoutParams(up_edge_video_red_width,
				up_edge_video_red_width);
		videoRedParams.addRule(RelativeLayout.LEFT_OF, _R.id.Chronometer_id);
		videoRedParams.rightMargin = dip2px(15);
		videoRedParams.topMargin = dip2px(45);

		connectParams = new LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		connectParams.addRule(RelativeLayout.CENTER_IN_PARENT);

		car_UD_Move_Params = new LayoutParams(Car_Move_Progress_Height,
				Car_Move_Progress_Width);
		car_UD_Move_Params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		car_UD_Move_Params.leftMargin = Car_Compont_UD_Marge_L;
		car_UD_Move_Params.bottomMargin = Car_Compont_UD_Marge_D;
		if((double)Screen_width/Screen_height == (double)4/3) {
			car_UD_Move_Params.leftMargin = Car_Compont_UD_Marge_L+dip2px(6);
		}
		
	//TODO
		volume_bar = new LayoutParams(Volume_Move_Height,
				Volume_Move_Width);
		volume_bar.topMargin = up_edge_marge_lr_width;
		volume_bar.leftMargin = up_edge_marge_lr_height;
		
		jet_bar = new LayoutParams(Jet_Move_Height,
				Jet_Move_Width);
		jet_bar.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		jet_bar.topMargin = up_center_zoom_top_marge + btn_bottom_height + up_jet_height;
		jet_bar.rightMargin = up_edge_marge_lr_height;
		
		jetpower = new LayoutParams(btn_bottom_width,
				btn_bottom_height);
		jetpower.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		jetpower.topMargin = up_center_zoom_top_marge;
		jetpower.rightMargin = up_edge_marge_lr_height;
		

		car_LR_Move_Params = new LayoutParams(Car_Move_Progress_Width,
				Car_Move_Progress_Height);
		car_LR_Move_Params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		car_LR_Move_Params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

		car_LR_Move_Params.rightMargin = Car_Compont_LR_Marge_R;
		car_LR_Move_Params.bottomMargin = Car_Compont_LR_Marge_D;
		if((double)Screen_width/Screen_height == (double)4/3) {
			car_LR_Move_Params.bottomMargin = Car_Compont_LR_Marge_D + dip2px(5);
		}
		Log.e("Car_Compont_LR_Marge_D", "Car_Compont_LR_Marge_D   "+Car_Compont_LR_Marge_D);

		camera_Move_Params = new LayoutParams(Car_Move_Progress_Width,
				Car_Move_Progress_Width);
		camera_Move_Params.addRule(RelativeLayout.ABOVE, _R.id.Car_LR_id);
		camera_Move_Params.addRule(RelativeLayout.ALIGN_RIGHT, _R.id.Car_LR_id);
		camera_Move_Params.bottomMargin = dip2px(20);

	}

	// 初始化变量
	public static int Screen_width;
	public static int Screen_height;
	public static float scale;// dp -- px
	public float density;
	public static BigDecimal a;
	public static BigDecimal b;
	public static BigDecimal c;
	public static BigDecimal d;
	public static float f;
	public double screenSize;
	

	// 组件布局参数
	public LayoutParams parentParams,parentParams0,loadingParams,contentParams;
	public LayoutParams gsensorParams;
	public LayoutParams gsensorParams1;
	public LayoutParams speedParams;
	public LayoutParams cameraChangeParams;
	public LayoutParams cameraMoveParams;
	public LayoutParams inforParams;
	public LayoutParams surfaceParams;
	public LayoutParams zoomInParams;
	public LayoutParams zoomOutParams;
	public LayoutParams photoParams;
	public LayoutParams videoParams;
	public LayoutParams videoParams1;
	public LinearLayout.LayoutParams photoParams2;
	public LinearLayout.LayoutParams videoParams2;
	public LayoutParams p_v_Params;
	public LayoutParams bottomParams;
	public LayoutParams showInfoTViewParams;
	public LayoutParams scaleTViewParams;
	public LayoutParams chronParams;
	public LayoutParams videoRedParams;
	public LayoutParams listenVolume_Change_Params;
	public LinearLayout.LayoutParams bottom_Btn_Params, last_Bottom_Btn_Params,bottom_Btn_Params1;
	public LayoutParams camera_lr_Params;
	public LayoutParams connectParams;
	public LayoutParams speed;
	public LayoutParams speed1;
	public LayoutParams irParams;
	public LayoutParams irParams1;
	public LayoutParams shareParams;
	public LayoutParams talkParams;
	public LayoutParams micParams;
	public LayoutParams scrol_leftParams;
	public LayoutParams scrol_rightParams;
	
	public LayoutParams scrol_leftParams1;
	public LayoutParams scrol_rightParams1;
	public LayoutParams scroViewParams;
	
	public LayoutParams pathParams;
	public LayoutParams pathParams1;
	public LayoutParams doorParams;
	public LayoutParams lightParams;
	public LayoutParams spoilerParams;
	public LayoutParams mgunParams;
	public LayoutParams photoParams1;
	public LayoutParams rocketParams;
	public LayoutParams batParams;
	public LayoutParams nullParams;
	

	public LayoutParams car_UD_Move_Params;//左边滑轮
	public LayoutParams car_LR_Move_Params;
	public LayoutParams camera_Move_Params;
	public LayoutParams volume_bar;//
	public LayoutParams jet_bar;
	public LayoutParams jetpower;
	// 大于5.8的时候需要添加一个底部的LinearLayout
	public LayoutParams bottomLayoutParams;
	public LinearLayout.LayoutParams btn_in_bottomParams;
	public LinearLayout.LayoutParams btn_in_bottomParams1;
	public LinearLayout.LayoutParams btn_in_bottomParamsr;
	public LinearLayout.LayoutParams btn_in_bottomParamsr1;
	// 组件属相变量
	public int up_edge_top_btn_width = 50;// dp
	public int up_edge_top_btn_height = 50;
	public int camera_change_btn_width = 50;
	public int camera_change_btn_height = 50;
	public int up_edge_top_scalebtn_width = 25;
	public int up_edge_ctn_btn_width = 50;
	public int up_edge_ctn_btn_height = 120;
	public int up_edge_ctn_btn_height_center = 60;
	public int up_edge_botm_joystick_width = 230;
	public int up_edge_botm_joystick_width_center = 115;
	public int up_edge_botm_right_joystick_width = 100;
	public int up_edge_botm_info_width = 25;
	public int up_edge_marge_lr_width;
	public int up_edge_marge_lr_height;
	public int up_edge_marge_ud_height = 10;
	public int up_edge_video_red_width = 25;
	public int up_center_surface_marge_bottom = 50;
	public int up_center_surface_marge_lr = 2;
	public int up_center_zoom_width = 20;
	public int up_center_zoom_height = 82;// 奇幻修改_0821
	public int up_center_zoom_top_marge;
	public int bottom_left;
	
	public int bottom_btn_width = 80;
	public int bottom_btn_height = 50;
	public int bottom_btn_phone_height = 34;// 奇幻修改_0826
	public int bottom_btn_phone_width = 66;// 奇幻修改_0826
	public int bottom_btn_marge = 10;
	public int bottom_height = 108;
	public int bottom_height_hint = 81;
	public int photo_bottom_magin = 60;// 奇幻修改_0826
	public int up_edge_marge_lr_width1 = 700;
	public int bottom_btn_marge1 = 5;
	public int up_share_height;
	public int up_ir_height;
	public int height_bottom;
	public int scrol_left_width;
	public int scrol_left_width1;
	public int scrol_left_height;
	public int scrol_right_width;
	public int scrol_right_height;
	public int last_Bottom_height;
	public int bat_height;
	public int up_edge_top_btn_width1 = 30;
	public int up_edge_top_btn_height1 = 15;
	public int bottom_heigth = 15;//中间6个按钮到底部的距离
	public int talk_width;
	// >5.8
	public int bottom_in_parent_height;
	public int btn_in_bottom_width;
	public int btn_in_bottom_width_center = 60;
	public int btn_in_bottom_height;
	public int btn_in_bottom_num = 10;
	public int btn_bottom_width = 120;//底部按钮
	public int btn_bottom_height = 100;
	public int up_jet_height;
	public int btn_right_width = 20;//talk离右边距离
	public int talk_up_bottom;
	public int btn_width = 100;//底部按钮的距离
	public int btn_big_width = 120;
	public int btn_big_height = 100;
	public int up_volume_height;
	public int delta_Car_Move_Progress_Width;
	public int delta_Car_Move_Progress_Height;
	/**
	 * 如果是UD：width = 50;height=180 如果是LR：width = 180；height = 50
	 * 如果是方向圆盘:width=180;height=180
	 */
	public static int Car_Move_Progress_Width;
	public static int Car_Move_Progress_Height;
	public static int Volume_Move_Width;
	public static int Volume_Move_Height;
	public static int Jet_Move_Width ;
	public static int Jet_Move_Height ;

	public static int Car_Compont_UD_Marge_L = 10;
	public static int Car_Compont_LR_Marge_R;
	public static int Car_Compont_UD_Marge_D = 70;
	public static int Car_Compont_LR_Marge_D;
	public static int UD_Diff_x;// 记录在LR的差值
	public static int UD_Diff_y;
	public static int UD_Jet_Diff_x;// 记录UD与Jet间diff
	public static int UD_Jet_Diff_y;
	public int surfaceParams_width = (Screen_width-Jet_Move_Height*3) * 4 / 3;
	public int surfaceParams_heigth = Screen_width-Jet_Move_Height*3;
}
