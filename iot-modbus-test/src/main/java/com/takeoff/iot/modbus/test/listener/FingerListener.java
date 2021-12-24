package com.takeoff.iot.modbus.test.listener;

import com.takeoff.iot.modbus.netty.channel.MiiChannel;
import com.takeoff.iot.modbus.netty.data.MiiFingerData;
import com.takeoff.iot.modbus.netty.listener.MiiListener;
import com.takeoff.iot.modbus.netty.message.MiiMessage;

import com.takeoff.iot.modbus.serialport.data.FingerData;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 类功能说明：指静脉指令监听器<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Slf4j
@Component
public class FingerListener implements MiiListener {
    
	@Override
	public void receive(MiiChannel channel, MiiMessage message) {
		if(message.command() == MiiMessage.FINGER){
			log.info("监听到指静脉指令: "+ message.command());
            MiiFingerData data = (MiiFingerData) message.data();
            int fingerType = data.fingerType();
            log.info("指纹状态: "+ fingerType);
            int fingerId = data.fingerId();
            log.info("指纹特征码："+fingerId);
        }
	}

    @EventListener
    public void handleReceiveDataEvent(FingerData data) {
        log.info("监听到指静脉指令: "+ data.getCommand());
        log.info("指纹状态: "+ data.getFingerType());
        log.info("指纹特征码："+data.getFingerId());
    }
}