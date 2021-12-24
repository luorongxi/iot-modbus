package com.takeoff.iot.modbus.test.listener;

import com.takeoff.iot.modbus.netty.channel.MiiChannel;
import com.takeoff.iot.modbus.netty.data.MiiCardData;
import com.takeoff.iot.modbus.netty.listener.MiiListener;
import com.takeoff.iot.modbus.netty.message.MiiMessage;

import com.takeoff.iot.modbus.serialport.data.CardData;
import com.takeoff.iot.modbus.serialport.data.ReceiveDataEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 类功能说明：刷卡指令监听器<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Slf4j
@Component
public class CardListener implements MiiListener {
    
    @Override
	public void receive(MiiChannel channel, MiiMessage message) {
		if(message.command() == MiiMessage.CARD){
			log.info("监听到刷卡指令: "+ message.command());
			MiiCardData data = (MiiCardData) message.data();
			String cardCode = data.content();
            log.info("卡号: "+ cardCode);
		}
	}

	@EventListener
	public void handleReceiveDataEvent(CardData data) {
		log.info("监听到刷卡指令: "+ data.getCommand());
		log.info("卡号: "+ data.getCardCode());
	}

}
