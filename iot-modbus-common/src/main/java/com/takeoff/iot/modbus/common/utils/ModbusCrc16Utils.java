package com.takeoff.iot.modbus.common.utils;

public class ModbusCrc16Utils {

	/**
	 * 计算CRC16校验码
	 *
	 * @param bytes
	 *            字节数组
	 * @return {@link String} 校验码
	 * @since 1.0
	 */
	public static String getCrcString(byte[] bytes) {
	    // CRC寄存器全为1
	    int CRC = 0x0000ffff;
	    // 多项式校验值
	    int POLYNOMIAL = 0x0000a001;
	    int i, j;
	    for (i = 0; i < bytes.length; i++) {
	        CRC ^= ((int) bytes[i] & 0x000000ff);
	        for (j = 0; j < 8; j++) {
	            if ((CRC & 0x00000001) != 0) {
	                CRC >>= 1;
	                CRC ^= POLYNOMIAL;
	            } else {
	                CRC >>= 1;
	            }
	        }
	    }
	    // 结果转换为16进制
	    String result = Integer.toHexString(CRC).toLowerCase();
	    if (result.length() != 4) {
	        StringBuffer sb = new StringBuffer("0000");
	        result = sb.replace(4 - result.length(), 4, result).toString();
	    }
	    //高位在前地位在后
	    //return result.substring(2, 4) + result.substring(0, 2);
	    // 交换高低位，低位在前高位在后
	    return result.substring(2, 4) + result.substring(0, 2);
	}
	
	/**
     * 计算CRC16校验码
     *
     * @param bytes
     * @return
     */
    public static byte[] getCrcByte(byte[] bytes) {
        int CRC = 0x0000ffff;
        int POLYNOMIAL = 0x0000a001;

        int i, j;
        for (i = 0; i < bytes.length; i++) {
            CRC ^= ((int) bytes[i] & 0x000000ff);
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) != 0) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
                }
            }
        }
        return new byte[]{
                (byte) (CRC & 0xff), (byte) (CRC >>> 8 & 0xff)
        };
    }
}
