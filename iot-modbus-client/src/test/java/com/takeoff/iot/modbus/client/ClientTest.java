package com.takeoff.iot.modbus.client;

import com.takeoff.iot.modbus.common.message.MiiMessage;
import com.takeoff.iot.modbus.netty.channel.MiiChannel;
import com.takeoff.iot.modbus.netty.listener.MiiListener;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Hex;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@Slf4j
public class ClientTest implements MiiListener {

	private static final String GROUPNAME = "1";
	private static final String IP = "127.0.0.1";
	private static MiiClient client;
	
	@BeforeClass
	public static void testStart() throws Exception {
		client = new MiiClient(GROUPNAME);
		client.connect(IP, 5000, 5000);
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
		client.disconnect(IP).sync();
	}
	
	@Test
	public void testUnlock() throws InterruptedException{
		client.sender().unlock(IP,1, 1);
	}
	
	@Override
	public void receive(MiiChannel channel, MiiMessage message) {
		log.info(Hex.toHexString(message.toBytes()));
	}
	
}
