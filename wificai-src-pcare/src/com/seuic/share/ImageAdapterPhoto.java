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

public class ImageAdapterPhoto extends BaseAdapter {
	private Context mContext;
	private ArrayList<Bitmap> photos = new ArrayList<Bitmap>();
	LayoutInflater inflater;
	int width = 0, height = 0;
	/**
	 * @param context
	 */
	public ImageAdapterPhoto(Context context) {
		mContext = context;
		inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		width = (ShareActivity.Screen_width - ShareActivity.dip2px(80))/4; //60
		height = (width * 3) /4;
	}

	public void addPhoto(Bitmap photo) {
		photos.add(photo);
	}
	/**
	 * 当删除图片时，局部更新数据
	 * @param postion
	 */
	public void removePhoto(int postion){
		if (postion < getCount()) {
			photos.remove(postion);
			this.notifyDataSetChanged();
		}
	}
	
	public int getCount() {
		return photos.size();
	}

	public Object getItem(int position) {
		return photos.get(position);
	}

	public long getItemId(int position) {
		return position;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		convertView = inflater.inflate(R.layout.griditemlayout,null);
		imageView = (ImageView) convertView.findViewById(R.id.itemImage);
		convertView.setLayoutParams(new GridView.LayoutParams(width, height));//重点行
		imageView.setImageBitmap(photos.get(position));
		return convertView;
	}
}
