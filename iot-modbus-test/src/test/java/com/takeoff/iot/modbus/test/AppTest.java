package com.takeoff.iot.modbus.test;

import com.takeoff.iot.modbus.test.config.IotModbusServerConfig;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest extends TestCase {

    private static final String DEVICE_IP = "192.168.1.198";

    private static final Integer DEVICE = 1;

    @Resource
    private IotModbusServerConfig iotModbusServerConfig;

    @Test
    public void openLockTest(){
        iotModbusServerConfig.getMiiServer().sender().unlock(DEVICE_IP, DEVICE);
    }
}
