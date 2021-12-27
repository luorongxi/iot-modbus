package com.takeoff.iot.modbus.test.listener;

import com.takeoff.iot.modbus.common.data.MiiHumitureData;
import com.takeoff.iot.modbus.common.message.MiiMessage;
import com.takeoff.iot.modbus.netty.channel.MiiChannel;
import com.takeoff.iot.modbus.netty.listener.MiiListener;
import com.takeoff.iot.modbus.serialport.data.HumitureData;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 类功能说明：温湿度指令监听器<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Slf4j
@Component
public class HumitureListener implements MiiListener {

	@Override
	public void receive(MiiChannel channel, MiiMessage message) {
		if(message.command() == MiiMessage.HM){
			log.info("监听到温湿度指令: "+ message.command());
			MiiHumitureData data = (MiiHumitureData) message.data();
            log.info("设备号: "+ data.device());
			log.info("温度: "+ data.temperature()+",湿度："+data.humidity());
        }
	}

	@EventListener
	public void handleReceiveDataEvent(HumitureData data) {
		log.info("监听到温湿度指令: "+ data.getCommand());
		log.info("设备号: "+ data.getDevice());
		log.info("温度: "+ data.getTemperature()+",湿度："+data.getHumidity());
	}
}
