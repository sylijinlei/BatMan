package com.seuic.protocol;

public class GO_DIRECTION_CAR {
	/*
	 *  电机1：是用来控制小车后面两个轮子前进和后退
	 *  电机2： 是用来控制小车前面两个轮子左转和右转
	 *  控制说明：当前进和后退时，只有后面两个轮子动（即只有电机1动）；若要转弯，则需电机1和电机2同时动
	 *  1、当小车前进时，前面两个轮子不动，电机1驱动后面两个轮子向前转；
	 *	2、当小车后退时，前面两个轮子不动，电机1驱动后面两个轮子向后转；
	 *	3、当小车左转弯时，电机2驱动前面两个轮子向左转，同时电机1驱动后面两个轮子向前转；
	 *	4、当小车右转弯时，电机2驱动前面两个轮子向右转，同时电机1驱动后面两个轮子向前转；
	 *	5、当小车前左转弯时，电机2驱动前面两个轮子向左转，同时电机1驱动后面两个轮子向前转；
	 *	6、当小车前右转弯时，电机2驱动前面两个轮子向右转，同时电机1驱动后面两个轮子向前转；
	 *	7、当小车后左转弯时，电机2驱动前面两个轮子向左转，同时电机1驱动后面两个轮子向后转；
	 *	8、当小车后右转弯时，电机2驱动前面两个轮子向右转，同时电机1驱动后面两个轮子向后转；
	 */
	public static final int move_stop_1 = 0; //电机1停止(后面轮子)
	public static final int move_forward_1 = 1;//电机1前进(前进)
	public static final int move_back_1 = 2;//电机1后退(后退)
	public static final int move_stop_2 = 3;//电机2停止(前面的轮子)
	public static final int move_forward_2 = 4;//电机2前进(右转)
	public static final int move_back_2 = 5;//电机2后退(左转)

}
