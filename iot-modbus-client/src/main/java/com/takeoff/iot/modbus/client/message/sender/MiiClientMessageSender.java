package com.takeoff.iot.modbus.client.message.sender;

import com.takeoff.iot.modbus.common.utils.CacheUtils;
import com.takeoff.iot.modbus.common.utils.JudgeEmptyUtils;
import org.bouncycastle.util.encoders.Hex;

import com.takeoff.iot.modbus.common.bytes.factory.MiiBytesCombinedFactory;
import com.takeoff.iot.modbus.common.bytes.factory.MiiBytesFactory;
import com.takeoff.iot.modbus.common.bytes.factory.MiiBytesFactorySubWrapper;
import com.takeoff.iot.modbus.common.bytes.factory.MiiSlotBytesFactory;
import com.takeoff.iot.modbus.common.bytes.factory.MiiStrings2BytesFactory;
import com.takeoff.iot.modbus.common.data.MiiData;
import com.takeoff.iot.modbus.common.message.MiiMessage;
import com.takeoff.iot.modbus.common.message.factory.MiiMessageFactory;
import com.takeoff.iot.modbus.common.message.factory.MiiOutMessageFactory;
import com.takeoff.iot.modbus.netty.channel.MiiChannel;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

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
	
	public MiiClientMessageSender(){

	}
	
	public MiiClientMessageSender(MiiChannel channel){
		CacheUtils.put(channel.name(), channel);
	}

	private <E> void sendMessage(MiiMessageFactory<E> factory, String ip, E... datas){
		MiiChannel channel = (MiiChannel) CacheUtils.get(ip);
		if(JudgeEmptyUtils.isEmpty(channel)){
			log.info("未找到对应的通讯连接："+ ip +"，下发指令失败");
		}else{
			MiiMessage message = factory.buildMessage(channel.name(), datas);
			log.info("待上报指令数据："+ Hex.toHexString(message.toBytes()));
			channel.send(message);
		}
	}

	@Override
	public void registerGroup(String ip, String deviceGroup) {
		sendMessage(REGISTERGROUP, ip,0, 0, 0, 0, deviceGroup);
	}
	
	@Override
	public void unlock(String ip, int device, int status) {
		sendMessage(SINGLE_LOCK, ip, MiiMessage.LOCK, device, MiiData.NULL, MiiData.NULL, status);
	}


}
