package com.seuic.protocol;

public class CMD_OP_CODE
{
	public static final int Login_Req = 0;
	public static final int Login_Resp = 1;
	public static final int Verify_Req = 2;
	public static final int Verify_Resp = 3;
	public static final int Video_Start_Req = 4;
	public static final int Video_Start_Resp = 5;
	public static final int Video_End = 6;
	public static final int Change_Fps = 7;
	public static final int Audio_Start_Req = 8;
	public static final int Audio_Start_Resp = 9;
	public static final int Audio_End = 10;
	public static final int Talk_Start_Req = 11;
	public static final int Talk_Start_Resp = 12;
	public static final int Talk_End = 13;
	public static final int Decoder_Control_Req = 14; //摄像头
	public static final int MOTOR_STPED = 15;
	public static final int Camera_Params_Set_Req = 19;
	public static final int light_th_Req = 20;  //温湿度
	public static final int light_th_Resp = 21;
	public static final int talk_end_Resp = 22;
	public static final int Device_State_Led_Set = 29;
	public static final int DEVICE_DEGREE_RESET = 248;
	public static final int DEVICE_DEGREE_RESP = 249;//旋转摄像头，记录旋转的角度
	public static final int Device_Control_Req = 250;//小车移动
	public static final int Keep_Alive = 255;
//	public static final int Alive_Resp = 254;
	public static final int Soundeffect_Play_Req = 253;//播放音效命令
	public static final int Soundeffect_PlayStop_Req = 254;//停止音效
	public static final int FETCH_BATTERY_POWER_REQ = 251;//获取电池电量请求
	public static final int FETCH_BATTERY_POWER_RESP = 252;//获取电池电量响应
	public static final int SomeSateChangemsg_resp = 130;//设备端状态上报
	public static final int Func_Demo = 123;//示范模式发送
	public static final int Func_Demo_Resp = 124;//示范模式接收
	public static final int SpolierState_Resp = 125;//尾翼
	public static final int Demo_Animation_End = 126;
	public static final int Query_Charging_Status = 127;
	public static final int Charging_Status_Resp = 128;
}
