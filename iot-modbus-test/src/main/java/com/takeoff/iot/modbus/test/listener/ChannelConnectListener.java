package com.takeoff.iot.modbus.test.listener;

import com.takeoff.iot.modbus.common.entity.ChannelConnectData;
import com.takeoff.iot.modbus.common.utils.JudgeEmptyUtils;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ChannelConnectListener {

	@EventListener
	public void handleReceiveDataEvent(ChannelConnectData data) {
		if(JudgeEmptyUtils.isEmpty(data.getDeviceConnect())){
			log.info("设备连接状态码："+data.getDeviceConnect()+" ---> "+data.getConnectMsg());
		}
	}
}
