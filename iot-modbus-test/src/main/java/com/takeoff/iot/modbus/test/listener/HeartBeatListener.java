package com.takeoff.iot.modbus.test.listener;

import com.takeoff.iot.modbus.serialport.data.BackLightData;
import com.takeoff.iot.modbus.serialport.data.HeartBeatData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HeartBeatListener {

    @EventListener
    public void handleReceiveDataEvent(HeartBeatData data) {
        log.info("监听到心跳指令: "+ data.getCommand());
        log.info("设备号: "+ data.getDeviceGroup());
    }
}
