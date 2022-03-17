package com.takeoff.iot.modbus.serialport.handler;

import java.util.List;

import com.takeoff.iot.modbus.serialport.data.factory.SerialportDataFactory;
import org.bouncycastle.util.encoders.Hex;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 类功能说明：netty接收串口数据进行拆包处理 <br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Slf4j
public class NettyRxtxDecoderHandler extends MessageToMessageDecoder<ByteBuf> {
    
	private SerialportDataFactory serialportDataFactory;

	public NettyRxtxDecoderHandler(SerialportDataFactory serialportDataFactory) {
		this.serialportDataFactory = serialportDataFactory;
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		try {
			byte[] msg = new byte[in.readableBytes()];
			in.readBytes(msg);
			log.info("接收到待处理指令："+Hex.toHexString(msg));
			serialportDataFactory.buildData(msg);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

}
