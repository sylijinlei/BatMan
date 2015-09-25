package com.wificar.util;

import com.batman.package1.R;

import android.content.Context;

public class MessageUtility {
	public static String MESSAGE_ENABLE_MIC = "";
	public static String MESSAGE_DISABLE_MIC = "";
	public static String MESSAGE_ENABLE_SPK = "";
	public static String MESSAGE_DISABLE_SPK = "";
	public static String MESSAGE_ENABLE_GSENSOR = "";
	public static String MESSAGE_DISABLE_GSENSOR = "";
	public static String MESSAGE_PORT_INPUT_ERROR = "";
	public static String MESSAGE_CONNECTION_ERROR = "";
	public static String MESSAGE_TAKE_PHOTO_SUCCESSFULLY = "";
	public static String MESSAGE_CAMERA_CHANGE = "";
	public static String MESSAGE_TAKE_PHOTO_FAIL = "";
	public static String MESSAGE_NET_CONNECT_CONTENT = "";
	public static String MESSAGE_NET_CONNECT_ERROR_CONTENT = "";
	public static String MESSAGE_SHARE_NET_ERROR_CONTENT = "";
	public static String MESSAGE_SHARE_FILE_DELETE = "";
	public static String MESSAGE_SHARE_VIDEO_PHOTO_MORE = "";
	public static String MESSAGE_SHARE_PICTURE_PHOTO_MORE = "";
	Context ct = null;
	public MessageUtility(Context ct){
		this.ct = ct;
		MESSAGE_DISABLE_MIC = ct.getResources().getString(R.string.mic_disable_label);
		MESSAGE_ENABLE_MIC = ct.getResources().getString(R.string.mic_enable_label);
		MESSAGE_CAMERA_CHANGE = ct.getResources().getString(R.string.camera_changing);
		MESSAGE_ENABLE_GSENSOR = ct.getResources().getString(R.string.g_sensor_enable_label);
		MESSAGE_DISABLE_GSENSOR = ct.getResources().getString(R.string.g_sensor_disable_label);
		MESSAGE_PORT_INPUT_ERROR = ct.getResources().getString(R.string.port_input_error);
		MESSAGE_CONNECTION_ERROR = ct.getResources().getString(R.string.connection_error);
		MESSAGE_TAKE_PHOTO_SUCCESSFULLY = ct.getResources().getString(R.string.take_photo_successfully);
		MESSAGE_TAKE_PHOTO_FAIL = ct.getResources().getString(R.string.take_photo_fail);
		MESSAGE_NET_CONNECT_CONTENT = ct.getResources().getString(R.string.net_connect_content);
		MESSAGE_NET_CONNECT_ERROR_CONTENT = ct.getResources().getString(R.string.net_connect_error_content);
		MESSAGE_SHARE_NET_ERROR_CONTENT = ct.getResources().getString(R.string.share_net_error_content);
		MESSAGE_SHARE_FILE_DELETE = ct.getResources().getString(R.string.share_file_delete_restart);
		MESSAGE_SHARE_VIDEO_PHOTO_MORE = ct.getResources().getString(R.string.share_videofile_more);
		MESSAGE_SHARE_PICTURE_PHOTO_MORE = ct.getResources().getString(R.string.share_picfile_more);
	}
}
