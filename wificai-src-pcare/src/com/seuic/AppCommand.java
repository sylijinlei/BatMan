package com.seuic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.seuic.protocol.AV_OP_CODE;
import com.seuic.protocol.CMD_OP_CODE;
import com.seuic.util.BlowFishLong;
import com.seuic.util.ByteUtility;

public class AppCommand
{
	
	private static AppCommand appConnectInstance = null;
	
	public AppCommand(){
		
	}
	
	public static AppCommand getAppCommandInstace(){
		if (appConnectInstance == null)
		{
			appConnectInstance = new AppCommand();
		}
		return appConnectInstance;
	}
	
	/*
	 * 发送操作命令
	 * @param inCommand
	 */
	public synchronized Boolean sendCommand(int inCommand) {
		// if not connected, return false directly
		if (!AppInforToSystem.ConnectStatus){
			return false;
		}
		Boolean bRet = false;
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		String szHeader = "MO_O";
		int cX = 0;

		switch (inCommand) {
		case CMD_OP_CODE.Login_Req:
			// 0 ~ 3 is "MO_O"
			bOut.write(szHeader.getBytes(), 0, 4);
			// 4 ~ 7 is command
			try {
				bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Login_Req));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 8 ~ 14 set to 0
			for (cX = 8; cX < 15; cX++)
				bOut.write(0x00);
			// 15 ~ 18 is the length = 16
			try {
				bOut.write(ByteUtility.int32ToByteArray(16));
			} catch (IOException e) {
				e.printStackTrace();
			}
			for (cX = 19; cX < 23; cX++)
				bOut.write(0x00);
			for (cX = 0; cX < 4; cX++) {
				try {
					// send 4 ch_num
					bOut.write(ByteUtility.int32ToByteArray(AppInforToSystem.ChNum[cX]));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			BlowFishLong.getBlowFishLongInstance().BlowfishEncrption(AppInforToSystem.ChNum, AppInforToSystem.ChNum.length);
			// for(cX=0;cX<4;cX++)
			// Log.e("ChNum","ChNum["+cX+"]is "+ChNum[cX]);
			break;

		case CMD_OP_CODE.Verify_Req:
			// 0 ~ 3 is "MO_O"

			bOut.write(szHeader.getBytes(), 0, 4);
			// 4 ~ 7 is command
			try {
				bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Verify_Req));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 8 ~ 14 set to 0
			for (cX = 8; cX < 15; cX++)
				bOut.write(0x00);
			// 15 ~ 18 is the length = 16
			try {
				bOut.write(ByteUtility.int32ToByteArray(16));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 19 ~ 22 set to 0
			for (cX = 19; cX < 23; cX++)
				bOut.write(0x00);

			BlowFishLong.getBlowFishLongInstance().BlowfishEncrption(AppInforToSystem.AuNum, AppInforToSystem.AuNum.length);
			for (cX = 0; cX < 4; cX++) {
				try {
					bOut.write(ByteUtility.int32ToByteArray(AppInforToSystem.AuNum[cX]));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;

		case CMD_OP_CODE.Video_Start_Req:
			// 0 ~ 3 is "MO_O"
			bOut.write(szHeader.getBytes(), 0, 4);
			// 4 ~ 7 is command
			try {
				bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Video_Start_Req));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 8 ~ 14 set to 0
			for (cX = 8; cX < 15; cX++)
				bOut.write(0x00);
			// 15 ~ 18 is the length = 1
			try {
				bOut.write(ByteUtility.int32ToByteArray(1));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 19 ~ 22 set to 0
			for (cX = 19; cX < 23; cX++)
				bOut.write(0x00);
			// 23 set to reserve = 1
			try {
				bOut.write(ByteUtility.int8ToByteArray(1));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case CMD_OP_CODE.Video_End:
			// 0 ~ 3 is "MO_O"
			bOut.write(szHeader.getBytes(), 0, 4);
			// 4 ~ 7 is command
			try {
				bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Video_End));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 8 ~ 14 set to 0
			for (cX = 8; cX < 15; cX++)
				bOut.write(0x00);
			// 15 ~ 18 is the length = 0
			try {
				bOut.write(ByteUtility.int32ToByteArray(0));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 19 ~ 22 set to 0
			for (cX = 19; cX < 23; cX++)
				bOut.write(0x00);
			break;

		case CMD_OP_CODE.Audio_Start_Req:
			// 0 ~ 3 is "MO_O"
			bOut.write(szHeader.getBytes(), 0, 4);
			// 4 ~ 7 is command
			try {
				bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Audio_Start_Req));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 8 ~ 14 set to 0
			for (cX = 8; cX < 15; cX++)
				bOut.write(0x00);
			// 15 ~ 18 is the length = 1
			try {
				bOut.write(ByteUtility.int32ToByteArray(1));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 19 ~ 22 set to 0
			for (cX = 19; cX < 23; cX++)
				bOut.write(0x00);
			// 23 set to reserve = 1
			try {
				bOut.write(ByteUtility.int8ToByteArray(1));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case CMD_OP_CODE.Audio_End:
			try {
				// 0 ~ 3 is "MO_O"
				bOut.write(szHeader.getBytes(), 0, 4);
				// 4 ~ 7 is command
				bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Audio_End));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 8 ~ 14 set to 0
			for (cX = 8; cX < 15; cX++)
				bOut.write(0x00);
			// 15 ~ 18 is the length = 0
			try {
				bOut.write(ByteUtility.int32ToByteArray(0));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 19 ~ 22 set to 0
			for (cX = 19; cX < 23; cX++)
				bOut.write(0x00);
			break;
		/* ---------------------------------------------- */
		// test talk audio by JGF
		case CMD_OP_CODE.Talk_Start_Req:
			try {
				// 0 ~ 3 is "MO_O"
				bOut.write(szHeader.getBytes(), 0, 4);
				// 4 ~ 7 is command
				bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Talk_Start_Req));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 8 ~ 14 set to 0
			for (cX = 8; cX < 15; cX++)
				bOut.write(0x00);
			// 15 ~ 18 is the length = 0
			try {
				bOut.write(ByteUtility.int32ToByteArray(1));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 19 ~ 22 set to 0
			for (cX = 19; cX < 23; cX++)
				bOut.write(0x00);
			// 23 set to reserve = 1
			try {
				bOut.write(ByteUtility.int8ToByteArray(1));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case CMD_OP_CODE.Talk_End:
			try {
				// 0 ~ 3 is "MO_O"
				bOut.write(szHeader.getBytes(), 0, 4);
				// 4 ~ 7 is command
				bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Talk_End));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 8 ~ 14 set to 0
			for (cX = 8; cX < 15; cX++)
				bOut.write(0x00);
			// 15 ~ 18 is the length = 0
			try {
				bOut.write(ByteUtility.int32ToByteArray(0));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 19 ~ 22 set to 0
			for (cX = 19; cX < 23; cX++)
				bOut.write(0x00);
			break;
		/* ---------------------------------------------- */
		case CMD_OP_CODE.Camera_Params_Set_Req:
			try {
				// 0 ~ 3 is "MO_O"
				bOut.write(szHeader.getBytes(), 0, 4);
				// 4 ~ 7 is command
				bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Camera_Params_Set_Req));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 8 ~ 14 set to 0
			for (cX = 8; cX < 15; cX++)
				bOut.write(0x00);
			// 15 ~ 18 is the length = 2
			try {
				bOut.write(ByteUtility.int32ToByteArray(2));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 19 ~ 22 set to 0
			for (cX = 19; cX < 23; cX++)
				bOut.write(0x00);
			// 23 set to Type = Camera_Params_Type
			try {
				bOut.write(ByteUtility.int8ToByteArray(Camera_Params_Type));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 24 set to Value = Camera_Params_Value
			try {
				bOut.write(ByteUtility.int8ToByteArray(Camera_Params_Value));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		 // 请求发送温度湿度状态，
		 case CMD_OP_CODE.light_th_Req:
		 try
		 {
		 // 0 ~ 3 is "MO_O"
		 bOut.write(szHeader.getBytes(), 0, 4);
		 // 4 ~ 7 is command
		 bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.light_th_Req));
		 } catch (IOException e)
		 {
		 e.printStackTrace();
		 }
		 // 8 ~ 22 set to 0
		 for (cX = 8; cX < 23; cX++)
		 bOut.write(0x00);
		
		 break;

		case CMD_OP_CODE.FETCH_BATTERY_POWER_REQ:
			try {
				bOut.write(szHeader.getBytes(), 0, 4);
				bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.FETCH_BATTERY_POWER_REQ));
			} catch (IOException e) {
				e.printStackTrace();
			}
			for (cX = 8; cX < 23; cX++)
				bOut.write(0x00);
			break;
		case CMD_OP_CODE.DEVICE_DEGREE_RESET:
			// 0 ~ 3 is "MO_O"
			bOut.write(szHeader.getBytes(), 0, 4);
			// 4 ~ 7 is command
			try {
				bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.DEVICE_DEGREE_RESET));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 8 ~ 22 set to 0
			for (cX = 8; cX < 23; cX++)
				bOut.write(0x00);
			break;
		case CMD_OP_CODE.Keep_Alive:
			// 0 ~ 3 is "MO_O"
			bOut.write(szHeader.getBytes(), 0, 4);
			// 4 ~ 7 is command
			try {
				bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Keep_Alive));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 8 ~ 22 set to 0
			for (cX = 8; cX < 23; cX++)
				bOut.write(0x00);
			break;
		case CMD_OP_CODE.SpolierState_Resp:
			bOut.write(szHeader.getBytes(), 0, 4);
			// 4 ~ 7 is command
			try {
				bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.SpolierState_Resp));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 8 ~ 22 set to 0
			for (cX = 8; cX < 23; cX++)
				bOut.write(0x00);
			break;
		case CMD_OP_CODE.Query_Charging_Status:
			bOut.write(szHeader.getBytes(), 0, 4);
			// 4 ~ 7 is command
			try {
				bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Query_Charging_Status));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 8 ~ 22 set to 0
			for (cX = 8; cX < 23; cX++)
				bOut.write(0x00);
			break;
		case CMD_OP_CODE.Demo_Animation_End:
			try {
				// 0 ~ 3 is "MO_O"
				bOut.write(szHeader.getBytes(), 0, 4);
				// 4 ~ 7 is command
				bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Demo_Animation_End));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 8 ~ 14 set to 0
			for (cX = 8; cX < 15; cX++)
				bOut.write(0x00);
			// 15 ~ 18 is the length = 0
			try {
				bOut.write(ByteUtility.int32ToByteArray(0));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 19 ~ 22 set to 0
			for (cX = 19; cX < 23; cX++)
				bOut.write(0x00);
			break;
		}
		
		

		if (bOut.size() > 0 && AppInforToSystem.isFirstEOFerror) {
			try {
				AppInforToSystem.dataOutputStream.write(bOut.toByteArray());
				AppInforToSystem.dataOutputStream.flush();
				bRet = true;
			} catch (IOException e) {
				e.printStackTrace();
				bRet = false;
			}finally{
				bOut = null;
			}
		}
		return bRet;
	}
	
	/*
	 * 发送视频命令
	 */
	public boolean sendAVCommand(int inCommand) {
		// if not connected, return false directly
		if (!AppInforToSystem.ConnectStatus){
			return false;
		}
		boolean bRet = false;
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		String szHeader = "MO_V";
		int cX = 0;
		switch (inCommand) {
		case AV_OP_CODE.Login_Req:
			// 0 ~ 3 is "MO_V"
			bOut.write(szHeader.getBytes(), 0, 4);
			// 4 ~ 7 is command
			try {
				bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Login_Req));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 8 ~ 14 set to 0
			for (cX = 8; cX < 15; cX++)
				bOut.write(0x00);
			// 15 ~ 18 is the length = 4
			try {
				bOut.write(ByteUtility.int32ToByteArray(4));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 19 ~ 22 set to 0
			for (cX = 19; cX < 23; cX++)
				bOut.write(0x00);
			// 23 ~ 26 is the VideoLinkID
			try {
				bOut.write(ByteUtility.int32ToByteArray(AppInforToSystem.iVideoLinkID));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case AV_OP_CODE.Resume:
			bOut.write(szHeader.getBytes(), 0, 4);
			try {
				bOut.write(ByteUtility.int32ToByteArray(AV_OP_CODE.Resume));
			} catch (IOException e) {
				e.printStackTrace();
			}
			for (cX = 8; cX < 23; cX++)
				bOut.write(0x00);
			break;

		case AV_OP_CODE.Stop:
			bOut.write(szHeader.getBytes(), 0, 4);
			try {
				bOut.write(ByteUtility.int32ToByteArray(AV_OP_CODE.Stop));
			} catch (IOException e) {
				e.printStackTrace();
			}
			for (cX = 8; cX < 23; cX++)
				bOut.write(0x00);
			break;

		case AV_OP_CODE.set_volume_Req:
			try {
				// 0 ~ 3 is "MO_O"
				bOut.write(szHeader.getBytes(), 0, 4);
				// 4 ~ 7 is command
				bOut.write(ByteUtility.int32ToByteArray(AV_OP_CODE.set_volume_Req));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 8 ~ 14 set to 0
			for (cX = 8; cX < 15; cX++)
				bOut.write(0x00);
			// 15 ~ 18 is the length = 0
			try {
				bOut.write(ByteUtility.int32ToByteArray(0));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 19 ~ 22 set to 0
			for (cX = 19; cX < 23; cX++)
				bOut.write(0x00);
			break;
		}
		if (bOut.size() > 0 && AppInforToSystem.isFirstEOFerror) {
			try {
				AppInforToSystem.dataOutputStreamAV.write(bOut.toByteArray());
				AppInforToSystem.dataOutputStreamAV.flush();
				bRet = true;
			} catch (IOException e) {
				e.printStackTrace();
				bRet = false;
			}
		}
		return bRet;
	}
	/*
	 * 设备移动命令  250
	 */
	public boolean moveCommand(int inDir) {
		if (!AppInforToSystem.ConnectStatus){
			return false;
		}
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		String szHeader = "MO_O";
		int cX = 0;
		// 0 ~ 3 is "MO_O"
		bOut.write(szHeader.getBytes(), 0, 4);
		// 4 ~ 7 is command
		//TODO
		try {
			bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Device_Control_Req));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 8 ~ 14 set to 0
		for (cX = 8; cX < 15; cX++)
			bOut.write(0x00);
		// 15 ~ 18 is the length = 1
		try {
			bOut.write(ByteUtility.int32ToByteArray(2));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 19 ~ 22 set to 0
		for (cX = 19; cX < 23; cX++)
			bOut.write(0x00);
		// 23 is the inDir
		try {
			bOut.write(ByteUtility.int8ToByteArray(inDir));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//24 is speed
		try {
			if (inDir <= 2) {
				bOut.write(ByteUtility.int8ToByteArray(Device_MoveSpeedFlag));
			}else {
				bOut.write(ByteUtility.int8ToByteArray(Device_MoveLRSpeedFlag));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (bOut.size() > 0 && AppInforToSystem.isFirstEOFerror) {
			try {
				AppInforToSystem.dataOutputStream.write(bOut.toByteArray());
				AppInforToSystem.dataOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	/*
	 * 摄像头移动命令 14
	 */
	public boolean cameraMoveCommand(int inDir){
		if (!AppInforToSystem.ConnectStatus){
			return false;
		}
		AppInforToSystem.Camera_Move_Direct_flag = inDir;
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		String szHeader = "MO_O";
		int cX = 0;
		// 0 ~ 3 is "MO_O"
		bOut.write(szHeader.getBytes(), 0, 4);
		// 4 ~ 7 is command
		try {
			bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Decoder_Control_Req));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 8 ~ 14 set to 0
		for (cX = 8; cX < 15; cX++)
			bOut.write(0x00);
		// 15 ~ 18 is the length = 1
		try {
			bOut.write(ByteUtility.int32ToByteArray(1));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 19 ~ 22 set to 0
		for (cX = 19; cX < 23; cX++)
			bOut.write(0x00);
		// 23 is the inDir
		try {
			bOut.write(ByteUtility.int8ToByteArray(inDir));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (bOut.size() > 0 && AppInforToSystem.isFirstEOFerror) {
			try {
				AppInforToSystem.dataOutputStream.write(bOut.toByteArray());
				AppInforToSystem.dataOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	/*
	 * 录制/播放路径命令
	 */
	public Boolean DeviceControlCommand(int key, int val) {
		if (!AppInforToSystem.ConnectStatus){
			return false;
		}
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		String szHeader = "MO_O";
		int cX = 0;
		try {
			// 0 ~ 3 is "MO_O" --4
			bOut.write(szHeader.getBytes(), 0, 4);
			// 4 ~ 7 is command 操作码
			//TODO
			bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Device_Control_Req));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 8 ~ 14 set to 0 保留
		for (cX = 8; cX < 15; cX++)
			bOut.write(0x00);
		// 15 ~ 18 is the length = 0 命令长度
		try {
			bOut.write(ByteUtility.int32ToByteArray(2));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 19 ~ 22 set to 0 保留
		for (cX = 19; cX < 23; cX++)
			bOut.write(0x00);
		// 23 set to reserve = 1 正文
		try {
			bOut.write(ByteUtility.int8ToByteArray(key));
			bOut.write(ByteUtility.int8ToByteArray(val));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (bOut.size() > 0 && AppInforToSystem.isFirstEOFerror) {
			try {
				AppInforToSystem.dataOutputStream.write(bOut.toByteArray());
				AppInforToSystem.dataOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	/*
	 * 播放音效
	 */
	//TODO
	public void soundCommand(Boolean bEnable) {
		if (!AppInforToSystem.ConnectStatus){
			return;
		}
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		String szHeader = "MO_O";
		int stopCommand = 0, cX = 0;
		// 0 ~ 3 is "MO_O"
		bOut.write(szHeader.getBytes(), 0, 4);
		// 4 ~ 7 is command
		try {
			bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Soundeffect_Play_Req));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 8 ~ 14 set to 0
		for (cX = 8; cX < 15; cX++)
			bOut.write(0x00);
		// 15 ~ 18 is the length = 1
		try {
			bOut.write(ByteUtility.int32ToByteArray(1));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 19 ~ 22 set to 0
		for (cX = 19; cX < 23; cX++)
			bOut.write(0x00);
		// 23 is the value
		try {
			bOut.write(ByteUtility.int8ToByteArray(stopCommand));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (bOut.size() > 0) {
			try {
				AppInforToSystem.dataOutputStream.write(bOut.toByteArray());
				AppInforToSystem.dataOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void soundCommand1(Boolean bEnable) {
		if (!AppInforToSystem.ConnectStatus){
			return;
		}

		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		String szHeader = "MO_O";
		int stopCommand = 1, cX = 0;
		// 0 ~ 3 is "MO_O"
		bOut.write(szHeader.getBytes(), 0, 4);
		// 4 ~ 7 is command
		try {
			bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Soundeffect_Play_Req));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 8 ~ 14 set to 0
		for (cX = 8; cX < 15; cX++)
			bOut.write(0x00);
		// 15 ~ 18 is the length = 1
		try {
			bOut.write(ByteUtility.int32ToByteArray(1));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 19 ~ 22 set to 0
		for (cX = 19; cX < 23; cX++)
			bOut.write(0x00);
		// 23 is the value
		try {
			bOut.write(ByteUtility.int8ToByteArray(stopCommand));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (bOut.size() > 0) {
			try {
				AppInforToSystem.dataOutputStream.write(bOut.toByteArray());
				AppInforToSystem.dataOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * 设备端状态
	 */
	//TODO
	public boolean SateCommand(int Sate){
		if (!AppInforToSystem.ConnectStatus){
			return false;
		}
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		String szHeader = "MO_O";
		int cX = 0;
		// 0 ~ 3 is "MO_O"
		bOut.write(szHeader.getBytes(), 0, 4);
		// 4 ~ 7 is command
		//TODO
		try {
			bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.SomeSateChangemsg_resp));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 8 ~ 14 set to 0
		for (cX = 8; cX < 15; cX++)
			bOut.write(0x00);
		// 15 ~ 18 is the length = 1
		try {
			bOut.write(ByteUtility.int32ToByteArray(1));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 19 ~ 22 set to 0
		for (cX = 19; cX < 23; cX++)
			bOut.write(0x00);
		// 23 is the inDir
		try {
			bOut.write(ByteUtility.int8ToByteArray(Sate));
			} catch (IOException e) {
				e.printStackTrace();
		}
		//24 is speed
		try {
			if (Sate == 0) {
			bOut.write(ByteUtility.int8ToByteArray(0));
			}else {
			bOut.write(ByteUtility.int8ToByteArray(1));
			}
		} catch (IOException e) {
				e.printStackTrace();
			}
		if (bOut.size() > 0 && AppInforToSystem.isFirstEOFerror) {
			try {
				AppInforToSystem.dataOutputStream.write(bOut.toByteArray());
				AppInforToSystem.dataOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
		
		return true;
		
	}
	
	
	/*
	 * 示范模式
	*/
	
	public boolean SateFunc(int Sate){
		if (!AppInforToSystem.ConnectStatus){
			return false;
		}
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		String szHeader = "MO_O";
		int cX = 0;
		// 0 ~ 3 is "MO_O"
		bOut.write(szHeader.getBytes(), 0, 4);
		// 4 ~ 7 is command
		//TODO
		try {
			bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Func_Demo_Resp));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 8 ~ 14 set to 0
		for (cX = 8; cX < 15; cX++)
			bOut.write(0x00);
		// 15 ~ 18 is the length = 1
		try {
			bOut.write(ByteUtility.int32ToByteArray(2));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 19 ~ 22 set to 0
		for (cX = 19; cX < 23; cX++)
			bOut.write(0x00);
		// 23 is the inDir
		try {
			bOut.write(ByteUtility.int8ToByteArray(Sate));
			} catch (IOException e) {
				e.printStackTrace();
		}
		//24 is speed
		try {
			if (Sate == 0) {
			bOut.write(ByteUtility.int8ToByteArray(0));
			}else if(Sate ==1){
			bOut.write(ByteUtility.int8ToByteArray(1));
			}else{
			bOut.write(ByteUtility.int8ToByteArray(2));
			}
			
		} catch (IOException e) {
				e.printStackTrace();
			}
		if (bOut.size() > 0 && AppInforToSystem.isFirstEOFerror) {
			try {
				AppInforToSystem.dataOutputStream.write(bOut.toByteArray());
				AppInforToSystem.dataOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
		
		return true;
		
	}
	
	
	public void stopCommand(Boolean bEnable) {
		if (!AppInforToSystem.ConnectStatus){
			return;
		}

		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		String szHeader = "MO_O";
		int stopCommand = 0, cX = 0;
		// 0 ~ 3 is "MO_O"
		bOut.write(szHeader.getBytes(), 0, 4);
		// 4 ~ 7 is command
		try {
			bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Soundeffect_PlayStop_Req));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 8 ~ 14 set to 0
		for (cX = 8; cX < 15; cX++)
			bOut.write(0x00);
		// 15 ~ 18 is the length = 1
		try {
			bOut.write(ByteUtility.int32ToByteArray(1));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 19 ~ 22 set to 0
		for (cX = 19; cX < 23; cX++)
			bOut.write(0x00);
		// 23 is the value
		try {
			bOut.write(ByteUtility.int8ToByteArray(stopCommand));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (bOut.size() > 0) {
			try {
				AppInforToSystem.dataOutputStream.write(bOut.toByteArray());
				AppInforToSystem.dataOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stopCommand1(Boolean bEnable) {
		if (!AppInforToSystem.ConnectStatus){
			return;
		}

		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		String szHeader = "MO_O";
		int stopCommand = 1, cX = 0;
		// 0 ~ 3 is "MO_O"
		bOut.write(szHeader.getBytes(), 0, 4);
		// 4 ~ 7 is command
		try {
			bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Soundeffect_PlayStop_Req));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 8 ~ 14 set to 0
		for (cX = 8; cX < 15; cX++)
			bOut.write(0x00);
		// 15 ~ 18 is the length = 1
		try {
			bOut.write(ByteUtility.int32ToByteArray(1));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 19 ~ 22 set to 0
		for (cX = 19; cX < 23; cX++)
			bOut.write(0x00);
		// 23 is the value
		try {
			bOut.write(ByteUtility.int8ToByteArray(stopCommand));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (bOut.size() > 0) {
			try {
				AppInforToSystem.dataOutputStream.write(bOut.toByteArray());
				AppInforToSystem.dataOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
/*
 * 尾翼部分开
 */
	public void Spoiler(Boolean bEnable) {
		if (!AppInforToSystem.ConnectStatus){
			return;
		}

		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		String szHeader = "MO_O";
		int stopCommand = 99, cX = 0;
		// 0 ~ 3 is "MO_O"
		bOut.write(szHeader.getBytes(), 0, 4);
		// 4 ~ 7 is command
		try {
			bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Decoder_Control_Req));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 8 ~ 14 set to 0
		for (cX = 8; cX < 15; cX++)
			bOut.write(0x00);
		// 15 ~ 18 is the length = 1
		try {
			bOut.write(ByteUtility.int32ToByteArray(1));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 19 ~ 22 set to 0
		for (cX = 19; cX < 23; cX++)
			bOut.write(0x00);
		// 23 is the value
		try {
			bOut.write(ByteUtility.int8ToByteArray(stopCommand));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (bOut.size() > 0) {
			try {
				AppInforToSystem.dataOutputStream.write(bOut.toByteArray());
				AppInforToSystem.dataOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
/*
 * 尾翼关
 * */
	public void stopSpoiler(Boolean bEnable) {
		if (!AppInforToSystem.ConnectStatus){
			return;
		}
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		String szHeader = "MO_O";
		int stopCommand = 100, cX = 0;
		// 0 ~ 3 is "MO_O"
		bOut.write(szHeader.getBytes(), 0, 4);
		// 4 ~ 7 is command
		try {
			bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Decoder_Control_Req));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 8 ~ 14 set to 0
		for (cX = 8; cX < 15; cX++)
			bOut.write(0x00);
		// 15 ~ 18 is the length = 1
		try {
			bOut.write(ByteUtility.int32ToByteArray(1));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 19 ~ 22 set to 0
		for (cX = 19; cX < 23; cX++)
			bOut.write(0x00);
		// 23 is the value
		try {
			bOut.write(ByteUtility.int8ToByteArray(stopCommand));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (bOut.size() > 0) {
			try {
				AppInforToSystem.dataOutputStream.write(bOut.toByteArray());
				AppInforToSystem.dataOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
/*
 * 开舱门
 */
	public void Door(Boolean bEnable) {
		if (!AppInforToSystem.ConnectStatus){
			return;
		}

		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		String szHeader = "MO_O";
		int stopCommand = 0, cX = 0;
		AppInforToCustom.getAppInforToCustomInstance().setIsDoor(bEnable);
		// 0 ~ 3 is "MO_O"
		bOut.write(szHeader.getBytes(), 0, 4);
		// 4 ~ 7 is command
		try {
			bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Decoder_Control_Req));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 8 ~ 14 set to 0
		for (cX = 8; cX < 15; cX++)
			bOut.write(0x00);
		// 15 ~ 18 is the length = 1
		try {
			bOut.write(ByteUtility.int32ToByteArray(1));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 19 ~ 22 set to 0
		for (cX = 19; cX < 23; cX++)
			bOut.write(0x00);
		// 23 is the value
		try {
			bOut.write(ByteUtility.int8ToByteArray(stopCommand));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (bOut.size() > 0) {
			try {
				AppInforToSystem.dataOutputStream.write(bOut.toByteArray());
				AppInforToSystem.dataOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}	
	
	/*
	 * 关舱门
	 */
		public void stopDoor(Boolean bEnable) {
			if (!AppInforToSystem.ConnectStatus){
				return;
			}

			ByteArrayOutputStream bOut = new ByteArrayOutputStream();
			String szHeader = "MO_O";
			int stopCommand = 2, cX = 0;
			AppInforToCustom.getAppInforToCustomInstance().setIsStopDoor(bEnable);
			AppInforToCustom.getAppInforToCustomInstance().setIsDoor(!bEnable);
			// 0 ~ 3 is "MO_O"
			bOut.write(szHeader.getBytes(), 0, 4);
			// 4 ~ 7 is command
			try {
				bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Decoder_Control_Req));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 8 ~ 14 set to 0
			for (cX = 8; cX < 15; cX++)
				bOut.write(0x00);
			// 15 ~ 18 is the length = 1
			try {
				bOut.write(ByteUtility.int32ToByteArray(1));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 19 ~ 22 set to 0
			for (cX = 19; cX < 23; cX++)
				bOut.write(0x00);
			// 23 is the value
			try {
				bOut.write(ByteUtility.int8ToByteArray(stopCommand));
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (bOut.size() > 0) {
				try {
					AppInforToSystem.dataOutputStream.write(bOut.toByteArray());
					AppInforToSystem.dataOutputStream.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}		
	
/*
 * 加速按钮
 */
		
		public void Speed(Boolean bEnable) {
			if (!AppInforToSystem.ConnectStatus){
				return;
			}

			ByteArrayOutputStream bOut = new ByteArrayOutputStream();
			String szHeader = "MO_O";
			int stopCommand = 98, cX = 0;
//			AppInforToCustom.getAppInforToCustomInstance().setIsDoor(bEnable);
			// 0 ~ 3 is "MO_O"
			bOut.write(szHeader.getBytes(), 0, 4);
			// 4 ~ 7 is command
			try {
				bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Decoder_Control_Req));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 8 ~ 14 set to 0
			for (cX = 8; cX < 15; cX++)
				bOut.write(0x00);
			// 15 ~ 18 is the length = 1
			try {
				bOut.write(ByteUtility.int32ToByteArray(1));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 19 ~ 22 set to 0
			for (cX = 19; cX < 23; cX++)
				bOut.write(0x00);
			// 23 is the value
			try {
				bOut.write(ByteUtility.int8ToByteArray(stopCommand));
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (bOut.size() > 0) {
				try {
					AppInforToSystem.dataOutputStream.write(bOut.toByteArray());
					AppInforToSystem.dataOutputStream.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
		
//case CMD_OP_CODE.Func_Demo:
//	bOut.write(szHeader.getBytes(), 0, 4);
//	// 4 ~ 7 is command
//	try {
//		bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Func_Demo));
//	} catch (IOException e) {
//		e.printStackTrace();
//	}
//	// 8 ~ 22 set to 0
//	for (cX = 8; cX < 23; cX++)
//		bOut.write(0x00);
//	break;
	
		public void Func(Boolean bEnable) {
			if (!AppInforToSystem.ConnectStatus){
				return;
			}

			ByteArrayOutputStream bOut = new ByteArrayOutputStream();
			String szHeader = "MO_O";
			int Func = 0, cX = 0;
			if (bEnable)
				Func = 0;
			else
				Func = 1;
//			AppInforToCustom.getAppInforToCustomInstance().setIsDoor(bEnable);
			// 0 ~ 3 is "MO_O"
			bOut.write(szHeader.getBytes(), 0, 4);
			// 4 ~ 7 is command
			try {
				bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Func_Demo));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 8 ~ 14 set to 0
			for (cX = 8; cX < 15; cX++)
				bOut.write(0x00);
			// 15 ~ 18 is the length = 1
			try {
				bOut.write(ByteUtility.int32ToByteArray(1));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 19 ~ 22 set to 0
			for (cX = 19; cX < 23; cX++)
				bOut.write(0x00);
			// 23 is the value
			try {
				bOut.write(ByteUtility.int8ToByteArray(Func));
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (bOut.size() > 0) {
				try {
					AppInforToSystem.dataOutputStream.write(bOut.toByteArray());
					AppInforToSystem.dataOutputStream.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
	
	public int Camera_Params_Type = 6;
	public int Camera_Params_Value = 2;
	public void SetCamera_Params_Change(int type ,int value){
		Camera_Params_Type = type;
		Camera_Params_Value = value;
	}
	private int Device_MoveSpeedFlag = 0;
	private int Device_MoveLRSpeedFlag = 0;
	
	/**
	 * 设置左右两个轮子
	 * @param opt
	 */
	public void setDevice_MoveLRSpeedFlag(int opt){
		Device_MoveLRSpeedFlag = opt;
	}
	
	public int getDevice_MoveLRSpeedFlag(){
		return Device_MoveLRSpeedFlag;
	}
	/*
	 * 前后轮子
	 * 设置设备的移动速度
	 */
	public void setDevice_MoveSpeedFlag(int opt){
		Device_MoveSpeedFlag = opt;
	}
	/*
	 * 前后轮子
	 * 获取设备的移动速度
	 */
	public int getDevice_MoveSpeedFlag(){
		return Device_MoveSpeedFlag;
	}
}
