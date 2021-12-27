package com.takeoff.iot.modbus.netty.handle;

import java.nio.ByteOrder;

import com.takeoff.iot.modbus.common.message.MiiMessage;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 类功能说明：自定义解码器长度，解决TCP粘包黏包问题<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
public class MiiBasedFrameDecoder extends LengthFieldBasedFrameDecoder {
	
	public MiiBasedFrameDecoder(){
		super(ByteOrder.LITTLE_ENDIAN, Integer.MAX_VALUE, MiiMessage.DATA_INDEX, MiiMessage.DATA_SIZE, 3, 0, true);
	}
	
}
