package com.seuic.function;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;

import com.seuic.AppInforToCustom;
import com.seuic.AppLog;
import com.seuic.jni.AppDecodeH264;

public class AppCameraSurfaceFunction
{
	private static AppCameraSurfaceFunction appAppCameraSurfaceFunctionInstance = null;
	public AppCameraSurfaceFunction(){
		
	}
	public static AppCameraSurfaceFunction getAppCameraSurfaceFunctionInstance(){
		if (appAppCameraSurfaceFunctionInstance == null)
		{
			appAppCameraSurfaceFunctionInstance = new AppCameraSurfaceFunction();
		}
		return appAppCameraSurfaceFunctionInstance;
	}
	/*
	 * 初始化Opengl es 2.0配置
	 */
	public void CameraShow_GLCreate(){
		AppDecodeH264.GlCreate(Bitmap2Bytes());
	}
	/*
	 * 初始化Opengl es 2.0 SurfaceChanged
	 */
	public void CameraShow_GLInit(int width, int height){
		AppDecodeH264.GlInit(width, height);
	}
	/*
	 * 将解码后的数据渲染到Opengl es 上面
	 */
	public void CameraShow_Render(){
		AppDecodeH264.GlRender();
	}
	/*
	 * 视频显示区域放大
	 */
	public int CameraZoomIn(){
		if (targetZoom >= 0 && targetZoom < 4) {
			AppDecodeH264.GlZoomIn();
			targetZoom++;
		}
		return targetZoom;
	}
	/*
	 * 视频显示区域缩小
	 */
	public int CameraZoomOut(){
		if (targetZoom > 0 && targetZoom <= 4) {
			AppDecodeH264.GlZoomOut();
			targetZoom--;
		}
		return targetZoom;
	}
	/*
	 * 视频显示区域移动
	 */
	public void CameraMove(int diff_x, int diff_y){
		AppDecodeH264.GlMove(diff_x, diff_y);
	}
	/*
	 * 获取当前显示的大小的值
	 */
	public final float getCameraShowZoomValue() {
		return ZOOM[targetZoom];
	}
	StringBuilder fileBuilder = new StringBuilder();
	/*
	 * 拍照功能
	 */
	@SuppressLint("SimpleDateFormat")
	public void CameraTakePicture() {
		Date d = new Date(System.currentTimeMillis());
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		File tempdir = new File(AppInforToCustom.getAppInforToCustomInstance().getCameraPicturePath());
		if (tempdir.exists() == false){
			tempdir.mkdirs();
		}
		//底层的名称的分配内容为52，所以filepath的长度52
		fileBuilder.append(AppInforToCustom.getAppInforToCustomInstance().getCameraPicturePath());
		fileBuilder.append("/P");
		fileBuilder.append(dateFormat.format(d));
		fileBuilder.append(".jpg");
		AppDecodeH264.GlSnapFlag(fileBuilder.toString());
		AppLog.e(fileBuilder.toString());
		fileBuilder.delete(0, fileBuilder.length());
	}
	/*
	 * 创建一张黑色的图片
	 * 大小为Video_WandH[0],Video_WandH[1]
	 */
	private byte[] Bitmap2Bytes(){
	    Bitmap bitmap= Bitmap.createBitmap(Video_WandH[0], Video_WandH[1], Bitmap.Config.ARGB_8888);
	    byte[] buffer = new byte[bitmap.getWidth() * bitmap.getHeight() * 4];
        for ( int y = 0; y <bitmap.getHeight(); y++ )
            for ( int x = 0; x < bitmap.getWidth(); x++ )
            {
            	buffer[y * x] = 0;
            }
        return buffer;
	}
	private float[] ZOOM = new float[] { 100, 125, 150, 175, 200 };
	private int targetZoom = 0;
	public static int[] Video_WandH = {640, 480};
}
