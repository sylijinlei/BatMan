package com.seuic.interfaces;

import android.R.integer;

public interface CustomerInterface
{
	void callbackCall(int opt);
	public static final int MESSAGE_BATTERY_CHANGE = 10001;
	public static final int MESSAGE_HEARTBEAT_WARNING = 10002;
	public static final int MESSAGE_PHONE_BATTERY_LOW = 10003;
	public static final int MESSAGE_PHONE_CAPACITY_LOW = 10004;
	public static final int MESSAGE_CAMERADATA_CHANGE = 20001;
	public static final int MESSAGE_SHOOTING_START = 20002;
	public static final int MESSAGE_SHOOTING_COMPLETE = 20003;
	public static final int MESSAGE_PHOTOGRAPH_END = 20004;
	public static final int MESSAGE_RECORDPATH_COMPLETE = 30001;
	public static final int MESSAGE_RECODEPATH_START = 30002;
	public static final int MESSAGE_PLAYPATH_START = 30003;
	public static final int MESSAGE_PLAYPATH_END = 30004;
	public static final int MESSAGE_GSENSER_START = 30005;
	public static final int MESSAGE_GSENSER_END = 30006;
	public static final int MESSAGE_SPK_START_SUCCESS = 40001;
	public static final int MESSAGE_SPK_START_FAIL = 40002;
	public static final int MESSAGE_SPK_END_SUCCESS = 40003;
	public static final int MESSAGE_SPK_END_FAIL = 40004;
	public static final int MESSAGE_NO_SHOOTING = 50001;
	public static final int MESSAGE_NO_RECORD = 50002;
	public static final int MESSAGE_SOCKER_EOF_ERROR = 60001;
	public static final int MESSAGE_CAMERACHANGE_END = 60002;
	public static final int MESSAGE_SCROLL_LR_FLAG = 60003;
	public static final int MESSAGE_CAMERA_DEGREE = 60004;
	public static final int MESSAGE_CAMERARESET_END = 60005;
	
	public static final int MESSAGE_JET_END = 70001;
	public static final int MESSAGE_DOOR_DONE= 70002;
	public static final int MESSAGE_G_VOLUME_ENABLE = 70003;
	public static final int MESSAGE_G_VOLUME_DISABLE = 70004;
	public static final int MESSGE_SCROLL_RIGHT = 60006;
	
	public static final int MESSGE_FUNC = 80001;
	public static final int MESSGE_FUNC_START = 80002;
	public static final int MESSGE_FUNC_STOP = 80003;
	public static final int MESSGE_SPOILER = 80004;
	public static final int MESSGE_CHARGING_STATUS = 80005;
}
