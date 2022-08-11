package com.takeoff.iot.modbus.test.config;

import com.takeoff.iot.modbus.serialport.service.SerialportService;
import com.takeoff.iot.modbus.test.properties.IotModbusSerialportProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 类功能说明：通讯协议客户端配置注册<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Slf4j
@Configuration
public class IotModbusSerialportConfig implements ApplicationRunner {

    @Resource
    private IotModbusSerialportProperties iotModbusSerialportProperties;

    @Resource
    private SerialportService serialportService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(iotModbusSerialportProperties.getOpen()){
            if(iotModbusSerialportProperties.getNetty()){
                serialportService.openComPort(iotModbusSerialportProperties.getPort(), iotModbusSerialportProperties.getBaudrate(), iotModbusSerialportProperties.getThread());
            }else{
                serialportService.openComPort(iotModbusSerialportProperties.getPort(), iotModbusSerialportProperties.getBaudrate(), iotModbusSerialportProperties.getTimeout(), iotModbusSerialportProperties.getThread(), iotModbusSerialportProperties.getSleepTime());
            }
        }
    }
}
