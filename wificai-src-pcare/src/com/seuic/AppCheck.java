package com.seuic;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import com.seuic.interfaces.CustomerInterface;

import android.content.Context;
import android.os.StatFs;

public class AppCheck
{
	private Context context;
	private Timer sdTimer = null;
	private int time_interval = 5000;
	private int kk = 1024 * 1024;
	private String sDcString = android.os.Environment.getExternalStorageState();
	private static AppCheck appCheckInstance = null;
	public AppCheck(Context context){
		this.context = context;
	}
	public Context getContext(){
		return this.context;
	}
	public static AppCheck getAppCheck(Context context){
		if (appCheckInstance == null)
		{
			appCheckInstance = new AppCheck(context);
		}
		return appCheckInstance;
	}
	/*
	 * 检测手机容量
	 */
	public void SDCardSizeTest(){
		sdTimer = new Timer();
		sdTimer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				if (sDcString.equals(android.os.Environment.MEDIA_MOUNTED)) {
					File pathFile = android.os.Environment.getExternalStorageDirectory();
					StatFs statfs = new android.os.StatFs(pathFile.getPath());
					long nBlocSize = statfs.getBlockSize();
					long nAvailaBlock = statfs.getAvailableBlocks();
					long sdcapacity = (nAvailaBlock * nBlocSize) / kk;
					AppInforToCustom.getAppInforToCustomInstance().setSDCapacity(sdcapacity);
					if (sdcapacity < AppInforToCustom.getAppInforToCustomInstance().SDLeastCapacity)
					{
						AppConnect.getInstance().callBack(CustomerInterface.MESSAGE_PHONE_CAPACITY_LOW);
					}
				}else{
					AppInforToCustom.getAppInforToCustomInstance().setIsAvaiableSDCard(false);
					AppInforToCustom.getAppInforToCustomInstance().setSDCapacity(-1);
				}
			}
		}, 0, time_interval);
	}
	/*
	 * 取消SD卡容量检测
	 */
	public void CacleSDTest(){
		if (sdTimer != null)
		{
			sdTimer.cancel();
			sdTimer = null;
		}
	}
}
