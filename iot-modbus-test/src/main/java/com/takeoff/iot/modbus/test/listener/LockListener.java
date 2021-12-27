package com.takeoff.iot.modbus.test.listener;

import com.takeoff.iot.modbus.common.data.MiiLockData;
import com.takeoff.iot.modbus.common.message.MiiMessage;
import com.takeoff.iot.modbus.netty.channel.MiiChannel;
import com.takeoff.iot.modbus.netty.listener.MiiListener;
import com.takeoff.iot.modbus.serialport.data.LockData;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * 类功能说明：开门状态指令监听器<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Slf4j
@Component
public class LockListener implements MiiListener {
    
	@Override
	public void receive(MiiChannel channel, MiiMessage message) {
		if(message.command() == MiiMessage.LOCK){
			MiiLockData data = (MiiLockData) message.data();
			log.info("监听到单开门状态指令: "+ message.command());
			//判断是否为多锁
			if(data.list().size() == 1){
				//单锁处理逻辑
				log.info("已进入单锁处理流程，门锁状态: "+ JSON.toJSONString(data.list()));
			}else{
				//多锁处理逻辑
				log.info("已进入多锁处理流程，门锁状态: "+ JSON.toJSONString(data.list()));
			}
		}
	}

	@EventListener
	public void handleReceiveDataEvent(LockData data) {
		log.info("监听到单开门状态指令: "+ data.getCommand());
		log.info("设备号: "+ data.getDevice());
		//判断是否为多锁
		if(data.getList().size() == 1){
			//单锁处理逻辑
			log.info("已进入单锁处理流程，门锁状态: "+ JSON.toJSONString(data.getList()));
		}else{
			//多锁处理逻辑
			log.info("已进入多锁处理流程，门锁状态: "+ JSON.toJSONString(data.getList()));
		}
	}
}
