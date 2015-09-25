package com.seuic.function;

import java.io.FileNotFoundException;
import java.util.Vector;

import com.seuic.AppCommand;
import com.seuic.AppConnect;
import com.seuic.AppInforToCustom;
import com.seuic.AppInforToSystem;
import com.seuic.AppLog;
import com.seuic.protocol.CMD_OP_CODE;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.AudioTrack;
import android.os.Build;
import android.util.Log;

public class AppListenAudioFunction
{	
	public static final String TAG = "AppListenAudioFunction";
	private static AppListenAudioFunction appAudioFunctionInstance = null;
	public static AppListenAudioFunction getAppAudioFunctionInstance(){
		if (appAudioFunctionInstance == null)
		{
			appAudioFunctionInstance = new AppListenAudioFunction();
		}
		return appAudioFunctionInstance;
	}
	public Vector<byte[]> vAudio = null;
	AudioTrack aAudio = null;
	AudioManager am = null;
	public synchronized Boolean AudioEnable() throws FileNotFoundException {
		if (!AppInforToSystem.ConnectStatus){
			return false;
		}
		boolean bRet = false;
		vAudio = new Vector<byte[]>();
		AppInforToSystem.islistening = true;
		if (AppInforToCustom.getAppInforToCustomInstance().getIsCameraShooting() == false) {
			bRet = AppCommand.getAppCommandInstace().sendCommand(CMD_OP_CODE.Audio_Start_Req);
			if (bRet) {
				AppInforToSystem.iAudioLinkID = 7501;
				if (AppInforToSystem.iAudioLinkID != 0)
					bRet = true;
				else
					bRet = false;
			}
		} else {
			bRet = true;
		}
		Log.i(TAG, "AudioEnable:bRet=" + bRet);
		if (bRet)
		{
			requestAudioFocus();
			if (aAudio == null) {
				int intSize = android.media.AudioTrack.getMinBufferSize(
						AppInforToSystem.SAMPLE_RATE, AudioFormat.CHANNEL_CONFIGURATION_MONO, 
						AudioFormat.ENCODING_PCM_16BIT);
				this.aAudio = new AudioTrack(AudioManager.STREAM_MUSIC, 
						AppInforToSystem.SAMPLE_RATE, AudioFormat.CHANNEL_CONFIGURATION_MONO,
						AudioFormat.ENCODING_PCM_16BIT, intSize, 
						AudioTrack.MODE_STREAM);
		Log.i(TAG, "AudioEnable:aAudio=" + aAudio);	
			}else if(aAudio.getPlayState() == AudioTrack.PLAYSTATE_STOPPED){
				
			}
			
		}
		return bRet;
	}
	/**
	 * 请求焦点
	 */
	private void requestAudioFocus(){
		if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.ECLAIR_MR1){  
            return;  
        } 
		if (am == null) {
			am = (AudioManager)AppConnect.getInstance().context.getSystemService(Context.AUDIO_SERVICE);
		}
		if (am != null) {
			am.requestAudioFocus(aFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
		}
	}
	/**
	 * 放弃焦点
	 */
	private void abandonAudioFocus(){
		if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.ECLAIR_MR1){  
            return;  
        }  
        if (am != null) {
        	am.abandonAudioFocus(aFocusChangeListener);  
        	am = null;  
        }  
	}
	/**
	 * 注册监听
	 */
	OnAudioFocusChangeListener aFocusChangeListener = new OnAudioFocusChangeListener() {
		public void onAudioFocusChange(int focusChange) {
			switch (focusChange) {
			case AudioManager.AUDIOFOCUS_LOSS://失去焦点
				AppLog.d("listen", "失去焦点");
				break;

			case AudioManager.AUDIOFOCUS_GAIN://获取焦点
				AppLog.d("listen", "获取焦点");
				break;
			}
		}
	};
	/*
	 * 关闭音频功能
	 */
	public Boolean AudioDisable() {
		if (!AppInforToSystem.ConnectStatus || !AppInforToSystem.islistening){
			return false;
		}
		AppInforToSystem.islistening = false;
		boolean bRet = false;
		try
		{
			abandonAudioFocus();
			if (AppInforToCustom.getAppInforToCustomInstance().getIsCameraShooting() == false)
				bRet = AppCommand.getAppCommandInstace().sendCommand(CMD_OP_CODE.Audio_End);
			else {
				bRet = true;
			}
			Log.i(TAG, "AudioDisable bRet:"+bRet);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			try
			{
				AppInforToSystem.iAudioLinkID = 0;
				if (vAudio != null) {
					vAudio.clear();
					vAudio = null;
				}
				if (aAudio != null) {
					aAudio.flush();
					if (aAudio.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {
						AppLog.i("decoder_aac", "palying to stop");
						aAudio.stop();
					}else {
						AppLog.i("decoder_aac", "is stop");
					}
				}
			} catch (Exception e2)
			{
			}
		}
		
		return bRet;
	}

	/*
	 * 关闭AudioTrack
	 */
	public void destoryAudioTrack(){
		if(aAudio != null){
			if (aAudio.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {
				aAudio.flush();
				aAudio.stop();
			}
			aAudio.release();
			aAudio = null;
		}
	}
	/*
	 * 播放音频
	 */
	public void playvideo(byte[] data){
		if (AppInforToSystem.islistening && aAudio != null){
			aAudio.play();
			aAudio.write(data, 0, data.length);
		}
	}
}
