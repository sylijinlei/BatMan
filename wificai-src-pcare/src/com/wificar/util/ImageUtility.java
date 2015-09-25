package com.wificar.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;

public class ImageUtility {
	public static Bitmap createBitmap(Resources res, int srcId) {

		Bitmap bitmap = BitmapFactory.decodeResource(res, srcId);
		return bitmap;

	}

	public static void createJPEGFile(byte[] buf, ContentResolver cr) {

        try {
            Bitmap snap = BitmapFactory.decodeByteArray(buf, 0, buf.length);
            MediaStore.Images.Media.insertImage(cr, snap, System.currentTimeMillis() + ".jpg", System.currentTimeMillis() + ".jpg");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
	public static int getWidth(Context context){
		int x = context.getResources().getDisplayMetrics().widthPixels;
		return x;
	}
	public static int getHeight(Context context){
		int y = context.getResources().getDisplayMetrics().heightPixels;
		return y;
	}
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		
		return (int) (dpValue * (scale/1.0f));
	}
	
	public static float getDensity(Context context){
		final float scale = context.getResources().getDisplayMetrics().density;
		return scale;
	}

	public static int px2dip(Context context, float pxValue) {
		
		final float scale = context.getResources().getDisplayMetrics().density;
		
		return (int) ((pxValue / (1.5f/scale)));
	}
	public static int getBatterySection(int value){
		if(value<10){
			return 0;
		}
		else if(value<30){
			return 1;
		}
		else if(value<60){
			return 2;
		}
		else if(value<90){
			return 3;
		}
		else {
			return 4;
		}
	}
}
