package com.takeoff.iot.modbus.client;

import com.takeoff.iot.modbus.netty.channel.MiiChannel;
import com.takeoff.iot.modbus.netty.listener.MiiListener;
import com.takeoff.iot.modbus.netty.message.MiiMessage;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Hex;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@Slf4j
public class ClientTest implements MiiListener {

	private static final String GROUPNAME = "1";
	private static MiiClient client;
	
	@BeforeClass
	public static void testStart() {
		client = new MiiClient(GROUPNAME);
		client.connect("127.0.0.1", 5000);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	@Before
	public void testListener(){
		for(int i = 1; i < 20; i++){
			client.addListener(i, this);
		}
	}

	@AfterClass
	public static void testStop() throws InterruptedException {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
		client.disconnect().sync();
	}
	
	@Test
	public void testUnlock() throws InterruptedException{
		client.sender().unlock(1, 1);
	}
	
	@Override
	public void receive(MiiChannel channel, MiiMessage message) {
		log.info(Hex.toHexString(message.toBytes()));
	}
	
}
