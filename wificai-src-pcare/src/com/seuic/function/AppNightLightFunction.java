package com.seuic.function;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.seuic.AppInforToCustom;
import com.seuic.AppInforToSystem;
import com.seuic.protocol.CMD_OP_CODE;
import com.seuic.util.ByteUtility;

public class AppNightLightFunction
{
	private static AppNightLightFunction appNightLightFunctionInstance = null;
	public AppNightLightFunction(){
		
	}
	public static AppNightLightFunction getAppNightLightFunctionInstance(){
		if (appNightLightFunctionInstance == null)
		{
			appNightLightFunctionInstance = new AppNightLightFunction();
		}
		return appNightLightFunctionInstance;
	}
	
	/*
	 * 开启和关闭夜视灯
	 */
	public void NightLight_OandC(Boolean bEnable) {
		if (!AppInforToSystem.ConnectStatus){
			return;
		}

		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		String szHeader = "MO_O";
		int Night_LightCtrlCode = 0, cX = 0;

		if (bEnable)
			Night_LightCtrlCode = 94;
		else
			Night_LightCtrlCode = 95;
		AppInforToCustom.getAppInforToCustomInstance().setIsStealthControl(bEnable);
		// 0 ~ 3 is "MO_O"
		bOut.write(szHeader.getBytes(), 0, 4);
		// 4 ~ 7 is command
		try {
			bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Decoder_Control_Req));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		try {
			bOut.write(ByteUtility.int8ToByteArray(Night_LightCtrlCode));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (bOut.size() > 0) {
			try {
				AppInforToSystem.dataOutputStream.write(bOut.toByteArray());
				AppInforToSystem.dataOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
