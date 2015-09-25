package com.seuic;

import android.content.Context;
import android.media.AudioManager;

public class AppVoiceManage {

	Context context;
	AppVoiceManage instance;
	AudioManager am;
	public final static int PHONE_VOICE_MAX = 0;
	public final static int PHONE_VOICE_CURRENT = 1;
	public final static int SYSTEM_VOICE_MAX = 2;
	public final static int SYSTEM_VOICE_CURRENT = 3;
	public final static int RING_VOICE_MAX = 4;
	public final static int RING_VOICE_CURRENT = 5;
	public final static int MUSIC_VOICE_MAX = 6;
	public final static int MUSIC_VOICE_CURRENT = 7;
	public final static int ALARM_VOICE_MAX = 8;
	public final static int ALARM_VOICE_CURRENT = 9;
	public  AppVoiceManage(Context context) {
		this.context = context;
		instance = this;
		am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
	}
	 
	
	/**
	 * 获取手机各种音量的最大值和当前值
	 * @param voice_type
	 * @return
	 */
	public int getVoiceLimits(int voice_type){
		int voice_value = - 1;
		switch (voice_type) {
		case PHONE_VOICE_MAX:
			if(phone_voice_max == -1)
				phone_voice_max = am.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
			voice_value = phone_voice_max;
			break;
		case PHONE_VOICE_CURRENT:
			phone_voice_current = am.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
			voice_value = phone_voice_current;
			break;
		case SYSTEM_VOICE_MAX:
			if(system_voice_max == -1)
				system_voice_max = am.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
			voice_value = system_voice_max;
			break;
		case SYSTEM_VOICE_CURRENT:
			system_voice_current = am.getStreamVolume(AudioManager.STREAM_SYSTEM);
			voice_value = system_voice_current;
			break;
		case RING_VOICE_MAX:
			if(ring_voice_max == 1)
				ring_voice_max = am.getStreamMaxVolume(AudioManager.STREAM_RING);
			voice_value = ring_voice_max;
			break;
		case RING_VOICE_CURRENT:
			ring_voice_current = am.getStreamVolume(AudioManager.STREAM_RING);
			voice_value = ring_voice_current;
			break;
		case MUSIC_VOICE_MAX:
			if(music_voice_max == -1)
				music_voice_max = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			voice_value = music_voice_max;
			break;
		case MUSIC_VOICE_CURRENT:
			music_voice_current = am.getStreamVolume(AudioManager.STREAM_MUSIC);
			voice_value = music_voice_current;
			break;
		case ALARM_VOICE_MAX:
			if(alarm_voice_max == -1)
				alarm_voice_max = am.getStreamMaxVolume(AudioManager.STREAM_ALARM);
			voice_value = alarm_voice_max;
			break;
		case ALARM_VOICE_CURRENT:
			alarm_voice_current = am.getStreamVolume(AudioManager.STREAM_ALARM);
			voice_value = alarm_voice_current;
			break;
		}
		return voice_value;
	}
	
	/**
	 * 设置手机各种音量, 显示音量调节的对话框
	 * @param voice_type 音量类型
	 * @param index   音量大小
	 * @param flags  标志位
	 */
	public void setVoiceValue(int voice_type, int index, int flags){
		switch (voice_type) {
		case PHONE_VOICE_CURRENT:
			am.setStreamVolume(AudioManager.STREAM_VOICE_CALL, index, flags);
			break;
		case SYSTEM_VOICE_CURRENT:
			am.setStreamVolume(AudioManager.STREAM_SYSTEM, index, flags);
			break;
		case RING_VOICE_CURRENT:
			am.setStreamVolume(AudioManager.STREAM_RING, index, flags);
			break;
		case MUSIC_VOICE_CURRENT:
			am.setStreamVolume(AudioManager.STREAM_MUSIC, index, flags);
			break;
		case ALARM_VOICE_CURRENT:
			am.setStreamVolume(AudioManager.STREAM_ALARM, index, flags);
			break;
		}
	}
	/**
	 *只调节音量，但是不显示音量的对话框 
	 * @param voice_type
	 * @param trend
	 */
	public void setVoiceValueNoDialog(int voice_type, int trend){
		switch (voice_type) {
		case PHONE_VOICE_CURRENT:
			if(AudioManager.ADJUST_LOWER == trend){
				am.adjustStreamVolume(AudioManager.STREAM_VOICE_CALL, AudioManager.ADJUST_LOWER, 0);
			}else if(AudioManager.ADJUST_RAISE == trend){
				am.adjustStreamVolume(AudioManager.STREAM_VOICE_CALL, AudioManager.ADJUST_RAISE, 0);
			}
		   break;
		case SYSTEM_VOICE_CURRENT:
			if(AudioManager.ADJUST_LOWER == trend){
				am.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_LOWER, 0);
			}else if(AudioManager.ADJUST_RAISE == trend){
				am.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_RAISE, 0);
			}
			break;
		case RING_VOICE_CURRENT:
			if(AudioManager.ADJUST_LOWER == trend){
				am.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_LOWER, 0);
			}else if(AudioManager.ADJUST_RAISE == trend){
				am.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_RAISE, 0);
			}
			break;
		case MUSIC_VOICE_CURRENT:
			if(AudioManager.ADJUST_LOWER == trend){
				am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, 0);
			}else if(AudioManager.ADJUST_RAISE == trend){
				am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, 0);
			}
			break;
		case ALARM_VOICE_CURRENT:
			if(AudioManager.ADJUST_LOWER == trend){
				am.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_LOWER, 0);
			}else if(AudioManager.ADJUST_RAISE == trend){
				am.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_RAISE, 0);
			}
			break;
		}
	}
	int phone_voice_max = -1;//通话音量
	int phone_voice_current = -1;
	int system_voice_max = -1;//系统音量
	int system_voice_current = -1;
	int ring_voice_max = -1;//铃声音量
	int ring_voice_current = -1;
	int music_voice_max = -1;//音乐音量
	int music_voice_current = -1;
	int alarm_voice_max = -1;//提示声音音量
	int alarm_voice_current = -1;
	
}
