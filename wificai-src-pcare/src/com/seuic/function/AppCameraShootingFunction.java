package com.seuic.function;

import java.io.File;

import android.graphics.Bitmap;

import com.beelinker.media.mp4mux.AppCameraShooting;
import com.seuic.AppConnect;
import com.seuic.AppInforToCustom;
import com.seuic.AppInforToSystem;
import com.seuic.AppLog;
import com.seuic.interfaces.CustomerInterface;
import com.seuic.thread.AppThread;

public class AppCameraShootingFunction {
	private final String TAG = AppCameraShootingFunction.class.getSimpleName();
	private static AppCameraShootingFunction appCameraShootingFunctionInstance = null;
	public AppCameraShootingFunction(){
		
	}
	public static AppCameraShootingFunction getAppCameraShootingFunctionInstance(){
		if (appCameraShootingFunctionInstance == null) {
			appCameraShootingFunctionInstance = new AppCameraShootingFunction();
		}
		return appCameraShootingFunctionInstance;
	}
	String filepath = "";
	/*
	 * 初始化视频参数
	 * @param filePath:存放视频的位置
	 */
	public void ShootingInit(int type){
		AppLog.i(TAG, "ShootingInit");
		if (AppInforToSystem.ConnectStatus) {
			String date = new java.sql.Date(new java.util.Date().getTime()).toString();
			java.sql.Timestamp time = new java.sql.Timestamp(new java.util.Date().getTime());
			String strTime = time.getHours() + "-" + time.getMinutes() + "-" + time.getSeconds();
			File tempdir = new File(AppInforToCustom.getAppInforToCustomInstance().getCameraShootingPath());
			if (tempdir.exists() == false){
				tempdir.mkdirs();
			}
			filepath = AppInforToCustom.getAppInforToCustomInstance().getCameraShootingPath() + "/V" + date + "_" + strTime + ".mp4";
			AppInforToSystem.isCameraShootingInitSuccess = AppCameraShooting.mp4init(filepath, type);
			if (AppInforToSystem.isCameraShootingInitSuccess) {
				AppInforToCustom.getAppInforToCustomInstance().setIsCameraShooting(true);
				/*
				 * 判断是否是录制路径--录像
				 */
			}else {//如果初始化失败，则判断是否有这个视频文件，如果有则删除文件
				File file_error = new File(filepath);
				if (file_error.exists()) {
					file_error.delete();
				}
				AppLog.e("recode", "camer_shoot_init_error");
				AppConnect.getInstance().callBack(CustomerInterface.MESSAGE_SHOOTING_COMPLETE);
			}
		}
	}
	/*
	 * 录制视频数据
	 * @param data 视频数据
	 * @param size 视频数据大小
	 */
	public void ShootingVideoData(byte[] data, int size, int keyframe, int frame_time){
		AppLog.i(TAG, "ShootingVideoData");
		if (AppInforToSystem.ConnectStatus) {
			AppCameraShooting.mp4packVideo(data, size, keyframe, frame_time);
		}
	}
	/*
	 * 录制音频数据
	 * @param data 音频数据
	 * @param size 音频数据大小
	 */
	public void ShootingAudioData(byte[] data,int size, int frame_time){
		AppLog.i(TAG, "ShootingAudioData");
		if (AppInforToSystem.ConnectStatus) {
			AppCameraShooting.mp4packAudio(data, size, frame_time);
		}
	}
	/*
	 * 关闭视频录制
	 */
	public void ShootingClose(){
		AppLog.i(TAG, "ShootingClose");
		try {
			if (AppInforToSystem.ConnectStatus && AppInforToSystem.isCameraShootingInitSuccess) {
				AppInforToSystem.isCameraShootingInitSuccess = false;
				AppCameraShooting.mp4close();
				AppThread.getAppThreadInstance().setFirstIFrame(true);
				AppInforToCustom.getAppInforToCustomInstance().setIsCameraShooting(false);
			}else if (!AppInforToSystem.isCameraShootingInitSuccess) {
				AppLog.e("error", "播放路径重启的错误原因");
			}
			AppInforToCustom.getAppInforToCustomInstance().setShootingTimes(0);
		} catch (Exception e) {
		}
		
	}
	/**
     * 获取视频缩略图
     * @param videoPath
     * @param width
     * @param height
     * @param kind
     * @return
     */
    private void getVideoThumbnail(Bitmap bitmap, String videoPath, int width , int height){
		
	}
}
