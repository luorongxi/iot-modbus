package com.takeoff.iot.modbus.serialport.handler;

import java.nio.ByteOrder;

import com.takeoff.iot.modbus.common.message.MiiMessage;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 类功能说明：netty接收串口数据自定义解码器 <br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
public class NettyRxtxFrameDecoder extends LengthFieldBasedFrameDecoder {

	public NettyRxtxFrameDecoder(){
		super(ByteOrder.LITTLE_ENDIAN, Integer.MAX_VALUE, MiiMessage.DATA_INDEX, MiiMessage.DATA_SIZE, 3, 0, true);
	}
}
