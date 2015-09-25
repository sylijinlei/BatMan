package com.seuic.share;

import java.util.ArrayList;

import com.batman.package1.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapterVideo extends BaseAdapter {

	private Context mContext;
	private ArrayList<Bitmap> videos = new ArrayList<Bitmap>();
	LayoutInflater inflater;
	 int width = 0, height = 0;
	/**
	 * @param context
	 */
	public ImageAdapterVideo(Context context) {
		mContext = context;
		inflater = LayoutInflater.from(mContext);
		width = (ShareActivity.Screen_width - ShareActivity.dip2px(80))/4; //60
		height = (width * 3) /4;
	}

	public void addPhoto(Bitmap photo) {
		videos.add(photo);
	}
	
	public void removePhoto(int position){
		if (position < getCount()) {
			videos.remove(position);
			notifyDataSetChanged();
		}
	}
	
	public int getCount() {
		return videos.size();
	}

	public Object getItem(int position) {
		return videos.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		HolderView holderView;
		convertView = inflater.inflate(R.layout.video_view_item, parent, false);		
		holderView  = new HolderView();
		holderView.imgv = (ImageView)convertView.findViewById(R.id.imageView_video_view);
		convertView.setLayoutParams(new GridView.LayoutParams(width, height));//重点行
		holderView.imgv.setImageBitmap(videos.get(position));
		
		return convertView;
	}
	class HolderView{
		ImageView imgv;
	}
}
