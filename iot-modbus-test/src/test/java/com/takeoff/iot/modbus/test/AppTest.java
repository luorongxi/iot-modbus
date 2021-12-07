package com.takeoff.iot.modbus.test;

import com.takeoff.iot.modbus.test.config.IotModbusNettyConfig;
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

    private static final String CABINET_GROUP_CODE = "1";

    private static final Integer CABINET_CODE = 1;

    @Resource
    private IotModbusNettyConfig iotModbusNettyConfig;

    @Test
    public void openLockTest(){
        iotModbusNettyConfig.getMiiServer().sender().unlock(CABINET_GROUP_CODE, CABINET_CODE);
    }
}
