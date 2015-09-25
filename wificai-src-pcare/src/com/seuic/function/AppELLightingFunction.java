package com.seuic.function;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.seuic.AppInforToCustom;
import com.seuic.AppInforToSystem;
import com.seuic.protocol.CMD_OP_CODE;
import com.seuic.util.ByteUtility;

public class AppELLightingFunction {
	private static AppELLightingFunction appELLightingFunctionFunctionInstance = null;
	public AppELLightingFunction(){
		
	}
	public static AppELLightingFunction getAppELLightingFunctionFunctionInstance(){
		if (appELLightingFunctionFunctionInstance == null)
		{
			appELLightingFunctionFunctionInstance = new AppELLightingFunction();
		}
		return appELLightingFunctionFunctionInstance;
	}
	public void ELLight_OandC(Boolean bEnable) {
		if (!AppInforToSystem.ConnectStatus){
			return;
		}
		try {
			ByteArrayOutputStream bOut = new ByteArrayOutputStream();
			String szHeader = "MO_O";
			int ELLightCtrlCode = 0, cX = 0;

			if (bEnable)
				ELLightCtrlCode = 96;
			else
				ELLightCtrlCode = 97;
			AppInforToCustom.getAppInforToCustomInstance().setIsELChange(bEnable);
			// 0 ~ 3 is "MO_O"
			bOut.write(szHeader.getBytes(), 0, 4);
			// 4 ~ 7 is command
			bOut.write(ByteUtility.int32ToByteArray(CMD_OP_CODE.Decoder_Control_Req));
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
			bOut.write(ByteUtility.int8ToByteArray(ELLightCtrlCode));
			if (bOut.size() > 0) {
				AppInforToSystem.dataOutputStream.write(bOut.toByteArray());
				AppInforToSystem.dataOutputStream.flush();
			}
		} catch (Exception e) {
		}
	}
}
