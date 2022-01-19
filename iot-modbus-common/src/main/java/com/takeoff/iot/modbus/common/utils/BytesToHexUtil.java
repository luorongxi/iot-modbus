package com.takeoff.iot.modbus.common.utils;

/**
 * 类功能说明：Bytes与Hex转换工具类<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
public class BytesToHexUtil {

    /**
     * Hex字符串转byte
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte
     */
    public static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }

    /**
     * hex字符串转byte数组
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte数组结果
     */
    public static byte[] hexToByteArray(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            // 奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            // 偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    /**
     * 字节转十六进制
     * @param b 需要进行转换的byte字节
     * @return  转换后的Hex字符串
     */
    public static String byteToHex(byte b){
        String hex = Integer.toHexString(b & 0xFF);
        if(hex.length() < 2){
            hex = "0" + hex;
        }
        return hex;
    }

    /**
     * 数组转换成十六进制字符串
     * @param bArray 需要转换的byte数组
     * @return 转换后的Hex字符串
     */
    public static String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        for (int i = 0; i < bArray.length; i++) {
            String hexStr = Integer.toHexString(0xFF & bArray[i]);
            if (hexStr.length() < 2)
                sb.append(0);
            sb.append(hexStr.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 函数功能说明 ： Ascii编码byte数组转换字符串<br/>
     * 修改者名字： <br/>
     * 修改日期： <br/>
     * 修改内容：<br/>
     * 作者：Lion <br/>
     * 参数：@param bArray
     * 参数：@return <br/>
     * return：String <br/>
     */
    public static String asciiToString(byte[] bArray) {
        StringBuilder sbu = new StringBuilder();
        byte b;
        int i;
        byte[] arrayOfByte;
        for (i = (arrayOfByte = bArray).length, b = 0; b < i; ) {
            byte b1 = arrayOfByte[b];
            if (b1 == 0)
                break;
            sbu.append((char)Integer.parseInt(String.valueOf(b1)));
            b++;
        }
        //去除转义字符、回车等范围
        return sbu.toString().replaceAll("[\\u0000-\\u001f\b]","");
    }
}
