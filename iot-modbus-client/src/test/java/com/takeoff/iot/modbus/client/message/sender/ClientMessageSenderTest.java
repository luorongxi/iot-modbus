package com.takeoff.iot.modbus.client.message.sender;

import java.util.ArrayList;

import com.takeoff.iot.modbus.netty.message.MiiMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Assert;
import org.junit.Test;

/**
 * 类功能说明：上传指令测试<br/>
 * 公司名称：takeoff开源 <br/>
 * 作者：luorongxi <br/>
 */
@Slf4j
public class ClientMessageSenderTest {

	@Test
	public void testRegoGroup(){
		ClientMessageSender sender = sender(MiiMessage.ACK,
				new byte[]{(byte) 0x7E, 0x06, 0x00,
						0x00, 0x00, 0x00, 0x00,
						0x00, 0x01,
						0x01, (byte) 0xE1, (byte) 0x7F});
		sender.registerGroup("1");
	}
		
	@Test
	public void testUnlock(){
		ClientMessageSender sender = sender(MiiMessage.LOCK,
				new byte[]{(byte) 0x7E, 0x05, 0x00,
						0x03, 0x01, 0x00, 0x00,
						0x01,
						(byte) 0xCA, (byte) 0x3C, (byte) 0x7F}
			);
		sender.unlock(1, 1);
	}
	
	private ClientMessageSender sender(int command, byte[] expecteds){
		ClientMessageSender sender = new MiiClientMessageSender(new TestChannel(){
			@Override
			public void send(MiiMessage msg) {
				Assert.assertEquals(command, msg.command());
				log.info(Hex.toHexString(msg.toBytes()));
				if(! ArrayUtils.isEmpty(expecteds)){
					Assert.assertArrayEquals(
							expecteds
							, msg.toBytes());
				}
			}
		});
		
		return sender;
	}
}
