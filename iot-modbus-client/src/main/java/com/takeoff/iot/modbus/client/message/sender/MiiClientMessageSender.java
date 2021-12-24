package com.takeoff.iot.modbus.client.message.sender;

import com.takeoff.iot.modbus.netty.bytes.factory.*;
import com.takeoff.iot.modbus.netty.channel.MiiChannel;
import com.takeoff.iot.modbus.netty.data.base.MiiData;
import com.takeoff.iot.modbus.netty.message.MiiMessage;
import com.takeoff.iot.modbus.netty.message.factory.MiiMessageFactory;
import com.takeoff.iot.modbus.netty.message.factory.MiiOutMessageFactory;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Hex;

import java.util.List;

/**
 * 类功能说明：指令下发接口实现<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Slf4j
public class MiiClientMessageSender implements ClientMessageSender {

	private static final MiiBytesFactory<Integer> BYTESFACTORY_SLOT = new MiiSlotBytesFactory();

	private static final MiiMessageFactory<Integer> SINGLE_LOCK = new MiiOutMessageFactory<>(BYTESFACTORY_SLOT);

	private static final MiiMessageFactory<Object> REGISTERGROUP = new MiiOutMessageFactory<>(
			new MiiBytesCombinedFactory<Object>(
					new MiiBytesFactorySubWrapper<Integer, Object>(BYTESFACTORY_SLOT, 0, 4)
					,new MiiBytesFactorySubWrapper<String, Object>(new MiiStrings2BytesFactory(), 4, 5)
			));

	private MiiChannel channel;
	
	public MiiClientMessageSender(){
	}
	
	public MiiClientMessageSender(MiiChannel channel){
		this.channel = channel;
	}
	
	private <E> void sendMessage(MiiMessageFactory<E> factory, E... datas){
		MiiMessage message = factory.buildMessage(channel.name(), datas);
		log.info("待上报指令数据："+ Hex.toHexString(message.toBytes()));
		channel.send(message);
	}

	@Override
	public void registerGroup(String deviceGroup) {
		sendMessage(REGISTERGROUP, 0, 0, 0, 0, deviceGroup);
	}
	
	@Override
	public void unlock(int device, int status) {
		sendMessage(SINGLE_LOCK, MiiMessage.LOCK, device, MiiData.NULL, MiiData.NULL, status);
	}


}
