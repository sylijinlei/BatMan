package com.seuic.function;

import com.seuic.AppCommand;
import com.seuic.AppInforToSystem;
import com.seuic.protocol.GO_DIRECTION;

public class AppCameraMoveFunction
{
	private static AppCameraMoveFunction appCameraMoveFunctionInstance = null;
	public AppCameraMoveFunction(){
		
	}
	public static AppCameraMoveFunction getAppCameraMoveFunctionInstance(){
		if (appCameraMoveFunctionInstance == null)
		{
			appCameraMoveFunctionInstance = new AppCameraMoveFunction();
		}
		return appCameraMoveFunctionInstance;
	}
	/*
	 * 摄像头上/停止
	 */
	public void CameraMove_Up(boolean opt){
		if (AppInforToSystem.ConnectStatus)
		{
			if (opt) {
				AppCommand.getAppCommandInstace().cameraMoveCommand(GO_DIRECTION.Up);
			}else {
				AppCommand.getAppCommandInstace().cameraMoveCommand(GO_DIRECTION.UpS);
			}
		}
	}
	/*
	 * 摄像头下/停止
	 */
	public void CameraMove_Down(boolean opt){
		if (AppInforToSystem.ConnectStatus)
		{
			if (opt) {
				AppCommand.getAppCommandInstace().cameraMoveCommand(GO_DIRECTION.Down);
			}else {
				AppCommand.getAppCommandInstace().cameraMoveCommand(GO_DIRECTION.DownS);
			}
		}
	}
	/*
	 * 摄像头左/停止
	 */
	public void CameraMove_Left(boolean opt){
		if (AppInforToSystem.ConnectStatus) {
			if (opt) {
				AppCommand.getAppCommandInstace().cameraMoveCommand(GO_DIRECTION.Left);
			}else {
				AppCommand.getAppCommandInstace().cameraMoveCommand(GO_DIRECTION.LeftS);
			}
		}
	}
	/*
	 * 摄像头右/停止
	 */
	public void CameraMove_Right(boolean opt){
		if (AppInforToSystem.ConnectStatus) {
			if (opt) {
				AppCommand.getAppCommandInstace().cameraMoveCommand(GO_DIRECTION.Right);
			}else {
				AppCommand.getAppCommandInstace().cameraMoveCommand(GO_DIRECTION.RightS);
			}
		}
	}
	/**
	 * 用来关闭按钮点击的摄像头移动
	 */
	public void Camera_Move_Stop(){
		switch (AppInforToSystem.Camera_Move_Direct_flag) {
		case GO_DIRECTION.Up:
			CameraMove_Up(false);
			break;
		case GO_DIRECTION.Down:
			CameraMove_Down(false);	
			break;
		case GO_DIRECTION.Left:
			CameraMove_Left(false);
			break;
		case GO_DIRECTION.Right:
			CameraMove_Right(false);
			break;
		default:
			AppInforToSystem.Camera_Move_Direct_flag = 8;
			break;
		}
	}
	/**
	 * 根据不同的角度来实现不同的功能
	 * 虚拟摇杆操作
	 * 0:右转             0-20
	 * 1:上+右	 20-70
	 * 2:上	 	 70-110
	 * 3:左+上	 110-160
	 * 4:左转	 190-200
	 * 5:左+下	 200-250
	 * 6:下 		 250-290
	 * 7:右+下 	 290-360
	 * 8:停止
	 * @param angle
	 */
	public void CameraMove_FSVJoy_Optimization(int angle){
		if (angle >=0 && angle < 20 && cameraMoveDirection != 0) {
			CameraMove_Pre_Optimization(cameraMoveDirection, 0);
			cameraMoveDirection = 0; //right
		}else if (angle >= 20 && angle < 70 && cameraMoveDirection != 1) {
			CameraMove_Pre_Optimization(cameraMoveDirection, 1);
			cameraMoveDirection = 1; //right + up
		}else if (angle >= 70 && angle < 110 && cameraMoveDirection != 2) {
			CameraMove_Pre_Optimization(cameraMoveDirection, 2);
			cameraMoveDirection = 2; //up
		}else if (angle >= 110 && angle < 160 && cameraMoveDirection != 3) {
			CameraMove_Pre_Optimization(cameraMoveDirection, 3);
			cameraMoveDirection = 3; //left + up
		}else if (angle >= 160 && angle < 200 && cameraMoveDirection != 4) {
			CameraMove_Pre_Optimization(cameraMoveDirection, 4);
			cameraMoveDirection = 4; //left
		}else if (angle >= 200 && angle < 250 && cameraMoveDirection != 5) {
			CameraMove_Pre_Optimization(cameraMoveDirection, 5);
			cameraMoveDirection = 5; //left + down
		}else if (angle >= 250 && angle < 290 && cameraMoveDirection != 6) {
			CameraMove_Pre_Optimization(cameraMoveDirection, 6);
			cameraMoveDirection = 6; //down
		}else if (angle >= 290 && angle < 360 && cameraMoveDirection != 7) {
			CameraMove_Pre_Optimization(cameraMoveDirection, 7);
			cameraMoveDirection = 7; //right + down
		}
	}
	/**
	 * 利用上一次方向和当前方向，来减少发命令的次数
	 * @param pre_dir
	 * @param curr_dir
	 */
	public void CameraMove_Pre_Optimization(int pre_dir, int curr_dir){
		switch (curr_dir) {
			case 0://right
				switch (pre_dir) {
					case 1://right + up
						CameraMove_Up(false);
						break;
					case 2://up
					case 3://left + up
						CameraMove_Up(false);
						CameraMove_Right(true);
						break;
					case 4://left
					case 8://stop
						CameraMove_Right(true);
						break;
					case 5://left + down
					case 6://down
						CameraMove_Down(false);
						CameraMove_Right(true);
						break;
					case 7://right + down
						CameraMove_Down(false);
						break;
				}
				break;
			case 1://up +　right
				switch (pre_dir) {
					case 0://right
						CameraMove_Up(true);
						break;
					case 2://up
					case 7://right + down
						CameraMove_Right(true);
						break;
					case 3://left + up
						CameraMove_Left(true);
						break;
					case 4://left
					case 5://left + down
					case 6://down
					case 8://stop
						CameraMove_Right(true);
						CameraMove_Up(true);
						break;
				}
				break;
			case 2://up
				switch (pre_dir) {
					case 0://right
					case 7://right + down
						CameraMove_Right(false);
						CameraMove_Up(true);
						break;
					case 1://up + right
						CameraMove_Right(false);
						break;
					case 3://up + left
						CameraMove_Left(false);
						break;
					case 4://left
					case 5://left + down
						CameraMove_Left(false);
						CameraMove_Up(true);
						break;
					case 6://down
					case 8://stop
						CameraMove_Up(true);
						break;
					}
				break;
			case 3://left + up
				switch (pre_dir) {
					case 0://right
					case 6://down
					case 7://right + down
					case 8://stop
						CameraMove_Left(true);
						CameraMove_Up(true);
						break;
					case 1://right + up
					case 2://up
						CameraMove_Left(true);
						break;
					case 4://left
					case 5://left + down
						CameraMove_Up(true);
						break;
				}
				break;
			case 4://left
				switch (pre_dir) {
					case 0://right
					case 8://stop
						CameraMove_Left(true);
						break;
					case 1://up + right
					case 2://up
						CameraMove_Up(false);
						CameraMove_Left(true);
						break;
					case 3://left + up
						CameraMove_Up(false);
						break;
					case 5://left + down
						CameraMove_Down(false);
						break;
					case 6://down
					case 7://right + down
						CameraMove_Down(false);
						CameraMove_Left(true);
						break;
				}
				break;
			case 5://left + down
					switch (pre_dir) {
					case 0://right
					case 1://up + right
					case 2://up
					case 8://stop
						CameraMove_Left(true);
						CameraMove_Down(true);
						break;
					case 3://up + left
					case 4://left
						CameraMove_Down(true);
						break;
					case 6://down
					case 7://right + down
						CameraMove_Left(true);
						break;
					}
				break;
			case 6://down
				switch (pre_dir) {
					case 0://right
					case 1://right + up
						CameraMove_Right(false);
						CameraMove_Down(true);
						break;
					case 2://up
					case 8://stop
						CameraMove_Down(true);
						break;
					case 3://left + up
					case 4://left
						CameraMove_Left(false);
						CameraMove_Down(true);
						break;
					case 5://left + down
						CameraMove_Left(false);
						break;
					case 7://right + down
						CameraMove_Right(false);
						break;
				}
				break;
			case 7://right + down
				switch (pre_dir) {
					case 0://right
					case 1://right + up
						CameraMove_Down(true);
						break;
					case 2://up
					case 3://left + up
					case 4://left
					case 8://stop
						CameraMove_Down(true);
						CameraMove_Up(true);
						break;
					case 5://left + down
					case 6://down
						CameraMove_Right(true);
						break;
				}
				break;
			case 8://stop
				switch (pre_dir) {
					case 0://right
						CameraMove_Right(false);
						break;
					case 1://right + up
						CameraMove_Right(false);
						CameraMove_Up(false);
						break;
					case 2://up
						CameraMove_Up(false);
						break;
					case 3://left + up
						CameraMove_Left(false);
						CameraMove_Up(false);
						break;
					case 4://left
						CameraMove_Left(false);
						break;
					case 5://left + down
						CameraMove_Left(false);
						CameraMove_Down(false);
						break;
					case 6://down
						CameraMove_Down(false);
						break;
					case 7://right + down
						CameraMove_Right(false);
						CameraMove_Down(false);
						break;
				}
				break;
		}
	}
	public static int cameraMoveDirection = 8;
}
