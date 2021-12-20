package com.takeoff.iot.modbus.test.config;

import com.takeoff.iot.modbus.client.MiiClient;
import com.takeoff.iot.modbus.netty.message.MiiMessage;
import com.takeoff.iot.modbus.test.listener.LockListener;
import com.takeoff.iot.modbus.test.properties.IotModbusClientProperties;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Slf4j
@Configuration
public class IotModbusClientConfig implements ApplicationRunner {

    @Resource
    private IotModbusClientProperties iotModbusClientProperties;

    @Resource
    private LockListener lockListener;

    @Getter
    private MiiClient miiClient;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(iotModbusClientProperties.getOpen()){
            miiClient = new MiiClient(iotModbusClientProperties.getDeviceGroup(), iotModbusClientProperties.getThread());
            miiClient.connect(iotModbusClientProperties.getIp(), iotModbusClientProperties.getPort());
            miiClient.addListener(MiiMessage.LOCK, lockListener);
            log.info("IOT通讯协议链接服务端，占用IP： " + iotModbusClientProperties.getIp() + ",链接端口:" + iotModbusClientProperties.getPort());
        }
    }
}
