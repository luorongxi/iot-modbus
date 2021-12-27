package com.takeoff.iot.modbus.common.bytes.factory;

public class MiiMultiLockDataBytesFactory implements MiiBytesFactory<Integer> {
	
	private static final int BYTE_SIZE = 1,START_POS = 0;
	
	@Override
	public byte[] toBytes(Integer... contents) {
		byte[] bytes = new byte[contents.length];
		int length = contents.length, n = START_POS;
		for(int i = 0; i < contents.length; ){
			if(n < length){
				long content = contents[n].longValue();
				for(int j = 0; j < BYTE_SIZE; j++){
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
