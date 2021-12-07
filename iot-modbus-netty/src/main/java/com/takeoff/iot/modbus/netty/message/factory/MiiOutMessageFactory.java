package com.takeoff.iot.modbus.netty.message.factory;

import com.takeoff.iot.modbus.netty.bytes.factory.MiiBytesFactory;
import com.takeoff.iot.modbus.netty.message.MiiByteArrayMessage;
import com.takeoff.iot.modbus.netty.message.MiiMessage;
import com.takeoff.iot.modbus.netty.utils.IntegerByteTransform;
import com.takeoff.iot.modbus.netty.utils.ModbusCrc16Utils;
import org.apache.commons.lang3.ArrayUtils;

/**
 * 类功能说明：指令下发处理工厂<br/>
 * 公司名称：takeoff开源 <br/>
 * 作者：luorongxi <br/>
 */
public class MiiOutMessageFactory<E> implements MiiMessageFactory<E> {
	
	private MiiBytesFactory<E> factory;
	
	public MiiOutMessageFactory(MiiBytesFactory<E> factory) {
		this.factory = factory;
	}
	
	@Override
	public MiiMessage buildMessage(String deviceGroup, E... datas) {
		byte[] bytes = factory.toBytes(datas);
		byte[] msg = new byte[MiiMessage.BEGIN_SIZE + MiiMessage.DATA_SIZE + bytes.length + MiiMessage.CHECKCODE_SIZE + MiiMessage.END_SIZE];
		msg[MiiMessage.BEGIN_INDEX] = MiiMessage.BEGIN_BYTES[MiiMessage.BEGIN_INDEX];
		byte[] lengthBytes = IntegerByteTransform.intToByteArray(bytes.length, MiiMessage.DATA_SIZE);
		System.arraycopy(lengthBytes, 0, msg, MiiMessage.DATA_INDEX, lengthBytes.length);
		System.arraycopy(bytes, 0, msg , MiiMessage.COMMAND_INDEX, bytes.length);
		byte[] checkData = ArrayUtils.subarray(msg, MiiMessage.DATA_INDEX, msg.length - 3);
		byte[] checkCodeBytes = ModbusCrc16Utils.getCrcByte(checkData);
		System.arraycopy(checkCodeBytes, 0, msg , msg.length - 3, checkCodeBytes.length);
		System.arraycopy(MiiMessage.END_BYTES, 0, msg , msg.length - 1, 1);
		return new MiiByteArrayMessage(deviceGroup, msg);
	}
	
}
