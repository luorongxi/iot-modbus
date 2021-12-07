package com.takeoff.iot.modbus.test.listener;

import com.takeoff.iot.modbus.netty.channel.MiiChannel;
import com.takeoff.iot.modbus.netty.data.MiiBackLightData;
import com.takeoff.iot.modbus.netty.listener.MiiListener;
import com.takeoff.iot.modbus.netty.message.MiiMessage;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 类功能说明：背光灯指令监听器<br/>
 * 公司名称：takeoff开源 <br/>
 * 作者：luorongxi <br/>
 */
@Slf4j
@Component
public class BackLightListener implements MiiListener {
	
	@Override
	public void receive(MiiChannel channel, MiiMessage message) {
		if(message.command() == MiiMessage.BACKLIGHT){
			log.info("监听到背光灯指令: "+ message.command());
			MiiBackLightData data = (MiiBackLightData) message.data();
			log.info("背光灯状态码：: "+ data.statusCode());
		}
	}

}
