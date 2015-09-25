package com.batman.package1;

import com.seuic.AppInforToCustom;
import com.seuic.interfaces.CustomerInterface;
import com.wificar.surface.CameraSurfaceView;
import com.wificar.util.ImageUtility;

public class BigeyeCallBack implements CustomerInterface
{
	CameraSurfaceView cameraSurfaceView = null;
	public void setCameraSurfaceView(CameraSurfaceView Cam){
		cameraSurfaceView = Cam;
	}
	public void callbackCall(int opt)
	{
		switch (opt)
		{
		case CustomerInterface.MESSAGE_BATTERY_CHANGE:
			//电池电量发生改变
			int value = AppInforToCustom.getAppInforToCustomInstance().getBatteryValue();
			int section = ImageUtility.getBatterySection(value);
			if (section == 0) {
				WificarNew.instance.sendMessages(WificarNew.MESSAGE_BATTERY_0);
			} else if (section == 1) {
				WificarNew.instance.sendMessages(WificarNew.MESSAGE_BATTERY_25);
			} else if (section == 2) {
				WificarNew.instance.sendMessages(WificarNew.MESSAGE_BATTERY_50);
			} else if (section == 3) {
				WificarNew.instance.sendMessages(WificarNew.MESSAGE_BATTERY_75);
			} else if (section == 4) {
				WificarNew.instance.sendMessages(WificarNew.MESSAGE_BATTERY_100);
			}
			break;
		case CustomerInterface.MESSAGE_CAMERADATA_CHANGE:
			//视频数据发生改变
			cameraSurfaceView.requestRender();
			break;
		case CustomerInterface.MESSAGE_HEARTBEAT_WARNING:
			WificarNew.instance.sendMessages(WificarNew.MESSAGE_HEARTBEAT_WARNING);
			//心跳包发出警报
			break;
		case CustomerInterface.MESSAGE_RECORDPATH_COMPLETE:
			//录制路径结束
		//	WificarNew.instance.sendMessages(WificarNew.MESSAGE_RECORDPATH_COMPLETE);
			break;
		case CustomerInterface.MESSAGE_RECODEPATH_START:
			//录制路径开始
			break;
		case CustomerInterface.MESSAGE_PLAYPATH_END:
			WificarNew.instance.sendMessages(WificarNew.MESSAGE_PLAYPATH_END);
			break;
		case CustomerInterface.MESSAGE_SHOOTING_START:
			//录像开始
			WificarNew.instance.sendMessages(WificarNew.MESSAGE_SHOOTING_START);
			break;
		case CustomerInterface.MESSAGE_NO_SHOOTING:
			WificarNew.instance.sendMessages(WificarNew.MESSAGE_NO_SHOOTING);
			break;
		case CustomerInterface.MESSAGE_NO_RECORD:
			WificarNew.instance.sendMessages(WificarNew.MESSAGE_NO_RECORD);
			break;
		case CustomerInterface.MESSAGE_SHOOTING_COMPLETE:
			//录像结束
			WificarNew.instance.sendMessages(WificarNew.MESSAGE_SHOOTING_COMPLETE);
			break;
		case CustomerInterface.MESSAGE_PHOTOGRAPH_END:
			//拍照结束
			WificarNew.instance.sendMessages(WificarNew.MESSAGE_PHOTOGRAPH_END);
			break;
		case CustomerInterface.MESSAGE_PHONE_BATTERY_LOW:
			//手机电量低的时候
			break;
		case CustomerInterface.MESSAGE_PHONE_CAPACITY_LOW:
			WificarNew.instance.sendMessages(WificarNew.MESSAGE_PHONE_CAPACITY_LOW);
			//手机容量低的时候
			break;
		case CustomerInterface.MESSAGE_GSENSER_START:
			//重力感应控制--隐藏虚拟摇杆,同时还要判断是否含有方向传感器或者加速度传感器
			WificarNew.instance.sendMessages(WificarNew.MESSAGE_GSENSER_START);
			break;
		case CustomerInterface.MESSAGE_GSENSER_END:
			//重力感应控制结束--显示虚拟摇杆
			break;
		case CustomerInterface.MESSAGE_SPK_START_SUCCESS:
			
			break;
		case CustomerInterface.MESSAGE_SPK_END_SUCCESS:
			//收到终端的结束请求回复或者超时3秒
			WificarNew.instance.sendMessages(WificarNew.MESSAGE_SPK_END_SUCCESS);
			break;
		case CustomerInterface.MESSAGE_SOCKER_EOF_ERROR:
			//由于其他的原因例如远程的设备的wifi模块没有关闭但是设备本身重启了，造成socket关闭生成EOF错误
			WificarNew.instance.sendMessages(WificarNew.MESSAGE_PING_FAIL);
			break;
		case CustomerInterface.MESSAGE_CAMERACHANGE_END:
			WificarNew.instance.sendMessages(WificarNew.MESSAGE_CAMERACHANGE_END);
			break;
		case CustomerInterface.MESSAGE_SCROLL_LR_FLAG:
			WificarNew.instance.sendMessages(WificarNew.MESSAGE_SCROLL_LR_FLAG);
			break;
		case CustomerInterface.MESSGE_SCROLL_RIGHT:
			WificarNew.instance.sendMessages(WificarNew.MESSGE_SCROLL_RIGHT);
			break;
		case CustomerInterface.MESSAGE_CAMERA_DEGREE:
			WificarNew.instance.sendMessages(WificarNew.MESSAGE_CAMERA_DEGREE);
			break;
		case CustomerInterface.MESSAGE_CAMERARESET_END:
			WificarNew.instance.sendMessages(WificarNew.MESSAGE_CAMERA_RESET_END);
			break;
		case CustomerInterface.MESSAGE_JET_END:
			WificarNew.instance.sendMessages(WificarNew.MESSAGE_JET_END);
			break;
		case CustomerInterface.MESSAGE_DOOR_DONE:
			WificarNew.instance.sendMessages(WificarNew.MESSAGE_DOOR_DONE);
			break;	
		case CustomerInterface.MESSAGE_G_VOLUME_ENABLE:
			WificarNew.instance.sendMessages(WificarNew.MESSAGE_G_VOLUME_ENABLE);
			break;
		case CustomerInterface.MESSAGE_G_VOLUME_DISABLE:
			WificarNew.instance.sendMessages(WificarNew.MESSAGE_G_VOLUME_DISABLE);
			break;
		case CustomerInterface.MESSGE_FUNC:
			WificarNew.instance.sendMessages(WificarNew.MESSGE_FUNC);
			break;
		case CustomerInterface.MESSGE_FUNC_START:
			WificarNew.instance.sendMessages(WificarNew.MESSGE_FUNC_START);
			break;
		case CustomerInterface.MESSGE_FUNC_STOP:
			WificarNew.instance.sendMessages(WificarNew.MESSGE_FUNC_STOP);
			break;
		case CustomerInterface.MESSGE_SPOILER:
			WificarNew.instance.sendMessages(WificarNew.MESSGE_SPOILER);
			break;
		case CustomerInterface.MESSGE_CHARGING_STATUS:
			WificarNew.instance.sendMessages(WificarNew.MESSGE_CHARGING_STATUS);
			break;
		}
	}
}
