package com.seuic.util;

import android.app.Activity;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

public class ToolsUnility
{
	private Context context = null;
	private static ToolsUnility toolsUnilityInstance = null;
	public ToolsUnility(Context context){
		this.context = context;
	}
	public Context getContext(){
		return context;
	}
	public static ToolsUnility getToolsUnilityInstance(Context context){
		if (toolsUnilityInstance == null)
		{
			toolsUnilityInstance = new ToolsUnility(context);
		}
		return toolsUnilityInstance;
	}
	public int dip2px(float dpValue) {  
        return (int)(dpValue * scale + 0.5f);
    }
	public int px2dip(float pxValue) {  
        return (int) (pxValue / scale + 0.5f);
    }
	//去掉标题栏
	public void noTitle(Activity activity){
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
	//去掉标题和状态栏
	public void noTitleAndStaBar(Activity activity){
		noTitle(activity);
		activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);    
		activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}
	public float scale;
	
	
}
