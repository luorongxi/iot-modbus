package com.takeoff.iot.modbus.netty.handle;

import com.takeoff.iot.modbus.netty.message.MiiMessage;
import org.bouncycastle.util.encoders.Hex;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 类功能说明：自定义编码器，将数据编码后进行下发<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Slf4j
public class MiiMessageEncoder extends MessageToByteEncoder<MiiMessage> {

	@Override
	public void encode(ChannelHandlerContext ctx, MiiMessage msg, ByteBuf out) throws Exception {
		try {
			out.writeBytes(msg.toBytes());
			log.debug(Hex.toHexString(msg.toBytes()));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
