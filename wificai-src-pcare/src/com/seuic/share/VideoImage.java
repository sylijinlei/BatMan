package com.seuic.share;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.batman.package1.R;
import com.seuic.AppActivityClose;
import com.seuic.AppInforToCustom;
import com.wificar.util.MessageUtility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore.Video.Thumbnails;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class VideoImage extends Activity {
	public List<String> video_path;
	public String path;
	public static ImageAdapterVideo imageAdapterV;
	private GridView video_gridview;
	public static VideoImage instance;
	Bitmap bitmapV = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("VideoImage", "in onCreate ...");
		setContentView(R.layout.videoimage);
		instance = this;
		AppActivityClose.getInstance().addActivity(this); // tianjia
		path = AppInforToCustom.getAppInforToCustomInstance()
				.getCameraShootingPath();
		imageAdapterV = new ImageAdapterVideo(getApplicationContext());
		video_gridview = (GridView) findViewById(R.id.videoGallery);
		video_gridview.setAdapter(imageAdapterV);
		video_gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					File file = new File(video_path.get(position).toString());
					if (file.exists()) {
						ShareActivity.isBackground = true;
						Intent v = new Intent();
						v.setClass(VideoImage.this, VideoGalleryActivity.class);
						v.putExtra("videoPath", video_path.get(position)
								.toString());
						v.putExtra("position", position);
						startActivity(v);
					} else {
						// 如果用户在后台删除视频，这里则重新加载所有的视频
						video_path = getInSDVideo();
						imageAdapterV.notifyDataSetChanged();
						showMessage(MessageUtility.MESSAGE_SHARE_FILE_DELETE);
					}
					file = null;
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		video_path = getInSDVideo();
		if (video_path.size() >= 100) {
			showMessage(MessageUtility.MESSAGE_SHARE_VIDEO_PHOTO_MORE);
		}
	}

	@Override
	public void onStart() {
		Log.e("VideoImage", "in onStart ...");
		super.onStart();
		getAsyncTaskVideo();
	}
	Timer BackTimer = null;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (!ShareActivity.isBack) {
				ShareActivity.isBack = true;
				ShareActivity.instance.ShowInfo(
						R.string.click_again_to_exit_the_program,
						Toast.LENGTH_SHORT);
				BackTimer = new Timer();
				BackTimer.schedule(new backTimerTask(), 1000);
			} else {
				finish();
			}
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}

	}

	// 定时双击back
	class backTimerTask extends TimerTask {
		@Override
		public void run() {
			ShareActivity.isBack = false;
		}
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return true;
	}

	/**
	 * 异步加载视频缩略图
	 */
	public void getAsyncTaskVideo() {
		final Object data = getLastNonConfigurationInstance();
		if (data == null) {
			new AsyncTaskLoadVideo(VideoImage.this, video_path).execute();
		} else {
			final Bitmap[] videos = (Bitmap[]) data;
			if (videos.length == 0) {
				new AsyncTaskLoadVideo(VideoImage.this, video_path).execute();
			}
			for (Bitmap photo : videos) {
				imageAdapterV.addPhoto(photo);
				imageAdapterV.notifyDataSetChanged();
			}
		}
	}

	/**
	 * 视频异步类
	 * 
	 * @author zhangjie
	 */
	class AsyncTaskLoadVideo extends AsyncTask<Object, Bitmap, Object> {
		private List<String> video_lis;

		public AsyncTaskLoadVideo(Context mContext, List<String> path) {
			video_lis = path;
		}

		/*
		 * 获取视频的缩图 先通过ThumbnailUtils来创建一个视频的图，然后再利用ThumbnailUtils来生成指定大小的图
		 * MICRO_KIND
		 */
		@Override
		protected Object doInBackground(Object... params) {
			Bitmap bitmapV = null;
			int size = video_lis.size();
			for (int i = 0; i < size; i++) {
				bitmapV = getVideoThumbnail(video_lis.get(i).toString(), 160,
						120, Thumbnails.MINI_KIND);
				if (bitmapV != null) {
					publishProgress(bitmapV);
				} else {
					Bitmap bitmapP1 = BitmapFactory.decodeResource(
							getResources(), R.drawable.wificar_icon);
					if (bitmapP1 != null) {
						bitmapV = Bitmap.createScaledBitmap(bitmapP1, 160, 120,
								true);
						publishProgress(bitmapV);
					}
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
		}

		@Override
		protected void onProgressUpdate(Bitmap... values) {
			for (Bitmap bitmap : values) {
				imageAdapterV.addPhoto(bitmap);
				imageAdapterV.notifyDataSetChanged();
			}
		}
	}

	/**
	 * 获取视频缩略图
	 * 
	 * @param videoPath
	 * @param width
	 * @param height
	 * @param kind
	 * @return
	 */
	private Bitmap getVideoThumbnail(String videoPath, int width, int height,
			int kind) {
		Bitmap bitmap = null;
		bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

	/*
	 * 获取SD卡指定目录的视频列表
	 */
	public List<String> getInSDVideo() {
		List<String> it_p = new ArrayList<String>();
		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		} else {
			File[] files = f.listFiles();
			for (File file : files) {
				if (file.isFile()) {
					String fileName = file.getName();
					if (fileName.endsWith(".mp4")) {
						it_p.add(file.getPath());
					}
					if (it_p.size() >= 100) {
						showMessage(MessageUtility.MESSAGE_SHARE_VIDEO_PHOTO_MORE);
						break;
					}
				}
			}
		}
		return it_p;
	}

	public void showMessage(String msg) {
		Toast toast = Toast.makeText(instance, msg, Toast.LENGTH_LONG);
		toast.show();
	}
}
