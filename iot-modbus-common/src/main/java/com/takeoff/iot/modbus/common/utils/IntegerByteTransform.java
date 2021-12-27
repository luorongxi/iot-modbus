package com.takeoff.iot.modbus.common.utils;

public class IntegerByteTransform {

	/**
	 * 函数功能说明 ：将整数转换为byte数组并指定长度 <br/>
	 * 参数：@param contents 内容
	 * 参数：@param byteLength 指定长度
	 * 参数：@return <br/>
	 * return：byte[] <br/>
	 */
	public static byte[] intToByteArray(int contents, int byteLength) {
	    byte[] bs = new byte[byteLength];
	    for (int i = 0; i < bs.length; i++) {
	        bs[i] = (byte) (contents % 256);
	        contents = contents / 256;
	    }
	    return bs;
	}
	
	/**
	 * 函数功能说明 ： 从第beginPos位开始计算<br/>
	 * 参数：@param bs
	 * 参数：@param beginPos
	 * 参数：@return <br/>
	 * return：byte <br/>
	 */
	public static byte checkout(byte[] bs, int beginPos) {
        byte nSum = 0x00;
        for (int j = beginPos; j < bs.length; j++)
        {
            nSum ^= bs[j];
        }
        return nSum;
    }
	
	/**
	 * 函数功能说明 ：将byte数组转换为整数 <br/>
	 * 参数：@param bs
	 * 参数：@return <br/>
	 * return：int <br/>
	 */
	public static int bytesToInt(byte[] bs) {
	    int a = 0;
	    for (int i = 0; i < bs.length; i++) {
	        a += (bs[i] & 0xFF) * Math.pow(256, i);
	    }
	    return a;
	}
	
	/**
	 * 函数功能说明 ：将int数值转换为占四个字节的byte数组，本方法适用于(低位在前，高位在后)的顺序<br/>
	 * 参数：@param n 要转换的int值
	 * 参数：@return <br/>
	 * return：byte[] byte数组<br/>
	 */
	public static byte[] intToBytes(int n) {   
		byte[] src = new byte[2];    
	    src[1] =  (byte) ((n>>8) & 0xFF);    
	    src[0] =  (byte) (n & 0xFF);                  
	    return src; 
    }
	
	/**
	 * 函数功能说明 ：将int数值转换为占四个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序 <br/>
	 * 参数：@param value 要转换的int值
	 * 参数：@return <br/>
	 * return：byte[] byte数组<br/>
	 */
	public static byte[] intToBytes2(int n) {   
	    byte[] src = new byte[2];   
	    src[0] = (byte) ((n>>8)&0xFF);    
	    src[1] = (byte) (n & 0xFF);       
	    return src;  
	} 
	
	
	/**
	 * 函数功能说明 ：将int数值转换为占两个字节的byte数组，本方法适用于(低位在前，高位在后)的顺序<br/>
	 * 参数：@param n 要转换的int值
	 * 参数：@return <br/>
	 * return：byte[] byte数组<br/>
	 */
	public static byte[] intToBytes3(int n) {   
		byte[] src = new byte[2];  
	    src[1] =  (byte) ((n>>8) & 0xFF);    
	    src[0] =  (byte) (n & 0xFF);                  
	    return src; 
    }
	
	/**
	 * 函数功能说明 ： byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序<br/>
	 * 参数：@param src byte数组
	 * 参数：@param offset 从数组的第offset位开始
	 * 参数：@return <br/>
	 * return：int int数值<br/>
	 */
	public static int bytesToInt(byte[] src, int offset) {  
	    int value;    
	    value = (int) ((src[offset] & 0xFF)   
	            | ((src[offset+1] & 0xFF)<<8)   
	            | ((src[offset+2] & 0xFF)<<16)   
	            | ((src[offset+3] & 0xFF)<<24));  
	    return value;  
	}  
	
	/**
	 * 函数功能说明 ：byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序 <br/>
	 * 参数：@param src byte数组
	 * 参数：@param offset 从数组的第offset位开始
	 * 参数：@return <br/>
	 * return：int int数值<br/>
	 */
	public static int bytesToInt2(byte[] src, int offset) {  
	    int value;    
	    value = (int) ( ((src[offset] & 0xFF)<<24)  
	            |((src[offset+1] & 0xFF)<<16)  
	            |((src[offset+2] & 0xFF)<<8)  
	            |(src[offset+3] & 0xFF));  
	    return value;  
	} 

	
}
