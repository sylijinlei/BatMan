package com.seuic.protocol;

public class STATUS_CONNECTION
{
	public static final int STATE_DISCONNECTED = 0;
	public static final int STATE_REQ_SEND = 1;
	public static final int STATE_RESP_RECEIVE = 2;
	public static final int STATE_VERI_SEND = 3;
	public static final int STATE_VERI_RECEIVE = 4;
	public static final int STATE_CONNECTED = 5;
}
