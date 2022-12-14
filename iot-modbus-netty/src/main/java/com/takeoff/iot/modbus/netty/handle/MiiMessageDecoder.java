package com.takeoff.iot.modbus.netty.handle;

import java.util.List;

import com.takeoff.iot.modbus.common.bytes.factory.MiiDataFactory;
import com.takeoff.iot.modbus.netty.channel.MiiChannel;
import com.takeoff.iot.modbus.netty.device.MiiDeviceChannel;
import com.takeoff.iot.modbus.netty.message.MiiInMessage;
import io.netty.channel.Channel;
import org.bouncycastle.util.encoders.Hex;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 类功能说明：自定义解码器，主要是用来解决半包积累的问题<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Slf4j
public class MiiMessageDecoder extends ByteToMessageDecoder {

	private MiiDataFactory dataFactory;

	public MiiMessageDecoder(MiiDataFactory dataFactory) {
		this.dataFactory = dataFactory;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		try {
			byte[] array = new byte[in.readableBytes()];
			in.readBytes(array);
			log.info("接收到待处理指令："+Hex.toHexString(array));
			MiiChannel miiChannel = new MiiDeviceChannel(ctx.channel());
			MiiInMessage msg = new MiiInMessage(miiChannel.name(),array,dataFactory);
			out.add(msg);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}
}
