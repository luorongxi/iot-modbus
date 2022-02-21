package com.takeoff.iot.modbus.test.controller;

import com.takeoff.iot.modbus.common.data.MiiData;
import com.takeoff.iot.modbus.common.entity.LcdData;
import com.takeoff.iot.modbus.serialport.service.SerialportSendService;
import com.takeoff.iot.modbus.test.config.IotModbusServerConfig;
import com.takeoff.iot.modbus.test.properties.IotModbusSerialportProperties;
import com.takeoff.iot.modbus.test.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 类功能说明：指令下发测试<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private IotModbusServerConfig iotModbusServerConfig;

    @Resource
    private IotModbusSerialportProperties iotModbusSerialportProperties;

    @Resource
    private SerialportSendService serialportSendService;

    /**
     * 发送控制单锁指令
     * @param deviceGroup
     * @param device
     * @return
     */
    @RequestMapping("/openlock/{deviceGroup}/{device}")
    public R openLock(@PathVariable("deviceGroup") String deviceGroup, @PathVariable("device") Integer device) {
        if(iotModbusSerialportProperties.getOpen()){
            serialportSendService.unlock(deviceGroup, device);
        }else{
            iotModbusServerConfig.getMiiServer().sender().unlock(deviceGroup, device);
        }
        return R.ok();
    }

    /**
     * 发送控制多开锁指令
     * @param map
     * @return
     */
    @RequestMapping("/openmultilock")
    public R openMultiLock(@RequestBody Map map) {
        if(iotModbusSerialportProperties.getOpen()){
            serialportSendService.unlock(
                    map.get("deviceGroup").toString(), Integer.valueOf(map.get("device").toString()),
                    Integer.valueOf(map.get("lockNo1").toString()), Integer.valueOf(map.get("lockStatus1").toString()),
                    Integer.valueOf(map.get("lockNo2").toString()), Integer.valueOf(map.get("lockStatus2").toString()));
        }else{
            iotModbusServerConfig.getMiiServer().sender().unlock(
                    map.get("deviceGroup").toString(), Integer.valueOf(map.get("device").toString()),
                    Integer.valueOf(map.get("lockNo1").toString()), Integer.valueOf(map.get("lockStatus1").toString()),
                    Integer.valueOf(map.get("lockNo2").toString()), Integer.valueOf(map.get("lockStatus2").toString()));
        }
        return R.ok();
    }

    /**
     * 发送设置扫码模式指令
     * @param deviceGroup
     * @param device
     * @return
     */
    @RequestMapping("/barcode/{deviceGroup}/{device}")
    public R barcode(@PathVariable("deviceGroup") String deviceGroup, @PathVariable("device") Integer device) {
        if(iotModbusSerialportProperties.getOpen()){
            serialportSendService.barcode(deviceGroup, device, MiiData.ONCE);
        }else{
            iotModbusServerConfig.getMiiServer().sender().backlight(deviceGroup, device, MiiData.ONCE);
        }
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
        if(iotModbusSerialportProperties.getOpen()){
            serialportSendService.backlight(deviceGroup, device, MiiData.ON);
        }else{
            iotModbusServerConfig.getMiiServer().sender().backlight(deviceGroup, device, MiiData.ON);
        }
        return R.ok();
    }

    /**
     * 发送背光灯指令
     * @param lcdDataList
     * @return
     */
    @RequestMapping("/lcdbatch")
    public R lcdBatch(@RequestBody List<LcdData> lcdDataList) {
        if(iotModbusSerialportProperties.getOpen()){
            serialportSendService.lcdBatch(lcdDataList);
        }else{
            iotModbusServerConfig.getMiiServer().sender().lcdBatch(lcdDataList);
        }
        return R.ok();
    }

}
