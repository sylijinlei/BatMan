package com.seuic.function;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.seuic.AppInforToCustom;
import com.seuic.AppInforToSystem;
import com.seuic.protocol.CMD_OP_CODE;
import com.seuic.util.ByteUtility;

public class AppBrightLightFunction {
	private static AppBrightLightFunction appAppBrightLightFunctionInstance = null;
	public AppBrightLightFunction(){
		
	}
	public static AppBrightLightFunction getAppBrightLightFunctionInstance(){
		if (appAppBrightLightFunctionInstance == null)
		{
			appAppBrightLightFunctionInstance = new AppBrightLightFunction();
		}
		return appAppBrightLightFunctionInstance;
	}
	public void BrightLight_OandC(Boolean bEnable) {
		if (!AppInforToSystem.ConnectStatus){
			return;
		}
		try {
			ByteArrayOutputStream bOut = new ByteArrayOutputStream();
			String szHeader = "MO_O";
			int BrightLightCtrlCode = 0, cX = 0;

			if (bEnable)
				BrightLightCtrlCode = 13;
			else
				BrightLightCtrlCode = 14;
			AppInforToCustom.getAppInforToCustomInstance().setIsBrightControl(bEnable);
			// 0 ~ 3 is "MO_O"
			bOut.write(szHeader.getBytes(), 0, 4);
			// 4 ~ 7 is command
			bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Device_Control_Req));
			// 8 ~ 14 set to 0
			for (cX = 8; cX < 15; cX++)
				bOut.write(0x00);
			// 15 ~ 18 is the length = 1
			try {
				bOut.write(ByteUtility.int32ToByteArray(1));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 19 ~ 22 set to 0
			for (cX = 19; cX < 23; cX++)
				bOut.write(0x00);
			// 23 is the value
			bOut.write(ByteUtility.int8ToByteArray(BrightLightCtrlCode));
			if (bOut.size() > 0) {
				AppInforToSystem.dataOutputStream.write(bOut.toByteArray());
				AppInforToSystem.dataOutputStream.flush();
			}
		} catch (Exception e) {
		}
	}
}
