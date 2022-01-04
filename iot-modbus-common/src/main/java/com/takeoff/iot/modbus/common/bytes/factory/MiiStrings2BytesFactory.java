package com.takeoff.iot.modbus.common.bytes.factory;

import com.takeoff.iot.modbus.common.utils.BytesToHexUtil;

/**
 * 类功能说明：字符串类型指令工厂<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
public class MiiStrings2BytesFactory implements MiiBytesFactory<String> {

	private static final int START_POS = 0;
	
	@Override
	public byte[] toBytes(String... contents) {
		String content = contents[START_POS];
		return BytesToHexUtil.hexToByteArray(content);
	}
}
