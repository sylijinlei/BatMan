package com.seuic.util;

import java.nio.ByteBuffer;

import com.seuic.AppInforToSystem;

public class Adpcm2Pcm
{
	
	private static int[] step_table = { 7, 8, 9, 10, 11, 12, 13, 14, 16, 17,
		19, 21, 23, 25, 28, 31, 34, 37, 41, 45, 50, 55, 60, 66, 73, 80, 88,
		97, 107, 118, 130, 143, 157, 173, 190, 209, 230, 253, 279, 307,
		337, 371, 408, 449, 494, 544, 598, 658, 724, 796, 876, 963, 1060,
		1166, 1282, 1411, 1552, 1707, 1878, 2066, 2272, 2499, 2749, 3024,
		3327, 3660, 4026, 4428, 4871, 5358, 5894, 6484, 7132, 7845, 8630,
		9493, 10442, 11487, 12635, 13899, 15289, 16818, 18500, 20350,
		22385, 24623, 27086, 29794, 32767 };
	
	private static int[] index_adjust = { -1, -1, -1, -1, 2, 4, 6, 8, -1, -1,
		-1, -1, 2, 4, 6, 8, };

	public static byte[] adpcm_encode(byte[] raw, int len, int pre_sample,int index) {
		ByteBuffer bEncoded = ByteBuffer.allocate(len / 4);
		int i;
		int code;
		int sb;
		int delta;
		int diff;
		int vpdiff;
		int step;
		byte output = 0;

		step = step_table[index];
		len /= 2;

		for (i = 0; i < len; i++) {
			code = ByteUtility.byteArrayToInt(raw, i * 2, 2);

			/* step 1 */
			diff = code - pre_sample;
			sb = (diff < 0) ? 8 : 0;
			if (sb != 0)
				diff = -diff;

			/* step 2 */
			delta = 0;
			vpdiff = (step >> 3);
			if (diff >= step) {
				delta = 4;
				diff -= step;
				vpdiff += step;
			}
			step >>= 1;
			if (diff >= step) {
				delta |= 2;
				diff -= step;
				vpdiff += step;
			}
			step >>= 1;
			if (diff >= step) {
				delta |= 1;
				vpdiff += step;
			}

			/* step 3 */
			if (sb != 0)
				pre_sample -= vpdiff;
			else
				pre_sample += vpdiff;

			/* step 4 */
			if (pre_sample > 32767)
				pre_sample = 32767;
			else if (pre_sample < -32768)
				pre_sample = -32768;

			/* step 5 */
			delta |= sb;

			index += index_adjust[delta];
			if (index < 0)
				index = 0;
			if (index > 88)
				index = 88;
			step = step_table[index];

			/* step 6 */
			if ((i & 0x01) != 0) {
				output = (byte) ((delta & 0x0f) | output);
				bEncoded.put(output);
			} else {
				output = (byte) ((delta << 4) & 0xf0);
			}
		}

		AppInforToSystem.capture_paraSample = pre_sample;
		AppInforToSystem.capture_paraIndex = index;

		return bEncoded.array();
	}
}
