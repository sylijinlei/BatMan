package com.wificar.surface;

import java.nio.ByteBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.batman.package1.WificarNew;
import com.seuic.AppInforToCustom;
import com.seuic.AppInforToSystem;
import com.seuic.AppLog;
import com.seuic.function.AppCameraSurfaceFunction;
import com.seuic.jni.AppDecodeH264;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CameraSurfaceView extends GLSurfaceView implements Renderer {
	public float[] ZOOM = new float[] { 100, 125, 150, 175, 200 };
	public int streamsize = 0;

	private int targetZoom = 0;

	public final int getTargetZoom() {
		return targetZoom;
	}
	/**
	 * 设置缩放倍数
	 * @param targetZoom
	 */
	public final void setTargetZoom(int targetZoom) {
		this.targetZoom = targetZoom;
	}

	/**
	 * 初始化显示屏幕
	 */
	public void zoomInit(){
		try {
			AppDecodeH264.GlZoomInit();
			targetZoom = 0;
		} catch (Exception e) {
		}
	}
	/**
	 * 放大
	 * @return
	 * @throws InterruptedException
	 */
	public int zoomIn() throws InterruptedException {

		if (targetZoom >= 0 && targetZoom < 4) {
			AppDecodeH264.GlZoomIn();
			targetZoom++;
		}
		return targetZoom;
	}
	/**
	 * 缩小
	 * @return
	 * @throws InterruptedException
	 */
	public int zoomOut() throws InterruptedException {
		if (targetZoom > 0 && targetZoom <= 4) {
			AppDecodeH264.GlZoomOut();
			targetZoom--;
		}
		return targetZoom;
	}

	public float getTargetZoomValue() {
		return ZOOM[targetZoom];
	}

	public void initial() {
		setEGLContextClientVersion(2);
		setRenderer(this);
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}

	public CameraSurfaceView(Context context) {
		super(context);
		initial();
	}

	public CameraSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initial();
	}
	
	float bx,bx1 = 0; // touch x;
	float by,by1 = 0; // touch y;
	long donw_time;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!WificarNew.isDrawSlider) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				bx = event.getX();// base x
				by = event.getY();// base y
				donw_time = System.currentTimeMillis();
				break;
			case MotionEvent.ACTION_MOVE:
				bx1 = event.getX();
				by1 = event.getY();
				AppDecodeH264.GlMove((bx1- bx), (by1-by));
				bx = event.getX();// base x
				by = event.getY();// base y
				break;
			case MotionEvent.ACTION_UP:
				if ((bx < 100 || bx > (mVideoWidth - 100)) && by < 100) {
					AppLog.e("test", "屏蔽区域");
					return false;
				}
				if (!AppInforToSystem.isCameraChanging && !AppInforToCustom.getAppInforToCustomInstance().getIsCameraShooting()) {
					if ((System.currentTimeMillis() - donw_time) < 500) {
					}
				}
				break;
			}
		}
		return true;
	}

	Bitmap bitmap;
	static int mVideoWidth = 640;
	static int mVideoHeight = 480;

	public Bitmap rgb565ToBitmap(byte[] data) {
		bitmap = Bitmap.createBitmap(mVideoWidth, mVideoHeight, Bitmap.Config.RGB_565);
		ByteBuffer buffer = ByteBuffer.wrap(data);
		bitmap.copyPixelsFromBuffer(buffer);
		return bitmap;
	}

	public void takePicture() {
		AppCameraSurfaceFunction.getAppCameraSurfaceFunctionInstance().CameraTakePicture();
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		AppCameraSurfaceFunction.getAppCameraSurfaceFunctionInstance().CameraShow_GLCreate();
	}
	
	public boolean isFirstChange = true;
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		mVideoWidth = width;
		mVideoHeight = height;
		if (isFirstChange) {
			AppCameraSurfaceFunction.getAppCameraSurfaceFunctionInstance().CameraShow_GLInit(width, height);
			isFirstChange = false;
		}
	}
	
	public void onDrawFrame(GL10 gl) {
		AppCameraSurfaceFunction.getAppCameraSurfaceFunctionInstance().CameraShow_Render();
	}
}
