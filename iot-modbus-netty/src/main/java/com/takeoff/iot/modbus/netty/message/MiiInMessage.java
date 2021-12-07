package com.takeoff.iot.modbus.netty.message;

import java.util.Arrays;

import com.takeoff.iot.modbus.netty.data.base.MiiData;
import com.takeoff.iot.modbus.netty.data.factory.MiiDataFactory;
import com.takeoff.iot.modbus.netty.utils.IntegerByteTransform;
import com.takeoff.iot.modbus.netty.utils.ModbusCrc16Utils;
import org.apache.commons.lang3.ArrayUtils;
import org.bouncycastle.util.encoders.Hex;

import io.netty.handler.codec.DecoderException;

/**
 * 类功能说明：对接收到的指令数据进行校验<br/>
 * 公司名称：takeoff开源 <br/>
 * 作者：luorongxi <br/>
 */
public class MiiInMessage implements MiiMessage {
	
	private String deviceGroup;
	private byte[] msg;
	private MiiData data;
	
	public MiiInMessage(byte[] msg, MiiDataFactory dataFactory){
		this(null, msg, dataFactory);
	}
	
	public MiiInMessage(String deviceGroup, byte[] msg, MiiDataFactory dataFactory){
		this.deviceGroup = deviceGroup;
		this.msg = msg;
		byte[] headBytes = {msg[BEGIN_INDEX]};
		if(!Arrays.equals(BEGIN_BYTES, headBytes)){
			throw new DecoderException(String.format("报文头异常:%s", Hex.toHexString(msg)));
		}
		byte[] datas = ArrayUtils.subarray(msg, COMMAND_INDEX, msg.length - 3);
		byte[] dataLength = ArrayUtils.subarray(msg, DATA_INDEX, COMMAND_INDEX);
		int num = datas.length;
		if(datas.length != IntegerByteTransform.bytesToInt(dataLength)){
			throw new DecoderException(String.format("报文长短异常:%s", Hex.toHexString(msg)));
		}
		byte[] checkcode = {msg[msg.length - 3],msg[msg.length - 2]};
		byte[] checkData = ArrayUtils.subarray(msg, DATA_INDEX, msg.length - 3);
		if(!ModbusCrc16Utils.getCrcString(checkData).equals(Hex.toHexString(checkcode))){
			throw new DecoderException(String.format("报文校验码校验错误:%s", Hex.toHexString(msg)));
		}
		int command = msg[COMMAND_INDEX] & 0x7F;
		this.data = dataFactory.buildData(command, datas);
	}
	
	public String deviceGroup() {
		return deviceGroup;
	}
	
	public int command() {
		return msg[COMMAND_INDEX] & 0x7F;
	}
	
	public int length() {
		byte[] dataLength = ArrayUtils.subarray(msg, DATA_INDEX, COMMAND_INDEX);
		return IntegerByteTransform.bytesToInt(dataLength);
	}
	
	public MiiData data() {
		return data;
	}

	public int type() {
		return RECV;
	}

	public byte[] toBytes() {
		return msg;
	}
}
