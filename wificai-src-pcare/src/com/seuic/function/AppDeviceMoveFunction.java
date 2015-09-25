package com.seuic.function;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.Socket;

import android.annotation.SuppressLint;
import android.content.Context;
import com.seuic.*;
import com.seuic.protocol.GO_DIRECTION_CAR;
import com.seuic.util.ByteUtility;

public class AppDeviceMoveFunction {
	private static AppDeviceMoveFunction appDeviceMoveFunctionInstance = null;

	public AppDeviceMoveFunction() {

	}

	public static AppDeviceMoveFunction getAppDeviceMoveFunctionInstance() {
		if (appDeviceMoveFunctionInstance == null) {
			appDeviceMoveFunctionInstance = new AppDeviceMoveFunction();
		}
		return appDeviceMoveFunctionInstance;
	}

	/*
	 * 设备前进/停止
	 */
	public boolean DeviceMove_Forward(boolean opt) {
		boolean status = false;
		if (AppInforToSystem.ConnectStatus) {
			if (opt) {
				status = AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_forward_1);
			} else {
				status = AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_stop_1);
			}
		} else {
			status = false;
		}
		return status;
	}

	/*
	 * 设备后退/停止
	 */
	public boolean DeviceMove_Back(boolean opt) {
		boolean status = false;
		if (AppInforToSystem.ConnectStatus) {
			if (opt) {
				status = AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_back_1);
			} else {
				status = AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_stop_1);
			}
		} else {
			status = false;
		}
		return status;
	}

	/**
	 * 设备左转/停止
	 * 
	 * @param opt
	 * @return
	 */
	public boolean DeviceMove_Left(boolean opt) {
		boolean status = false;
		if (AppInforToSystem.ConnectStatus) {
			if (opt) {
				status = AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_back_2);
			} else {
				status = AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_stop_2);
			}
		} else {
			status = false;
		}
		return status;
	}

	/*
	 * 设备左前转/停止
	 */
	public boolean DeviceMove_Left_Forward(boolean opt) {
		boolean status = false;
		if (AppInforToSystem.ConnectStatus) {
			if (opt) {
				status = AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_back_2);
				status = AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_forward_1);
			} else {
				status = AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_stop_1);
				status = AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_stop_2);
			}
		} else {
			status = false;
		}
		return status;
	}

	public boolean DeviceMove_Right(boolean opt) {
		boolean status = false;
		if (AppInforToSystem.ConnectStatus) {
			if (opt) {
				status = AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_forward_2);
			} else {
				status = AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_stop_2);
			}
		} else {
			status = false;
		}
		return status;
	}

	/*
	 * 设备右前转/停止
	 */
	public boolean DeviceMove_Right_Forward(boolean opt) {
		boolean status = false;
		if (AppInforToSystem.ConnectStatus) {
			if (opt) {
				status = AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_forward_2);
				status = AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_forward_1);
			} else {
				status = AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_stop_1);
				status = AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_stop_2);
			}
		} else {
			status = false;
		}
		return status;
	}

	/*
	 * 设备后左转/停止
	 */
	public boolean DeviceMove_Left_Back(boolean opt) {
		boolean status = false;
		if (AppInforToSystem.ConnectStatus) {
			if (opt) {
				status = AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_back_2);
				status = AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_back_1);
			} else {
				status = AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_stop_1);
				status = AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_stop_2);
			}
		} else {
			status = false;
		}
		return status;
	}

	/*
	 * 设备后右转/停止
	 */
	public boolean DeviceMove_Right_Back(boolean opt) {
		boolean status = false;
		if (AppInforToSystem.ConnectStatus) {
			if (opt) {
				status = AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_forward_2);
				status = AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_back_1);
			} else {
				status = AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_stop_1);
				status = AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_stop_2);
			}
		} else {
			status = false;
		}
		return status;
	}

	public void G_Move_Record(int direction) {
		switch (direction) {
		case 1:
			DeviceMove_Right_Forward(true);
			break;
		case 2:
			DeviceMove_Forward(true);
			break;
		case 3:
			DeviceMove_Left_Forward(true);
			break;
		case 4:
			DeviceMove_Left_Back(true);
			break;
		case 5:
			DeviceMove_Back(true);
			break;
		case 6:
			DeviceMove_Right_Back(true);
			break;
		}
	}

	/*
	 * 虚拟摇杆操作 1:右转(右前转) 2:前进 3:左转(左前转) 4:左后转 5:后退 6:右后转
	 * 这部分还可以继续优化--》细化前一个步骤和后一个步骤命令的异同
	 */
	public void DeviceMove_FSVJoy(int angle) {
		if (((angle >= 0 && angle < 65) || angle > 350 && angle <= 360)
				&& direction != 1) {
			DeviceMove_PreDirect(direction);
			direction = 1;
			DeviceMove_Right_Forward(true);
		} else if (angle >= 65 && angle < 115 && direction != 2) {
			DeviceMove_PreDirect(direction);
			direction = 2;
			DeviceMove_Forward(true);
		} else if (angle >= 115 && angle < 190 && direction != 3) {
			DeviceMove_PreDirect(direction);
			direction = 3;
			DeviceMove_Left_Forward(true);
		} else if (angle >= 190 && angle < 245 && direction != 4) {
			DeviceMove_PreDirect(direction);
			direction = 4;
			DeviceMove_Left_Back(true);
		} else if (angle >= 245 && angle < 295 && direction != 5) {
			DeviceMove_PreDirect(direction);
			direction = 5;
			DeviceMove_Back(true);
		} else if (angle >= 295 && angle < 350 && direction != 6) {
			DeviceMove_PreDirect(direction);
			direction = 6;
			DeviceMove_Right_Back(true);
		}
	}

	/*
	 * 用于判断前一个方向，然后停止，在发送新的方向
	 */
	public void DeviceMove_PreDirect(int direction) {
		switch (direction) {
		case 1:
			DeviceMove_Right_Forward(false);
			break;
		case 2:
			DeviceMove_Forward(false);
			break;
		case 3:
			DeviceMove_Left_Forward(false);
			break;
		case 4:
			DeviceMove_Left_Back(false);
			break;
		case 5:
			DeviceMove_Back(false);
			break;
		case 6:
			DeviceMove_Right_Back(false);
			break;
		}
	}

	public void DeviceMove_ReMove(int gangle) {
		switch (gangle) {
		case 4:
			DeviceMove_Left_Forward(true);
			direction = 3;
			break;
		case 5:
			DeviceMove_Right_Forward(true);
			direction = 1;
			break;
		case 6:
			DeviceMove_Left_Back(true);
			direction = 4;
			break;
		case 7:
			DeviceMove_Right_Back(true);
			direction = 6;
			break;
		}
	}

	public void DeviceMove_Optimization(int angle, int gangle) {
		if (gangle != -1) {
			switch (gangle) {
			case 1:
				angle = 65;
				break;
			case 2:
				angle = 245;
				break;
			case 3:
				angle = -1;
				break;
			case 4:
				angle = 115;
				break;
			case 5:
				angle = 0;
				break;
			case 6:
				angle = 190;
				break;
			case 7:
				angle = 295;
				break;
			}
		}
		if (((angle >= 0 && angle < 65) || (angle > 350 && angle <= 360))
				&& direction != 1) {
			AppLog.e("test", "右转");
			DeviceMove_Pre_Optimization(direction, 1);
			direction = 1;
		} else if (angle >= 65 && angle < 115 && direction != 2) {
			AppLog.e("test", "前进");
			DeviceMove_Pre_Optimization(direction, 2);
			direction = 2;
		} else if (angle >= 115 && angle < 190 && direction != 3) {
			AppLog.e("test", "左转");
			DeviceMove_Pre_Optimization(direction, 3);
			direction = 3;
		} else if (angle >= 190 && angle < 245 && direction != 4) {
			AppLog.e("test", "左后转");
			DeviceMove_Pre_Optimization(direction, 4);
			direction = 4;
		} else if (angle >= 245 && angle < 295 && direction != 5) {
			AppLog.e("test", "后退");
			DeviceMove_Pre_Optimization(direction, 5);
			direction = 5;
		} else if (angle >= 295 && angle < 350 && direction != 6) {
			AppLog.e("test", "右后转");
			DeviceMove_Pre_Optimization(direction, 6);
			direction = 6;
		} else if (angle == -1) { // 停止
			AppLog.e("test", "停止");
			DeviceMove_Pre_Optimization(direction, 7);
			direction = 0;
		}
	}

	public void DeviceMove_Pre_Optimization(int pre_dir, int curr_dir) {
		switch (curr_dir) {
		case 1: // 右前转
			switch (pre_dir) {
			case 2: // 前进,左前转
			case 3:
				AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_forward_2);
				break;
			case 0:// 初始状态
			case 4:// 左后转
			case 5:// 后退
				DeviceMove_Right_Forward(true);
				break;
			case 6:// 右后转
				AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_forward_1);
				break;
			}
			break;
		case 2:// 前进
			switch (pre_dir) {
			case 1: // 右前转
			case 3: // 左前转
				AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_stop_2);
				break;
			case 4:// 左后转
			case 6:// 右后转
				AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_stop_2);
				AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_forward_1);
				break;
			case 0:// 初始状态
			case 5:// 后退
				DeviceMove_Forward(true);
				break;
			}
			break;
		case 3:// 左前转
			switch (pre_dir) {
			case 1:// 右前转
			case 2: // 前进
				AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_back_2);
				break;
			case 4:// 左后转
				AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_forward_1);
				break;
			case 0:// 初始状态
			case 5:// 后退
			case 6:// 右后转
				DeviceMove_Left_Forward(true);
				break;
			}
			break;
		case 4:// 左后转
			switch (pre_dir) {
			case 0:// 初始状态
			case 1:// 右前转
			case 2:// 前进
				DeviceMove_Left_Back(true);
				break;
			case 3:// 左前转
				AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_back_1);
				break;
			case 5:// 后退
			case 6:// 右后转
				AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_back_2);
				break;
			}
			break;
		case 5:// 后退
			switch (pre_dir) {
			case 1:// 右前进
			case 3:// 左前进
				AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_stop_2);
				AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_back_1);
				break;
			case 0:// 初始状态
			case 2:// 前进
				DeviceMove_Back(true);
				break;
			case 4:// 左后退
			case 6:// 右后退
				AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_stop_2);
				break;
			}
			break;
		case 6:// 右后退
			switch (pre_dir) {
			case 1:// 右前进
				AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_back_1);
				break;
			case 0:// 初始状态
			case 2:// 前进
			case 3:// 左前进
				DeviceMove_Right_Back(true);
				break;
			case 4:// 左后进
			case 5:// 后退
				AppCommand.getAppCommandInstace().moveCommand(
						GO_DIRECTION_CAR.move_forward_2);
				break;
			}
			break;
		case 7:// 停止
			switch (pre_dir) {
			case 1:// 右前进
			case 3:// 左前进
			case 4:// 左后退
			case 6:// 右后退
				DeviceMove_Right_Forward(false);
				break;
			case 2:// 前进
			case 5:// 后退
				DeviceMove_Forward(false);
				break;
			}
			break;
		}
	}

	/*
	 * 通知设备录制路径/停止录制路径
	 * 
	 * @param opt:true--开始录制/false--停止录制
	 */
	public void DeviceMove_ShootingPath(boolean opt) {
		if (AppInforToSystem.ConnectStatus) {
			if (opt) {
				AppCommand.getAppCommandInstace().DeviceControlCommand(11, 1);
			} else {
				AppCommand.getAppCommandInstace().DeviceControlCommand(11, 0);
			}
		}
	}

	/*
	 * 通知设备播放路径/停止播放路径
	 * 
	 * @param opt：true--开始播放路径/false--停止播放路径
	 */
	public void DeviceMove_PlayingPath(boolean opt) {
		if (AppInforToSystem.ConnectStatus) {
			if (opt) {
				AppCommand.getAppCommandInstace().DeviceControlCommand(12, 1);
			} else {
				AppCommand.getAppCommandInstace().DeviceControlCommand(12, 0);
			}
		}
	}

	/**
	 * 测试小车是否进行过路径录制
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean TestCarPlayFileIsExit(Context con, String fileName) {
		boolean isExit = false;
		String[] fileNameArray = con.fileList();
		for (int i = 0; i < fileNameArray.length; i++) {
			if (fileNameArray[i].equals(fileName)) {
				isExit = true;
			}
		}
		return isExit;
	}

	/*
	 * 将本地存放的录制的路径传送给设备
	 * 
	 * @param fileNamePath 存放播放路径文件的全路径，但不包括文件名字
	 */
	public void DeviceMove_TransPathToDevice() {
		FileInputStream fis = null;
		try {
			boolean isExit = TestCarPlayFileIsExit(
					AppConnect.getInstance().context, AppInforToCustom
							.getAppInforToCustomInstance().getConfigFileName());
			if (isExit) {
				byte direct = 1;
				byte[] bufferAvilable = new byte[1024];
				int dataAvailable = 1024, totalLenth = 0;
				socketRec = new Socket("192.168.1.100", 8080);
				dataOutputStreamRec = new DataOutputStream(
						socketRec.getOutputStream());
				dataOutputStreamRec.write(direct);
				fis = AppConnect.getInstance().context
						.openFileInput(AppInforToCustom
								.getAppInforToCustomInstance()
								.getConfigFileName());
				while (dataAvailable == 1024) { // 每次读取1024字节，当小于1024时，说明读取完毕
					dataAvailable = fis.read(bufferAvilable, 0, 1024);
					AppLog.e("dataAvailable", "" + dataAvailable);
					if (dataAvailable != -1) {
						dataOutputStreamRec.write(bufferAvilable, 0,
								dataAvailable);
						totalLenth += dataAvailable;
					}
				}
				AppLog.e("Track", "Track File Lenth is " + totalLenth);
				socketRec.close();
				fis.close();
				dataOutputStreamRec.close();
				// Thread.sleep(100);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dataInputStreamRec = null;
				socketRec = null;
				Thread.sleep(500); // 给设备准备时间
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/*
	 * 从设备端获取设备录制的路径文件
	 * 
	 * @param fileNamePath 存放播放路径文件的全路径，但不包括文件名字
	 */
	@SuppressLint("WorldReadableFiles")
	public void DeviceMove_TransPathFromDevie() {
		byte[] bufferAvilable = new byte[1024];
		int dataAvailable = 0;
		FileOutputStream fos = null;
		try {
			byte direct = 0;
			socketRec = new Socket("192.168.1.100", 8080);
			dataOutputStreamRec = new DataOutputStream(
					socketRec.getOutputStream());
			dataInputStreamRec = new DataInputStream(socketRec.getInputStream());
			dataOutputStreamRec.write(direct);
			boolean isExit = TestCarPlayFileIsExit(
					AppConnect.getInstance().context, AppInforToCustom
							.getAppInforToCustomInstance().getConfigFileName());
			if (isExit) {
				AppConnect.getInstance().context.deleteFile(AppInforToCustom
						.getAppInforToCustomInstance().getConfigFileName());
			}
			fos = AppConnect.getInstance().context.openFileOutput(
					AppInforToCustom.getAppInforToCustomInstance()
							.getConfigFileName(), Context.MODE_WORLD_READABLE);
			boolean isFirst = true;
			int temp_times = 0;
			int length = 0;
			while (isFirst) {
				dataAvailable = dataInputStreamRec.available();
				if (dataAvailable < 4) {
					Thread.sleep(50);
					if (temp_times < 20) {
						temp_times++;
					} else {
						break;
					}
				} else {
					isFirst = false;
					byte[] bHeader = new byte[4];
					dataInputStreamRec.read(bHeader, 0, 4);
					length = ByteUtility.byteArrayToInt(bHeader, 0, 4);
				}
			}
			AppLog.e("len:  "+length);
			temp_times = 0;
			while (!isFirst && length > 0) {
				dataAvailable = dataInputStreamRec.available();
				if (dataAvailable > 0) {
					if (dataAvailable > 1024) {
						dataAvailable = 1024;
					}
					AppLog.e("dataAvailable:  "+dataAvailable);
					dataInputStreamRec.read(bufferAvilable, 0, dataAvailable);
					fos.write(bufferAvilable, 0, dataAvailable);
					fos.flush();
					length = length - dataAvailable;
				} else if (dataAvailable == 0 && length != 0) {
					Thread.sleep(50);
					if (temp_times < 20) {
						temp_times++;
					} else {
						break;
					}
				}
			}
			fos.close();
			dataInputStreamRec.close();
			dataOutputStreamRec.close();
			socketRec.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dataInputStreamRec = null;
				dataOutputStreamRec = null;
				socketRec = null;
				bufferAvilable = null;
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	Socket socketRec = null;
	DataOutputStream dataOutputStreamRec = null;
	DataInputStream dataInputStreamRec = null;
	public static int direction = 0;
}
