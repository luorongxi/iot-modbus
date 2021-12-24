package com.takeoff.iot.modbus.test.listener;

import com.takeoff.iot.modbus.netty.channel.MiiChannel;
import com.takeoff.iot.modbus.netty.data.MiiBarcodeData;
import com.takeoff.iot.modbus.netty.listener.MiiListener;
import com.takeoff.iot.modbus.netty.message.MiiMessage;

import com.takeoff.iot.modbus.serialport.data.BarCodeData;
import com.takeoff.iot.modbus.serialport.data.ReceiveDataEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 类功能说明：扫码指令监听器<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Slf4j
@Component
public class BarCodeListener implements MiiListener {
	
	@Override
	public void receive(MiiChannel channel, MiiMessage message) {
	    if(message.command() == MiiMessage.BARCODE){
	    	log.info("监听到扫码指令: "+ message.command());
	    	MiiBarcodeData data = (MiiBarcodeData) message.data();
            log.info("条形码: "+ data.content());
        }
	}

	@EventListener
	public void handleReceiveDataEvent(BarCodeData data) {
		log.info("监听到扫码指令: "+ data.getCommand());
		log.info("条形码: "+ data.getBarCode());
	}

}
