package com.wificar.dialog;

import com.batman.package1.R;
import com.seuic.util.WificarNewLayoutParams;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class G_Dilaog extends Dialog {

	public WificarNewLayoutParams wificarNewLayoutParams;//奇幻修改_0826
	
	public G_Dilaog(Context context, int type) {
		super(context, type);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		wificarNewLayoutParams = WificarNewLayoutParams.getWificarNewLayoutParams();//奇幻修改_0826
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if(wificarNewLayoutParams.screenSize>5.8){//奇幻修改_0826
			setContentView(R.layout.disagsensor_pad);
		}else{
			setContentView(R.layout.disagsensor);
		}
		
		ImageView imageView =(ImageView)findViewById(R.id.imageView1);
		imageView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancel();
			}
		});
	}
}
