package com.seuic.function;

import com.batman.package1.WificarNew;
import com.seuic.AppCommand;
import com.seuic.AppConnect;
import com.seuic.AppInforToCustom;
import com.seuic.AppLog;
import com.seuic.interfaces.CustomerInterface;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class AppGSenserFunction implements SensorEventListener
{
	public static final String TAG = "OriginSensor";
	private SensorManager mSensorManager;
	private Sensor aSensor;
	private float accDefaultX = 9999;
	private float accDefaultY = 9999;
	private float fBaseDefault = 9999;
	private int orientation = 0;
	private int current_dir = 0;
	private int times = 0;
	private double angle = 25;
	private float[] fValues = null;
	public boolean isFirst = true;
	private boolean isFirstEvent = true;
	public static int Sensor_Type = -1;
	public static final double Move_Value = 2.2;
	public static final double Move_Value_min = 0.5;
	public static final double Error_Forward_min = 1.6;
	public static final double Error_Back_max = 8.8;
	public static final double Error_Left  = 5;
	float mx, my;
	private WindowManager mWindowManager;
	private Display mDisplay;
	public static boolean isStop = true;
	public static boolean jet_G_Enable;
	private static AppGSenserFunction appGSenserFunctionInstance = null;
	public AppGSenserFunction(Context context){
		mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
	    mDisplay = mWindowManager.getDefaultDisplay();
	    orientation = mDisplay.getOrientation();
	}
	public static AppGSenserFunction getAppCameraSurfaceFunction(Context context){
		if (appGSenserFunctionInstance == null)
		{
			appGSenserFunctionInstance = new AppGSenserFunction(context);
		}
		return appGSenserFunctionInstance;
	}
	
	public void Check_Horizontal_Angle(){
		
	}
	/*
	 * 设置使用重力感应控制
	 */
	public void EnableGSensorControl(){
		/*
		 * 屏蔽虚拟摇杆功能
		 */
		if (Sensor_Type == -1) {
			SelectSensorType();
			if (Sensor_Type == 2) {
				
			}
		}else if(Sensor_Type != 2){
			aSensor = mSensorManager.getDefaultSensor(Sensor_Type);
			mSensorManager.registerListener(this, aSensor,SensorManager.SENSOR_DELAY_UI);
		}
		if (Sensor_Type != 2) {
			accDefaultX = fBaseDefault; //这样做的原因为每次重启，将重启的位置作为水平位置
			accDefaultY = fBaseDefault;
			AppInforToCustom.getAppInforToCustomInstance().setIsGSensorControl(true);
		}
		isFirst = true;
		isFirstEvent = true;
		times = 0;
		//AppConnect.getInstance().callBack(CustomerInterface.MESSAGE_GSENSER_START);
	}
	/*
	 * 优先选择方向传感器，如果没有方向传感器则选择加速度传感器，如果没有加速度传感器则屏蔽重力感应功能
	 * Sensor_Type = 3 ：方向传感器
	 * Sensor_Type = 1 ：加速度传感器
	 * Sensor_Type = 2：屏蔽重力感应功能
	 */
	public void SelectSensorType(){
		aSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		boolean ok = mSensorManager.registerListener(this, aSensor,SensorManager.SENSOR_DELAY_UI);
		if (ok) {
			Sensor_Type = Sensor.TYPE_ACCELEROMETER;
			angle = 2.5;
			AppLog.e(TAG, "启用加速度传感器");
		}else
		{
			aSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
			boolean ok2 = mSensorManager.registerListener(this, aSensor,SensorManager.SENSOR_DELAY_UI);
			if (ok2) {
				Sensor_Type = Sensor.TYPE_ORIENTATION;
				angle = 25;
				AppLog.e(TAG, "启用方向传感器");
			}else {
				Sensor_Type = 2;
				AppLog.e(TAG, "屏蔽重力感应功能");
			}
		}
	}
	/*
	 * 设置停止使用重力感应
	 */
	public void DisableGSensorControl(){
		AppInforToCustom.getAppInforToCustomInstance().setIsGSensorControl(false);
		mSensorManager.unregisterListener(this);
		AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_Optimization(0, 3);
		current_dir = 0;
		accDefaultX = fBaseDefault;
		accDefaultY = fBaseDefault;
	}
	public void onSensorChanged(SensorEvent event)
	{
		if (Sensor_Type == Sensor.TYPE_ACCELEROMETER) {
			fValues = event.values;
			if (accDefaultX == fBaseDefault) {
				accDefaultX = fValues[0];
			}
			if (accDefaultY == fBaseDefault) {
				accDefaultY = fValues[1];
			}
			mx = fValues[0] - accDefaultX;
			my = fValues[1] - accDefaultY;
			if (times < 4) {
				times++;
				return;
			}
			if (!isFirst) {
				if (orientation == 0) {
					if (my < -Move_Value && Math.abs(mx) < Move_Value_min && current_dir != 1) {
						current_dir = 1;  //前
						jet_G_Enable = true;
						Log.e("Gsensor", "----前----");
						AppLog.e("atest_pad", orientation+ "  "+current_dir+ "   vlaue[0]: "+mx+  "   value[1]:"+my);
						AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_Optimization(0, current_dir);
						
					}else if (my > Move_Value && Math.abs(mx) < Move_Value_min && current_dir != 2) {
						current_dir = 2;  //后
						jet_G_Enable = true;
						Log.e("Gsensor", "----后----");
						AppLog.e("atest_pad", orientation+ "  "+current_dir+ "   vlaue[0]: "+mx+  "   value[1]:"+my);
						AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_Optimization(0, current_dir);
					}else if (Math.abs(mx)  <=  Move_Value && Math.abs(my) <= Move_Value && current_dir != 3) {
						jet_G_Enable = false;
						current_dir = 3;  //停止
						Log.e("Gsensor", "----停止----");
//						AppLog.e("atest_pad", orientation+ "  "+current_dir+ "   vlaue[0]: "+mx+  "   value[1]:"+my);
						AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_Optimization(0, current_dir);
					}else if (mx > Move_Value && my <= Move_Value_min) {
						jet_G_Enable = true;
						if (current_dir != 8 && mx >= Error_Left) {
							current_dir = 8;  //左转/左前转 
							AppCommand.getAppCommandInstace().setDevice_MoveLRSpeedFlag(0);
							AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_ReMove(4);
						}else if (current_dir != 4 && mx < Error_Left) {
							AppCommand.getAppCommandInstace().setDevice_MoveLRSpeedFlag(1);
							if (current_dir == 8) {
								AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_ReMove(4);
							}else {
								AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_Optimization(0, 4);
							}
							current_dir = 4;  //左转/左前转
						}
					}else if (mx < -Move_Value && my <= Move_Value_min) {
						jet_G_Enable = true;
						if (current_dir != 9 && mx <= -Error_Left) {
							current_dir = 9;
							AppCommand.getAppCommandInstace().setDevice_MoveLRSpeedFlag(0);
							AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_ReMove(5);
						}else if(current_dir != 5 && mx > -Error_Left){
							AppCommand.getAppCommandInstace().setDevice_MoveLRSpeedFlag(1);
							if (current_dir == 9) {
								AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_ReMove(5);
							}else {
								AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_Optimization(0, 5);
							}
							current_dir = 5;  //右转/右前转
						}
					}else if (mx > Move_Value && my > Move_Value) {
						jet_G_Enable = true;
						if (current_dir != 10 && mx >= Error_Left) {
							current_dir = 10;
							AppCommand.getAppCommandInstace().setDevice_MoveLRSpeedFlag(0);
							AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_ReMove(6);
						}else if (current_dir != 6 && mx < Error_Left) {
							AppCommand.getAppCommandInstace().setDevice_MoveLRSpeedFlag(1);
							if (current_dir == 10) {
								AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_ReMove(6);
							}else {
								AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_Optimization(0, 6);
							}
							current_dir = 6;  //左后转
						}
					}else if (mx < -Move_Value && my > Move_Value) {
						jet_G_Enable = false;
						if (current_dir != 11 && mx <= -Error_Left) {
							current_dir = 11;
							AppCommand.getAppCommandInstace().setDevice_MoveLRSpeedFlag(0);
							AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_ReMove(7);
						}else if(current_dir != 7 && mx > -Error_Left){
							AppCommand.getAppCommandInstace().setDevice_MoveLRSpeedFlag(1);
							if (current_dir == 11) {
								AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_ReMove(7);
							}else {
								AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_Optimization(0, 7);
							}
							current_dir = 7;  //右后转
						}
					}
				}else if (orientation == 1) {
					if (mx < -Move_Value && Math.abs(my) < Move_Value_min && current_dir != 1) {
						current_dir = 1;  //前
						Log.e("Gsensor", "----前----");
						jet_G_Enable = true;
						AppLog.e("atest", orientation+ "  "+current_dir+ "   vlaue[0]: "+mx+  "   value[1]:"+my);
						AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_Optimization(0, current_dir);
					}else if (mx > Move_Value && Math.abs(my) < Move_Value_min && current_dir != 2) {
						current_dir = 2;  //后
						Log.e("Gsensor", "----后----");
						jet_G_Enable = true;
						AppLog.e("atest", orientation+ "  "+current_dir+ "   vlaue[0]: "+mx+  "   value[1]:"+my);
						AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_Optimization(0, current_dir);
					}else if (Math.abs(mx)  <=  Move_Value && Math.abs(my) <= Move_Value && current_dir != 3) {
						current_dir = 3;  //停止
						jet_G_Enable = false;
						AppCommand.getAppCommandInstace().setDevice_MoveLRSpeedFlag(0);
						Log.e("Gsensor","----停止----");
						AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_Optimization(0, current_dir);
					}else if (mx <= Move_Value_min && my < -Move_Value) {
						jet_G_Enable = true;
						if (current_dir != 8 && my <= -Error_Left) {
							current_dir = 8;  //左转/左前转 
							AppCommand.getAppCommandInstace().setDevice_MoveLRSpeedFlag(0);
							AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_ReMove(4);
						}else if (current_dir != 4 && my > -Error_Left) {
							AppCommand.getAppCommandInstace().setDevice_MoveLRSpeedFlag(1);
							if (current_dir == 8) {
								AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_ReMove(4);
							}else {
								AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_Optimization(0, 4);
							}
							current_dir = 4;  //左转/左前转
						}
					}else if (mx <= Move_Value_min && my > Move_Value) {
						jet_G_Enable = true;
						if (current_dir != 9 && my >= Error_Left) {
							current_dir = 9;
							AppCommand.getAppCommandInstace().setDevice_MoveLRSpeedFlag(0);
							AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_ReMove(5);
						}else if(current_dir != 5 && my < Error_Left){
							AppCommand.getAppCommandInstace().setDevice_MoveLRSpeedFlag(1);
							if (current_dir == 9) {
								AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_ReMove(5);
							}else {
								AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_Optimization(0, 5);
							}
							current_dir = 5;  //右转/右前转
						}
					}else if (mx > Move_Value && my < -Move_Value) {
						jet_G_Enable = true;
						if (current_dir != 10 && my <= -Error_Left) {
							current_dir = 10;
							AppCommand.getAppCommandInstace().setDevice_MoveLRSpeedFlag(0);
							AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_ReMove(6);
						}else if (current_dir != 6 && my > -Error_Left) {
							AppCommand.getAppCommandInstace().setDevice_MoveLRSpeedFlag(1);
							if (current_dir == 10) {
								AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_ReMove(6);
							}else {
								AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_Optimization(0, 6);
							}
							current_dir = 6;  //左后转
						}
					}else if (mx > Move_Value && my > Move_Value) {
						jet_G_Enable = true;
						if (current_dir != 11 && my >= Error_Left) {
							current_dir = 11;
							AppCommand.getAppCommandInstace().setDevice_MoveLRSpeedFlag(0);
							AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_ReMove(7);
						}else if(current_dir != 7 && my < Error_Left){
							AppCommand.getAppCommandInstace().setDevice_MoveLRSpeedFlag(1);
							if (current_dir == 11) {
								AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_ReMove(7);
							}else {
								AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_Optimization(0, 7);
							}
							current_dir = 7;  //右后转
						}
					}else if (isFirst) {
						
					}
				}
				
				if (current_dir == 3) {
					if (isStop != false) {
					AppConnect.getInstance().callBack(CustomerInterface.MESSAGE_G_VOLUME_ENABLE);
					}
					isStop  = true;
				} else {
					if (isStop == true) {
					AppConnect.getInstance().callBack(CustomerInterface.MESSAGE_G_VOLUME_DISABLE);
					}
					isStop = false;
				}
				
			}else {
				if (isFirstEvent) {
					isFirstEvent = false;
					if (orientation == 0) {//pad
						if (fValues[1] > Error_Back_max || fValues[1] < -Error_Forward_min) {
							AppLog.e("testpad", orientation+ "   vlaue[0]: "+fValues[0]+  "   value[1]:"+fValues[1]);
							WificarNew.instance.showDialog();
						}else {
							isFirst = false;
						}
					}else if (orientation == 1) {//phone
						if (fValues[0] > Error_Back_max || fValues[0] < -Error_Forward_min) {
							AppLog.e("test_phone", orientation+ "   vlaue[0]: "+fValues[0]+  "   value[1]:"+fValues[1]);
							WificarNew.instance.showDialog();
						}else {
							isFirst = false;
						}
					}
					AppConnect.getInstance().callBack(CustomerInterface.MESSAGE_GSENSER_START);
				}
			}
		}else if (Sensor_Type == Sensor.TYPE_ORIENTATION) {
				fValues = event.values;
				AppLog.e("test_1", "vlaue[0]: "+fValues[0]+  "   value[1]:"+fValues[1]+"  value[2]:"+fValues[2]);
				AppLog.e("TYPE_ORIENTATION", "TYPE_ORIENTATION ------");
			if (orientation == 0) {
				if (fValues[1] > angle && (Math.abs(fValues[2]) <  5) && current_dir != 1) {  
					current_dir = 1;  //前
					jet_G_Enable = true;
					AppLog.e("atest", orientation+ "  "+current_dir);
					AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_Optimization(0, current_dir);
				}else if (fValues[1] < -angle && (Math.abs(fValues[2])  <  5) && current_dir != 2) {
					current_dir = 2;  //后
					jet_G_Enable = true;
					AppLog.e("atest", orientation+ "  "+current_dir);
					AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_Optimization(0, current_dir);
				}else if (Math.abs(fValues[1])  <  5 && Math.abs(fValues[2]) < 5 && current_dir != 3) {
					current_dir = 3;  //停止
					jet_G_Enable = false;
					AppLog.e("atest", orientation+ "  "+current_dir);
					AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_Optimization(0, current_dir);
				}else if (fValues[1] > 0 && fValues[2] > 15 && current_dir != 4) {
					current_dir = 4;  //左转/左前转
					jet_G_Enable = true;
					AppLog.e("atest", orientation+ "  "+current_dir);
					AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_Optimization(0, current_dir);
				}else if (fValues[1] > 0 && fValues[2] < -15 && current_dir != 5) {
					current_dir = 5;  //右转/右前转
					jet_G_Enable = true;
					AppLog.e("atest", orientation+ "  "+current_dir);
					AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_Optimization(0, current_dir);
				}else if (fValues[1] < -15 && fValues[2] > 15 && current_dir != 6) {
					current_dir = 6;  //左后转
					jet_G_Enable = true;
					AppLog.e("atest", orientation+ "  "+current_dir);
					AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_Optimization(0, current_dir);
				}else if (fValues[1] < -15 && fValues[2] < -15 && current_dir != 7) {
					current_dir = 7;  //右后转
					jet_G_Enable = true;
					AppLog.e("atest", orientation+ "  "+current_dir);
					AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_Optimization(0, current_dir);
				}
			}else if (orientation == 1) {
				if (fValues[2] < -angle && (Math.abs(fValues[1])  <  5) && current_dir != 1) {  
					current_dir = 1;  //前
					jet_G_Enable = true;
					AppLog.e("atest", orientation+ "  "+current_dir);
					AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_Optimization(0, current_dir);
				}else if (fValues[2] > angle && (Math.abs(fValues[1])  <  5) && current_dir != 2) {
					current_dir = 2;  //后
					jet_G_Enable = true;
					AppLog.e("atest", orientation+ "  "+current_dir);
					AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_Optimization(0, current_dir);
				}else if (Math.abs(fValues[1])  <  5 && Math.abs(fValues[2]) < 5 && current_dir != 3) {
					current_dir = 3;  //停止
					jet_G_Enable = false;
					AppLog.e("atest", orientation+ "  "+current_dir);
					AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_Optimization(0, current_dir);
				}else if (fValues[1] > 15 && fValues[2] <= 5 && current_dir != 4) {
					current_dir = 4;  //左转/左前转
					jet_G_Enable = true;
					AppLog.e("atest", orientation+ "  "+current_dir);
					AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_Optimization(0, current_dir);
				}else if (fValues[1] < -15 && fValues[2] <= 5 && current_dir != 5) {
					current_dir = 5;  //右转/右前转
					jet_G_Enable = true;
					AppLog.e("atest", orientation+ "  "+current_dir);
					AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_Optimization(0, current_dir);
				}else if (fValues[1] > 15 && fValues[2] > 15 && current_dir != 6) {
					current_dir = 6;  //左后转
					jet_G_Enable = true;
					AppLog.e("atest", orientation+ "  "+current_dir);
					AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_Optimization(0, current_dir);
				}else if (fValues[1] < -15 && fValues[2] > 15 && current_dir != 7) {
					current_dir = 7;  //右后转
					jet_G_Enable = true;
					AppLog.e("atest", orientation+ "  "+current_dir);
					AppDeviceMoveFunction.getAppDeviceMoveFunctionInstance().DeviceMove_Optimization(0, current_dir);
				}
			}
			
			if (current_dir == 3) {
				AppConnect.getInstance().callBack(CustomerInterface.MESSAGE_G_VOLUME_ENABLE);
			} else {
				AppConnect.getInstance().callBack(CustomerInterface.MESSAGE_G_VOLUME_DISABLE);
			}
		}
	}
	
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{
		
	}
}
