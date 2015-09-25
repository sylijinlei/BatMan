package com.seuic.thread;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import com.batman.package1.WificarNew;
import com.seuic.AppConnect;
import com.seuic.AppInforToCustom;
import com.seuic.AppInforToSystem;
import com.seuic.AppJet_MoveCar;
import com.seuic.AppLog;
import com.seuic.function.AppCameraShootingFunction;
import com.seuic.function.AppListenAudioFunction;
import com.seuic.function.AppSpkAudioFunction;
import com.seuic.interfaces.CustomerInterface;
import com.seuic.jni.AppDecodeH264;
import com.seuic.jni.AppDecoderAAC;
import com.seuic.jni.AppGetCpuFeatures;
import com.seuic.protocol.AV_OP_CODE;
import com.seuic.protocol.CMD_OP_CODE;
import com.seuic.protocol.STATUS_CONNECTION;
import com.seuic.util.Adpcm2Pcm;
import com.seuic.util.ByteUtility;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

public class AppThread
{
	private static AppThread appThreadInstance = null;
	public static AppThread getAppThreadInstance(){
		if (appThreadInstance == null)
		{
			appThreadInstance = new AppThread();
		}
		return appThreadInstance;
	}
	
	private static int[] step_table =
		{ 7, 8, 9, 10, 11, 12, 13, 14, 16, 17, 19, 21, 23, 25, 28, 31, 34, 37, 41, 45, 50, 55, 60, 66, 73, 80, 88, 97, 107, 118, 130, 143, 157, 173, 190, 209, 230, 253, 279, 307, 337, 371, 408, 449, 494, 544, 598, 658, 724, 796, 876, 963, 1060, 1166, 1282, 1411, 1552, 1707, 1878, 2066, 2272, 2499, 2749, 3024, 3327, 3660, 4026, 4428, 4871, 5358, 5894, 6484, 7132, 7845, 8630, 9493, 10442, 11487, 12635, 13899, 15289, 16818, 18500, 20350, 22385, 24623, 27086, 29794, 32767 };
		private static int[] index_adjust =
		{ -1, -1, -1, -1, 2, 4, 6, 8, -1, -1, -1, -1, 2, 4, 6, 8, };
	/*
	 * 处理命令通道的线程
	 */
	public Runnable threadReceiver = new Runnable() {
		public void run() {
			byte[] bufs = new byte[100];//存放命令内容
			byte[] bHeader = new byte[AppInforToSystem.iHeaderLen];
			int iOpCode ,iContentLen;
			byte[] trueHeader = new byte[]{(byte)0x4D, (byte)0x4F, (byte)0x5F, (byte)0x4F};
			int trueHeader_length = trueHeader.length;
			boolean isHeader = true;
			while (AppInforToSystem.isFirstEOFerror && AppInforToSystem.ConnectStatus) {
				try
				{
					AppInforToSystem.dataInputStream.readFully(bHeader, 0, AppInforToSystem.iHeaderLen);
					isHeader = true;
					 for (int i = 0; i < trueHeader_length; i++) {
				            if (bHeader[i] != trueHeader[i]) {
				            	isHeader = false;
				            }
				     }	
					if (isHeader)
					{
						iOpCode = ByteUtility.byteArrayToInt(bHeader, 4, 2);
						iContentLen = ByteUtility.byteArrayToInt(bHeader, 15, 4);
						if (iContentLen > 100)
						{
							bufs = new byte[iContentLen];
						}
						AppInforToSystem.dataInputStream.readFully(bufs, 0, iContentLen);
						Parse_Packet(iOpCode, bufs);
					}
				}catch (EOFException exception){ //这时可以远程设备已经断开，但是网络没有断开，可以发送消息给上层用户，提示这时网络已经断开
					ExitCicle();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	};
	RecordThread recordThread;
	/*
	 * 解析命令函数
	 */
	public void Parse_Packet(int inCode, byte[] inPacket) {
		switch (inCode) {
		case CMD_OP_CODE.Login_Resp:
			int Login_Resp_iResult = ByteUtility.byteArrayToInt(inPacket, 0, 2);
			if (Login_Resp_iResult == 0) {
				// Result == OK
				if (STATUS_CONNECTION.STATE_RESP_RECEIVE > AppInforToSystem.iStatus)
				{
					AppInforToSystem.iStatus = STATUS_CONNECTION.STATE_RESP_RECEIVE;
				}
				// Get ID
				char[] chCameraID = new char[19];
				for (int x = 0; x < 19; x++)
					chCameraID[x] = (char) inPacket[2 + x];
				AppInforToCustom.getAppInforToCustomInstance().setTargetDevID(new String(chCameraID).substring(0, 19));
				// Get firmware version
				int[] iCameraVer = new int[4];
				for (int x = 0; x < 4; x++) {
					iCameraVer[x] = ByteUtility.byteArrayToInt(inPacket, 26 + x, 1);
				}
				AppInforToCustom.getAppInforToCustomInstance().setFWVersion(iCameraVer[0] + "." + iCameraVer[1] + "." + iCameraVer[2] + "." + iCameraVer[3]);
				byte[] byteAuNum = new byte[4];

				for (int y = 0; y < 4; y++) {
					for (int x = 0; x < 4; x++) {
						byteAuNum[x] = inPacket[30 + y * 4 + x];
					}
					AppInforToSystem.AuNum[y] = ByteUtility.byteArrayToInt(byteAuNum, 0, 4);
				}
				// verify
				for (int x = 0; x < 4; x++) {
					if (AppInforToSystem.AuNum[x] == AppInforToSystem.ChNum[x]) {
						continue;
					} else {
						break;
					}
				}
				for (int x = 0; x < 4; x++) {
					AppInforToSystem.AuNum[x] = ByteUtility.byteArrayToInt(inPacket, 46 + x * 4, 4);
				}
			} else if (Login_Resp_iResult == 2) {
				// MAX connections
			}
			break;

		case CMD_OP_CODE.Verify_Resp:
			int Verify_Resp_iResult = ByteUtility.byteArrayToInt(inPacket, 0, 2);
			if (Verify_Resp_iResult == 0) {
				// Result = OK
				if (STATUS_CONNECTION.STATE_VERI_RECEIVE > AppInforToSystem.iStatus)
				{
					AppInforToSystem.iStatus = STATUS_CONNECTION.STATE_VERI_RECEIVE;
				}
			} else if (Verify_Resp_iResult == 1) {
				// Result = user error
				AppLog.e("Verify_Resp", "Result = user error");
			} else if (Verify_Resp_iResult == 5) {
				// password error
				AppLog.e("Verify_Resp", "Result = password error");
			} else {
				// unknown error
				AppLog.e("Verify_Resp", "Result = unknown error");
			}
			break;

		case CMD_OP_CODE.Video_Start_Resp:
			int Video_Start_Resp_iResult = ByteUtility.byteArrayToInt(inPacket, 0, 2);
			int Video_Start_Resp_iLinkID = ByteUtility.byteArrayToInt(inPacket, 2, 4);
			if (Video_Start_Resp_iResult == 0) {
				// result = agree
				AppInforToSystem.iVideoLinkID = Video_Start_Resp_iLinkID;
			} else if (Video_Start_Resp_iResult == 2) {
				// MAX
			} else {
				// unknown
			}
			break;

		case CMD_OP_CODE.Audio_Start_Resp:
			int Audio_Start_Resp_iResult = ByteUtility.byteArrayToInt(inPacket, 0, 2);
			// Parse ID only if the length over 6 bytes
			int Audio_Start_Resp_iLinkID = 0;
			if (inPacket.length == 6)
				Audio_Start_Resp_iLinkID = ByteUtility.byteArrayToInt(inPacket, 2, 4);
			if (Audio_Start_Resp_iResult == 0) {
				// result = agree
				// If link ID is 0, use existed Video ID
				if (Audio_Start_Resp_iLinkID == 0)
					AppInforToSystem.iAudioLinkID = AppInforToSystem.iVideoLinkID;
				else
					AppInforToSystem.iAudioLinkID = Audio_Start_Resp_iLinkID;
			} else if (Audio_Start_Resp_iResult == 2) {
				// MAX
			} else {
				// unknown
			}
			break;
		case CMD_OP_CODE.Talk_Start_Resp:
			if (AppInforToSystem.record_step == 1) {
				AppInforToSystem.record_step = 2;  //得到请求回应
				boolean re = false;
				int preventd = 0;
				AppLog.d("spk ", "Talk_Start_Resp:    "+ AppInforToSystem.record_step);
				while (re == false && AppInforToSystem.record_step == 2) {
					if (preventd > 0) {
						try {
							Thread.sleep(30);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					recordThread = new RecordThread();
					re = recordThread.init();
					if (AppInforToSystem.record_step == 2) {
						AppInforToSystem.record_step = 3;
					}else {
						recordThread.closeSocket();
						AppSpkAudioFunction.getAppSpkAudioFunctionInstance().closeSpk();
					}
					AppLog.d("spk ", "Talk_Start_Resp_in:    "+ AppInforToSystem.record_step);
					if (re && AppInforToSystem.record_step == 3) {
						recordThread.start();
						AppInforToSystem.record_step = 4;
					}
					preventd++;
					if (preventd >= 20) {
						break;
					}
				}
			}else {
				AppSpkAudioFunction.getAppSpkAudioFunctionInstance().closeSpk();
			}
			break;
		case CMD_OP_CODE.talk_end_Resp:
			if (AppInforToSystem.record_step == 5) {
				AppInforToSystem.record_step = 6;
				AppLog.d("spk ", "talk_end_Resp"+AppInforToSystem.record_step);
			}
			break;
		 // 分析温湿度数据
		 case CMD_OP_CODE.light_th_Resp:
		 break;

		case CMD_OP_CODE.FETCH_BATTERY_POWER_RESP:
			int battery = (int) ByteUtility.byteArrayToInt(inPacket, 0, 1);
			AppInforToCustom.getAppInforToCustomInstance().setBatteryValue(battery);
			AppConnect.getInstance().callBack(CustomerInterface.MESSAGE_BATTERY_CHANGE);
			break;
		case CMD_OP_CODE.DEVICE_DEGREE_RESP:
			int degree = (int)ByteUtility.byteArrayToInt(inPacket, 0, 4);
			if (AppInforToSystem.camera_reset == 0) {
				AppInforToSystem.Camera_lr_degree = (float)(degree * 0.041);
				AppConnect.getInstance().callBack(CustomerInterface.MESSAGE_CAMERA_DEGREE);
			}else if (AppInforToSystem.camera_reset == 1) {
				if (degree == 0) {//回位完成
					AppConnect.getInstance().callBack(CustomerInterface.MESSAGE_CAMERARESET_END);
				}
			}
			break;
		case CMD_OP_CODE.SomeSateChangemsg_resp:
			int state = (int)ByteUtility.byteArrayToInt(inPacket, 0, 1);
			Log.e("SomeSateChangemsg_resp", "state"+state);
			if (state == 1)
			{
				AppConnect.getInstance().callBack(CustomerInterface.MESSAGE_JET_END);
			} else if (state == 0) {
				AppConnect.getInstance().callBack(CustomerInterface.MESSAGE_DOOR_DONE);
			}
			break;
			
		case CMD_OP_CODE.Func_Demo_Resp:
			int sate = (int)ByteUtility.byteArrayToInt(inPacket, 0, 1);
			if(sate == 0)
			{
				AppConnect.getInstance().callBack(CustomerInterface.MESSGE_FUNC);
			}else if(sate ==1){
				AppConnect.getInstance().callBack(CustomerInterface.MESSGE_FUNC_START);
			}else{
				AppConnect.getInstance().callBack(CustomerInterface.MESSGE_FUNC_STOP);
			}
			break;
		case CMD_OP_CODE.SpolierState_Resp:
			AppConnect.getInstance().callBack(CustomerInterface.MESSGE_SPOILER);
			break;
		case CMD_OP_CODE.Charging_Status_Resp:
			int value= (int)ByteUtility.byteArrayToInt(inPacket, 0, 1);
			if (value == 0)
			{
				WificarNew.isCharging = false;
			} else {
				WificarNew.isCharging = true;
			}
			AppConnect.getInstance().callBack(CustomerInterface.MESSGE_CHARGING_STATUS);
			break;
		}
		
		
	}
	
	/*
	 * 处理视频通道的线程
	 */
	public Runnable threadAVReceiver = new Runnable() {
		public void run() {
				byte[] bufs = new byte[1024 * 200];
				byte[] bHeader = new byte[AppInforToSystem.iHeaderLen];
				byte[] trueHeader = new byte[]{(byte)0x4D, (byte)0x4F, (byte)0x5F, (byte)0x56};
				int trueHeader_length = trueHeader.length;
				boolean isAVHeader = true;
				int iOpCode ,iContentLen;
				while (AppInforToSystem.isFirstEOFerror && (AppInforToSystem.iVideoLinkID > 0) && AppInforToSystem.ConnectStatus)
				{
					try
					{
						AppInforToSystem.dataInputStreamAV.readFully(bHeader, 0, AppInforToSystem.iHeaderLen);
						isAVHeader = true;
						 for (int i = 0; i < trueHeader_length; i++) {
					            if (bHeader[i] != trueHeader[i]) {
					            	isAVHeader = false;
					            }
					     }	
						if (isAVHeader)
						{
							iOpCode = ByteUtility.byteArrayToInt(bHeader, 4, 2);
							iContentLen = ByteUtility.byteArrayToInt(bHeader, 15, 4);
							if (iContentLen > 204800)
							{
								bufs = new byte[iContentLen];
							}
							AppInforToSystem.dataInputStreamAV.readFully(bufs, 0, iContentLen);
							Parse_AVPacket(iOpCode, bufs, 0);
						}
					}catch (EOFException exception){ //这时可以远程设备已经断开，但是网络没有断开
						ExitCicle();
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
	};
	public boolean isFirstShow = true;
	byte[] bArrayImage = new byte[1024*200];  //设置数据库保存最大值
	public byte[] bDecoded = new byte[8192];
	public int Video_Data_iVideoLen = 0,Audio_Data_iAudioLen = 0, CurrentVideoType = 2,PreVideoType = 2;
	private boolean isFirstIFrame = true;
	byte IFrame = (byte)0x65;//关键帧是0x65
	int type = 1;
	int  audio_frame_time = 64, audio_preframe_time = 64, audio_frame_flag = 0, audio_frame_time_during = 0;
	int  video_frame_time = 50, video_preframe_time = 50, video_frame_flag = 0, video_frame_time_during = 0;
	public void Parse_AVPacket(int inCode, byte[] inPacket, int headOffset) {
		try {
			switch (inCode) {
			case AV_OP_CODE.Video_Data:
				if (isFirstShow && AppInforToSystem.isCameraChanging) {
					AppInforToSystem.isCameraChanging = false;
					isFirstShow = false;
				}
				CurrentVideoType = ByteUtility.byteArrayToInt(inPacket, headOffset + 8, 1);  //双摄像头
				if (PreVideoType != CurrentVideoType) {
					AppDecodeH264.Uninit();
					PreVideoType = CurrentVideoType;
					AppDecodeH264.Init(AppGetCpuFeatures.CpuCount);
					AppConnect.getInstance().callBack(CustomerInterface.MESSAGE_CAMERACHANGE_END);
				}
				Video_Data_iVideoLen = ByteUtility.byteArrayToInt(inPacket, headOffset + 9,4);
				System.arraycopy(inPacket, headOffset + 13, bArrayImage, 0, Video_Data_iVideoLen);
				AppDecodeH264.sessionDataCallBack(bArrayImage, Video_Data_iVideoLen, CurrentVideoType);
				AppConnect.getInstance().callBack(CustomerInterface.MESSAGE_CAMERADATA_CHANGE);
				if (AppInforToCustom.getAppInforToCustomInstance().getIsCameraShooting() && AppInforToSystem.isCameraShootingInitSuccess) {
					video_frame_time = ByteUtility.byteArrayToInt(inPacket, headOffset, 4);
					if (video_frame_flag == 0) {
						video_frame_flag = 1;
						video_frame_time_during = 50;
					}else {
						video_frame_time_during = video_frame_time - video_preframe_time;
					}
					video_preframe_time = video_frame_time;
					if (isFirstIFrame) {
						if (CurrentVideoType == 2) {
							if (IFrame == bArrayImage[29]) {
								isFirstIFrame = false;
								type = 1;
								AppCameraShootingFunction.getAppCameraShootingFunctionInstance().ShootingVideoData(bArrayImage, Video_Data_iVideoLen, type, video_frame_time_during);
							}
						}else if (CurrentVideoType == 1) {
							if (IFrame == bArrayImage[28]) {
								isFirstIFrame = false;
								type = 1;
								AppCameraShootingFunction.getAppCameraShootingFunctionInstance().ShootingVideoData(bArrayImage, Video_Data_iVideoLen, type, video_frame_time_during);
							}
						}
					}else {
						//底层录像的时候会判断当前是否是关键帧，并且录制在视频中，如果不处理那么有的视频播放器拖动进度条会出现花屏现象
						if (CurrentVideoType == 2) {
							if (IFrame == bArrayImage[29]) {
								type = 1;
							}else {
								type = 0;
							}
						}else if (CurrentVideoType == 1) {
							if (IFrame == bArrayImage[28]) {
								type = 1;
							}else {
								type = 0;
							}
						}
						AppCameraShootingFunction.getAppCameraShootingFunctionInstance().ShootingVideoData(bArrayImage, Video_Data_iVideoLen, type, video_frame_time_during);
					}
				}
				break;
//TODO
			case AV_OP_CODE.Audio_Data:
				Audio_Data_iAudioLen = ByteUtility.byteArrayToInt(inPacket,headOffset + 13, 4);
				ByteArrayOutputStream bAudio = new ByteArrayOutputStream();
				bAudio.write(inPacket, headOffset + 17, Audio_Data_iAudioLen);
				int Audio_Data_paraSample =ByteUtility.byteArrayToInt(inPacket, headOffset + 17 + Audio_Data_iAudioLen, 2);
				int Audio_data_paraIndex = ByteUtility.byteArrayToInt(inPacket, headOffset + 17 + Audio_Data_iAudioLen + 2, 1);
				
				byte[] bDecoded = adpcm_decode(bAudio.toByteArray(), bAudio.size(), Audio_Data_paraSample, Audio_data_paraIndex);
				if (AppListenAudioFunction.getAppAudioFunctionInstance().vAudio != null) {
					AppLog.e("vAudio","vAudio decode ok");
					AppListenAudioFunction.getAppAudioFunctionInstance().playvideo(bDecoded);
					AppLog.e("bDecoded length:"+bDecoded.length);
				}
				if (AppInforToCustom.getAppInforToCustomInstance().getIsCameraShooting() && AppInforToSystem.isCameraShootingInitSuccess && !isFirstIFrame) {
					audio_frame_time = ByteUtility.byteArrayToInt(inPacket, headOffset, 4);
					if (audio_frame_flag == 0) {
						audio_frame_flag = 1;
						audio_frame_time_during = 64;
					}else {
						audio_frame_time_during = audio_frame_time - audio_preframe_time;
					}				
					audio_preframe_time = audio_frame_time;			
					AppCameraShootingFunction.getAppCameraShootingFunctionInstance().ShootingAudioData(
							bDecoded, bDecoded.length, audio_frame_time_during);
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//TODO adpcm解码
	private static byte[] adpcm_decode(byte[] raw, int len, int pre_sample, int index)
	{
		ByteBuffer bDecoded = ByteBuffer.allocate(len * 4);

		int i;
		int code;
		int sb;
		int delta;
		// short[] pcm = new short[len * 2];
		len <<= 1;

		for (i = 0; i < len; i++)
		{
			if ((i & 0x01) != 0)
				code = raw[i >> 1] & 0x0f;
			else
				code = raw[i >> 1] >> 4; // 先解析高4位
			if ((code & 8) != 0)
				sb = 1;
			else
				sb = 0;
			code &= 7;

			delta = (step_table[index] * code) / 4 + (step_table[index]) / 8;
			if (sb != 0)
				delta = -delta;
			pre_sample += delta;
			if (pre_sample > 32767)
				pre_sample = 32767;
			else if (pre_sample < -32768)
				pre_sample = -32768;
			// pcm[i] = (short)pre_sample;
			bDecoded.put(ByteUtility.int16ToByteArray(pre_sample));
			index += index_adjust[code];
			if (index < 0)
				index = 0;
			if (index > 88)
				index = 88;
		}

		return bDecoded.array();
	}

	
	
	/******************************* 传输手机端的录音数据到小车端播放 ***********************************/
	class RecordThread extends Thread {
		static final int frequency = 16000; // rate
		static final int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
		static final int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
		int recBufSize; // playBufSize;
		AudioRecord audioRecord;
		DataOutputStream clientOut = null;
		OutputStream output = null;
		byte[] header = new byte[12];

		private Socket clientSocket;

		public boolean init() {
			recBufSize = AudioRecord.getMinBufferSize(frequency,channelConfiguration, audioEncoding);
			audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
					frequency, channelConfiguration, audioEncoding, recBufSize);
			try {
				//测试一下socket == null的时候
				clientSocket = new Socket(AppInforToCustom.getAppInforToCustomInstance().getTargetIP(), AppInforToCustom.getAppInforToCustomInstance().getTargetPort()); // JGF
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		/*
		 * 关闭音频线程
		 */
		public void closeSocket() {
			try {
				if (clientSocket != null) {
					clientSocket.close();
					clientSocket = null;
				}
				if (clientOut != null) {
					clientOut.close();
					clientOut = null;
				}
				if (audioRecord != null) {
					audioRecord.stop();
					audioRecord.release();
					audioRecord = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void run() {
			try {
				DataOutputStream clientOut = null;
				OutputStream output = clientSocket.getOutputStream();
				// 发送文件
				clientOut = new DataOutputStream(new BufferedOutputStream(output));
				byte[] buffer = new byte[recBufSize];
				int bufferReadResult;
				audioRecord.startRecording(); // 开始录制
				clientOut.write(ByteUtility.int64ToByteArray(recBufSize));
				clientOut.write(ByteUtility.int8ToByteArray(7));
				clientOut.write(ByteUtility.int8ToByteArray(1));
				clientOut.write(ByteUtility.int8ToByteArray(16));
				clientOut.write(ByteUtility.int8ToByteArray(1));

				AppInforToSystem.capture_paraSample = 0;
				AppInforToSystem.capture_paraIndex = 0;
				AppLog.d("spk ", "start:    "+ AppInforToSystem.record_step);
				while (AppInforToSystem.record_step == 4) {
					// 从MIC保存数据到缓冲区
					bufferReadResult = audioRecord.read(buffer, 0, recBufSize);
					byte[] tmpBuf = Adpcm2Pcm.adpcm_encode(buffer, bufferReadResult,
							AppInforToSystem.capture_paraSample, AppInforToSystem.capture_paraIndex);
					try {
						if (AppInforToSystem.record_step == 4) {
							clientOut.write(tmpBuf, 0, tmpBuf.length);
							clientOut.flush();
						} else{
							AppLog.d("spk ", "write 中断");
							break;
						}
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
				}
			} catch (Exception t) {
				t.printStackTrace();
			} finally {
				try
				{
					if (audioRecord != null) {
						audioRecord.stop();
						audioRecord.release();
						audioRecord = null;
					}
					closeSocket();
				} catch (Exception e)
				{
				}
			}
			AppLog.d("spk ", "recordthread_close");
		}

	}
	/*
	 * 退出循环，发送通知提示网络断开
	 */
	public void ExitCicle(){
		if (AppInforToSystem.isFirstEOFerror) {
			AppInforToSystem.isFirstEOFerror = false;
			AppConnect.getInstance().callBack(CustomerInterface.MESSAGE_SOCKER_EOF_ERROR);
		}
		return;
	}
	/*
	 * 当录像结束后，需要重新设置isFirstIFrame为true
	 */
	public void setFirstIFrame(boolean opt){
		isFirstIFrame = opt;
		video_frame_flag = 0;
		audio_frame_flag = 0;
	}
}
