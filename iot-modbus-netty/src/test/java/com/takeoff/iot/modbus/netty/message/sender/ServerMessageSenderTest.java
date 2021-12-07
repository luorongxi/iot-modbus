package com.takeoff.iot.modbus.netty.message.sender;

import com.takeoff.iot.modbus.netty.message.MiiMessage;
import org.apache.commons.lang3.ArrayUtils;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerMessageSenderTest {

	private static final String GROUPNAME = "1";
	
	@Test
	public void testUnlock() {
		ServerMessageSender sender = sender(MiiMessage.LOCK,
				new byte[]{(byte) 0x7E, (byte) 0x08, (byte) 0x00,
						0x03, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 
						0x01, 0x00, 0x02, 0x01, 
						(byte) 0x7F, (byte) 0xB0, (byte) 0x7F}
			);
		sender.unlock(GROUPNAME, 1, 1, 0, 2, 1);
	}
	
	private ServerMessageSender sender(int command, byte[] expecteds) {
		ServerMessageSender sender = new MiiServerMessageSender(new TestControlCentre(){

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
