package com.takeoff.iot.modbus.netty.bytes.factory;

import com.takeoff.iot.modbus.netty.utils.BytesHexTransform;

public class MiiStrings2BytesFactory implements MiiBytesFactory<String> {

	private static final int START_POS = 0;
	
	@Override
	public byte[] toBytes(String... contents) {
		String content = contents[START_POS];
		return BytesHexTransform.hexToByteArray(content);
	}
}
