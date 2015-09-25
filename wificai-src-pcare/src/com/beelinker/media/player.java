package com.beelinker.media;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.libsdl.app.SDLActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.batman.package1.R;

public class player extends SDLActivity{

	public static final String version = "0.0.0.1";
	private final String TAG = player.class.getSimpleName();
	
	private final int AKEYCODE_MEDIA_PLAY_PAUSE = 85;
	private RelativeLayout mRelativeLayoutControl = null;
	private Button mButtonPlayerPause;
	private TextView mTextViewPlayTimeAt, mTextViewPlayTimeTotal;
	private SeekBar mSeekBar;
	private Boolean bPlayerState = true;
	
	static String mVideoFileName = null;
	
	private String getRealPath(Uri fileUrl){
		String fileName = null;
		Uri filePathUri = fileUrl;
		if(fileUrl!= null){
			if (fileUrl.getScheme().toString().compareTo("content")==0)
			{ 
				Cursor cursor = getApplicationContext().getContentResolver().query(fileUrl, null, null, null, null);
				if (cursor != null && cursor.moveToFirst())
				{
					int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
					fileName = cursor.getString(column_index); 
					cursor.close();
				}
			}else if (fileUrl.getScheme().compareTo("file")==0)
			{
				fileName = filePathUri.toString();
				fileName = filePathUri.toString().replace("file://", "");
			}
		}
		try {
			fileName = URLDecoder.decode(fileName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return fileName;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player);
		mRelativeLayoutControl = (RelativeLayout) findViewById(R.id.player_overlay_decor);
		mButtonPlayerPause = (Button) findViewById(R.id.play_button_pause);
		mButtonPlayerPause.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(bPlayerState){
					v.setBackgroundResource(R.drawable.player_play);
					bPlayerState = false;
					onNativeKeyDown(AKEYCODE_MEDIA_PLAY_PAUSE);
					onNativeKeyUp(AKEYCODE_MEDIA_PLAY_PAUSE);
				}
				else{
					v.setBackgroundResource(R.drawable.player_pause);
					bPlayerState = true;
					onNativeKeyDown(AKEYCODE_MEDIA_PLAY_PAUSE);
					onNativeKeyUp(AKEYCODE_MEDIA_PLAY_PAUSE);
				}
			}
		});
		mTextViewPlayTimeAt = (TextView) findViewById(R.id.play_time_at);
		mTextViewPlayTimeTotal = (TextView) findViewById(R.id.play_time_total);
		mSeekBar = (SeekBar) findViewById(R.id.play_seekbar);
		mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				if(arg0.isPressed())
					onNativeVideoSeek(arg1);
			}
		});
		mLayout = (AbsoluteLayout)findViewById(R.id.window);
		mLayout.addView(mSurface);
		mVideoFileName = null;
		if(!(getIntent().getAction() != null && getIntent().getAction().equals(Intent.ACTION_VIEW ))){
			mVideoFileName = getIntent().getExtras().getString("file_name");
		}
		if (mVideoFileName == null)
		{
			mVideoFileName = getRealPath(getIntent().getData());
		}
		Log.e(TAG, "mVideoFileName:"+mVideoFileName);
	}
	
	
	static final int MESSAGE_CMD_VIDEO_DURATION = 0x1001;
	static final int MESSAGE_CMD_VIDEO_UPDATE_TIME = 0x1002;
	static final int MESSAGE_CMD_VIDEO_END = 0x1003;
	
	
	static final int MESSAGE_CMD_CONTROL_DISMISS = 0xF001;
	static final int MESSAGE_CMD_CONTROL_SHOW    = 0xF002;
	
	@Override
	protected boolean onUnhandledMessage(int command, Object param) {
		switch (command) {
		case MESSAGE_CMD_VIDEO_DURATION:
		{
			int tenSec = (Integer)param;
			Log.i(TAG, "duration:"+tenSec);
			int secs = tenSec/10;
			int hour = secs/3600;
			int min = (secs/60)%60;
			int sec = secs%60;
			mTextViewPlayTimeTotal.setText(hour+":"+min+":"+sec);
			mSeekBar.setMax(secs);
		}
			break;
		case MESSAGE_CMD_VIDEO_UPDATE_TIME:
		{
			int tenSec = (Integer)param;
			int secs = tenSec/10;
			int hour = secs/3600;
			int min = (secs/60)%60;
			int sec = secs%60;
			mTextViewPlayTimeAt.setText(hour+":"+min+":"+sec);
			mSeekBar.setProgress(secs);
		}
			break;
		case MESSAGE_CMD_VIDEO_END:
			break;
		case MESSAGE_CMD_CONTROL_DISMISS:
			Log.i(TAG, "MESSAGE_CMD_CONTROL_DISMISS");
			mRelativeLayoutControl.setVisibility(View.INVISIBLE);
			break;
		case MESSAGE_CMD_CONTROL_SHOW:
			Log.i(TAG, "MESSAGE_CMD_CONTROL_SHOW");
			mRelativeLayoutControl.setVisibility(View.VISIBLE);
			break;
		default:
			return false;
		}
		return true;
	}
	@Override
	protected String[] getArguments() {
		String argv[] = {mVideoFileName};
		return argv;
	}
}
