package com.seuic;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.os.Environment;

import com.seuic.interfaces.CustomerInterface;
import com.seuic.protocol.CMD_OP_CODE;

public class AppInforToCustom
{
	private static AppInforToCustom appInforToCustomeInstance = null;
	public AppInforToCustom(){
		
	}
	public static AppInforToCustom getAppInforToCustomInstance(){
		if (appInforToCustomeInstance == null)
		{
			appInforToCustomeInstance = new AppInforToCustom();
		}
		return appInforToCustomeInstance;
	}
	
	private String targetIP = "192.168.1.100"; // Target IP
	private String targetFWVersion = ""; //固件版本号
	private String targetDevID = ""; // 设备ID
	private String storage_directory = Environment.getExternalStorageDirectory().getPath()+"/DCIM/RC_Tumbler";
	private String storage_picture = storage_directory+"/Pictures";
	private String storage_video = storage_directory+"/Video";
	private String storage_config = "wificarTrackLog";
	private int targetPort = 80; // Target port
	private int batteryRequestFreq = 5000;
	private int battery_show = -1;
	private int heartBeatRequestFreq = 1000;
	private int warningHeartBeat = 15;
	private int VideoWidth = 640;
	private int VideoHeight = 480;
	private int Record_flag = 0;
	private int ShootingTimes = 0;
	private long SDCapacity = 0;
	private int ScrollStatus = -1;
	public long SDLeastCapacity = 50;
	public int MAXPhoto = 200;
	private boolean isPlayModeEnable = false;
	private boolean isCyclePlayMode = false;
	private boolean isCameraShooting = false;
	public static boolean isGSensorControl = false;
	private boolean isBrightControl = false;
	private boolean isStealthControl = false;//隐身模式
	private boolean isStopDoor = false;
	private boolean isDoor  = false;
	private boolean isstopCommand = false;
	private boolean isDeviceStateLedControl = true;
	private boolean isAvaiableSDCard = true;
	private boolean isELChange = false;
	private Timer batteryReqTimer = null;
	private Timer heartBeaTimer = null;
	/*
	 * 设置连接设备的IP和Port
	 */
	public final void setIpAndPort(String ip, int port){
		targetIP = ip;
		targetPort = port;
	}
	/*
	 * 获取连接设备的IP地址
	 */
	public final String getTargetIP(){
		return targetIP;
	}
	/*
	 * 获取连接设备的Port端口号
	 */
	public final int getTargetPort(){
		return targetPort;
	}
	/*
	 * 设置设备的固件版本号
	 */
	public final void setFWVersion(String version){
		targetFWVersion = version;
	}
	/*
	 * 获取设备的固件版本号
	 */
	public final String getFWVersion(){
		return targetFWVersion;
	}
	/*
	 * 设置设备的ID号
	 */
	public final void setTargetDevID(String devid){
		targetDevID = devid;
	}
	/*
	 * 获取设备的ID号
	 */
	public final String getTargetDevID(){
		return targetDevID;
	}
	/*
	 * 开始请求电池电量信息
	 * @param 没参数，默认的请求电量的频率为5秒
	 */
	public void startRequestBattery(){
		if (batteryReqTimer == null)
		{
			batteryReqTimer = new Timer("batteryReqTimer");
			batteryReqTimer.schedule(new TimerTask()
			{
				@Override
				public void run()
				{
					AppCommand.getAppCommandInstace().sendCommand(CMD_OP_CODE.FETCH_BATTERY_POWER_REQ);
				}
			}, 0, batteryRequestFreq);
		}
	}
	/*
	 * 开始请求电池电量信息
	 * @param freq  请求电量的频率单位:毫秒
	 */
	public void startRequestBattery(int freq){
		if (batteryReqTimer == null)
		{
			batteryRequestFreq = freq;
			batteryReqTimer = new Timer("batteryReqTimer");
			batteryReqTimer.schedule(new TimerTask()
			{
				@Override
				public void run()
				{
					AppCommand.getAppCommandInstace().sendCommand(CMD_OP_CODE.FETCH_BATTERY_POWER_REQ);
				}
			}, 0, batteryRequestFreq);
		}
	}
	/*
	 * 设置电量数据
	 */
	public final void setBatteryValue(int value){
		battery_show = value;
	}
	/*
	 * 获取电量信息
	 */
	public final int getBatteryValue(){
		return battery_show;
	}
	/*
	 * 取消电量请求功能
	 */
	public void cancleRequestBattery(){
		if (batteryReqTimer != null)
		{
			batteryReqTimer.cancel();
			batteryReqTimer = null;
		}
	}
	/*
	 * 开始请求心跳包
	 * @param 没参数，默认的请求电量的频率为1秒
	 */
	public void startRequestHeartBeat(){
		if (heartBeaTimer == null)
		{
			heartBeaTimer = new Timer("heartBeaTimer");
			heartBeaTimer.schedule(new TimerTask()
			{
				@Override
				public void run()
				{
					if (AppInforToSystem.looseHeartBeatTimes > warningHeartBeat)
					{
						//提示给用户警告
						AppConnect.getInstance().callBack(CustomerInterface.MESSAGE_HEARTBEAT_WARNING);
					}
					AppCommand.getAppCommandInstace().sendCommand(CMD_OP_CODE.Keep_Alive);
				}
			}, 0, heartBeatRequestFreq);
		}
	}
	/*
	 * 开始请求心跳包
	 * @param freq 心跳包请求的频率单位:毫秒
	 */
	public void startRequestHeartBeat(int freq){
		if (heartBeaTimer == null)
		{
			heartBeaTimer = new Timer("heartBeaTimer");
			heartBeaTimer.schedule(new TimerTask()
			{
				@Override
				public void run()
				{
					if (AppInforToSystem.looseHeartBeatTimes > warningHeartBeat)
					{
						//提示给用户警告
						AppConnect.getInstance().callBack(CustomerInterface.MESSAGE_HEARTBEAT_WARNING);
					}
					AppCommand.getAppCommandInstace().sendCommand(CMD_OP_CODE.Keep_Alive);
				}
			}, 0, heartBeatRequestFreq);
		}
	}
	/*
	 * 取消心跳包请求
	 */
	public void cancleRequestHeartBeat(){
		if (heartBeaTimer != null)
		{
			heartBeaTimer.cancel();
			heartBeaTimer = null;
		}
	}
	/*
	 * 设置丢失多少心跳包进行警告
	 */
	public final void setWarningLooseHeartBeat(int times){
		warningHeartBeat = times;
	}
	/*
	 * 设置视频的宽度和高度
	 */
	public final void setVideo_WandH(int width, int height){
		VideoWidth = width;
		VideoHeight = height;
	}
	/*
	 * 获取视频的宽度和高度
	 * int[0]:宽度
	 * int[1]:高度
	 */
	public final int[] getVideo_WandH(){
		int video_WandH[] = {VideoWidth,VideoHeight};
		return video_WandH;
	}
	/*
	 * 获取程序存放的总目录
	 */
	public final String getAppStoragePath(){
		return storage_directory;
	}
	/*
	 * 设置程序的存储路径
	 */
	public final void setAppStoragePath(String path){
		storage_directory = path;
	}
	/*
	 * 获取录制路径的地址
	 */
	public final String getRecordPathConfigFile(){
		return storage_config;
	}
	/*
	 * 判断是否录制路径
	 */
	public final int getRecordPath_flag(){
		return Record_flag;
	}
	/*
	 * 设置是否在录制路径
	 */
	public final void setRecordPath_flag(int flag){
		Record_flag = flag;
	}
	/*
	 * 获取当前播放路径的状态
	 */
	public final boolean getPlayModeStatus(){
		return isPlayModeEnable;
	}
	/*
	 * 设置当前的播放路径状态
	 */
	public final void setPlayModeStatus(boolean opt){
		isPlayModeEnable = opt;
	}
	/*
	 * 获取照相的路径
	 */
	public final String getCameraPicturePath(){
		return storage_picture;
	}
	/*
	 * 获取录像的路径
	 */
	public final String getCameraShootingPath(){
		return storage_video;
	}

    /**
     * 获取配置文件的名字
     * @return
     */
    public final String getConfigFileName(){
        return storage_config;
    }
	/*
	 * 是否是在循环播放
	 */
	public final boolean getIsCyclePlayMode(){
		return isCyclePlayMode;
	}
	/*
	 * 设置是否循环播放
	 */
	public final void setIsCyclePlayMode(boolean opt){
		isCyclePlayMode = opt;
	}
	/*
	 * 判断是否是在录像
	 */
	public final boolean getIsCameraShooting(){
		return isCameraShooting;
	}
	/*
	 * 设置是否在录像
	 */
	public final void setIsCameraShooting(boolean opt){
		isCameraShooting = opt;
	}
	/*
	 * 获取SD卡的可用容量
	 */
	public final long getSDCapacity(){
		return SDCapacity;
	}
	/*
	 * 设置SD卡的可用容量
	 */
	public final void setSDCapacity(long capacity){
		SDCapacity = capacity;
	}
	/*
	 * 获取是否G-senser控制
	 */
	public final boolean getIsGSensorControl(){
		return isGSensorControl;
	}
	/*
	 * 设置是否G-senser控制
	 */
	public final void setIsGSensorControl(boolean opt){
		isGSensorControl = opt;
	}
	/*
	 * 设置是否打开前光灯
	 */
	public final void setIsBrightControl(boolean opt){
		isBrightControl = opt;
	}
	/*
	 * 获取是否打开前光灯
	 */
	public final boolean getIsBrightControl(){
		return isBrightControl;
	}
	/**
	 * 设置EL灯的开关
	 * @param opt
	 */
	public final void setIsELChange(boolean opt){
		isELChange = opt;
	}
	/**
	 * 判读是否打开EL灯
	 * @return
	 */
	public final boolean getIsELChange(){
		return isELChange;
	}
	/*
	 * 设置是否隐身模式
	 */
	public final void setIsStealthControl(boolean opt){
		isStealthControl = opt;
	}
	/*
	 * 获取是否打开了隐身模式
	 */
	public final boolean getIsStealthControl(){
		return isStealthControl;
	}
	
	
	/*
	 * 设置是否打开设备状态灯
	 */
	public final void setIsDeviceStateLedControl(boolean opt){
		isDeviceStateLedControl = opt;
	}
	/*
	 * 获取是否打开设备状态灯
	 */
	public final boolean getIsDeviceStateLedControl(){
		return isDeviceStateLedControl;
	}
	
	/**
	 * 设置sd卡是否挂载
	 * @param opt
	 */
	public final void setIsAvaiableSDCard(boolean opt){
		isAvaiableSDCard = opt;
	}
	/**
	 * 获取sd卡是否可用
	 * @return
	 */
	public final boolean getIsAvaiableSDCard(){
		return isAvaiableSDCard;
	}
	/**
	 * 设置录像录制的时间，用来限制录像最少时间
	 * @param opt
	 */
	public final void setShootingTimes(int opt){
		ShootingTimes = opt;
	}
	/**
	 * 获取录像录制的最少时间
	 * @return
	 */
	public final int getShootingTimes(){
		return ShootingTimes;
	}
	/**
	 * 设置ShootingTimes自增
	 */
	public final void addShootingTimes(){
		ShootingTimes++;
	}
	/**
	 *获取最多的有所路图的图片数量
	 * @return
	 */
	public final int getMAXPhoto(){
		return MAXPhoto;
	}
	/**
	 * 返回小布局滑动状态
	 * -1：表示没有滑动过，此时需要获取一些数据
	 * 0：代表初始状态
	 * @return
	 */
	public final int getScrollStatus(){
		return ScrollStatus;
	}
	/**
	 * 设置小布局滑动状态
	 * @param opt
	 */
	public final void setScrollStatus(int opt){
		ScrollStatus = opt;
	}
	public void setIsDoor(Boolean opt) {
		isDoor = opt;
		
	}
	
	public final boolean getIsDoor(){
		return isDoor;
		
	}
	
	public void setIsStopDoor(Boolean opt) {
		isStopDoor = opt;
		
	}
	
	public final boolean getIsStopDoor(){
		return isStopDoor;
	}
}
