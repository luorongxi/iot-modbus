package com.takeoff.iot.modbus.test.listener;

import com.takeoff.iot.modbus.netty.channel.MiiChannel;
import com.takeoff.iot.modbus.netty.data.MiiCardData;
import com.takeoff.iot.modbus.netty.listener.MiiListener;
import com.takeoff.iot.modbus.netty.message.MiiMessage;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 类功能说明：刷卡指令监听器<br/>
 * 公司名称：takeoff开源 <br/>
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

}
