package com.takeoff.iot.modbus.common.message;

import com.takeoff.iot.modbus.common.data.MiiByteArrayData;
import com.takeoff.iot.modbus.common.data.MiiData;
import com.takeoff.iot.modbus.common.utils.IntegerByteTransform;

import org.apache.commons.lang3.ArrayUtils;

public class MiiByteArrayMessage implements MiiMessage {
	
	private String deviceGroup;
	private byte[] bytes;
	private MiiData data;
	
	public MiiByteArrayMessage(String deviceGroup, byte[] bytes){
		this.deviceGroup = deviceGroup;
		this.bytes = bytes;
		this.data = new MiiByteArrayData(ArrayUtils.subarray(bytes, MiiMessage.COMMAND_INDEX, bytes.length - 3));
	}

	@Override
	public String deviceGroup() {
		return this.deviceGroup;
	}
	
	@Override
	public int length() {
		byte[] dataLength = ArrayUtils.subarray(bytes, DATA_INDEX, COMMAND_INDEX);
		return IntegerByteTransform.bytesToInt(dataLength);
	}

	@Override
	public int command() {
		return bytes[COMMAND_INDEX] & 0x7F;
	}

	@Override
	public int type() {
		return SEND;
	}

	@Override
	public MiiData data() {
		return this.data;
	}

	@Override
	public byte[] toBytes() {
		return bytes;
	}

}
