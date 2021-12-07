package com.takeoff.iot.modbus.test.controller;

import com.takeoff.iot.modbus.netty.data.base.MiiData;
import com.takeoff.iot.modbus.test.config.IotModbusNettyConfig;
import com.takeoff.iot.modbus.test.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 类功能说明：指令下发测试<br/>
 * 公司名称：takeoff开源 <br/>
 * 作者：luorongxi <br/>
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private IotModbusNettyConfig iotModbusNettyConfig;

    /**
     * 发送开锁指令
     * @param deviceGroup
     * @param device
     * @return
     */
    @RequestMapping("/openlock/{deviceGroup}/{device}")
    public R openLock(@PathVariable("deviceGroup") String deviceGroup, @PathVariable("device") Integer device) {
        iotModbusNettyConfig.getMiiServer().sender().unlock(deviceGroup, device);
        return R.ok();
    }

    /**
     * 发送多门开锁指令
     * @param map
     * @return
     */
    @RequestMapping("/openmultilock")
    public R openMultiLock(@RequestBody Map map) {
        iotModbusNettyConfig.getMiiServer().sender().unlock(
                map.get("deviceGroup").toString(), Integer.valueOf(map.get("device").toString()),
                Integer.valueOf(map.get("lockNo1").toString()), Integer.valueOf(map.get("lockStatus1").toString()),
                Integer.valueOf(map.get("lockNo2").toString()), Integer.valueOf(map.get("lockStatus2").toString())
                );
        return R.ok();
    }

    /**
     * 发送背光灯指令
     * @param deviceGroup
     * @param device
     * @return
     */
    @RequestMapping("/backlight/{deviceGroup}/{device}")
    public R backLight(@PathVariable("deviceGroup") String deviceGroup, @PathVariable("device") Integer device) {
        iotModbusNettyConfig.getMiiServer().sender().backlight(deviceGroup, device, MiiData.ON);
        return R.ok();
    }
}
