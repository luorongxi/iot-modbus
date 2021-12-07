package com.takeoff.iot.modbus.netty.bytes.factory;

/**
 * 类功能说明：整型指令工厂<br/>
 * 公司名称：takeoff开源 <br/>
 * 作者：luorongxi <br/>
 */
public class MiiInteger2BytesFactory<E extends Number> implements MiiBytesFactory<E> {
	
	private int byteSize,startPos;
	private int total;
	
	public MiiInteger2BytesFactory(int byteSize,int maxLenght,int startPos){
		this.byteSize = byteSize;
		this.startPos = startPos;
		this.total = byteSize * maxLenght;
	}
	
	@Override
	public byte[] toBytes(Number... contents) {
		byte[] bytes = new byte[total];
		int length = contents.length, n = startPos;
		for(int i = 0; i < total; ){
			if(n < length){
				long content = contents[n].longValue();
				for(int j = 0; j < byteSize; j++){
					bytes[i] = (byte) (content & 0xFF);
					content >>>= 8;
					i++;
				}
				n++;
			} else {
				bytes[i] = 0x00;
				i++;
			}
		}
		return bytes;
	}

}
