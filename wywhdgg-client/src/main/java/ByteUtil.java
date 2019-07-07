/**
 * 数字与字节数组转换
 * <p>
 * short 2字节数组
 * </p>
 * <p>
 * int 4字节
 * </p>
 * <p>
 * long 8字节
 * </p>
 * 
 *
 */
public class ByteUtil {
	/** short2字节数组 */
	public static byte[] short2bytes(short v) {
		byte[] b = new byte[4];
		b[1] = (byte) v;
		b[0] = (byte) (v >>> 8);
		return b;
	}

	/** int4字节数组 */
	public static byte[] int2bytes(int v) {
		byte[] b = new byte[4];
		b[3] = (byte) v;
		b[2] = (byte) (v >>> 8);
		b[1] = (byte) (v >>> 16);
		b[0] = (byte) (v >>> 24);
		return b;
	}

	/** long8字节数组 */
	public static byte[] long2bytes(long v) {
		byte[] b = new byte[8];
		b[7] = (byte) v;
		b[6] = (byte) (v >>> 8);
		b[5] = (byte) (v >>> 16);
		b[4] = (byte) (v >>> 24);
		b[3] = (byte) (v >>> 32);
		b[2] = (byte) (v >>> 40);
		b[1] = (byte) (v >>> 48);
		b[0] = (byte) (v >>> 56);
		return b;
	}

	/** 字节数组转字符串 */
	public static String bytesToHexString(byte[] bs) {
		if (bs == null || bs.length == 0) {
			return null;
		}

		StringBuffer sb = new StringBuffer();
		String tmp = null;
		for (byte b : bs) {
			tmp = Integer.toHexString(Byte.toUnsignedInt(b));
			if (tmp.length() < 2) {
				sb.append(0);
			}
			sb.append(tmp);
		}
		return sb.toString();
	}
}
