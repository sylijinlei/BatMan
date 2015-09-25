package com.seuic.share;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.xmlpull.v1.XmlPullParser;

import com.batman.package1.R;
import com.seuic.AppActivityClose;
import com.seuic.AppInforToCustom;
import com.seuic.share.DeleteDialog;
import com.seuic.share.layoutparams.ImageParams;
import com.wificar.dialog.wifi_not_connect;

import android.content.ContentResolver;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Gallery.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ImageGalleryActivity extends Activity {
	private static ImageGalleryActivity mContext = null;
	public final static int DELETE_ENBALE = 5000;
	public final static int SHARE_ENABLE = 5001;
	public ImageAdapter imageAdapter;
	public static XmlPullParser parser;
	private PopupWindow mPopupWindow;
	private MyGallery myGallery;
	public LinearLayout Parent;
	private String photoSDpath;
	RelativeLayout myGallerylLayout;
	private String photoPath;
	private int positionP = 0;
	private List<String> photo_path1;
	public ImageParams imageParams;
	private TextView photos_count;	
	private Button deleButton;
	private Button shareButton;
	Timer deletetDelayTimer;
	Timer sharetDelayTimer;
	private boolean isShowing = false;
	private boolean connectWifi = false;
	private File file;
	private Dialog dlg;
	Handler handler;
	public boolean isExit = false;
	public boolean isShared = false;
	public boolean isBackgroud = false;
	public static ImageGalleryActivity getInstance() {
		return mContext;
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (isShared && !isExit) {
			isShared = false;
		}else if (isBackgroud) {
			isBackgroud = false;
		}else if (!isShared && !isExit && !AppActivityClose.isExit) {
			isExit = true;
			AppActivityClose.getInstance().exitAll();
		}
	}
	
	@Override
	protected void onDestroy() {

		
		if (sharetDelayTimer != null) {
			sharetDelayTimer.cancel();
			sharetDelayTimer = null;
		}
		if (deletetDelayTimer != null) {
			deletetDelayTimer.cancel();
			deletetDelayTimer = null;
		}
		dismiss();
		super.onDestroy();
	}
	
	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		if (parser == null) {
			parser = getResources().getXml(R.layout.my_gallery);
		}
		AttributeSet attributes = Xml.asAttributeSet(parser);
		imageParams = new ImageParams(this);
		Parent = new LinearLayout(this);
		myGallerylLayout = new RelativeLayout(this);
		myGallerylLayout.setId(3);
		myGallery = new MyGallery(mContext, attributes);
		myGallery.setSpacing(16);
		
		imageParams.getDisplayMetrics();
		imageParams.initVar();
		imageParams.initLandLayoutParams();
		
		myGallerylLayout.addView(myGallery,imageParams.myGallerylLayoutParams);
		Parent.addView(myGallerylLayout,imageParams.GallerylLayoutParams);
		Parent.setBackgroundResource(R.drawable.background);//奇幻修改_0826
		setContentView(Parent,imageParams.parentLayoutParams);		
		AppActivityClose.getInstance().addActivity(this); //tianjia
		photoSDpath = AppInforToCustom.getAppInforToCustomInstance().getCameraPicturePath();
		connectWifi = note_Intent(mContext);
		photo_path1  = getInSDPhoto();
		
		imageAdapter = new ImageAdapter(getApplicationContext());
		Intent intent = getIntent();
		photoPath = intent.getStringExtra("ImagePath");
		int currenPosition = intent.getIntExtra("position", 0);
		
		myGallery.setAdapter(imageAdapter);
    	myGallery.setSelection(currenPosition);
		myGallery.setOnItemSelectedListener(listenerPhoto);

		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case DELETE_ENBALE:
					deleButton.setEnabled(true);
                    shareButton.setEnabled(true);
					break;
				case SHARE_ENABLE:
					shareButton.setEnabled(true);
                    deleButton.setEnabled(true);
					break;
				}
			}
		};
	}
	/*
	 * 获取照片路径下的图片
	 */
	public List<String> getInSDPhoto() {
			List<String> it_p = new ArrayList<String>();
//			String path = AppInforToCustom.getAppInforToCustomInstance().getCameraShootingPath();
			File f = new File(photoSDpath);
			if (!f.exists()) {
				f.mkdirs();
			}else {
				File[] files = f.listFiles();
				for(File file : files){
					if (file.isFile()) {
						String fileName = file.getName(); 
						if (fileName.endsWith(".jpg")) { 						
							it_p.add(file.getPath());
						}
					}
				}
			}
			return it_p;
	}
	/*
	 * 判断网络是否连接
	 */
	public boolean note_Intent(Context context) {
		ConnectivityManager con = (ConnectivityManager) context
		.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkinfo = con.getActiveNetworkInfo();
		if (networkinfo == null || !networkinfo.isAvailable()) {
			return false;
		}
		else{
			return true;
		}
	}
	/*
	 * 当界面消失时，将PopupWindow取消
	 */
	 public void dismiss() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
	 }
	
	 class deleteDelayTask extends TimerTask{
		@Override
		public void run() {
			Message msg = new Message();
			msg.what = DELETE_ENBALE;
			handler.sendMessage(msg);
			deletetDelayTimer.cancel();
			deletetDelayTimer = null;
		}
	 }
	 class shareDelayTask extends TimerTask{
		@Override
		public void run() {
			Message msg = new Message();
			msg.what = SHARE_ENABLE;
			handler.sendMessage(msg);
			sharetDelayTimer.cancel();
			sharetDelayTimer = null;
		}
	 }
	private void showPopWindow(){
	    dismiss();
	    isShowing = true;
        View foot_popunwindwow = null;
        LayoutInflater LayoutInflater = (LayoutInflater) mContext
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        foot_popunwindwow = LayoutInflater.inflate(R.layout.photo_count, null);
        mPopupWindow = new PopupWindow(foot_popunwindwow,
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mPopupWindow.showAtLocation(findViewById(3),
                Gravity.TOP , 0, 5);
        mPopupWindow.update();
        photos_count = (TextView) foot_popunwindwow.findViewById(R.id.photo_counts);
        deleButton = (Button) foot_popunwindwow.findViewById(R.id.delete_button);
        deleButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				deleButton.setEnabled(false);
                shareButton.setEnabled(false);
				if(deletetDelayTimer == null){
					deletetDelayTimer = new Timer();
					deletetDelayTimer.schedule(new deleteDelayTask(), 1500);
				}
				dlg = new DeleteDialog(mContext,R.style.DeleteDialog,1);
				 
				 WindowManager m = getWindowManager(); 
			 	 Display d = m.getDefaultDisplay();
			 	 Window w=dlg.getWindow(); 
				 WindowManager.LayoutParams lp =w.getAttributes(); 
				 
				 w.setGravity(Gravity.RIGHT | Gravity.TOP);
				 lp.x=10; 
				 lp.y=70;
				 lp.height = (int) (d.getHeight() * 0.3);
				 w.setAttributes(lp);
				 dlg.show();
			}
		});
        shareButton = (Button) foot_popunwindwow.findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(connectWifi){
					shareButton.setEnabled(false);
                    deleButton.setEnabled(false);
					if(sharetDelayTimer ==null){
						sharetDelayTimer = new Timer();
						sharetDelayTimer.schedule(new shareDelayTask(), 1500);
					}
					Intent shareIntent =new Intent();
					shareIntent.setAction(Intent.ACTION_SEND);
					shareIntent.setType("image/jpeg"); 
					shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share");
					
					 file = new File(photoPath); 
					 
					 ContentValues content = new ContentValues(5);
					 content.put(MediaStore.Images.ImageColumns.TITLE, "Share");
					 content.put(MediaStore.Images.ImageColumns.SIZE, file.length());
					 content.put(MediaStore.Images.ImageColumns.DATE_ADDED,System.currentTimeMillis() / 1000); 
					 content.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
					 content.put(MediaStore.Images.Media.DATA, photoPath);
					 ContentResolver resolver = mContext.getContentResolver();
					 Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, content);
					 
					 if(uri == null){
						 shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file)); 
					 }else{
						 shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
					 }
					 
					startActivity(Intent.createChooser(shareIntent, "Share"));
					isShared = true;			
			}else{
        		wifi_not_connect.createwificonnectDialog(mContext).show();
        	}
			}
		});
	}
	
	public OnItemSelectedListener listenerPhoto = new OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> adapter, View view, int position,
				long id) {
			positionP = position;
			photoPath = photo_path1.get(positionP).toString();
			if(!isShowing){
				showPopWindow();
			}
			photos_count.setText(positionP + 1 + " of " + photo_path1.size());
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			
		}
	};
	public void Delete_photo() {
		photoPath = photo_path1.get(positionP).toString();//由于浏览大图时，没有更新photoPath的数据，所以删除的时候，要更新
		file = new File(photoPath);
		if(file.exists()){
			 file.delete();
		}
		PhotoImage.imageAdapterP.removePhoto(positionP); //删除图片的所路图// 这里要考虑200章图片
		if (positionP < PhotoImage.instance.photo.size()) {
			PhotoImage.instance.photo.remove(positionP);//删除PhotoImage
		}
		if (photo_path1.size() - 1 == 0)
		{
			dismiss();
			isExit = true;
			mContext.finish();
		}else {
			photo_path1.remove(positionP);
			if(positionP == photo_path1.size()){//如果删除最后一页，则返回第一页
				positionP = 0;
				imageAdapter = new ImageAdapter(getApplicationContext());
				myGallery.setAdapter(imageAdapter);
				myGallery.setSelection(positionP);
			}else {
				imageAdapter.notifyDataSetChanged();
			}
			dlg.dismiss();
			photoPath = photo_path1.get(positionP).toString();
			photos_count.setText(positionP + 1 + " of " + photo_path1.size());
		}
	}
	public Bitmap[] bitmap;
	class ImageAdapter extends BaseAdapter{
		private Context mContext; 
		LayoutInflater inflater1;
		BitmapFactory.Options options;
		Bitmap bitmap;
		public ImageAdapter(Context applicationContext) {
			mContext = applicationContext;
			inflater1 = LayoutInflater.from(mContext);
			options = new BitmapFactory.Options();
			options.inSampleSize = 2;
		}

		public int getCount() {
			return photo_path1.size();
		}

		public Object getItem(int potion) {
			return potion;
		}

		public long getItemId(int potion) {
			return potion;
		}

		public View getView(int potion, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {
				convertView = inflater1.inflate(R.layout.photo_view, null);
				viewHolder = new ViewHolder();
				viewHolder.imageView = (ImageView)convertView.findViewById(R.id.imageView_view);
				convertView.setTag(viewHolder);
			}else {
				viewHolder = (ViewHolder)convertView.getTag();
			}
			viewHolder.imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
			bitmap = BitmapFactory.decodeFile(photo_path1.get(potion).toString(), options);
			viewHolder.imageView.setImageBitmap(bitmap);
			return convertView;
		}
	}
	class ViewHolder {
		ImageView imageView;
	}
	
	@Override
	public void onBackPressed()
	{
		isExit = true;
		mContext.finish();
	}
}
